package DAO;

import java.util.List;

import Model.Message;

public interface MessageDAOInterface {
    Message insertMessage(Message userMessage);
    List <Message> getAllMessages();
    Message getMessageByMessageID(int message_id);
    Message deleteMessage(int message_id);
    Message updateMessage(String message_text, int message_id);
    List<Message> getAllMessagesGivenAccountID(int account_id);
}
