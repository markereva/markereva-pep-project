package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
public class SocialMediaController {
  MessageService messageService;
  AccountService accountService;

  public SocialMediaController() {
    this.messageService = new MessageService();
    this.accountService = new AccountService();
  }

  /**
   * In order for the test cases to work, you will need to write the endpoints in
   * the startAPI() method, as the test
   * suite must receive a Javalin object from this method.
   * 
   * @return a Javalin app object which defines the behavior of the Javalin
   *         controller.
   */
  public Javalin startAPI() {
    Javalin app = Javalin.create();
    app.post("register", this::postAccountHandler);
    app.post("login", this::postLoginHandler);
    app.get("messages", this::getMessagesHandler);
    app.post("messages", this::postMessageHandler);
    app.get("messages/{id}", this::getMessageById);
    app.delete("messages/{id}", this::deleteMessageById);
    app.patch("messages/{id}", this::patchMessageHandler);
    app.get("accounts/{id}/messages", this::getMessagesByAccountId);
    return app;
  }

  private void postAccountHandler(Context ctx) throws JsonProcessingException {
    Account account;
    try {
      account = ctx.bodyValidator(Account.class)
      .check(obj -> obj.getUsername().length() != 0, "username length cannot be 0")
      .check(obj -> obj.getUsername().length() <= 255, "username length cannot be longer than 255")
      .check(obj -> obj.getPassword().length() >= 4, "password length cannot less than 4")
      .check(obj -> obj.getUsername().length() <= 255, "password length cannot be longer than 255")
      .get();
    } catch (ValidationException e) {
      ctx.status(400);
      return;
    }
    
    Account addedAccount = accountService.createAccount(account);
    if (addedAccount != null) {
      ctx.json(addedAccount);
    } else {
      ctx.status(400);
    }
  }

  private void postLoginHandler(Context ctx) throws JsonProcessingException {
    Account account = ctx.bodyAsClass(Account.class);
    Account addedAccount = accountService.login(account);
    if (addedAccount != null) {
      ctx.json(addedAccount);
    } else {
      ctx.status(401);
    }
  }

  private void postMessageHandler(Context ctx) throws JsonProcessingException {
    Message message;
    try {
      message = ctx.bodyValidator(Message.class)
      .check(obj -> obj.getMessage_text().length() != 0, "message_text length cannot be 0")
      .check(obj -> obj.getMessage_text().length() <= 255, "message_text length cannot be longer than 255")
      .get();
    } catch (ValidationException e) {
      ctx.status(400);
      return;
    }

    Account account = accountService.getAccountById(message.getPosted_by());
    if (account == null) {
      ctx.status(400);
      return;
    }

    Message addedMessage = messageService.createMessage(message);
    if (addedMessage != null) {
      ctx.json(addedMessage);
    } else {
      ctx.status(401);
    }
  }

  private void getMessagesHandler(Context ctx) {
    List<Message> messages = messageService.getAllMessages();
    ctx.json(messages);
  }

  private void getMessageById(Context ctx) {
    int id;
    try {
      id = ctx.pathParamAsClass("id", Integer.class)
      .check(i -> i > 0, "message_id cannot be less than 1")
      .get();
    } catch (ValidationException e) {
      ctx.status(400);
      return;
    }

    Message message = messageService.getMessageById(id);
    ctx.json(message != null ? message : "");
  }

  private void deleteMessageById(Context ctx) {
    int id;
    try {
      id = ctx.pathParamAsClass("id", Integer.class)
      .check(i -> i > 0, "message_id cannot be less than 1")
      .get();
    } catch (ValidationException e) {
      ctx.status(400);
      return;
    }

    Message message = messageService.deleteMessageById(id);
    ctx.json(message != null ? message : "");
  }

  private void patchMessageHandler(Context ctx) throws JsonProcessingException {
    int id;
    try {
      id = ctx.pathParamAsClass("id", Integer.class)
      .check(i -> i > 0, "message_id cannot be less than 1")
      .get();
    } catch (ValidationException e) {
      ctx.status(400);
      return;
    }
    
    Message message;
    try {
      message = ctx.bodyValidator(Message.class)
      .check(obj -> obj.getMessage_text().length() != 0, "message_text length cannot be 0")
      .check(obj -> obj.getMessage_text().length() <= 255, "message_text length cannot be longer than 255")
      .get();
    } catch (ValidationException e) {
      ctx.status(400);
      return;
    }

    Message addedMessage = messageService.updateMessageById(id, message);
    if (addedMessage != null) {
      ctx.json(addedMessage);
    } else {
      ctx.status(400);
    }
  }

  private void getMessagesByAccountId(Context ctx) {
    int id;
    try{ 
      id = ctx.pathParamAsClass("id", Integer.class)
      .check(i -> i >= 1, "account_id cannot be less than 1")
      .get();
    } catch (ValidationException e) {
      ctx.status(400);
      return;
    }

    List<Message> messages = messageService.getAllMessagesByAccountId(id);
    ctx.json(messages != null ? messages : "");
  }

}