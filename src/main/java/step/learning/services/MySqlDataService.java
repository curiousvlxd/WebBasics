package step.learning.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlDataService implements DataService {
    private final String connectionString =
            "jdbc:mysql://localhost:3306/JavaSandbox" +
                    "?useUnicode=true&characterEncoding=UTF-8" +
                    "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC" ;
    private final String dbUser = "curiousvlxd";
    private final String dbPassword = "chad";
    private Connection connection;
    @Override
    public Connection getConnection() {
        if( connection == null ) {
            try {
                Class.forName( "com.mysql.cj.jdbc.Driver" ) ;
                connection = DriverManager.getConnection(
                        connectionString, dbUser, dbPassword
                ) ;
            }
            catch( Exception ex ) {
                System.out.println(
                        "MysqlDataService::getConnection " +
                                ex.getMessage() ) ;
            }
        }
        return connection ;
    }
}

