package step.learning.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.protobuf.Message;
import com.sun.source.tree.UsesTree;
import step.learning.dao.UserDAO;
import step.learning.entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import step.learning.services.MimeService;
import step.learning.services.GmailService;

import static java.lang.System.out;


@WebServlet("/register/")
@MultipartConfig
@Singleton
public class RegUserServlet extends HttpServlet {
    @Inject private UserDAO userDAO;
//    @Inject
//    public RegUserServlet(UserDAO userDAO) {
//        this.userDAO = userDAO;
//    }
    @Inject private MimeService mimeService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String reqError = (String) session.getAttribute("regError");
        if (reqError != null) {
            req.setAttribute("regError", reqError);
            session.removeAttribute("regError");
        }
        req.setAttribute( "pageBody", "reguser.jsp" ) ;
        req.getRequestDispatcher( "/WEB-INF/_layout.jsp" )
                .forward( req, resp ) ;
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userLogin = req.getParameter( "userLogin" ) ;
        String userPassword = req.getParameter( "userPassword" ) ;
        String userPassword2 = req.getParameter( "userPassword2" ) ;
        String userName = req.getParameter( "userName" ) ;
        String userEmail = req.getParameter( "userEmail" ) ;
        HttpSession session = req.getSession() ;
        try {
            if (userLogin == null || userLogin.isEmpty()) {
                throw new Exception("Login could not be empty");
            }
            if (!userPassword.equals(userPassword2)) {
                throw new Exception("Password and confirm password should match");
            }
            if (!userLogin.matches("^[a-zA-Z0-9]{3,8}$") && !userLogin.equals(userLogin.trim())) {
                throw new Exception("Login should be at least 3, at most 8 characters long, and should contain only numbers and letters");
            }
            if (!userPassword.matches("^[a-zA-Z0-9]{3,8}$")) {
                throw new Exception("Password should be at least 3, at most 8 characters long, and should contain only numbers and letters");
            }
            if (!userName.matches("^[a-zA-Z0-9 ]{3,25}$")) {
                throw new Exception("Name should be at least 3, at most 25 characters long, and should contain only numbers, letters, and spaces");
            }
            Part userAvatar = req.getPart("userAvatar");
            String avatarName = null;
            if (userAvatar.getSize() > 0) {
                String avatarFileName = userAvatar.getSubmittedFileName();
                String extension = FilenameUtils.getExtension(avatarFileName);
                if (extension == null || extension.isEmpty()) {
                    throw new Exception("Avatar file should have an extension");
                }
                if (!mimeService.isImage(extension)) {
                    throw new Exception("File type is not supported: " + extension);
                }
                avatarName = UUID.randomUUID().toString() + "." + extension;
                String path = req.getServletContext().getRealPath("/");
                File uploaded = new File(path + "/../Uploads/" + avatarName);
                Files.copy(userAvatar.getInputStream(), uploaded.toPath());
            }
            GmailService sm = new GmailService();
            String code = sm.getRandom();
            User user = new User();
            user.setLogin(userLogin);
            user.setPass(userPassword);
            user.setName(userName);
            user.setAvatar(avatarName);
            user.setEmail(userEmail);
            user.setCode(code);
//            boolean test = false;
//            for (int i = 0; i < 500; i++) {
//                test = sm.send(user, "Registration", "Your code is: " + code);
//            }
//            
//            if(test){
//                session.setAttribute("authcode", user);
//                resp.sendRedirect("verify.jsp");
//            }else{
//                out.println("Failed to send verification email");
//            }
            
            if (userDAO.add(user) == null) {
                throw new Exception( "Server error, try later" );
            }
        }
        catch (Exception e) {
            session.setAttribute( "regError", e.getMessage() ) ;
            resp.sendRedirect( req.getRequestURI() ) ;
            return ;
        }
        session.setAttribute( "regOk", "Registration successful" ) ;
        resp.sendRedirect(req.getRequestURI());
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPut(req, resp);
        String message = null;
        User user = (User) req.getAttribute("authUser");
        User changes = new User();
        changes.setName(req.getParameter("name"));
        changes.setId(user.getId());
        changes.setEmail(req.getParameter("email"));
        String login = req.getParameter("login");
        if (login != null && !login.isEmpty()) {
           if (!userDAO.isLoginFree(login)){
               message = "Login " + login + " is already taken";
               resp.getWriter().print(message);
               return;
           }
           changes.setLogin(login);
        }   
//        resp.getWriter().println("PUT works " + req.getParameter("name") + " " + user.getId());
        if (userDAO.updateUser(changes)){
            message = "OK";
        }
        else {
            message = "User not updated";
        }
        resp.getWriter().print(message);
    }
}
