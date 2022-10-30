package step.learning.servlets;

import com.google.inject.Singleton;
import step.learning.services.DataService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// @WebServlet("/filter")
@Singleton
public class FiltersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DataService dataService = (DataService) req.getAttribute( "DataService" ) ;
        List<String> randData = new ArrayList<>() ;
        try( Statement statement = dataService.getConnection().createStatement() ;
             ResultSet res = statement.executeQuery( "SELECT * FROM randoms" )
        ) {
            while( res.next() ) {
                randData.add( res.getLong( 1 ) + " " + res.getInt( 2 ) ) ;
            }
        }
        catch( SQLException ex ) {
            System.out.println( ex.getMessage() ) ;
        }
        req.setAttribute( "randData", randData.toArray( new String[0] ) ) ;
        req.getRequestDispatcher("WEB-INF/filters.jsp").forward(req,resp);
    }
}