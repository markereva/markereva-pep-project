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

  public Message deleteMessageById(Message message) {
    Message msg = messageDAO.getMessageById(message.getMessage_id());
    if (messageDAO.deleteMessageById(message.getMessage_id()))
      return msg;
    return null;
  }

  public Message updateMessageById(Message message) {
    Message msg = messageDAO.getMessageById(message.getMessage_id());
    if (messageDAO.updateMessageById(message.getMessage_id(), message.getMessage_text())) {
      msg.setMessage_text(message.getMessage_text());
      return msg;
    }
    return null;
  }

  public List<Message> getAllMessagesByAccountId(int id) {
    return messageDAO.getMessagesByAccountId(id);
  }
}
