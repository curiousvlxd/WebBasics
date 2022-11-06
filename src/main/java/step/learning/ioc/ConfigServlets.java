package step.learning.ioc;

import com.google.inject.servlet.ServletModule;
import step.learning.filters.*;
import step.learning.servlets.*;

public class ConfigServlets extends ServletModule {
    @Override
    protected void configureServlets() {
        // конфігурація, що раніше була у web-old.xml - фільтри та ...
        filter( "/*" ).through( CharsetFilter.class ) ;
        filter( "/*" ).through( DataFilter.class ) ;
        filter( "/*" ).through( AuthFilter.class ) ;
        filter( "/*" ).through( DemoFilter.class ) ;

        // ... та сервлети
        serve( "/filter" ).with( FiltersServlet.class ) ;
        serve( "/hello" ).with( HelloServlet.class ) ;
        serve( "/home/" ).with( HomeServlet.class ) ;
        serve("/register/").with( RegUserServlet.class ) ;
        serve("/image/*").with( ImageServlet.class ) ;
        serve("/profile/").with( ProfileServlet.class ) ;
        serve("/confirm/").with( ConfirmEmailServlet.class ) ;
        serve( "/" ).with( HomeServlet.class ) ;
    }
}