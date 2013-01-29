package eu.elderspaces.activities.services;

import eu.elderspaces.activities.TestServiceConfig;
import it.cybion.commons.AbstractJerseyRESTTestCase;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public abstract class BaseServiceTestCase extends AbstractJerseyRESTTestCase {

    protected BaseServiceTestCase() {
        super(AbstractJerseyRESTTestCase.PORT, TestServiceConfig.class.getName());
    }
}
