package step.learning.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import step.learning.dao.UserDAO;
import step.learning.entities.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class ConfirmEmailServlet extends HttpServlet {
    @Inject
    private UserDAO userDAO ;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId  = req.getParameter( "userid" ) ;
        String confirm = req.getParameter( "confirm" ) ;
        String view = null;

        if( confirm != null ) {     // Є передані дані
            User user =
                    userId == null
                            ? (User) req.getAttribute( "authUser" )  // дані від форми
                            : userDAO.getUserById( userId ) ;        // дані від е-листа
            // якщо user - null, то це помилка авторизації
            // якщо код - null, то пошта вже підтверджена
            // якщо код у БД не збігається з кодом confirm, то це помилка коду
            // якщо збігається, то слід внести зміни у БД, це теж може не вийти
            try {
                view = "confirm_email2.jsp";
                if( user == null )
                    throw new Exception( "Авторизацію не підтверджено" ) ;
                if( userDAO.isEmailConfirmed( user ) )
                    throw new Exception( "Пошту вже підтверджено" ) ;
                view = "confirm_email.jsp";
                if( ! user.getCode().equals( confirm ) )
                    throw new Exception( "Код підтвердження не прийнято" ) ;
                if( ! userDAO.confirmEmail( user ) )
                    throw new Exception( "Вибачте, сталася помилка" ) ;
                // Досягнення цього місця = відсутність помилок
                req.setAttribute( "confirmed", "Ok" ) ;
            }
            catch( Exception ex ) {
                req.setAttribute( "confirmed", ex.getMessage() ) ;
            }
        }
        req.setAttribute( "pageBody", view ) ;
        req.getRequestDispatcher( "/WEB-INF/_layout.jsp" ).forward( req, resp ) ;
    }
}