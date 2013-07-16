package eu.elderspaces.persistence;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.test.TestGraphDatabaseFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph;

import eu.elderspaces.persistence.helpers.Costants;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class SocialNetworkRepositoryTestCase {
    
    private static final Logger LOGGER = Logger.getLogger(SocialNetworkRepositoryTestCase.class);
    
    SocialNetworkRepository repository;
    Neo4jGraph graph;
    
    @BeforeClass
    public void setup() {
    
        final GraphDatabaseService graphService = new TestGraphDatabaseFactory()
                .newImpermanentDatabaseBuilder().newGraphDatabase();
        graph = new Neo4jGraph(graphService);
        repository = new BluePrintsSocialNetworkRepository(graph);
    }
    
    @AfterClass
    public void shutdown() {
    
        repository.shutdown();
    }
    
    @Test
    public void shouldTestActivityStreamUpdates() {
    
        repository.modifyProfileData("p1", new Date());
        repository.modifyProfileData("p2", new Date());
        repository.addNewFriend("p1", "p2", new Date());
        repository.createClub("p1", "c1", new Date());
        
    }
    
    @Test
    public void shouldTestQueries() {
    
        repository.modifyProfileData("pp1", new Date());
        repository.modifyProfileData("pp2", new Date());
        repository.modifyProfileData("pp3", new Date());
        repository.modifyProfileData("pp4", new Date());
        
        // test 1 friend
        Assert.assertEquals(0, repository.getFriends("pp1").size());
        repository.addNewFriend("pp1", "pp2", new Date());
        Assert.assertEquals(1, repository.getFriends("pp1").size());
        
        // test 1 club
        Assert.assertEquals(0, repository.getClubs("pp1").size());
        repository.createClub("pp1", "cc1", new Date());
        Assert.assertEquals(1, repository.getClubs("pp1").size());
        
        // assert 1 club join
        Assert.assertEquals(0, repository.getClubs("pp2").size());
        repository.joinClub("pp2", "cc1", new Date());
        Assert.assertEquals(1, repository.getClubs("pp2").size());
        
        repository.addNewFriend("pp2", "pp3", new Date());
        repository.addNewFriend("pp2", "pp4", new Date());
        
        printGraph(graph, Costants.HAS_FRIEND);
        
        final Set<String> ids = repository.getFriendsOfFriends("pp1");
        Assert.assertEquals(2, ids.size());
        LOGGER.debug("found " + ids.size() + " recommendations");
    }
    
    private void printGraph(final Graph graph, final String relation) {
    
        final Iterator<Vertex> it = graph.getVertices().iterator();
        while (it.hasNext()) {
            
            final Vertex v = it.next();
            System.out.println(v.getProperty(Costants.VERTEX_ID));
            final Iterator<Vertex> ite = v.getVertices(Direction.OUT, relation).iterator();
            while (ite.hasNext()) {
                System.out.println("    " + v.getProperty(Costants.VERTEX_ID) + " --> "
                        + ite.next().getProperty(Costants.VERTEX_ID));
            }
            System.out.println();
        }
    }
}
