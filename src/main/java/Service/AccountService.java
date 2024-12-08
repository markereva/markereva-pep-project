package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
  private AccountDAO accountDAO;

  public AccountService() {
    accountDAO = new AccountDAO();
  }

  public AccountService(AccountDAO accountDAO) {
    this.accountDAO = accountDAO;
  }

  public Account login(Account account) {
    return accountDAO.getUserByUsernameAndPassword(account.getUsername(), account.getPassword());
  }

  public Account createAccount(Account account) {
    int uLen = account.getUsername().length();
    int pLen = account.getPassword().length();
    boolean usernameInvalid = uLen < 4 || uLen > 255;
    boolean passwordInvalid = pLen < 4 || pLen > 255;
    if (usernameInvalid || passwordInvalid)
      return null;
    boolean success = accountDAO.insertAccount(account);
    if (success)
      return accountDAO.getUserByUsername(account.getUsername());
    return null;
  }
}
