package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
  private MessageDAO messageDAO;

  public MessageService() {
    messageDAO = new MessageDAO();
  }

  public MessageService(MessageDAO messageDAO) {
    this.messageDAO = messageDAO;
  }

  public Message createMessage(Message message) {
    return messageDAO.insertMessage(message);
  }

  public List<Message> getAllMessages() {
    return messageDAO.getAllMessages();
  }

  public Message getMessageById(int id) {
    return messageDAO.getMessageById(id);
  }

  public Message deleteMessageById(int id) {
    Message msg = messageDAO.getMessageById(id);
    if (messageDAO.deleteMessageById(id)) return msg;
    return null;
  }

  public Message updateMessageById(int id, Message message) {
    Message msg = messageDAO.getMessageById(id);
    if (messageDAO.updateMessageById(id, message.getMessage_text())) {
      msg.setMessage_text(message.getMessage_text());
      return msg;
    }
    return null;
  }

  public List<Message> getAllMessagesByAccountId(int id) {
    return messageDAO.getMessagesByAccountId(id);
  }
}
