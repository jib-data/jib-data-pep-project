package DAO;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;

public class AccountDAO implements AccountDAOInterface{


    
            @Override
            public Account insertAccount(Account userAccount) {               
                Connection connection = ConnectionUtil.getConnection();
                try {
                    String sqlQuery = "INSERT INTO account (username, password) VALUES (?,?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, userAccount.getUsername());
                    preparedStatement.setString(2, userAccount.getPassword());
                    preparedStatement.executeUpdate();
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

                    while (generatedKeys.next()) {                        
                        int generatedKey = (int) generatedKeys.getLong(1);
                        return new Account(generatedKey, userAccount.getUsername(), userAccount.getPassword());
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }            
            @Override
            public Account getAccount(Account userAccount) {
            Connection connection = ConnectionUtil.getConnection();
            try {
                String sqlQuery = "SELECT * FROM account WHERE username = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
                preparedStatement.setString(1, userAccount.getUsername());
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    return new Account(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
            }
}