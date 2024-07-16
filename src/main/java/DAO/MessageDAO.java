package DAO;

import java.util.List;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;



import Model.Message;

public class MessageDAO implements MessageDAOInterface {

    @Override
    public Message insertMessage(Message userMessage) {
        // create the connection.
        Connection connection = ConnectionUtil.getConnection();

        // sql query statement
        try {

            String sqlQuery = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, userMessage.getPosted_by());
            preparedStatement.setString(2, userMessage.getMessage_text());
            preparedStatement.setObject(3, userMessage.getTime_posted_epoch(), java.sql.Types.BIGINT);

            preparedStatement.executeUpdate();
            ResultSet generatedKey = preparedStatement.getGeneratedKeys();
            while (generatedKey.next()){
                int generated_message_id = (int) generatedKey.getLong(1);
                return new Message(generated_message_id, userMessage.getPosted_by(), userMessage.getMessage_text(), userMessage.getTime_posted_epoch());
            };


            
        } catch (SQLException e) {
            
            e.printStackTrace();
        }     



        return null;
    }

    @Override
    public List<Message> getAllMessages() {
        
        // instantiate a list
        List<Message> messageList = new ArrayList<>();
        // Create connection to datatbase
        Connection connection = ConnectionUtil.getConnection();
        try {
            // Write sql query
                    String sqlQuery = "SELECT * FROM message";
                    // write preparedStatement to get all messages
                    PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
                    // Execute query
                    ResultSet resultSet = preparedStatement.executeQuery();
                    // Extract results, create a a Message object, populate the fields of the object and add to list 
                    while(resultSet.next()){
                        Message message = new Message();
                        message.setMessage_id(resultSet.getInt(1));
                        message.setPosted_by(resultSet.getInt(2));
                        message.setMessage_text(resultSet.getString(3));
                        message.setTime_posted_epoch(resultSet.getLong(4));
                        messageList.add(message);
                    }
                    
        } catch(SQLException e){
            e.printStackTrace();
        }    

        //  Return list

        return messageList;
    }
    @Override
    public Message getMessageByMessageID(int message_id) {
       Connection connection = ConnectionUtil.getConnection();
       Message selectedMessage = new Message();

       try {
        String sqlQuery = "SELECT * FROM message WHERE message_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setInt(1, message_id);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){            
            selectedMessage.setMessage_id(resultSet.getInt(1));
            selectedMessage.setPosted_by(resultSet.getInt(2));
            selectedMessage.setMessage_text(resultSet.getString(3));
            selectedMessage.setTime_posted_epoch(resultSet.getLong(4));
        }

       } catch (SQLException e) {
        e.printStackTrace();
       }
        return selectedMessage;
    }

    @Override
    public Message deleteMessage(int message_id) {
        
        Connection connection = ConnectionUtil.getConnection();
        Message deletedMessage = getMessageByMessageID(message_id);

        try {
           
           String sqlQuery = "DELETE FROM message WHERE message_id = ?";           
           PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
           preparedStatement.setInt(1, message_id);
           preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deletedMessage;
    }

    @Override
    public Message updateMessage(String message_text, int message_id) {
        // Create connection
        Connection connection = ConnectionUtil.getConnection();        

        try {
            // SQL query
            String sqlQuery = "UPDATE message SET message_text = ? WHERE message_id = ?";

            // creating a preparedStatement            
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

            // Setting the parameters of the preparedStatement
            preparedStatement.setString(1, message_text);
            preparedStatement.setInt(2, message_id);

            // Executing the query
            int affected_rows = preparedStatement.executeUpdate();

            if (affected_rows > 0){
                System.out.println("Rows were affected: " + affected_rows);
            } else{
                System.out.println("No update was done");
            }
            Message updatedMessage = getMessageByMessageID(message_id);

            return updatedMessage;

            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    @Override
    public List<Message> getAllMessagesGivenAccountID(int account_id) {

        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        
        try {
            String sqlQuery = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, account_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Message message = new Message();
                message.setMessage_id(resultSet.getInt(1));
                message.setPosted_by(resultSet.getInt(2));
                message.setMessage_text(resultSet.getString(3));
                message.setTime_posted_epoch(resultSet.getLong(4));
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }
}
