package eu.elderspaces.persistence;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.testng.log4testng.Logger;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph;
import com.tinkerpop.gremlin.java.GremlinPipeline;

import eu.elderspaces.model.Event.InvitationAnswer;
import eu.elderspaces.persistence.helpers.Costants;
import eu.elderspaces.persistence.helpers.GraphHelper;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class BluePrintsSocialNetworkRepository implements SocialNetworkRepository {
    
    private static final Logger LOGGER = Logger.getLogger(BluePrintsSocialNetworkRepository.class);
    
    String socialNetworkRepository;
    
    Neo4jGraph graph;
    
    GraphHelper helper;
    
    public BluePrintsSocialNetworkRepository(String socialNetworkRepository) {
    
        this.socialNetworkRepository = socialNetworkRepository;
        
        this.graph = new Neo4jGraph(socialNetworkRepository);
        this.helper = new GraphHelper(graph);
        
    }
    
    public BluePrintsSocialNetworkRepository(Neo4jGraph graph) {
    
        this.graph = graph;
        this.helper = new GraphHelper(graph);
        
    }
    
    @Override
    public void shutdown() {
    
        graph.shutdown();
    }
    
    @Override
    public void addNewFriend(final String actorId, final String objectId, final Date eventTime) {
    
        try {
            final Vertex actorPersonVertex = helper.getOrCreatePerson(actorId, eventTime);
            final Vertex objectPersonVertex = helper.getOrCreatePerson(objectId, eventTime);
            
            Edge edge = actorPersonVertex.addEdge(Costants.HAS_FRIEND, objectPersonVertex);
            edge.setProperty(Costants.EDGE_DATE, eventTime.getTime());
            
            edge = objectPersonVertex.addEdge(Costants.HAS_FRIEND, actorPersonVertex);
            edge.setProperty(Costants.EDGE_DATE, eventTime.getTime());
            
            graph.commit();
        } catch (final Exception e) {
            LOGGER.error("Invalid addNewFriend request between id: " + actorId + " and id: "
                    + objectId);
        }
        
    }
    
    @Override
    public void deleteFriendConnection(final String actorId, final String objectId,
            final Date eventTime) {
    
        final Vertex actorPersonVertex = helper.getOrCreatePerson(actorId, eventTime);
        final Vertex objectPersonVertex = helper.getOrCreatePerson(objectId, eventTime);
        
        Iterator<Edge> iterator = actorPersonVertex.getEdges(Direction.OUT, Costants.HAS_FRIEND)
                .iterator();
        while (iterator.hasNext()) {
            final Edge edge = iterator.next();
            if (edge.getVertex(Direction.IN).getProperty(Costants.VERTEX_ID).equals(objectId)) {
                edge.remove();
                break;
            }
        }
        
        // inverse removal
        iterator = objectPersonVertex.getEdges(Direction.OUT, Costants.HAS_FRIEND).iterator();
        while (iterator.hasNext()) {
            final Edge edge = iterator.next();
            if (edge.getVertex(Direction.IN).getProperty(Costants.VERTEX_ID).equals(actorId)) {
                edge.remove();
                break;
            }
        }
        
        graph.commit();
        
    }
    
    @Override
    public void createNewUser(final String actorId, final Date eventTime) {
    
        helper.getOrCreatePerson(actorId, eventTime);
        
    }
    
    @Override
    public void deleteUser(final String actorId, final Date eventTime) {
    
        // remove all users's activities prior to removing it
        final Set<String> ids = getActivities(actorId);
        
        for (final String id : ids) {
            helper.removeActivity(id);
        }
        
        helper.removePerson(actorId);
        
    }
    
    @Override
    public void postActivity(final String actorId, final String objectId, final Date eventTime) {
    
        try {
            final Vertex personVertex = helper.getOrCreatePerson(actorId, eventTime);
            
            final Vertex activityVertex = helper.getOrCreateActivity(objectId, eventTime);
            
            final Edge edge = personVertex.addEdge(Costants.HAS_ACTIVITY, activityVertex);
            edge.setProperty(Costants.EDGE_DATE, eventTime.getTime());
            
            graph.commit();
        } catch (final Exception e) {
            LOGGER.error("Invalid postActivity request for user id: " + actorId
                    + " on activity id: " + objectId);
        }
    }
    
    @Override
    public void createClub(final String actorId, final String objectId, final Date eventTime) {
    
        try {
            final Vertex personVertex = helper.getOrCreatePerson(actorId, eventTime);
            
            final Vertex clubVertex = helper.getOrCreateClub(objectId, eventTime);
            
            Edge edge = personVertex.addEdge(Costants.HAS_CLUB, clubVertex);
            edge.setProperty(Costants.EDGE_DATE, eventTime.getTime());
            
            edge = clubVertex.addEdge(Costants.HAS_MEMBER, personVertex);
            edge.setProperty(Costants.EDGE_DATE, eventTime.getTime());
            
            graph.commit();
            
        } catch (final Exception e) {
            LOGGER.error("Invalid createClub request for user id: " + actorId + " on club id: "
                    + objectId);
        }
        
    }
    
    @Override
    public void deleteClub(final String actorId, final String objectId, final Date eventTime) {
    
        // remove all club activities prior to removing club.
        final Set<String> ids = getClubActivities(objectId);
        for (final String id : ids) {
            helper.removeActivity(id);
        }
        
        helper.removeClub(objectId);
        
    }
    
    @Override
    public void joinClub(final String actorId, final String objectId, final Date eventTime) {
    
        try {
            final Vertex personVertex = helper.getOrCreatePerson(actorId, eventTime);
            
            final Vertex clubVertex = helper.getOrCreateClub(objectId, eventTime);
            
            Edge edge = personVertex.addEdge(Costants.HAS_CLUB, clubVertex);
            edge.setProperty(Costants.EDGE_DATE, eventTime.getTime());
            
            edge = clubVertex.addEdge(Costants.HAS_MEMBER, personVertex);
            edge.setProperty(Costants.EDGE_DATE, eventTime.getTime());
            
            graph.commit();
            
        } catch (final Exception e) {
            LOGGER.error("Invalid joinClub request for user id: " + actorId + " on club id: "
                    + objectId);
        }
        
    }
    
    @Override
    public void postClubActivity(final String actorId, final String objectId,
            final String targetId, final Date eventTime) {
    
        try {
            final Vertex personVertex = helper.getOrCreatePerson(actorId, eventTime);
            final Vertex activityVertex = helper.getOrCreateActivity(objectId, eventTime);
            final Vertex clubVertex = helper.getOrCreateClub(targetId, eventTime);
            
            Edge edge = personVertex.addEdge(Costants.HAS_ACTIVITY, activityVertex);
            edge.setProperty(Costants.EDGE_DATE, eventTime.getTime());
            
            edge = clubVertex.addEdge(Costants.HAS_ACTIVITY, activityVertex);
            edge.setProperty(Costants.EDGE_DATE, eventTime.getTime());
            
            graph.commit();
            
        } catch (final Exception e) {
            LOGGER.error("Invalid postClubActivity request for user id: " + actorId
                    + " on club id: " + targetId + " with activity id: " + objectId);
        }
        
    }
    
    @Override
    public void createEvent(final String actorId, final String objectId, final Date eventTime) {
    
        try {
            final Vertex personVertex = helper.getOrCreatePerson(actorId, eventTime);
            
            final Vertex eventVertex = helper.getOrCreateEvent(objectId, eventTime);
            
            Edge edge = personVertex.addEdge(Costants.HAS_EVENT, eventVertex);
            edge.setProperty(Costants.EDGE_DATE, eventTime.getTime());
            
            edge = eventVertex.addEdge(Costants.HAS_MEMBER, personVertex);
            edge.setProperty(Costants.EDGE_DATE, eventTime.getTime());
            
            graph.commit();
            
        } catch (final Exception e) {
            LOGGER.error("Invalid createEvent request for user id: " + actorId + " on event id: "
                    + objectId);
        }
        
    }
    
    @Override
    public void deleteEvent(final String actorId, final String objectId, final Date eventTime) {
    
        // remove all event activities prior to remove the event
        final Set<String> ids = getEventActivities(objectId);
        for (final String id : ids) {
            helper.removeActivity(id);
        }
        
        helper.removeEvent(objectId);
        
    }
    
    @Override
    public void respondEvent(final String actorId, final String objectId,
            final InvitationAnswer answer, final Date eventTime) {
    
        if (answer.equals(InvitationAnswer.MAYBE) || answer.equals(InvitationAnswer.YES)) {
            try {
                final Vertex personVertex = helper.getOrCreatePerson(actorId, eventTime);
                
                final Vertex eventVertex = helper.getOrCreateEvent(objectId, eventTime);
                
                Edge edge = personVertex.addEdge(Costants.HAS_EVENT, eventVertex);
                edge.setProperty(Costants.EDGE_DATE, eventTime.getTime());
                
                edge = eventVertex.addEdge(Costants.HAS_MEMBER, personVertex);
                edge.setProperty(Costants.EDGE_DATE, eventTime.getTime());
                
                graph.commit();
                
            } catch (final Exception e) {
                LOGGER.error("Invalid respondEvent request for user id: " + actorId
                        + " on event id: " + objectId);
            }
        }
        
    }
    
    @Override
    public void postEventActivity(final String actorId, final String objectId,
            final String targetId, final Date eventTime) {
    
        try {
            final Vertex personVertex = helper.getOrCreatePerson(actorId, eventTime);
            final Vertex activityVertex = helper.getOrCreateActivity(objectId, eventTime);
            final Vertex eventVertex = helper.getOrCreateEvent(targetId, eventTime);
            
            Edge edge = personVertex.addEdge(Costants.HAS_ACTIVITY, activityVertex);
            edge.setProperty(Costants.EDGE_DATE, eventTime.getTime());
            
            edge = eventVertex.addEdge(Costants.HAS_ACTIVITY, activityVertex);
            edge.setProperty(Costants.EDGE_DATE, eventTime.getTime());
            
            graph.commit();
            
        } catch (final Exception e) {
            LOGGER.error("Invalid postEventActivity request for user id: " + actorId
                    + " on event id: " + targetId + " with activity id: " + objectId);
        }
        
    }
    
    @Override
    public void deleteActivity(final String actorId, final String objectId, final Date eventTime) {
    
        helper.removeActivity(objectId);
        
    }
    
    @Override
    public void leaveClub(final String actorId, final String objectId, final Date eventTime) {
    
        try {
            final Vertex personVertex = helper.getOrCreatePerson(actorId, eventTime);
            final Vertex clubVertex = helper.getOrCreateClub(objectId, eventTime);
            
            // remove person-club edge
            Iterator<Edge> iterator = personVertex.getEdges(Direction.OUT, Costants.HAS_CLUB)
                    .iterator();
            while (iterator.hasNext()) {
                final Edge edge = iterator.next();
                
                // iterate till we find the left club
                if (edge.getVertex(Direction.IN).getProperty(Costants.VERTEX_ID).equals(objectId)) {
                    graph.removeEdge(edge);
                    break;
                }
            }
            
            // remove club-person edge
            iterator = clubVertex.getEdges(Direction.OUT, Costants.HAS_MEMBER).iterator();
            while (iterator.hasNext()) {
                final Edge edge = iterator.next();
                
                // iterate till we find the unsubscribing member
                if (edge.getVertex(Direction.IN).getProperty(Costants.VERTEX_ID).equals(actorId)) {
                    graph.removeEdge(edge);
                    break;
                }
            }
            
            graph.commit();
            
        } catch (final Exception e) {
            LOGGER.error("Invalid leaveClub request for user id: " + actorId + " on club id: "
                    + objectId);
        }
        
    }
    
    @Override
    public void deleteClubActivity(final String actorId, final String objectId,
            final String target, final Date eventTime) {
    
        helper.removeActivity(objectId);
        
    }
    
    @Override
    public void deleteEventActivity(final String actorId, final String objectId,
            final String targetId, final Date eventTime) {
    
        helper.removeActivity(objectId);
        
    }
    
    // *****************************************************************************
    // QUERIES
    
    @Override
    public Set<String> getFriends(final String id) {
    
        List<String> ids = new ArrayList<String>();
        
        final Vertex vertex = helper.getOrCreatePerson(id, new Date());
        
        final GremlinPipeline pipe = new GremlinPipeline();
        ids = pipe.start(vertex).out(Costants.HAS_FRIEND).property(Costants.VERTEX_ID).toList();
        
        final Set<String> uniqueIds = new HashSet<String>();
        for (final String friendId : ids) {
            uniqueIds.add(friendId);
        }
        
        uniqueIds.remove(id);
        
        return uniqueIds;
    }
    
    @Override
    public Set<String> getClubs(final String id) {
    
        List<String> ids = new ArrayList<String>();
        
        final Vertex vertex = helper.getOrCreatePerson(id, new Date());
        
        final GremlinPipeline pipe = new GremlinPipeline();
        ids = pipe.start(vertex).out(Costants.HAS_CLUB).property(Costants.VERTEX_ID).toList();
        
        final Set<String> uniqueIds = new HashSet<String>();
        for (final String clubId : ids) {
            uniqueIds.add(clubId);
        }
        
        uniqueIds.remove(id);
        
        return uniqueIds;
    }
    
    @Override
    public Set<String> getEvents(final String id) {
    
        List<String> ids = new ArrayList<String>();
        
        final Vertex vertex = helper.getOrCreatePerson(id, new Date());
        
        final GremlinPipeline pipe = new GremlinPipeline();
        ids = pipe.start(vertex).out(Costants.HAS_EVENT).property(Costants.VERTEX_ID).toList();
        
        final Set<String> uniqueIds = new HashSet<String>();
        for (final String eventId : ids) {
            uniqueIds.add(eventId);
        }
        
        uniqueIds.remove(id);
        
        return uniqueIds;
    }
    
    @Override
    public Set<String> getActivities(final String id) {
    
        List<String> ids = new ArrayList<String>();
        
        final Vertex vertex = helper.getOrCreatePerson(id, new Date());
        
        final GremlinPipeline pipe = new GremlinPipeline();
        ids = pipe.start(vertex).out(Costants.HAS_ACTIVITY).property(Costants.VERTEX_ID).toList();
        
        final Set<String> uniqueIds = new HashSet<String>();
        for (final String activityId : ids) {
            uniqueIds.add(activityId);
        }
        
        uniqueIds.remove(id);
        
        return uniqueIds;
    }
    
    @Override
    public Set<String> getEventActivities(final String id) {
    
        List<String> ids = new ArrayList<String>();
        
        final Vertex vertex = helper.getOrCreateEvent(id, new Date());
        
        final GremlinPipeline pipe = new GremlinPipeline();
        ids = pipe.start(vertex).out(Costants.HAS_ACTIVITY).property(Costants.VERTEX_ID).toList();
        
        final Set<String> uniqueIds = new HashSet<String>();
        for (final String activityId : ids) {
            uniqueIds.add(activityId);
        }
        
        uniqueIds.remove(id);
        
        return uniqueIds;
    }
    
    @Override
    public Set<String> getClubActivities(final String id) {
    
        List<String> ids = new ArrayList<String>();
        
        final Vertex vertex = helper.getOrCreateClub(id, new Date());
        
        final GremlinPipeline pipe = new GremlinPipeline();
        ids = pipe.start(vertex).out(Costants.HAS_ACTIVITY).property(Costants.VERTEX_ID).toList();
        
        final Set<String> uniqueIds = new HashSet<String>();
        for (final String activityId : ids) {
            uniqueIds.add(activityId);
        }
        
        uniqueIds.remove(id);
        
        return uniqueIds;
    }
    
    @Override
    public Set<String> getEventMembers(final String id) {
    
        List<String> ids = new ArrayList<String>();
        
        final Vertex vertex = helper.getOrCreateEvent(id, new Date());
        
        final GremlinPipeline pipe = new GremlinPipeline();
        ids = pipe.start(vertex).out(Costants.HAS_MEMBER).property(Costants.VERTEX_ID).toList();
        
        final Set<String> uniqueIds = new HashSet<String>();
        for (final String memberId : ids) {
            uniqueIds.add(memberId);
        }
        
        uniqueIds.remove(id);
        
        return uniqueIds;
    }
    
    @Override
    public Set<String> getClubMembers(final String id) {
    
        List<String> ids = new ArrayList<String>();
        
        final Vertex vertex = helper.getOrCreateEvent(id, new Date());
        
        final GremlinPipeline pipe = new GremlinPipeline();
        ids = pipe.start(vertex).out(Costants.HAS_MEMBER).property(Costants.VERTEX_ID).toList();
        
        final Set<String> uniqueIds = new HashSet<String>();
        for (final String memberId : ids) {
            uniqueIds.add(memberId);
        }
        
        uniqueIds.remove(id);
        
        return uniqueIds;
    }
    
    @Override
    public Map<String, Double> getFriendsOfFriends(final String id) {
    
        final Vertex personVertex = helper.getOrCreatePerson(id, new Date());
        
        final List<List> test = new GremlinPipeline<Vertex, List<List>>().start(personVertex)
                .out(Costants.HAS_FRIEND).out(Costants.HAS_FRIEND).property(Costants.VERTEX_ID)
                .path().toList();
        
        // count common friends of each friend of a friend (foaf)
        final Map<String, Double> scores = new HashMap<String, Double>();
        for (final List<String> path : test) {
            if (!path.get(path.size() - 1).equals(id)) {
                final String foafId = path.get(path.size() - 1);
                Double score = scores.get(foafId);
                if (score == null) {
                    score = 1.0;
                } else {
                    score += 1.0;
                }
                
                scores.put(foafId, score);
            }
            
        }
        
        return scores;
    }
    
    @Override
    public Map<String, Double> getClubsOfFriends(final String id) {
    
        final Vertex personVertex = helper.getOrCreatePerson(id, new Date());
        
        final List<List> test = new GremlinPipeline<Vertex, List<List>>().start(personVertex)
                .out(Costants.HAS_FRIEND).out(Costants.HAS_CLUB).property(Costants.VERTEX_ID)
                .path().toList();
        
        // count common friends of each club of a friend (coaf)
        final Map<String, Double> scores = new HashMap<String, Double>();
        for (final List<String> path : test) {
            if (!path.get(path.size() - 1).equals(id)) {
                final String coafId = path.get(path.size() - 1);
                Double score = scores.get(coafId);
                if (score == null) {
                    score = 1.0;
                } else {
                    score += 1.0;
                }
                
                scores.put(coafId, score);
            }
            
        }
        
        return scores;
    }
    
    @Override
    public Map<String, Double> getEventsOfFriends(final String id) {
    
        final Vertex personVertex = helper.getOrCreatePerson(id, new Date());
        
        final List<List> test = new GremlinPipeline<Vertex, List<List>>().start(personVertex)
                .out(Costants.HAS_FRIEND).out(Costants.HAS_EVENT).property(Costants.VERTEX_ID)
                .path().toList();
        
        // count common friends of each event of a friend (eoaf)
        final Map<String, Double> scores = new HashMap<String, Double>();
        for (final List<String> path : test) {
            if (!path.get(path.size() - 1).equals(id)) {
                final String eoafId = path.get(path.size() - 1);
                Double score = scores.get(eoafId);
                if (score == null) {
                    score = 1.0;
                } else {
                    score += 1.0;
                }
                
                scores.put(eoafId, score);
            }
            
        }
        
        return scores;
    }
    
    @Override
    public void reset() throws Exception {
    
        shutdown();
        
        FileUtils.deleteDirectory(new File(socialNetworkRepository));
        
        File directoryFile = new File(socialNetworkRepository);
        if (!directoryFile.exists())
            directoryFile.mkdirs();
        
        this.graph = new Neo4jGraph(socialNetworkRepository);
        this.helper = new GraphHelper(graph);
        
    }
    
}
