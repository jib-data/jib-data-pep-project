package Service;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Account;
import Model.Message;

public class MessageService implements MessageServiceInterface {

    AccountDAO accountDAO;
    MessageDAO messageDAO;

    public MessageService(){
        this.accountDAO = new AccountDAO();
        this.messageDAO = new MessageDAO();
    }
    
    public MessageService (AccountDAO accountDAO){
        this.accountDAO = accountDAO;
        this.messageDAO = new MessageDAO();
    }
    public MessageService (MessageDAO messageDAO){
        this.accountDAO = new AccountDAO();
        this.messageDAO = messageDAO;
    }

    public MessageService (AccountDAO accountDAO, MessageDAO messageDAO){
        this.accountDAO = accountDAO;
        this.messageDAO = messageDAO;
    }

    @Override
    public Message createMessage(Message userMessage) {
       List<Account> accounts = accountDAO.getAllAccounts();
       for (Account account: accounts){
            if (userMessage.getPosted_by()== account.getAccount_id() &&
                userMessage.getMessage_text().length() > 0 &&
                userMessage.getMessage_text().length()< 255){  
                        
                    Message createdMessage;
                    createdMessage = messageDAO.insertMessage(userMessage);
                    return createdMessage;          
                
            } else {
                return null;
            }
       }
        return null;
    }
    @Override
    public List<Message> getAllMessages() {
        List<Message> allMessages;
        allMessages = messageDAO.getAllMessages();
        return allMessages;
    }
    @Override
    public Message getMessageByMessageID(int message_id) {
        List<Message> allMessages = messageDAO.getAllMessages(); 
        for (Message existingMessage: allMessages){
            if (message_id == existingMessage.getMessage_id()){
                Message  message = messageDAO.getMessageByMessageID(message_id);        
                return message;
            }
        }
        return null;
        
    }

    @Override
    public Message deleteMessageByID(int message_id) {
        List<Message> allMessages;
        allMessages = messageDAO.getAllMessages();
        for (Message message: allMessages){
            if (message.getMessage_id() == message_id){
                Message deletedMessage = messageDAO.deleteMessage(message_id);
                return deletedMessage;
            }
        }
        return null;
    }

    @Override
    public Message updateMessage(String message_text, int message_id) {
        List<Message> allMessages = messageDAO.getAllMessages();
        for (Message currentMessage: allMessages){
            if(currentMessage.getMessage_id() == message_id&&
                message_text.length() > 0 &&
                message_text.length() <= 255){
                    Message updatedMessage;
                    updatedMessage = messageDAO.updateMessage(message_text, message_id);
                    return updatedMessage;
                } 
        }
        return null;
    }
    @Override
    public List<Message> getAllMessagesWithID(int account_id) {
        List<Message> returnedMessages;
        returnedMessages = messageDAO.getAllMessagesGivenAccountID(account_id);
        return returnedMessages; 
    }
}
