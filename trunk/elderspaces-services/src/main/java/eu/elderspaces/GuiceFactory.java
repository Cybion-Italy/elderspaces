package eu.elderspaces;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class GuiceFactory {
    
    private static Injector inj = Guice.createInjector(new ProductionJerseyServletModule());
    
    public static Injector getInjector() {
    
        return inj;
    }
    
    public static Injector updateInjector() {
    
        inj = Guice.createInjector(new ProductionJerseyServletModule());
        return inj;
    }
}