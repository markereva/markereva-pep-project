package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.validation.Validator;

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
    app.get("example-endpoint", this::exampleHandler);
    app.post("register", this::postAccountHandler);
    app.post("login", this::postLoginHandler);
    app.get("messages", this::getMessagesHandler);
    app.post("messages", this::postMessageHandler);
    app.get("messages/{id}", this::getMessageById);
    app.delete("messages/{id}", this::deleteMessageById);
    app.patch("messages/{id}", this::patchMessageHandler);
    return app;
  }

  /**
   * This is an example handler for an example endpoint.
   * 
   * @param context The Javalin Context object manages information about both the
   *                HTTP request and response.
   */
  private void exampleHandler(Context context) {
    context.json("sample text");
  }

  private void postAccountHandler(Context ctx) throws JsonProcessingException {
    Account account = ctx.bodyAsClass(Account.class);
    int uLen = account.getUsername().length();
    int pLen = account.getPassword().length();
    boolean usernameInvalid = uLen == 0 || uLen > 255;
    boolean passwordInvalid = pLen < 4 || pLen > 255;
    if (usernameInvalid || passwordInvalid) {
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
    Message message = ctx.bodyAsClass(Message.class);
    int msgLen = message.getMessage_text().length();
    boolean messageInvalid = msgLen == 0 || msgLen > 255;
    if (messageInvalid) {
      ctx.status(400);
      return;
    }

    Account account = accountService.getUserById(message.getPosted_by());
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
    Validator<Integer> validator = ctx.pathParamAsClass("id", Integer.class);
    int id = validator.check(i -> i > 0, "message_id cannot be less than 1").get();

    if (validator.errors().size() != 0) {
      ctx.status(400);
      return;
    }

    Message message = messageService.getMessageById(id);
    ctx.json(message != null ? message : "");
  }

  private void deleteMessageById(Context ctx) {
    Validator<Integer> validator = ctx.pathParamAsClass("id", Integer.class);
    int id = validator.check(i -> i > 0, "message_id cannot be less than 1").get();

    if (validator.errors().size() != 0) {
      ctx.status(400);
      return;
    }

    Message message = messageService.deleteMessageById(id);
    ctx.json(message != null ? message : "");
  }

  private void patchMessageHandler(Context ctx) throws JsonProcessingException {
    Validator<Integer> validator = ctx.pathParamAsClass("id", Integer.class);
    int id = validator.check(i -> i > 0, "message_id cannot be less than 1").get();

    if (validator.errors().size() != 0) {
      ctx.status(400);
      return;
    }
    
    Message message = ctx.bodyAsClass(Message.class);
    int msgLen = message.getMessage_text().length();
    boolean messageInvalid = msgLen == 0 || msgLen > 255;
    if (messageInvalid) {
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

}