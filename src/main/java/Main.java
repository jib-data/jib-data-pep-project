import java.util.List;

import Controller.SocialMediaController;
import DAO.AccountDAO;
import Model.Account;
import Service.AccountService;
import io.javalin.Javalin;

/**
 * This class is provided with a main method to allow you to manually run and test your application. This class will not
 * affect your program in any way and you may write whatever code you like here.
 */
public class Main {
    public static void main(String[] args) {
        // SocialMediaController controller = new SocialMediaController();
        // Javalin app = controller.startAPI();
        // app.start(8080);
        Account dummyAccount = new Account("testuser1", "password");
        AccountService dummyAccountService = new AccountService();
        // Account registeredAccount = dummyAccountService.registerAccount(dummyAccount);
        // System.out.println(registeredAccount.toString()); 
        Account loggedInUser = dummyAccountService.loginAccount(dummyAccount);
        System.out.println(loggedInUser);
        // System.out.println(loggedInAccount);
        // AccountDAO accountDao = new AccountDAO();
        // List<Account> accounts = accountDao.getAllAccounts();
        
    }
}
