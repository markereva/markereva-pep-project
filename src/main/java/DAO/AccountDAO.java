package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {

  public boolean insertAccount(Account account) {
    String sql = "INSERT INTO Account (username, password) VALUES (?, ?)";
    Connection conn = ConnectionUtil.getConnection();

    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, account.getUsername());
      ps.setString(2, account.getPassword());
      boolean isSuccessful = ps.executeUpdate() == 1;
      return isSuccessful;
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return false;
  }

  public Account getUserByUsername(String username) {
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

  public Account getUserByUsernameAndPassword(String username, String password) {
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
