package step.learning.filters;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import step.learning.services.DataService;
import step.learning.services.MySqlDataService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;

@Singleton
public class DataFilter implements Filter {
    private FilterConfig filterConfig;
    @Inject
    private DataService dataService;
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        DataService dataService = new MySqlDataService();
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String localUrl = request.getServletPath();
        if ("/img/tomcat.jpg".equals(localUrl) || "/WEB-INF/static.jsp".equalsIgnoreCase(localUrl)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        Connection connection = dataService.getConnection();
        if (connection == null) {
            servletRequest.getRequestDispatcher("/WEB-INF/static.jsp").forward(servletRequest, servletResponse);
        }
        else {
            servletRequest.setAttribute("DataService", dataService);
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
    public void destroy() {
        filterConfig = null;
    }
}
