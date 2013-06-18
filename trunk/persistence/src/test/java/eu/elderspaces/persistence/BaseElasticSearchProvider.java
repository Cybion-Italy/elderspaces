package eu.elderspaces.persistence;

import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class BaseElasticSearchProvider {

    private Node node;

    private Client client;

    @BeforeClass
    public void setUpLocalEsInstance() {

        this.node = nodeBuilder().local(true).node();
        //TODO get node host and port
//        this.node.settings();
//        this.client = this.node.client();
    }

    @AfterClass
    public void tearDownEsInstance() {

//        this.client.close();
        this.node.close();
    }

}
