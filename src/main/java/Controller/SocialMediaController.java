package Controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

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
    ObjectMapper mapper = new ObjectMapper();
    Account Account = mapper.readValue(ctx.body(), Account.class);
    Account addedAccount = accountService.createAccount(Account);
    if (addedAccount != null) {
      ctx.json(mapper.writeValueAsString(addedAccount));
    } else {
      ctx.status(400);
    }
  }

  private void postLoginHandler(Context ctx) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    Account Account = mapper.readValue(ctx.body(), Account.class);
    Account addedAccount = accountService.login(Account);
    if (addedAccount != null) {
      ctx.json(mapper.writeValueAsString(addedAccount));
    } else {
      ctx.status(401);
    }
  }

}