package eu.elderspaces;

import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import eu.elderspaces.activities.services.ActivitiesService;
import eu.elderspaces.persistence.SocialNetworkRepository;
import eu.elderspaces.recommendations.services.RecommendationService;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class ServicesInjectionTestCase {
    
    private Injector injector;
    
    @Test
    public void testInjection() {
    
        final Injector injector = Guice.createInjector(new ProductionJerseyServletModule());
        
        final RecommendationService recommendationsService = injector
                .getInstance(RecommendationService.class);
        Assert.assertNotNull(recommendationsService);
        
        final ActivitiesService activitiesService = injector.getInstance(ActivitiesService.class);
        Assert.assertNotNull(activitiesService);
    }
    
    @AfterClass
    public void shutdown() {
    
        injector.getInstance(Node.class).stop();
        injector.getInstance(Client.class).close();
        injector.getInstance(SocialNetworkRepository.class).shutdown();
    }
}
