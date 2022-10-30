package step.learning.servlets;

import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// @WebServlet("/hello")
@Singleton
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // resp.getWriter().print( "<h1>Hello</h1>" ) ;
        HttpSession session = req.getSession() ;
        String userInput = (String) session.getAttribute( "userInput" ) ;
        req.setAttribute( "userInput", userInput ) ;
        if( userInput != null ) {
            session.removeAttribute( "userInput" ) ;
        }
        req.getRequestDispatcher( "WEB-INF/hello.jsp" )
                .forward( req, resp ) ;
        /*
        Д.З. Реалізувати передачу даних форми (з попереднього ДЗ)
        та їх відображення після відправлення. Використовувати
        Сервлети.
         */
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding( "UTF-8" ) ;
        String userInput = req.getParameter( "userInput" ) ;
        // validations
        // req.setAttribute( "userInput", userInput ) ;
        // req.getRequestDispatcher( "WEB-INF/hello.jsp" ).forward( req, resp ) ;
        HttpSession session = req.getSession() ;
        session.setAttribute( "userInput", userInput ) ;
        resp.sendRedirect( req.getRequestURI() ) ;
    }
}
/*
    Servlet - різновид Java-класів, що грають роль контроллерів.
    За аналогією з ASP їх можна назвати API-контроллерами.
    Сервлет створюється через розширення класу HttpServlet та
    реєструється одним з двох способів:
    - анотацією @WebServlet("/path") - простіше
    - директивами у web.xml - наочніше, контролює порядок створення
       і оброблення (у порядку оголошення)
 */