package DAO;
import java.util.List;

import Model.Account;

public interface AccountDAOInterface {
    Account insertAccount(Account userAccount);
    Account getAccount(Account userAccount);
    List<Account> getAllAccounts();
}
