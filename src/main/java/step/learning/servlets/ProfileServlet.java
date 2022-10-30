package step.learning.servlets;

import com.google.inject.Singleton;
import step.learning.entities.User;

import javax.servlet.http.HttpServlet;

@Singleton
public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws javax.servlet.ServletException, java.io.IOException {
        User authUser = (User) req.getSession().getAttribute("authUser");
        if(authUser == null) {
            req.setAttribute("pageBody", "profile401.jsp");
        }
        else {
            req.setAttribute("pageBody", "profile.jsp");
        }
        req.setAttribute("pageBody", "profile.jsp");
        req.getRequestDispatcher( "/WEB-INF/_layout.jsp" )
                .forward(req, resp) ;
    }
}
