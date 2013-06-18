package eu.elderspaces.persistence;

import org.elasticsearch.node.Node;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;
import static org.testng.Assert.assertNotNull;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class ElasticSearchNodeProvider {

    private Node node;

    @BeforeClass
    public void setUpLocalEsInstance() {

        this.node = nodeBuilder().node();
        assertNotNull(this.node);
    }

    @AfterClass
    public void tearDownEsInstance() {

        this.node.close();
    }

}
