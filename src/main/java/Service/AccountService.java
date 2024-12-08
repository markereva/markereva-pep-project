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

  public Account getAccountByUsername(String username) {
    return accountDAO.getAccountByUsername(username);
  }

  public Account getAccountById(int id) {
    return accountDAO.getAccountById(id);
  }

  public Account login(Account account) {
    return accountDAO.getAccountByUsernameAndPassword(account.getUsername(), account.getPassword());
  }

  public Account createAccount(Account account) {
    return accountDAO.insertAccount(account);
  }
}
