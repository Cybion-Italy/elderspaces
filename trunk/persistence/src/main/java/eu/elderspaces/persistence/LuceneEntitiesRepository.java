package eu.elderspaces.persistence;

import it.cybion.commons.exceptions.RepositoryException;
import it.cybion.commons.repository.BaseLuceneRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.TermVector;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.StaleReaderException;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;

import com.google.inject.Inject;

import eu.elderspaces.model.Activity;
import eu.elderspaces.model.Club;
import eu.elderspaces.model.Entity;
import eu.elderspaces.model.Event;
import eu.elderspaces.model.Person;
import eu.elderspaces.persistence.exceptions.UnknownObjectTypeException;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class LuceneEntitiesRepository extends BaseLuceneRepository<String, Entity> implements
        EntitiesRepository {
    
    private static final Logger LOGGER = Logger.getLogger(LuceneEntitiesRepository.class);
    
    // MISC
    private static final String STRING_SEPARATOR = "###";
    
    // Entity Properties
    private static final String ID = "id";
    private static final String OBJECT_TYPE = "objectType";
    
    private static final String PERSON_TYPE = "Person";
    private static final String CLUB_TYPE = "Club";
    private static final String EVENT_TYPE = "Event";
    private static final String ACTIVITY_TYPE = "Activity";
    
    // Person Properties
    private static final String PERSON_FRIEND_COUNT = "friendsCount";
    private static final String PERSON_INTERESTS = "interests";
    private static final String PERSON_MOVIES = "movies";
    private static final String PERSON_ABOUT_ME = "aboutMe";
    private static final String PERSON_LANGUAGE_SPOKEN = "languagesSpoken";
    private static final String PERSON_TV_SHOWS = "tvShows";
    private static final String PERSON_THUMBNAILS = "thumbnailUrl";
    private static final String PERSON_NAME = "name";
    private static final String PERSON_PETS = "pets";
    private static final String PERSON_GENDER = "gender";
    private static final String PERSON_ACTIVITIES = "activities";
    private static final String PERSON_MUSIC = "music";
    private static final String PERSON_BOOKS = "books";
    private static final String PERSON_DISPLAY_NAME = "displayName";
    
    // Club Properties
    private static final String CLUB_NAME = "name";
    private static final String CLUB_DESCRIPTION = "description";
    private static final String CLUB_SHORT_DESCRIPTION = "shortDescription";
    private static final String CLUB_CATEGORY = "category";
    
    // Event Properties
    private static final String EVENT_START_DATE = "startDate";
    private static final String EVENT_CATEGORY = "category";
    private static final String EVENT_SHORT_DESCRIPTION = "shortDescription";
    private static final String EVENT_DESCRIPTION = "description";
    private static final String EVENT_NAME = "name";
    private static final String EVENT_END_DATE = "endDate";
    
    // Activity Properties
    private static final String ACTIVITY_BODY = "body";
    private static final String ACTIVITY_TITLE = "title";
    
    @Inject
    public LuceneEntitiesRepository(final Directory directory, final Analyzer analyzer) {
    
        super(directory, analyzer);
        
    }
    
    @Override
    public void updateProfile(final Person actor, final Date eventTime) {
    
        try {
            if (alreadyExists(actor.getId())) {
                deleteUser(actor, eventTime);
            }
            
            store(actor);
            commit();
        } catch (final RepositoryException e) {
            LOGGER.error(e.getMessage());
        }
        
    }
    
    @Override
    public void postActivity(final Activity object, final Date eventTime) {
    
        try {
            if (!alreadyExists(object.getId())) {
                store(object);
                commit();
            }
        } catch (final RepositoryException e) {
            LOGGER.error("Could not store activity");
        }
        
    }
    
    @Override
    public void createClub(final Club object, final Date eventTime) {
    
        try {
            if (!alreadyExists(object.getId())) {
                store(object);
                commit();
            }
            
        } catch (final RepositoryException e) {
            LOGGER.error("Could not store activity");
        }
        
    }
    
    @Override
    public void modifyClub(final Club club, final Date eventTime) {
    
        try {
            if (alreadyExists(club.getId())) {
                deleteClub(club, eventTime);
            }
            
            store(club);
            commit();
        } catch (final RepositoryException e) {
            LOGGER.error(e.getMessage());
        }
        
    }
    
    @Override
    public void createEvent(final Event object, final Date eventTime) {
    
        try {
            if (!alreadyExists(object.getId())) {
                store(object);
                commit();
            }
        } catch (final RepositoryException e) {
            LOGGER.error("Could not store activity");
        }
        
    }
    
    @Override
    public void modifyEvent(final Event event, final Date eventTime) {
    
        try {
            if (alreadyExists(event.getId())) {
                deleteEvent(event, eventTime);
            }
            
            store(event);
            commit();
        } catch (final RepositoryException e) {
            LOGGER.error(e.getMessage());
        }
        
    }
    
    @Override
    public void deleteUser(final Person user, final Date eventTime) {
    
        try {
            writer.deleteDocuments(new Term(ID, user.getId()));
            writer.commit();
        } catch (final StaleReaderException e) {
            LOGGER.error(e.getMessage());
        } catch (final CorruptIndexException e) {
            LOGGER.error(e.getMessage());
        } catch (final LockObtainFailedException e) {
            LOGGER.error(e.getMessage());
        } catch (final IOException e) {
            LOGGER.error(e.getMessage());
        }
        
    }
    
    @Override
    public void deleteActivity(final Activity activity, final Date eventTime) {
    
        try {
            writer.deleteDocuments(new Term(ID, activity.getId()));
            writer.commit();
        } catch (final StaleReaderException e) {
            LOGGER.error(e.getMessage());
        } catch (final CorruptIndexException e) {
            LOGGER.error(e.getMessage());
        } catch (final LockObtainFailedException e) {
            LOGGER.error(e.getMessage());
        } catch (final IOException e) {
            LOGGER.error(e.getMessage());
        }
        
    }
    
    @Override
    public void deleteClub(final Club club, final Date eventTime) {
    
        try {
            writer.deleteDocuments(new Term(ID, club.getId()));
            writer.commit();
        } catch (final StaleReaderException e) {
            LOGGER.error(e.getMessage());
        } catch (final CorruptIndexException e) {
            LOGGER.error(e.getMessage());
        } catch (final LockObtainFailedException e) {
            LOGGER.error(e.getMessage());
        } catch (final IOException e) {
            LOGGER.error(e.getMessage());
        }
        
    }
    
    @Override
    public void deleteEvent(final Event event, final Date eventTime) {
    
        try {
            writer.deleteDocuments(new Term(ID, event.getId()));
            writer.commit();
        } catch (final StaleReaderException e) {
            LOGGER.error(e.getMessage());
        } catch (final CorruptIndexException e) {
            LOGGER.error(e.getMessage());
        } catch (final LockObtainFailedException e) {
            LOGGER.error(e.getMessage());
        } catch (final IOException e) {
            LOGGER.error(e.getMessage());
        }
        
    }
    
    @Override
    public void store(final Entity entity) throws RepositoryException {
    
        try {
            final Document doc = buildFromEntity(entity);
            super.writer.addDocument(doc);
            commit();
            
        } catch (final CorruptIndexException e) {
            throw new RepositoryException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new RepositoryException(e.getMessage(), e);
        } catch (final Exception e) {
            throw new RepositoryException(e.getMessage());
        }
    }
    
    private Document buildFromEntity(final Entity entity) throws Exception {
    
        if (entity.getClass().equals(Person.class)) {
            return buildFromPerson((Person) entity);
        } else if (entity.getClass().equals(Club.class)) {
            return buildFromClub((Club) entity);
        } else if (entity.getClass().equals(Event.class)) {
            return buildFromEvent((Event) entity);
        } else if (entity.getClass().equals(Activity.class)) {
            return buildFromActivity((Activity) entity);
        } else {
            throw new Exception("Invalid entity provided!");
        }
        
    }
    
    private Document buildFromActivity(final Activity activity) {
    
        final Document doc = new Document();
        
        doc.add(new Field(ID, activity.getId(), Field.Store.YES, Field.Index.NOT_ANALYZED,
                TermVector.NO));
        doc.add(new Field(OBJECT_TYPE, Activity.class.getSimpleName(), Field.Store.YES,
                Field.Index.NOT_ANALYZED, TermVector.NO));
        
        doc.add(new Field(ACTIVITY_TITLE, activity.getTitle(), Field.Store.YES,
                Field.Index.NOT_ANALYZED, TermVector.NO));
        doc.add(new Field(ACTIVITY_BODY, activity.getBody(), Field.Store.YES,
                Field.Index.NOT_ANALYZED, TermVector.NO));
        
        return doc;
    }
    
    private Document buildFromEvent(final Event event) {
    
        final Document doc = new Document();
        
        doc.add(new Field(ID, event.getId(), Field.Store.YES, Field.Index.NOT_ANALYZED,
                TermVector.NO));
        doc.add(new Field(OBJECT_TYPE, Event.class.getSimpleName(), Field.Store.YES,
                Field.Index.NOT_ANALYZED, TermVector.NO));
        
        doc.add(new Field(EVENT_CATEGORY, event.getCategory(), Field.Store.YES,
                Field.Index.NOT_ANALYZED, TermVector.NO));
        doc.add(new Field(EVENT_DESCRIPTION, event.getDescription(), Field.Store.YES,
                Field.Index.NOT_ANALYZED, TermVector.NO));
        doc.add(new Field(EVENT_END_DATE, Long.toString(event.getEndDate().getTime()),
                Field.Store.YES, Field.Index.NOT_ANALYZED, TermVector.NO));
        doc.add(new Field(EVENT_NAME, event.getName(), Field.Store.YES, Field.Index.NOT_ANALYZED,
                TermVector.NO));
        doc.add(new Field(EVENT_SHORT_DESCRIPTION, event.getShortDescription(), Field.Store.YES,
                Field.Index.NOT_ANALYZED, TermVector.NO));
        doc.add(new Field(EVENT_START_DATE, Long.toString(event.getStartDate().getTime()),
                Field.Store.YES, Field.Index.NOT_ANALYZED, TermVector.NO));
        
        return doc;
    }
    
    private Document buildFromClub(final Club club) {
    
        final Document doc = new Document();
        doc.add(new Field(ID, club.getId(), Field.Store.YES, Field.Index.NOT_ANALYZED,
                TermVector.NO));
        doc.add(new Field(OBJECT_TYPE, Club.class.getSimpleName(), Field.Store.YES,
                Field.Index.NOT_ANALYZED, TermVector.NO));
        
        doc.add(new Field(CLUB_CATEGORY, club.getCategory(), Field.Store.YES,
                Field.Index.NOT_ANALYZED, TermVector.NO));
        doc.add(new Field(CLUB_DESCRIPTION, club.getDescription(), Field.Store.YES,
                Field.Index.NOT_ANALYZED, TermVector.NO));
        doc.add(new Field(CLUB_NAME, club.getName(), Field.Store.YES, Field.Index.NOT_ANALYZED,
                TermVector.NO));
        doc.add(new Field(CLUB_SHORT_DESCRIPTION, club.getShortDescription(), Field.Store.YES,
                Field.Index.NOT_ANALYZED, TermVector.NO));
        
        return doc;
    }
    
    private Document buildFromPerson(final Person person) {
    
        final Document doc = new Document();
        doc.add(new Field(ID, person.getId(), Field.Store.YES, Field.Index.NOT_ANALYZED,
                TermVector.NO));
        doc.add(new Field(OBJECT_TYPE, Person.class.getSimpleName(), Field.Store.YES,
                Field.Index.NOT_ANALYZED, TermVector.NO));
        
        doc.add(new Field(PERSON_ABOUT_ME, person.getAboutMe(), Field.Store.YES,
                Field.Index.NOT_ANALYZED, TermVector.NO));
        doc.add(new Field(PERSON_ACTIVITIES, person.getActivitiesString(STRING_SEPARATOR),
                Field.Store.YES, Field.Index.NOT_ANALYZED, TermVector.NO));
        doc.add(new Field(PERSON_BOOKS, person.getBooksString(STRING_SEPARATOR), Field.Store.YES,
                Field.Index.NOT_ANALYZED, TermVector.NO));
        doc.add(new Field(PERSON_DISPLAY_NAME, person.getDisplayName(), Field.Store.YES,
                Field.Index.NOT_ANALYZED, TermVector.NO));
        doc.add(new Field(PERSON_FRIEND_COUNT, Integer.toString(person.getFriendsCount()),
                Field.Store.YES, Field.Index.NOT_ANALYZED, TermVector.NO));
        doc.add(new Field(PERSON_GENDER, person.getGender(), Field.Store.YES,
                Field.Index.NOT_ANALYZED, TermVector.NO));
        doc.add(new Field(PERSON_INTERESTS, person.getInterestsString(STRING_SEPARATOR),
                Field.Store.YES, Field.Index.NOT_ANALYZED, TermVector.NO));
        doc.add(new Field(PERSON_LANGUAGE_SPOKEN,
                person.getLanguagesSpokenString(STRING_SEPARATOR), Field.Store.YES,
                Field.Index.NOT_ANALYZED, TermVector.NO));
        doc.add(new Field(PERSON_MOVIES, person.getMoviesString(STRING_SEPARATOR), Field.Store.YES,
                Field.Index.NOT_ANALYZED, TermVector.NO));
        doc.add(new Field(PERSON_MUSIC, person.getMusicString(STRING_SEPARATOR), Field.Store.YES,
                Field.Index.NOT_ANALYZED, TermVector.NO));
        doc.add(new Field(PERSON_NAME, person.getName(), Field.Store.YES, Field.Index.NOT_ANALYZED,
                TermVector.NO));
        doc.add(new Field(PERSON_PETS, person.getPets(), Field.Store.YES, Field.Index.NOT_ANALYZED,
                TermVector.NO));
        doc.add(new Field(PERSON_THUMBNAILS, person.getThumbnailUrl(), Field.Store.YES,
                Field.Index.NOT_ANALYZED, TermVector.NO));
        doc.add(new Field(PERSON_TV_SHOWS, person.getTvShowsString(STRING_SEPARATOR),
                Field.Store.YES, Field.Index.NOT_ANALYZED, TermVector.NO));
        
        return doc;
    }
    
    private Entity getActivityFromDoc(final Document doc) {
    
        final Activity activity = new Activity();
        activity.setBody(doc.get(ACTIVITY_BODY));
        activity.setId(doc.get(ID));
        activity.setTitle(doc.get(ACTIVITY_TITLE));
        
        return activity;
    }
    
    private Entity getEventFromDoc(final Document doc) {
    
        final Event event = new Event();
        
        event.setCategory(doc.get(EVENT_CATEGORY));
        event.setDescription(doc.get(EVENT_DESCRIPTION));
        event.setEndDate(new Date(Long.parseLong(doc.get(EVENT_END_DATE))));
        event.setId(doc.get(ID));
        event.setName(doc.get(EVENT_NAME));
        event.setShortDescription(doc.get(EVENT_SHORT_DESCRIPTION));
        event.setStartDate(new Date(Long.parseLong(doc.get(EVENT_START_DATE))));
        
        return event;
    }
    
    private Entity buildClubFromDoc(final Document doc) {
    
        final Club club = new Club();
        
        club.setCategory(doc.get(CLUB_CATEGORY));
        club.setDescription(doc.get(CLUB_DESCRIPTION));
        club.setId(doc.get(ID));
        club.setName(doc.get(CLUB_NAME));
        club.setShortDescription(doc.get(CLUB_SHORT_DESCRIPTION));
        
        return club;
    }
    
    private Entity buildPersonFromDoc(final Document doc) {
    
        final Person person = new Person();
        
        person.setAboutMe(doc.get(PERSON_ABOUT_ME));
        person.setActivities(doc.get(PERSON_ACTIVITIES).split(STRING_SEPARATOR));
        person.setBooks(doc.get(PERSON_BOOKS).split(STRING_SEPARATOR));
        person.setDisplayName(doc.get(PERSON_DISPLAY_NAME));
        person.setFriendsCount(Integer.parseInt(doc.get(PERSON_FRIEND_COUNT)));
        person.setGender(doc.get(PERSON_GENDER));
        person.setId(doc.get(ID));
        person.setInterests(doc.get(PERSON_INTERESTS).split(STRING_SEPARATOR));
        person.setLanguagesSpoken(doc.get(PERSON_LANGUAGE_SPOKEN).split(STRING_SEPARATOR));
        person.setMovies(doc.get(PERSON_MOVIES).split(STRING_SEPARATOR));
        person.setMusic(doc.get(PERSON_MUSIC).split(STRING_SEPARATOR));
        person.setName(doc.get(PERSON_NAME));
        person.setPets(doc.get(PERSON_PETS));
        person.setThumbnailUrl(doc.get(PERSON_THUMBNAILS));
        person.setTvShows(doc.get(PERSON_TV_SHOWS).split(STRING_SEPARATOR));
        
        return person;
    }
    
    @Override
    public Entity get(final String documentId) throws RepositoryException {
    
        try {
            final int docNumber = searchUnique(documentId);
            
            final Document doc = super.reader.document(docNumber);
            
            final Entity entity = getEntityFromDoc(doc);
            
            return entity;
            
        } catch (final CorruptIndexException e) {
            throw new RepositoryException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new RepositoryException(e.getMessage(), e);
        } catch (final UnknownObjectTypeException e) {
            throw new RepositoryException(e.getMessage());
        }
        
    }
    
    private int searchUnique(final String documentId) throws RepositoryException {
    
        try {
            final IndexReader newIndexReader = IndexReader.openIfChanged(reader);
            if (newIndexReader != null) {
                reader.close();
                reader = newIndexReader;
            }
            
            final IndexSearcher searcher = new IndexSearcher(reader);
            
            final TermQuery query = new TermQuery(new Term(ID, documentId));
            final TopScoreDocCollector collector = TopScoreDocCollector.create(10, true);
            searcher.search(query, collector);
            searcher.close();
            
            if (collector.getTotalHits() > 1) {
                throw new RepositoryException("Multiple results on exact query with id: "
                        + documentId);
            } else if (collector.getTotalHits() == 0) {
                throw new RepositoryException("No match on id: " + documentId);
            }
            
            final ScoreDoc[] hits = collector.topDocs().scoreDocs;
            return hits[0].doc;
            
        } catch (final CorruptIndexException e) {
            throw new RepositoryException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }
    
    private Entity getEntityFromDoc(final Document doc) throws UnknownObjectTypeException {
    
        Entity entity;
        if (doc.get(OBJECT_TYPE).equals(PERSON_TYPE)) {
            entity = buildPersonFromDoc(doc);
        } else if (doc.get(OBJECT_TYPE).equals(CLUB_TYPE)) {
            entity = buildClubFromDoc(doc);
        } else if (doc.get(OBJECT_TYPE).equals(EVENT_TYPE)) {
            entity = getEventFromDoc(doc);
        } else if (doc.get(OBJECT_TYPE).equals(ACTIVITY_TYPE)) {
            entity = getActivityFromDoc(doc);
        } else {
            throw new UnknownObjectTypeException("Unknow class found during casting for id: "
                    + doc.get(ID) + " class: " + doc.get(OBJECT_TYPE));
        }
        
        return entity;
    }
    
    @Override
    public void delete(final String documentId) throws RepositoryException {
    
        throw new UnsupportedOperationException("Not supported");
        
    }
    
    @Override
    public void deleteAll() throws RepositoryException {
    
        throw new UnsupportedOperationException("Not supported");
        
    }
    
    @Override
    public List<String> getAllKeys() throws RepositoryException {
    
        try {
            
            final IndexReader newIndexReader = IndexReader.openIfChanged(reader);
            if (newIndexReader != null) {
                reader.close();
                reader = newIndexReader;
            }
            
            final List<String> keys = new ArrayList<String>(reader.maxDoc());
            
            for (int i = 0; i < reader.maxDoc(); i++) {
                if (!reader.isDeleted(i)) {
                    keys.add(reader.document(i).get(ID));
                }
            }
            
            return keys;
        } catch (final CorruptIndexException e) {
            throw new RepositoryException(e.getMessage());
        } catch (final IOException e) {
            throw new RepositoryException(e.getMessage());
        }
    }
    
    public boolean alreadyExists(final String id) throws RepositoryException {
    
        try {
            final IndexReader newIndexReader = IndexReader.openIfChanged(reader);
            if (newIndexReader != null) {
                reader.close();
                reader = newIndexReader;
            }
            
            final IndexSearcher searcher = new IndexSearcher(reader);
            
            final TermQuery query = new TermQuery(new Term(ID, id));
            final TopScoreDocCollector collector = TopScoreDocCollector.create(10, true);
            searcher.search(query, collector);
            searcher.close();
            
            if (collector.getTotalHits() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (final IOException e) {
            throw new RepositoryException(e.getMessage());
        }
        
    }
    
    // ******************************************************************************
    // Getters
    @Override
    public Person getPerson(final String id) throws RepositoryException {
    
        try {
            final Person person = (Person) get(id);
            return person;
        } catch (final ClassCastException e) {
            throw new RepositoryException("Couldn't cast entity with id: " + id + " to Person");
        }
    }
    
    @Override
    public Club getClub(final String id) throws RepositoryException {
    
        try {
            final Club club = (Club) get(id);
            return club;
        } catch (final ClassCastException e) {
            throw new RepositoryException("Couldn't cast entity with id: " + id + " to Club");
        }
    }
    
    @Override
    public Event getEvent(final String id) throws RepositoryException {
    
        try {
            final Event event = (Event) get(id);
            return event;
        } catch (final ClassCastException e) {
            throw new RepositoryException("Couldn't cast entity with id: " + id + " to Event");
        }
    }
    
    @Override
    public Activity getActivity(final String id) throws RepositoryException {
    
        try {
            final Activity activity = (Activity) get(id);
            return activity;
        } catch (final ClassCastException e) {
            throw new RepositoryException("Couldn't cast entity with id: " + id + " to Activity");
        }
    }
    
    @Override
    public void shutdown() throws RepositoryException {
    
        commit();
        close();
    }
    
    @Override
    public void storeIfNewEntity(Entity entity, Date eventTime) {
    
    }
    
}
