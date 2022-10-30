package step.learning.ioc;

import com.google.inject.AbstractModule;
import step.learning.services.DataService;
import step.learning.services.HashService;
import step.learning.services.MySqlDataService;
import step.learning.services.Sha1HashService;

public class ConfigModule extends AbstractModule {
    @Override
    protected void configure() {
        bind( DataService.class ).to( MySqlDataService.class ) ;
        bind(HashService.class).to(Sha1HashService.class);
    }
}