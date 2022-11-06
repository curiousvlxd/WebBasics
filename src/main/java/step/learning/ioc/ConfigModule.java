package step.learning.ioc;

import com.google.inject.AbstractModule;
import step.learning.services.*;

public class ConfigModule extends AbstractModule {
    @Override
    protected void configure() {
        bind( DataService.class ).to( MySqlDataService.class ) ;
        bind(HashService.class).to(Sha1HashService.class);
        bind(EmailService.class).to(GmailService.class);
    }
}