package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {

  public Account insertAccount(Account account) {
    String sql = "INSERT INTO Account (username, password) VALUES (?, ?)";
    Connection conn = ConnectionUtil.getConnection();

    try {
      PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, account.getUsername());
      ps.setString(2, account.getPassword());
      ps.executeUpdate();

      ResultSet rs = ps.getGeneratedKeys();
      rs.next();
      int account_id = rs.getInt("account_id");
      account.setAccount_id(account_id);
      return account;
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return null;
  }

  public Account getAccountByUsername(String username) {
    String sql = "SELECT * FROM Account WHERE username = ?";
    Connection conn = ConnectionUtil.getConnection();

    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, username);
      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        return new Account(
            rs.getInt("account_id"),
            rs.getString("username"),
            rs.getString("password"));
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return null;
  }

  public Account getAccountById(int id) {
    String sql = "SELECT * FROM Account WHERE account_id = ?";
    Connection conn = ConnectionUtil.getConnection();

    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, id);
      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        return new Account(
            rs.getInt("account_id"),
            rs.getString("username"),
            rs.getString("password"));
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return null;
  }

  public Account getAccountByUsernameAndPassword(String username, String password) {
    String sql = "SELECT * FROM Account WHERE username = ? AND password = ?";
    Connection conn = ConnectionUtil.getConnection();

    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, username);
      ps.setString(2, password);
      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        return new Account(
            rs.getInt("account_id"),
            rs.getString("username"),
            rs.getString("password"));
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return null;
  }
}
