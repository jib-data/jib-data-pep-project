package DAO;

import java.util.List;

import Model.Message;

public interface MessageDAOInterface {
    Message insertMessage(Message userMessage);
    List <Message> getAllMessages();
    Message getMessageByMessageID(int message_id);
    Message deleteMessage(int message_id);
    Message updateMessage(Message message);
    List<Message> getAllMessagesGivenAccountID(int account_id);
}
