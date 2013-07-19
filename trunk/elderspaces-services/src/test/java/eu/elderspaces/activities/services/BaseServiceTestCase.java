package eu.elderspaces.activities.services;

import it.cybion.commons.AbstractJerseyRESTTestCase;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public abstract class BaseServiceTestCase extends AbstractJerseyRESTTestCase {
    
    protected BaseServiceTestCase() {
        super(AbstractJerseyRESTTestCase.PORT, TestServiceConfig.class.getName());
    }
}
