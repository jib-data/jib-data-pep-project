package Service;

import java.util.List;

import Model.Message;

public interface MessageServiceInterface {
    Message createMessage(Message userMessage);
    List<Message> getAllMessages();
    Message getMessageByMessageID(int message_id);
    Message deleteMessageByID(int message_id);
    Message updateMessage(Message message);
    List<Message> getAllMessagesWithID(int account_id);
}
