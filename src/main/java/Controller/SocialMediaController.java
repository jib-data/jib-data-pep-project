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
        app.post("localhost:8080/register", this::userRegisterationHandler);
        app.post("localhost:8080/login", this::userLoginHandler);
        app.post("localhost:8080/messages", this::createMessageHandler);
        app.get("localhost:8080/messages", this::getAllMessageHandler);
        app.get("localhost:8080/messages/{message_id}", this::getMessageByMessageID);
        app.delete("localhost:8080/messages/{message_id}", this::deleteMessageByMessageID);
        app.patch("localhost:8080/messages/{message_id}", this::updateMEssageByMessageID);
        app.get("localhost:8080/accounts/{account_id}/messages", this::getMessageByMessageID);

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
                context.status(400);
            }

            Account loggedInAccount = accountService.loginAccount(receivedAccount);
            if (loggedInAccount != null){
                context.status(200);
                context.json(mapper.writeValueAsString(loggedInAccount));
            } else{
                context.status(400);
            }

        } catch (JsonProcessingException e) {
           context.status(400);
        }


    }
    private void createMessageHandler(Context context) throws JsonProcessingException{
        try {
            ObjectMapper mapper = new ObjectMapper();
            String messageString = context.body();
            Message receivedMessage = mapper.readValue(messageString, Message.class);
            if (receivedMessage.getMessage_text()== null){
                context.status(400);
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
        context.status();
        context.json(mapper.writeValueAsString(retrievedMessage));
    }

    private void deleteMessageByMessageID(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessageByID(message_id);
        context.status(200);
        context.json(mapper.writeValueAsString(deletedMessage));
    }

    private void updateMEssageByMessageID(Context context) throws JsonProcessingException{
        try {
            ObjectMapper mapper = new ObjectMapper();
            String receivedString = context.body();
            Message receivedMessage = mapper.readValue(receivedString, Message.class);
            if (receivedMessage.getMessage_text().length() == 0){
                context.status(400);
            }

            Message updatedMessage = messageService.updateMessage(receivedMessage);
            if (updatedMessage != null){
                context.status(400);
                context.json(mapper.writeValueAsString(updatedMessage));
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