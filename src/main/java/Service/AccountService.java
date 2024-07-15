package Service;

import DAO.AccountDAO;
import Model.Account;
import java.util.List;


public class AccountService implements AccountServiceInterface {
    AccountDAO accountDAO;

    public AccountService(){
        this.accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    @Override
    public Account registerAccount(Account userAccount) {
        List<Account> accounts = accountDAO.getAllAccounts();
        for (Account account: accounts){
            if (account.getUsername() == userAccount.getUsername()){
                System.out.println("Username already exists");
                return null;
            }
        }

        if (userAccount.getUsername().length() > 0 && userAccount.getPassword().length() >= 4){            
                Account newAccount;
                System.out.println("registerAccount method was called inside service");
                newAccount = accountDAO.insertAccount(userAccount);
                
                return newAccount;
            } 
        return null;
    }

    @Override
    public Account loginAccount(Account userAccount) {
        System.out.println("userAccount: " + userAccount.getUsername() + " " + userAccount.getPassword());
        List<Account> accounts = accountDAO.getAllAccounts();
        for (Account account: accounts){
            System.out.println(account.getUsername() + " " + account.getPassword());
            
            if (account.getPassword().equals(userAccount.getPassword())  && account.getPassword().equals(userAccount.getPassword())){
                Account user;
                user = accountDAO.getAccount(userAccount);
                System.out.println("User found");
                return user;
            } else {
                System.out.println("User doesn't exist");
            }
        }
        return null;
    }
}
