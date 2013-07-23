package eu.elderspaces;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class GuiceFactory {
    
    private static final Injector inj = Guice.createInjector(new ProductionJerseyServletModule());
    
    public static Injector getInjector() {
    
        return inj;
    }
}