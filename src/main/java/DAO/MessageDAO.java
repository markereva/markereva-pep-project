package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
  
  public List<Message> getAllMessages() {
    String sql = "SELECT * FROM Message";
    Connection conn = ConnectionUtil.getConnection();
    List<Message> list = new ArrayList<>();

    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ResultSet rs = ps.executeQuery();
      
      while (rs.next()) {
        Message message = new Message(
          rs.getInt("message_id"),
          rs.getInt("posted_by"),
          rs.getString("message_text"), 
          rs.getLong("time_posted_epoch")
        );
        list.add(message);
      }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }

    return list;
  }

  public List<Message> getMessagesByAccountId(int accountId) {
    String sql = "SELECT * FROM Message WHERE posted_by = ?";
    Connection conn = ConnectionUtil.getConnection();
    List<Message> list = new ArrayList<>();

    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, accountId);
      ResultSet rs = ps.executeQuery();
      
      while (rs.next()) {
        Message message = new Message(
          rs.getInt("message_id"),
          rs.getInt("posted_by"),
          rs.getString("message_text"), 
          rs.getLong("time_posted_epoch")
        );
        list.add(message);
      }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }

    return list;
  }

  public Message getMessageById(int id) {
    String sql = "SELECT * FROM Message WHERE id = ?";
    Connection conn = ConnectionUtil.getConnection();

    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, id);
      ResultSet rs = ps.executeQuery();
      
      if (rs.next()) {
        return new Message(
          rs.getInt("message_id"),
          rs.getInt("posted_by"),
          rs.getString("message_text"),
          rs.getLong("time_posted_epoch")
          );
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return null;
  }

  public Message insertMessage(Message message) {
    String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
    Connection conn = ConnectionUtil.getConnection();

    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, message.getPosted_by());
      ps.setString(2, message.getMessage_text());
      ps.setLong(3, message.getTime_posted_epoch());
      ps.executeUpdate();
      return message;
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    
    return null;
  }

  public boolean deleteMessageById(int id) {
    String sql = "DELETE FROM Message WHERE id = ?";
    Connection conn = ConnectionUtil.getConnection();

    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, id);

      if (ps.executeUpdate() == 1) return true;
      else return false;
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return false;
  }

  public boolean updateMessageById(int id, String text) {
    String sql = "UPDATE Message SET message_text = ? WHERE id = ?";
    Connection conn = ConnectionUtil.getConnection();

    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, text);
      ps.setInt(2, id);

      if (ps.executeUpdate() == 1) return true;
      else return false;
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return false;
  }
}
