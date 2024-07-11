package Service;

import Model.Account;

public interface AccountServiceInterface {
    Account registerAccount(Account userAccount);
    Account loginAccount(Account userAccount);
}
