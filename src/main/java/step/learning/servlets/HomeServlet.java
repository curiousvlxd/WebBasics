package step.learning.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import step.learning.entities.User;
import step.learning.services.EmailService;
import step.learning.services.GmailService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class HomeServlet extends HttpServlet {
    
    @Inject
    private EmailService emailService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        emailService.send(new User(), "Hello", "Hello, world!);
        req.setAttribute( "header", "header.jsp" ) ;
        req.getRequestDispatcher( "/WEB-INF/index.jsp" )
                .forward( req, resp ) ;
    }
}