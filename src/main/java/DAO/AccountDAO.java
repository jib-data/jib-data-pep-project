package DAO;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO implements AccountDAOInterface{


    
            @Override
            public Account insertAccount(Account userAccount) {               
                Connection connection = ConnectionUtil.getConnection();
                try {
                    String sqlQuery = "INSERT INTO account (username, password) VALUES (?,?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, userAccount.getUsername());
                    preparedStatement.setString(2, userAccount.getPassword());
                    int updateSuccess = preparedStatement.executeUpdate();
                    if (updateSuccess > 0){
                        System.out.println("Update was succesfull");
                    } else {
                        System.out.println("insert was not successful");
                    }
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
                    System.out.println("Result contains values");
                    return new Account(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
            }
            @Override
            public List<Account> getAllAccounts() {
            Connection connection = ConnectionUtil.getConnection();
            List<Account> accounts = new ArrayList<>();

            try {
                String sqlQuery = "SELECT * FROM account";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
                ResultSet resultSet =  preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Account account = new Account();
                    account.setAccount_id(resultSet.getInt(1));
                    account.setUsername(resultSet.getString(2));
                    account.setPassword(resultSet.getString(3));
                    accounts.add(account);
                    System.out.println("Account was added");
                }
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return accounts;
            }
}