package eu.elderspaces.persistence;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

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

import eu.elderspaces.model.Event.InvitationAnswer;
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
    
        // remember: empty graph contains at least a root vertex
        assertEquals(countVertices(), 1);
        LOGGER.debug("creating person p1...");
        repository.createNewUser("p1", new Date());
        assertEquals(countVertices(), 2);
        LOGGER.debug("OK");
        
        LOGGER.debug("creating another person p2...");
        repository.createNewUser("p2", new Date());
        assertEquals(countVertices(), 3);
        LOGGER.debug("OK");
        
        LOGGER.debug("p1-p2 creating friendship...");
        assertEquals(repository.getFriends("p1").size(), 0);
        repository.addNewFriend("p1", "p2", new Date());
        assertEquals(repository.getFriends("p1").size(), 1);
        LOGGER.debug("OK");
        
        LOGGER.debug("p1 creating club...");
        assertEquals(repository.getClubs("p1").size(), 0);
        repository.createClub("p1", "c1", new Date());
        assertEquals(countVertices(), 4);
        assertEquals(repository.getClubs("p1").size(), 1);
        LOGGER.debug("OK");
        
        LOGGER.debug("p1 creating activity...");
        assertEquals(repository.getActivities("p1").size(), 0);
        repository.postActivity("p1", "a1p1", new Date());
        assertEquals(countVertices(), 5);
        assertEquals(repository.getActivities("p1").size(), 1);
        LOGGER.debug("OK");
        
        LOGGER.debug("p2 joining club...");
        assertEquals(repository.getClubs("p2").size(), 0);
        repository.joinClub("p2", "c1", new Date());
        assertEquals(countVertices(), 5);
        assertEquals(repository.getClubs("p1").size(), 1);
        assertEquals(repository.getClubs("p2").size(), 1);
        LOGGER.debug("OK");
        
        LOGGER.debug("creating club activity for p1...");
        assertEquals(repository.getActivities("p1").size(), 1);
        repository.postClubActivity("p1", "a2p1", "c1", new Date());
        assertEquals(countVertices(), 6);
        assertEquals(repository.getActivities("p1").size(), 2);
        assertEquals(repository.getClubActivities("c1").size(), 1);
        LOGGER.debug("OK");
        
        LOGGER.debug("creating club activity for p2...");
        assertEquals(repository.getActivities("p2").size(), 0);
        repository.postClubActivity("p2", "a1p2", "c1", new Date());
        assertEquals(countVertices(), 7);
        assertEquals(repository.getActivities("p2").size(), 1);
        assertEquals(repository.getClubActivities("c1").size(), 2);
        LOGGER.debug("OK");
        
        LOGGER.debug("deleting p1 club activity...");
        assertEquals(repository.getActivities("p1").size(), 2);
        assertEquals(repository.getClubActivities("c1").size(), 2);
        repository.deleteClubActivity("p1", "a2p1", "c1", new Date());
        assertEquals(countVertices(), 6);
        assertEquals(repository.getActivities("p1").size(), 1);
        assertEquals(repository.getClubActivities("c1").size(), 1);
        LOGGER.debug("OK");
        
        LOGGER.debug("leaving club...");
        assertEquals(repository.getClubs("p1").size(), 1);
        repository.leaveClub("p1", "c1", new Date());
        assertEquals(countVertices(), 6);
        assertEquals(repository.getClubs("p1").size(), 0);
        LOGGER.debug("OK");
        
        LOGGER.debug("deleting club...");
        assertEquals(repository.getClubs("p1").size(), 0);
        assertEquals(repository.getClubs("p2").size(), 1);
        repository.deleteClub("p1", "c1", new Date());
        assertEquals(countVertices(), 4);
        assertEquals(repository.getClubs("p1").size(), 0);
        assertEquals(repository.getClubs("p2").size(), 0);
        LOGGER.debug("OK");
        
        LOGGER.debug("creating event e1...");
        assertEquals(repository.getEvents("p1").size(), 0);
        repository.createEvent("p1", "e1", new Date());
        assertEquals(countVertices(), 5);
        assertEquals(repository.getEvents("p1").size(), 1);
        LOGGER.debug("OK");
        
        LOGGER.debug("p2 responding event 'maybe'...");
        assertEquals(repository.getEvents("p2").size(), 0);
        repository.respondEvent("p2", "e1", InvitationAnswer.MAYBE, new Date());
        assertEquals(countVertices(), 5);
        assertEquals(repository.getEvents("p2").size(), 1);
        LOGGER.debug("OK");
        
        LOGGER.debug("p2 responding event 'yes'...");
        assertEquals(repository.getEvents("p2").size(), 1);
        repository.respondEvent("p2", "e1", InvitationAnswer.YES, new Date());
        assertEquals(countVertices(), 5);
        assertEquals(repository.getEvents("p2").size(), 1);
        LOGGER.debug("OK");
        
        LOGGER.debug("p2 responding event 'no'...");
        assertEquals(repository.getEvents("p2").size(), 1);
        repository.respondEvent("p2", "e1", InvitationAnswer.NO, new Date());
        assertEquals(countVertices(), 5);
        assertEquals(repository.getEvents("p2").size(), 1);
        LOGGER.debug("OK");
        
        LOGGER.debug("p1 posting event activity...");
        assertEquals(repository.getEventActivities("e1").size(), 0);
        assertEquals(repository.getActivities("p1").size(), 1);
        repository.postEventActivity("p1", "a3p1", "e1", new Date());
        assertEquals(countVertices(), 6);
        assertEquals(repository.getEventActivities("e1").size(), 1);
        assertEquals(repository.getActivities("p1").size(), 2);
        LOGGER.debug("OK");
        
        LOGGER.debug("p1 deleting event activity...");
        assertEquals(repository.getEventActivities("e1").size(), 1);
        assertEquals(repository.getActivities("p1").size(), 2);
        repository.deleteEventActivity("p1", "a3p1", "e1", new Date());
        assertEquals(countVertices(), 5);
        assertEquals(repository.getEventActivities("e1").size(), 0);
        assertEquals(repository.getActivities("p1").size(), 1);
        LOGGER.debug("OK");
        
        LOGGER.debug("p1 deleting event...");
        assertEquals(repository.getEvents("p1").size(), 1);
        assertEquals(repository.getEvents("p2").size(), 1);
        repository.deleteEvent("p1", "e1", new Date());
        assertEquals(countVertices(), 4);
        assertEquals(repository.getEvents("p1").size(), 0);
        assertEquals(repository.getEvents("p2").size(), 0);
        LOGGER.debug("OK");
        
        LOGGER.debug("p1 deleting activity a1...");
        assertEquals(repository.getActivities("p1").size(), 1);
        repository.deleteActivity("p1", "a1p1", new Date());
        assertEquals(countVertices(), 3);
        assertEquals(repository.getActivities("p1").size(), 0);
        LOGGER.debug("OK");
        
        LOGGER.debug("deleting p1-p2 friendiship connection...");
        assertEquals(repository.getFriends("p1").size(), 1);
        assertEquals(repository.getFriends("p2").size(), 1);
        repository.deleteFriendConnection("p1", "p2", new Date());
        assertEquals(countVertices(), 3);
        assertEquals(repository.getFriends("p1").size(), 0);
        assertEquals(repository.getFriends("p2").size(), 0);
        LOGGER.debug("OK");
        
        LOGGER.debug("deleting user p1...");
        repository.deleteUser("p1", new Date());
        assertEquals(countVertices(), 2);
        LOGGER.debug("OK");
        
        LOGGER.debug("deleting user p2...");
        repository.deleteUser("p2", new Date());
        assertEquals(countVertices(), 1);
        LOGGER.debug("OK");
    }
    
    @Test
    public void shouldTestQueries() {
    
        repository.createNewUser("pp1", new Date());
        repository.createNewUser("pp2", new Date());
        repository.createNewUser("pp3", new Date());
        repository.createNewUser("pp4", new Date());
        
        // test 1 friend
        assertEquals(0, repository.getFriends("pp1").size());
        repository.addNewFriend("pp1", "pp2", new Date());
        assertEquals(1, repository.getFriends("pp1").size());
        
        // test 1 club
        assertEquals(0, repository.getClubs("pp1").size());
        repository.createClub("pp1", "cc1", new Date());
        assertEquals(1, repository.getClubs("pp1").size());
        
        // assert 1 club join
        assertEquals(0, repository.getClubs("pp2").size());
        repository.joinClub("pp2", "cc1", new Date());
        assertEquals(1, repository.getClubs("pp2").size());
        
        repository.addNewFriend("pp2", "pp3", new Date());
        repository.addNewFriend("pp2", "pp4", new Date());
        
        repository.createClub("pp2", "cc1", new Date());
        repository.createEvent("pp2", "ee1", new Date());
        
        repository.addNewFriend("pp1", "pp5", new Date());
        repository.addNewFriend("pp5", "pp3", new Date());
        repository.addNewFriend("pp1", "pp6", new Date());
        repository.addNewFriend("pp6", "pp3", new Date());
        
        Map<String, Double> scores = repository.getFriendsOfFriends("pp1");
        
        assertEquals(scores.size(), 2);
        assertTrue(scores.containsKey("pp3"));
        assertTrue(scores.containsKey("pp4"));
        assertEquals(scores.get("pp3"), 3.0);
        assertEquals(scores.get("pp4"), 1.0);
        
        LOGGER.debug("found " + scores.size() + " friend recommendations");
        
        scores = repository.getClubsOfFriends("pp1");
        assertTrue(scores.containsKey("cc1"));
        assertEquals(scores.size(), 1);
        LOGGER.debug("found " + scores.size() + " club recommendations");
        
        scores = repository.getEventsOfFriends("pp1");
        assertTrue(scores.containsKey("ee1"));
        assertEquals(scores.size(), 1);
        LOGGER.debug("found " + scores.size() + " event recommendations");
    }
    
    private void printGraph(final Graph graph, final String relation) {
    
        final Iterator<Vertex> it = graph.getVertices().iterator();
        while (it.hasNext()) {
            
            final Vertex v = it.next();
            System.out.println(v.getProperty(Costants.VERTEX_ID));
            final Iterator<Vertex> ite = v.getVertices(Direction.OUT, relation).iterator();
            while (ite.hasNext()) {
                System.out.println("    " + v.getProperty(Costants.VERTEX_ID) + " " + relation
                        + " " + ite.next().getProperty(Costants.VERTEX_ID));
            }
            System.out.println();
        }
    }
    
    private int countVertices() {
    
        final Iterator<Vertex> iterator = graph.getVertices().iterator();
        int count = 0;
        
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return count;
    }
}
