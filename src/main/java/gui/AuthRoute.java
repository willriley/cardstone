package gui;

import java.util.Arrays;
import java.util.List;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateViewRoute;

/**
 * An interface for a route that checks if a user
 * is logged in before displaying itself.
 * @author Willay
 *
 */
public interface AuthRoute extends TemplateViewRoute {

  default ModelAndView handle(Request req, Response res) {
    List<String> cookies = Arrays.asList(req.cookie("id"),
        req.cookie("username"), req.cookie("tutorial"));

    if (!authorized(req.session(), cookies)) {
      res.redirect("/login");
    }
    return customHandle(req, res);
  }

  ModelAndView customHandle(Request req, Response res);

  default boolean authorized(Session s, List<String> cookies) {
    for (String cookie : cookies) {
      if (cookie == null) {
        return false;
      }
    }
    System.out.println(
        "Parsed id " + Integer.parseInt(cookies.get(0)) + " from cookies");
    boolean res = LoginHandler.validSession(Integer.parseInt(cookies.get(0)),
        s);
    System.out.println("Authorized? " + res);
    return res;
  }
}