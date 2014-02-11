package eu.elderspaces.persistence;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import it.cybion.commons.exceptions.RepositoryException;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.elasticsearch.common.UUID;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import eu.elderspaces.model.Activity;
import eu.elderspaces.model.Club;
import eu.elderspaces.model.Entity;
import eu.elderspaces.model.Event;
import eu.elderspaces.model.Person;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class EntitiesRepositoryTestCase {
    
    public static final Logger LOGGER = Logger.getLogger(EntitiesRepositoryTestCase.class);
    
    private EntitiesRepository repository;
    private Person person;
    private Club club;
    private Event event;
    private Activity activity;
    
    @BeforeClass
    public void setup() {
    
        final Directory directory = new RAMDirectory();
        final Analyzer analyzer = new WhitespaceAnalyzer(Version.LUCENE_36);
        repository = new LuceneEntitiesRepository(directory, analyzer);
        
        initEntities();
    }
    
    @AfterClass
    public void shutdown() {
    
        repository = null;
    }
    
    @Test
    public void shouldTestRandomPersons() throws RepositoryException {
    
        repository.updateProfile(new Person("1", "url", "name"), new Date());
        repository.updateProfile(new Person("2", "url", "name"), new Date());
        repository.updateProfile(new Person("3", "url", "name"), new Date());
        
        List<Entity> persons = repository.getRandomPersons(100);
        assertTrue(persons.size() == 3);
        
        assertTrue(persons.get(0).getId().equals("1"));
        assertTrue(persons.get(1).getId().equals("2"));
        assertTrue(persons.get(2).getId().equals("3"));
        
        persons = repository.getRandomPersons(2);
        assertTrue(persons.size() == 2);
        
        repository.deleteUser(new Person("1", "url", "name"), new Date());
        repository.deleteUser(new Person("2", "url", "name"), new Date());
        repository.deleteUser(new Person("3", "url", "name"), new Date());
    }
    
    @Test
    public void shouldTestActivities() throws RepositoryException {
    
        repository.postActivity(activity, new Date());
        assertEquals(repository.getActivity(activity.getId()), activity);
        
        repository.deleteActivity(activity, new Date());
        
        try {
            repository.getActivity(activity.getId());
        } catch (final RepositoryException e) {
            assertTrue(true);
        }
        
    }
    
    @Test
    public void shouldTestPersons() throws RepositoryException {
    
        repository.updateProfile(person, new Date());
        assertEquals(repository.getPerson(person.getId()), person);
        
        person.setAboutMe("everything changed!");
        repository.updateProfile(person, new Date());
        assertEquals(repository.getPerson(person.getId()), person);
        
        repository.deleteUser(person, new Date());
        
        try {
            repository.getPerson(person.getId());
        } catch (final RepositoryException e) {
            assertTrue(true);
        }
        
    }
    
    @Test
    public void shouldTestClubs() throws RepositoryException {
    
        repository.createClub(club, new Date());
        assertEquals(repository.getClub(club.getId()), club);
        
        club.setDescription("everything changed!");
        repository.modifyClub(club, new Date());
        assertEquals(repository.getClub(club.getId()), club);
        
        repository.deleteClub(club, new Date());
        
        try {
            repository.getClub(club.getId());
        } catch (final RepositoryException e) {
            assertTrue(true);
        }
        
    }
    
    @Test
    public void shouldTestEvents() throws RepositoryException {
    
        repository.createEvent(event, new Date());
        assertEquals(repository.getEvent(event.getId()), event);
        
        event.setDescription("everything changed!");
        repository.modifyEvent(event, new Date());
        assertEquals(repository.getEvent(event.getId()), event);
        
        repository.deleteEvent(event, new Date());
        
        try {
            repository.getEvent(event.getId());
        } catch (final RepositoryException e) {
            assertTrue(true);
        }
        
    }
    
    @Test
    public void shouldTestSearch() throws RepositoryException {
    
        person.setId("1");
        repository.updateProfile(person, new Date());
        
        person.setId("2");
        repository.updateProfile(person, new Date());
        
        repository.getPerson("1");
        repository.getPerson("2");
        ((LuceneEntitiesRepository) repository).get("1");
        ((LuceneEntitiesRepository) repository).get("2");
    }
    
    private void initEntities() {
    
        person = new Person();
        person.setAboutMe("me");
        person.setActivities("eating drinking".split(" "));
        person.setBooks("foundation ".split(" "));
        person.setDisplayName("dname");
        person.setFriendsCount(100);
        person.setGender("M");
        person.setId(Long.toString(UUID.randomUUID().getLeastSignificantBits()));
        person.setInterests("music movies".split(" "));
        person.setLanguagesSpoken("en it".split(" "));
        person.setMovies("star wars".split(" "));
        person.setMusic("indie rock".split(" "));
        person.setName("name");
        person.setPets("furret");
        person.setThumbnailUrl("http://babsjdaslkd");
        person.setTvShows("lost big bang theory".split(" "));
        
        activity = new Activity();
        activity.setBody("body");
        activity.setId(Long.toString(UUID.randomUUID().getLeastSignificantBits()));
        activity.setTitle("title");
        
        event = new Event();
        event.setCategory("category");
        event.setDescription("description");
        event.setEndDate(new Date());
        event.setId(Long.toString(UUID.randomUUID().getLeastSignificantBits()));
        event.setName("event name");
        event.setShortDescription("short description");
        event.setStartDate(new Date());
        
        club = new Club();
        club.setCategory("category");
        club.setDescription("description");
        club.setId(Long.toString(UUID.randomUUID().getLeastSignificantBits()));
        club.setName("name");
        club.setShortDescription("description");
        
    }
}
