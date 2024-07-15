package DAO;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO implements AccountDAOInterface{


    
            @Override
            public Account insertAccount(Account userAccount) {      
                System.out.println("insertAccount in DAO is called");         
                Connection connection = ConnectionUtil.getConnection();
                System.out.println("connection made");
                try {
                    System.out.println("In try");
                    String sqlQuery = "INSERT INTO account (username, password) VALUES (?,?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
                    System.out.println("preparedStatement written");
                    preparedStatement.setString(1, userAccount.getUsername());
                    preparedStatement.setString(2, userAccount.getPassword());
                    int updateSuccess = preparedStatement.executeUpdate();
                    System.out.println("preparedStatement Executed");
                    if (updateSuccess > 0){
                        System.out.println("Update was succesfull");
                    } else {
                        System.out.println("insert was not successful");
                    }
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    

                    while (generatedKeys.next()) {                        
                        int generatedKey = (int) generatedKeys.getLong(1);
                        Account registeredAccount;
                        registeredAccount = new Account(generatedKey, userAccount.getUsername(), userAccount.getPassword());
                        return registeredAccount;
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
                System.out.println("In getAccount Method in Account DAO");
                String sqlQuery = "SELECT * FROM account WHERE username = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
                preparedStatement.setString(1, userAccount.getUsername());
                ResultSet resultSet = preparedStatement.executeQuery();
                System.out.println("getAccountMethod was called");

                while (resultSet.next()) {
                    System.out.println("ResultSet contains values");
                    Account loggedInAccount = new Account(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
                    System.out.println(loggedInAccount.toString());
                    return loggedInAccount;
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
                    
                }
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("All counts were returned");
            return accounts;
            }
}