package step.learning.filters;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import step.learning.dao.UserDAO;
import step.learning.entities.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Singleton
public class AuthFilter implements Filter {
    private FilterConfig filterConfig ;
    private final UserDAO userDAO ;

    @Inject
    public AuthFilter( UserDAO userDAO ) {
        this.userDAO = userDAO ;
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig ;
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest  request  = (HttpServletRequest)  servletRequest ;
        HttpServletResponse response = (HttpServletResponse) servletResponse ;
        HttpSession session = request.getSession() ;
        // традиційно, перша перевірка - logout
        if( request.getParameter( "logout" ) != null ) {
            session.removeAttribute( "authUserId" ) ;
            response.sendRedirect( request.getContextPath() ) ;
            return ;
        }
        // другим кроком - автентифікація
        if( request.getMethod().equalsIgnoreCase( "POST" ) ) {
            if( "nav-auth-form".equals( request.getParameter( "form-id" ) ) ) {
                String userLogin = request.getParameter( "userLogin" ) ;
                String userPassword = request.getParameter( "userPassword" ) ;
                User user = userDAO.getUserByCredentials( userLogin, userPassword ) ;
                if( user == null ) {  // помилка авторизації
                    session.setAttribute( "authError", "Credentials incorrect" ) ;
                }
                else {  // успішна авторизація
                    session.setAttribute( "authUserId", user.getId() ) ;
                }
                response.sendRedirect( request.getRequestURI() ) ;
                return ;
            }
        }

        // Перевіряємо, чи є у сесії параметри від авторизації
        String authError = (String) session.getAttribute( "authError" ) ;
        if( authError != null ) {  // у сесії - помилка авторизації
            request.setAttribute( "authError", authError ) ;  // переносимо у request
            session.removeAttribute( "authError" ) ;  // видаляємо з сесії (однократний ефект)
        }
        String userId = (String) session.getAttribute( "authUserId" ) ;
        if( userId != null ) {  // ознака успішної попередньої автентифікації
            // у сесії зберігається userId, за ним відновлюємо дані
            request.setAttribute( "authUser", userDAO.getUserById( userId ) ) ;  // зберігаємо, не видаляємо з сесії
        }

        filterChain.doFilter( servletRequest, servletResponse ) ;
    }

    public void destroy() {
        this.filterConfig = null ;
    }
}