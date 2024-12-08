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

  public Account getUserByUsername(String username) {
    return accountDAO.getUserByUsername(username);
  }

  public Account getUserById(int id) {
    return accountDAO.getUserById(id);
  }

  public Account login(Account account) {
    return accountDAO.getUserByUsernameAndPassword(account.getUsername(), account.getPassword());
  }

  public Account createAccount(Account account) {
    boolean success = accountDAO.insertAccount(account);
    if (success) return accountDAO.getUserByUsername(account.getUsername());
    return null;
  }
}
