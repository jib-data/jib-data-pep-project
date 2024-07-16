import java.util.List;

import Controller.SocialMediaController;
import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
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

        MessageService dummyMessageService = new MessageService();

        Message updatedMessage = dummyMessageService.updateMessage("update again", 1);
        System.out.println(updatedMessage);
        
       
       
        
    }
}
