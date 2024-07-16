package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }


    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("register", this::userRegisterationHandler);
        app.post("login", this::userLoginHandler);
        app.post("messages", this::createMessageHandler);
        app.get("messages", this::getAllMessageHandler);
        app.get("messages/{message_id}", this::getMessageByMessageID);
        app.delete("messages/{message_id}", this::deleteMessageByMessageID);
        app.patch("messages/{message_id}", this::updateMessageByMessageID);
        app.get("accounts/{account_id}/messages", this::getMessageByAccountID);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    // private void exampleHandler(Context context) {
    //     context.json("sample text");
    // }

    private void userRegisterationHandler(Context context) throws JsonProcessingException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String accountString = context.body();
            Account receivedAccount = mapper.readValue(accountString, Account.class);
            if (receivedAccount.getPassword() == null && receivedAccount.getUsername() == null){
            context.status(400);
            return;
            }  
                
            Account createdAccount = accountService.registerAccount(receivedAccount);
            if (createdAccount != null){
                context.status(200);
                context.json(mapper.writeValueAsString(createdAccount));
            } else {
                context.status(400);
            }

        } catch (JsonProcessingException e) {
            context.status(400);
        }       
                
    }

    private void userLoginHandler(Context context) throws JsonProcessingException{
        try {
            ObjectMapper mapper = new ObjectMapper();
            String accountString = context.body();
            Account receivedAccount = mapper.readValue(accountString, Account.class);

            if (receivedAccount.getPassword() == null && receivedAccount.getUsername() == null){
                context.status(401);
                return;
            }

            Account loggedInAccount = accountService.loginAccount(receivedAccount);
            if (loggedInAccount != null){
                context.status(200);
                context.json(mapper.writeValueAsString(loggedInAccount));
            } else{
                context.status(401);
            }

        } catch (JsonProcessingException e) {
           context.status(401);
        }


    }
    private void createMessageHandler(Context context) throws JsonProcessingException{
        try {
            ObjectMapper mapper = new ObjectMapper();
            String messageString = context.body();
            Message receivedMessage = mapper.readValue(messageString, Message.class);
            if (receivedMessage.getMessage_text()== null){
                context.status(400);
                return;
            } 
            Message createdMessage = messageService.createMessage(receivedMessage);
            if (createdMessage != null){
                context.status(200);
                context.json(mapper.writeValueAsString(createdMessage));
            } else {
                context.status(400);
            }

        } catch (JsonProcessingException e) {
            context.status(400);
        }
    }

    private void getAllMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        context.json(mapper.writeValueAsString(messageService.getAllMessages())); 
    }

    private void getMessageByMessageID(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message retrievedMessage = messageService.getMessageByMessageID(message_id);
        if (retrievedMessage != null){
            context.status(200);
            context.json(mapper.writeValueAsString(retrievedMessage));
        } else {
            context.status(200);
            
        }
        
    }

    private void deleteMessageByMessageID(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessageByID(message_id);
        if (deletedMessage != null){
            context.status(200);
            context.json(mapper.writeValueAsString(deletedMessage));
        } else {
            context.status(200);
        }
       
    }

    private void updateMessageByMessageID(Context context) throws JsonProcessingException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            // Extracting message_id as String and parsing String version of message_id to int
            int receivedMessageID = Integer.parseInt(context.pathParam("message_id")); 

            // Extracting the body of the request in the form of String data type
            Message receivedMessage = mapper.readValue(context.body(), Message.class) ;
            String receivedMessageText = receivedMessage.getMessage_text();
            
            // Checking if receievedMessageText is not empty
            if (receivedMessageText.length() == 0){
                context.status(400);
                return;
            } 
            // Extracting updated Data if received message string is not empty
            Message updatedMessage = messageService.updateMessage(receivedMessageText, receivedMessageID);

            // If an updatedMessage is successfully returned status 200, send updated message as string, else status 400
            if (updatedMessage != null){
                context.status(200);
                context.json(mapper.writeValueAsString(updatedMessage));
            } else {
                context.status(400);
            }
            
        } catch (JsonProcessingException e) {
            context.status(400);            
        }              
    }

    private void getMessageByAccountID(Context context) throws JsonProcessingException{
        try {
            ObjectMapper mapper = new ObjectMapper();
            int account_id = Integer.parseInt(context.pathParam("account_id"));
            List <Message> retreivedMessages = messageService.getAllMessagesWithID(account_id);
            context.status(200);
            context.json(mapper.writeValueAsString(retreivedMessages));
        } catch (JsonProcessingException e) {
            context.status(200);
        }        
    }

}