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
                newAccount = accountDAO.insertAccount(userAccount);
                System.out.println("Account was added");
                return newAccount;
            } 
        return null;
    }

    @Override
    public Account loginAccount(Account userAccount) {
        List<Account> accounts = accountDAO.getAllAccounts();
        for (Account account: accounts){
            if (account.getPassword() == userAccount.getPassword() && account.getPassword() == userAccount.getPassword()){
                Account user;
                user = accountDAO.getAccount(userAccount);
                return user;
            }
        }
        return null;
    }
}
