package step.learning.ioc;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.servlet.GuiceServletContextListener;

// ContextListener - слухач події створення контексту, тобто передачі
// "роботи" від веб-сервера до нашого домену (програми)
@Singleton
public class ConfigListener extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(
                new ConfigServlets(),
                new ConfigModule()
        ) ;
    }
}