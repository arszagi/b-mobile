package ihm;

import bizz.BizzFactory;
import dto.UserDto;
import ucc.UserUcController;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import com.owlike.genson.reflect.VisibilityFilter;

import org.eclipse.jetty.http.HttpStatus;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Servlet extends HttpServlet {

  private final static String SECRET =
      "LICORNEkjcajn edea zfalzenf  faezfbalzbflf5f5eaz45 546 a4f5 af46 aezPONEY";

  private UserUcController userUcc = null;
  private BizzFactory bizzFactory = null;

  private UserDto userDtoRecept = null;
  private Genson genson = new GensonBuilder().useFields(true, VisibilityFilter.PRIVATE)
      .useMethods(false).exclude("mdp").create();

  public Servlet(UserUcController userUcc, BizzFactory bizzFactory) {
    this.userUcc = userUcc;
    this.bizzFactory = bizzFactory;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      String action = req.getParameter("action");
      HttpSession session = req.getSession();

      switch (action) {

        case "login":
          String username = req.getParameter("username");
          String password = req.getParameter("password");
          /*
           * UserDto userDtoSend = bizzFactory.getUserDto(); userDtoSend.setMdp(mdp);
           * userDtoSend.setPseudo(pseudo);
           */
          userDtoRecept = userUcc.login(username, password);

          if (userDtoRecept == null) {
            resp.setStatus(HttpStatus.FORBIDDEN_403);
          } else {
            session.setAttribute("username", userDtoRecept.getPseudo());
            createJwtCookie(resp, userDtoRecept.getPseudo());
            resp.setStatus(HttpStatus.ACCEPTED_202);
            resp.getWriter().println(dtoToJson(userDtoRecept));
          }
          break;

        case "authenticate":

          if (null != session.getAttribute("username")) {
            resp.setStatus(HttpStatus.ACCEPTED_202);
            resp.getWriter().println(dtoToJson(userDtoRecept));
          } else {
            if (readJwtCookie(req)) {
              resp.setStatus(HttpStatus.ACCEPTED_202);
              resp.getWriter().println(dtoToJson(userDtoRecept));
            } else {
              resp.setStatus(HttpStatus.FORBIDDEN_403);
            }
          }

          break;

        default:
          resp.setStatus(HttpStatus.BAD_REQUEST_400);
      }

    } catch (Exception exc) {
      exc.printStackTrace();
      resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR_500);
      resp.getWriter().println(exc.getMessage());
      resp.flushBuffer();
    }

  }

  /**
   * Lit le cookie JWT afin de verifier si l'utilisateur est authentifie.
   * 
   * @param req La requete envoyee par la page
   * @return true si l'utilisateur etait authentifie via JWT. false si il n'était pas authentifie.
   */
  private boolean readJwtCookie(HttpServletRequest req) {
    Map<String, Object> decodedPayload;
    try {
      Cookie[] cookies = req.getCookies();
      String token = null;
      for (Cookie c : cookies) {
        if ("user".equals(c.getName())) {
          token = c.getValue();
        }
      }
      decodedPayload = new JWTVerifier(SECRET).verify(token);
    } catch (InvalidKeyException | NoSuchAlgorithmException | IllegalStateException
        | SignatureException | IOException | JWTVerifyException | NullPointerException exc) {
      return false;
    }

    req.getSession().setAttribute("username", decodedPayload.get("username"));
    return true;
  }

  /**
   * Cree un cookie avec un token JWT afin de ne pas perdre l'authentification d'un utilisateur.
   * 
   * @param resp La reponse qui serra renvoyee par le serveur.
   * @param login Le pseudo de l'utilisateur.
   */
  private void createJwtCookie(HttpServletResponse resp, String login) {

    Map<String, Object> claims = new HashMap<String, Object>();
    claims.put("username", login);
    claims.put("id", "1");

    String token = new JWTSigner(SECRET).sign(claims);

    Cookie cookie = new Cookie("user", token);
    cookie.setPath("/");
    cookie.setMaxAge(60 * 60 * 24 * 365);
    resp.addCookie(cookie);
  }

  private String dtoToJson(UserDto dto) {
    return genson.serialize(dto);
  }

}
