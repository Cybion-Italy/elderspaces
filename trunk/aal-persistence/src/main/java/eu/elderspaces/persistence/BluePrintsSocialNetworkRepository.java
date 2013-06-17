package eu.elderspaces.persistence;

import java.util.Date;
import java.util.Iterator;

import org.testng.log4testng.Logger;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Index;
import com.tinkerpop.blueprints.TransactionalGraph.Conclusion;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph;

import eu.elderspaces.model.Activity;
import eu.elderspaces.model.Club;
import eu.elderspaces.model.Event;
import eu.elderspaces.model.Event.InvitationAnswer;
import eu.elderspaces.model.Person;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class BluePrintsSocialNetworkRepository implements SocialNetworkRepository {
    
    private static final Logger LOGGER = Logger.getLogger(BluePrintsSocialNetworkRepository.class);
    
    // ********************************************************************************
    // vertex index name
    private static final String VERTEX_INDEX_NAME = "vertex-index";
    
    // vertex index keys
    private static final String VIK_PERSON = "person";
    private static final String VIK_CLUB = "club";
    private static final String VIK_EVENT = "event";
    private static final String VIK_ACTIVITY = "activity";
    
    // properties
    private static final String VERTEX_ID = "id";
    private static final String VERTEX_DATE = "date";
    
    // ******************************************************************************
    // edge index name
    // private static final String EDGE_INDEX_NAME = "edge-index";
    //
    // // edge index keys
    // private static final String EIK_HAS_FRIEND = "has-friend";
    // private static final String EIK_HAS_EVENT = "has-event";
    // private static final String EIK_HAS_ACTIVITY = "has-activity";
    // private static final String EIK_HAS_CLUB = "has-club";
    // private static final String EIK_HAS_MEMBER = "has-member";
    //
    // // edges
    private static final String HAS_FRIEND = "has-friend";
    private static final String HAS_EVENT = "has-event";
    private static final String HAS_ACTIVITY = "has-activity";
    private static final String HAS_CLUB = "has-club";
    private static final String HAS_MEMBER = "has-member";
    
    // edge properties
    private static final String EDGE_DATE = "date";
    
    // ********************************************************************
    
    Neo4jGraph graph;
    Index<Vertex> vertexIndex;
    Index<Edge> edgeIndex;
    
    @Inject
    public BluePrintsSocialNetworkRepository(@Named("graphDir") final String graphDirectoryPath) {
    
        graph = new Neo4jGraph(graphDirectoryPath);
        
        try {
            if (!graph.getIndices().iterator().hasNext()) {
                this.vertexIndex = graph.createIndex(VERTEX_INDEX_NAME, Vertex.class);
            } else {
                this.vertexIndex = graph.getIndex(VERTEX_INDEX_NAME, Vertex.class);
            }
        } catch (final Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
    
    @Override
    public void addNewFriend(final Person actor, final Person object, final Date eventTime) {
    
        try {
            final Vertex actorPersonVertex = vertexIndex.get(VIK_PERSON, actor.getId()).iterator()
                    .next();
            final Vertex objectPersonVertex = vertexIndex.get(VIK_PERSON, object.getId())
                    .iterator().next();
            
            Edge edge = graph.addEdge(null, actorPersonVertex, objectPersonVertex, HAS_FRIEND);
            edge.setProperty(EDGE_DATE, eventTime.getTime());
            
            edge = graph.addEdge(null, objectPersonVertex, actorPersonVertex, HAS_FRIEND);
            edge.setProperty(EDGE_DATE, eventTime.getTime());
            
            graph.stopTransaction(Conclusion.SUCCESS);
        } catch (final Exception e) {
            LOGGER.error("Invalid addNewFriend request between id: " + actor.getId() + " and id: "
                    + object.getId());
        }
        
    }
    
    @Override
    public void deleteFriendConnection(final Person actor, final Person object, final Date eventTime) {
    
        final Vertex actorPersonVertex = vertexIndex.get(VIK_PERSON, actor.getId()).iterator()
                .next();
        final Vertex objectPersonVertex = vertexIndex.get(VIK_PERSON, object.getId()).iterator()
                .next();
        
        Iterator<Edge> iterator = actorPersonVertex.getEdges(Direction.OUT, HAS_FRIEND).iterator();
        while (iterator.hasNext()) {
            final Edge edge = iterator.next();
            if (edge.getVertex(Direction.IN).getId().equals(object.getId())) {
                edge.remove();
                break;
            }
        }
        
        // inverse removal
        iterator = objectPersonVertex.getEdges(Direction.OUT, HAS_FRIEND).iterator();
        while (iterator.hasNext()) {
            final Edge edge = iterator.next();
            if (edge.getVertex(Direction.IN).getId().equals(actor.getId())) {
                edge.remove();
                break;
            }
        }
        
        graph.stopTransaction(Conclusion.SUCCESS);
        
    }
    
    @Override
    public void modifyProfileData(final Person actor, final Date eventTime) {
    
        Vertex personVertex;
        
        try {
            personVertex = vertexIndex.get(VIK_PERSON, actor.getId()).iterator().next();
        } catch (final Exception e) {
            
            // this is a new user
            personVertex = graph.addVertex(null);
            personVertex.setProperty(VERTEX_ID, actor.getId());
            personVertex.setProperty(VERTEX_DATE, eventTime.getTime());
            
            vertexIndex.put(VIK_PERSON, actor.getId(), personVertex);
        }
        
    }
    
    @Override
    public void deleteUser(final Person actor, final Date eventTime) {
    
        try {
            final Vertex personVertex = vertexIndex.get(VIK_PERSON, actor.getId()).iterator()
                    .next();
            vertexIndex.remove(VIK_PERSON, actor.getId(), personVertex);
            graph.removeVertex(personVertex);
            graph.stopTransaction(Conclusion.SUCCESS);
            
        } catch (final Exception e) {
            LOGGER.error("Invalid deleteUser request on id: " + actor.getId());
        }
        
    }
    
    @Override
    public void postActivity(final Person actor, final Activity object, final Date eventTime) {
    
        // final Vertex actorPersonVertex = vertexIndex,.
        
    }
    
    @Override
    public void createClub(final Person actor, final Club object, final Date eventTime) {
    
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void modifyClub(final Person actor, final Club object, final Date eventTime) {
    
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void deleteClub(final Person actor, final Club object, final Date eventTime) {
    
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void joinClub(final Person actor, final Club object, final Date eventTime) {
    
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void postClubActivity(final Person actor, final Activity object, final Club target,
            final Date eventTime) {
    
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void createEvent(final Person actor, final Event object, final Date eventTime) {
    
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void modifyEvent(final Person actor, final Event object, final Date eventTime) {
    
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void deleteEvent(final Person actor, final Event object, final Date eventTime) {
    
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void respondEvent(final Person actor, final Event object, final InvitationAnswer answer,
            final Date eventTime) {
    
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void postEventActivity(final Person actor, final Activity object, final Event target,
            final Date eventTime) {
    
        // TODO Auto-generated method stub
        
    }
    
}
