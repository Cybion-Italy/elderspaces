package eu.elderspaces.persistence;

import it.cybion.commons.exceptions.RepositoryException;

import java.util.Date;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.test.TestGraphDatabaseFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph;

import eu.elderspaces.model.Activity;
import eu.elderspaces.model.Club;
import eu.elderspaces.model.Event;
import eu.elderspaces.model.Event.InvitationAnswer;
import eu.elderspaces.model.Person;
import eu.elderspaces.persistence.exceptions.EnrichedEntitiesRepositoryException;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class EnrichedEntitiesRepositoryBuildTestCase {
    
    public static final Logger LOGGER = Logger.getLogger(EntitiesRepositoryTestCase.class);
    
    private EnrichedEntitiesRepository enrichedRepository;
    private EntitiesRepository entitiesRepository;
    private SocialNetworkRepository snRepository;
    
    private Person person1;
    private Person person2;
    private Person person3;
    private Club club1;
    private Club club2;
    private Club club3;
    private Event event1;
    private Event event2;
    private Event event3;
    private Activity activity1;
    private Activity activity2;
    private Activity activity3;
    
    @BeforeClass
    public void setup() {
    
        enrichedRepository = new LuceneEnrichedEntitiesRepository();
        entitiesRepository = new LuceneEntitiesRepository();
        
        final GraphDatabaseService graphService = new TestGraphDatabaseFactory()
                .newImpermanentDatabaseBuilder().newGraphDatabase();
        snRepository = new BluePrintsSocialNetworkRepository(new Neo4jGraph(graphService));
        
        initEntities();
        initEntitiesRepository();
        initSocialNetworkRepository();
        
    }
    
    @AfterClass
    public void shutdown() {
    
        enrichedRepository = null;
    }
    
    @Test
    public void shouldTestRepositoryBuilding() throws EnrichedEntitiesRepositoryException,
            RepositoryException {
    
        enrichedRepository.buildEnrichedEntities(entitiesRepository, snRepository);
        
        int entities = ((LuceneEntitiesRepository) entitiesRepository).countDocuments();
        Assert.assertEquals(12, entities);
        
        entities = ((LuceneEnrichedEntitiesRepository) enrichedRepository).countDocuments();
        Assert.assertEquals(9, entities);
    }
    
    private void initEntities() {
    
        person1 = new Person();
        person1.setAboutMe("happy traveller");
        person1.setActivities("eating drinking".split(" "));
        person1.setBooks("foundation ".split(" "));
        person1.setDisplayName("dname");
        person1.setFriendsCount(100);
        person1.setGender("M");
        person1.setId("p1");
        person1.setInterests("music movies go-carts car racing formula 1".split(" "));
        person1.setLanguagesSpoken("en it".split(" "));
        person1.setMovies("star wars".split(" "));
        person1.setMusic("indie rock".split(" "));
        person1.setName("name");
        person1.setPets("furret raccoon");
        person1.setThumbnailUrl("http://babsjdaslkd");
        person1.setTvShows("lost big bang theory".split(" "));
        
        person2 = new Person();
        person2.setAboutMe("driver");
        person2.setActivities("driving cars and rally".split(" "));
        person2.setBooks("world championships ".split(" "));
        person2.setDisplayName("formula master");
        person2.setFriendsCount(100);
        person2.setGender("M");
        person2.setId("p2");
        person2.setInterests("formula 1".split(" "));
        person2.setLanguagesSpoken("el en".split(" "));
        person2.setMovies("world war z".split(" "));
        person2.setMusic("rock".split(" "));
        person2.setName("name");
        person2.setPets("dog");
        person2.setThumbnailUrl("http://babsjdaslkd");
        person2.setTvShows("".split(" "));
        
        person3 = new Person();
        person3.setAboutMe("cooker");
        person3.setActivities("cook drinking".split(" "));
        person3.setBooks("all my cookies ".split(" "));
        person3.setDisplayName("dname");
        person3.setFriendsCount(100);
        person3.setGender("F");
        person3.setId("p3");
        person3.setInterests("music".split(" "));
        person3.setLanguagesSpoken("it".split(" "));
        person3.setMovies("mans and womens".split(" "));
        person3.setMusic("neomelodic".split(" "));
        person3.setName("Assunta");
        person3.setPets("cane");
        person3.setThumbnailUrl("http://babsjdaslkd");
        person3.setTvShows("big bang theory".split(" "));
        
        activity1 = new Activity();
        activity1.setBody("going to the mountains to drive my go kart");
        activity1.setId("a1");
        activity1.setTitle("wohoo");
        
        activity2 = new Activity();
        activity2.setBody("city drifting with my golf");
        activity2.setId("a2");
        activity2.setTitle("ooops, almost killed a granny");
        
        activity3 = new Activity();
        activity3.setBody("aggia cucina p'a famija");
        activity3.setId("a3");
        activity3.setTitle("san gennare aiutame te");
        
        event1 = new Event();
        event1.setCategory("hobby");
        event1.setDescription("city sightseeing from the mountains");
        event1.setEndDate(new Date(new Date().getTime() + 7200000));
        event1.setId("e1");
        event1.setName("good time friends");
        event1.setShortDescription("city view from mountains");
        event1.setStartDate(new Date());
        
        event2 = new Event();
        event2.setCategory("hobby");
        event2.setDescription("race with me at the park");
        event2.setEndDate(new Date(new Date().getTime() + 7200000));
        event2.setId("e2");
        event2.setName("callenge");
        event2.setShortDescription("challenge me with your car");
        event2.setStartDate(new Date());
        
        event3 = new Event();
        event3.setCategory("compleanno");
        event3.setDescription("purtate i piciriddi ca si mangia buooon. vabbuò?");
        event3.setEndDate(new Date(new Date().getTime() + 7200000));
        event3.setId("e3");
        event3.setName("compleanno Ciro, detto o nipote");
        event3.setShortDescription("compleanno di ciro");
        event3.setStartDate(new Date());
        
        club1 = new Club();
        club1.setCategory("karts");
        club1.setDescription("go karts lovers");
        club1.setId("c1");
        club1.setName("kart kart kart");
        club1.setShortDescription("club for go kart lovers. share your experience here");
        
        club2 = new Club();
        club2.setCategory("racing");
        club2.setDescription("cars, go karts and formula 1 lovers. alltogether");
        club2.setId("c2");
        club2.setName("speed lovers");
        club2.setShortDescription("speed lovers and drifters too");
        
        club3 = new Club();
        club3.setCategory("casalinghe");
        club3.setDescription("casalinghe disperate napoletane");
        club3.setId("c3");
        club3.setName("casalinghe disperate");
        club3.setShortDescription("casalinghe disperate");
        
    }
    
    private void initSocialNetworkRepository() {
    
        snRepository.createNewUser(person1.getId(), new Date());
        snRepository.createNewUser(person2.getId(), new Date());
        snRepository.createNewUser(person3.getId(), new Date());
        
        snRepository.addNewFriend(person1.getId(), person2.getId(), new Date());
        
        snRepository.createEvent(person1.getId(), event1.getId(), new Date());
        snRepository.createEvent(person2.getId(), event2.getId(), new Date());
        snRepository.createEvent(person3.getId(), event3.getId(), new Date());
        
        snRepository
                .respondEvent(person2.getId(), event1.getId(), InvitationAnswer.YES, new Date());
        
        snRepository.createClub(person1.getId(), club1.getId(), new Date());
        snRepository.createClub(person3.getId(), club3.getId(), new Date());
        snRepository.createClub(person3.getId(), club2.getId(), new Date());
        snRepository.joinClub(person2.getId(), club1.getId(), new Date());
        snRepository.joinClub(person2.getId(), club3.getId(), new Date());
        
        snRepository.postActivity(person2.getId(), activity2.getId(), new Date());
        snRepository
                .postClubActivity(person1.getId(), activity1.getId(), club1.getId(), new Date());
        snRepository.postEventActivity(person1.getId(), activity3.getId(), event3.getId(),
                new Date());
    }
    
    private void initEntitiesRepository() {
    
        entitiesRepository.updateProfile(person1, new Date());
        entitiesRepository.updateProfile(person2, new Date());
        entitiesRepository.updateProfile(person3, new Date());
        
        entitiesRepository.postActivity(activity1, new Date());
        entitiesRepository.postActivity(activity2, new Date());
        entitiesRepository.postActivity(activity3, new Date());
        
        entitiesRepository.createClub(club1, new Date());
        entitiesRepository.createClub(club2, new Date());
        entitiesRepository.createClub(club3, new Date());
        
        entitiesRepository.createEvent(event1, new Date());
        entitiesRepository.createEvent(event2, new Date());
        entitiesRepository.createEvent(event3, new Date());
        
    }
}
