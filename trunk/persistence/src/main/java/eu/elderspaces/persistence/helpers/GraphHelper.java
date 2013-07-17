package eu.elderspaces.persistence.helpers;

import java.util.Date;
import java.util.NoSuchElementException;

import org.testng.log4testng.Logger;

import com.tinkerpop.blueprints.Index;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph;

import eu.elderspaces.persistence.BluePrintsSocialNetworkRepository;
import eu.elderspaces.persistence.exceptions.NonExistingUserException;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class GraphHelper {
    
    private static final Logger LOGGER = Logger.getLogger(BluePrintsSocialNetworkRepository.class);
    
    Neo4jGraph graph;
    Index<Vertex> vertexIndex;
    
    public GraphHelper(final Neo4jGraph graph) {
    
        super();
        this.graph = graph;
        
        try {
            if (!graph.getIndices().iterator().hasNext()) {
                this.vertexIndex = graph.createIndex(Costants.VERTEX_INDEX_NAME, Vertex.class);
            } else {
                this.vertexIndex = graph.getIndex(Costants.VERTEX_INDEX_NAME, Vertex.class);
            }
        } catch (final Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
    
    public Vertex getOrCreatePerson(final String id, final Date eventTime) {
    
        Vertex personVertex;
        
        try {
            personVertex = vertexIndex.get(Costants.VIK_PERSON, id).iterator().next();
        } catch (final NoSuchElementException e) {
            
            personVertex = graph.addVertex(null);
            personVertex.setProperty(Costants.VERTEX_ID, id);
            personVertex.setProperty(Costants.VERTEX_DATE, eventTime.getTime());
            
            vertexIndex.put(Costants.VIK_PERSON, id, personVertex);
            graph.commit();
        }
        
        return personVertex;
    }
    
    public Vertex getOrCreateEvent(final String id, final Date eventTime) {
    
        Vertex eventVertex;
        
        try {
            eventVertex = vertexIndex.get(Costants.VIK_EVENT, id).iterator().next();
        } catch (final NoSuchElementException e) {
            
            eventVertex = graph.addVertex(null);
            eventVertex.setProperty(Costants.VERTEX_ID, id);
            eventVertex.setProperty(Costants.VERTEX_DATE, eventTime.getTime());
            
            vertexIndex.put(Costants.VIK_EVENT, id, eventVertex);
            graph.commit();
        }
        
        return eventVertex;
    }
    
    public Vertex getOrCreateClub(final String id, final Date eventTime) {
    
        Vertex clubVertex;
        
        try {
            clubVertex = vertexIndex.get(Costants.VIK_CLUB, id).iterator().next();
        } catch (final NoSuchElementException e) {
            
            clubVertex = graph.addVertex(null);
            clubVertex.setProperty(Costants.VERTEX_ID, id);
            clubVertex.setProperty(Costants.VERTEX_DATE, eventTime.getTime());
            
            vertexIndex.put(Costants.VIK_CLUB, id, clubVertex);
            graph.commit();
        }
        
        return clubVertex;
    }
    
    public Vertex getOrCreateActivity(final String id, final Date eventTime) {
    
        Vertex activityVertex;
        
        try {
            activityVertex = vertexIndex.get(Costants.VIK_ACTIVITY, id).iterator().next();
        } catch (final NoSuchElementException e) {
            
            activityVertex = graph.addVertex(null);
            activityVertex.setProperty(Costants.VERTEX_ID, id);
            activityVertex.setProperty(Costants.VERTEX_DATE, eventTime.getTime());
            
            vertexIndex.put(Costants.VIK_ACTIVITY, id, activityVertex);
            graph.commit();
        }
        
        return activityVertex;
    }
    
    public void removePerson(final String id) {
    
        final Vertex vertex = getOrCreatePerson(id, new Date());
        vertexIndex.remove(Costants.VIK_PERSON, id, vertex);
        graph.removeVertex(vertex);
        graph.commit();
    }
    
    public void removeEvent(final String id) {
    
        final Vertex vertex = getOrCreateEvent(id, new Date());
        vertexIndex.remove(Costants.VIK_EVENT, id, vertex);
        graph.removeVertex(vertex);
        graph.commit();
    }
    
    public void removeClub(final String id) {
    
        final Vertex vertex = getOrCreateClub(id, new Date());
        vertexIndex.remove(Costants.VIK_CLUB, id, vertex);
        graph.removeVertex(vertex);
        graph.commit();
    }
    
    public void removeActivity(final String id) {
    
        final Vertex vertex = getOrCreateActivity(id, new Date());
        vertexIndex.remove(Costants.VIK_ACTIVITY, id, vertex);
        graph.removeVertex(vertex);
        graph.commit();
    }
    
    public Vertex getPerson(final String personId) throws NonExistingUserException {
    
        try {
            final Vertex vertex = vertexIndex.get(Costants.VIK_PERSON, personId).iterator().next();
            return vertex;
        } catch (final NoSuchElementException e) {
            throw new NonExistingUserException("Requested user " + personId + " does not exist");
        }
    }
    
}
