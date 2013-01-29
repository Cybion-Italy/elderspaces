package eu.elderspaces.activities.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class ActivityServiceTestCase extends BaseServiceTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityServiceTestCase.class);

    @Test
    public void shouldBeGreen() {
        LOGGER.debug("it runs");
        assertTrue(true);



    }
}
