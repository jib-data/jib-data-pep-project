package DAO;
import Model.Account;

public interface AccountDAOInterface {
    Account insertAccount(Account userAccount);
    Account getAccount(Account userAccount);
}
