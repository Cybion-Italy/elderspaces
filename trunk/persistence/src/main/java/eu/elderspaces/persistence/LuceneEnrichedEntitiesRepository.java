package eu.elderspaces.persistence;

import it.cybion.commons.exceptions.RepositoryException;
import it.cybion.commons.repository.BaseLuceneRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.TermVector;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.similar.MoreLikeThis;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;

import com.google.inject.Inject;

import eu.elderspaces.model.Activity;
import eu.elderspaces.model.Club;
import eu.elderspaces.model.Entity;
import eu.elderspaces.model.Event;
import eu.elderspaces.model.Person;
import eu.elderspaces.persistence.exceptions.EnrichedEntitiesRepositoryException;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class LuceneEnrichedEntitiesRepository extends BaseLuceneRepository<String, String>
        implements EnrichedEntitiesRepository {
    
    private static final Logger LOGGER = Logger.getLogger(LuceneEnrichedEntitiesRepository.class);
    
    private static int MAX_RECOMMENDATIONS = 100;
    
    private static final String ID = "id";
    private static final String OBJECT_TYPE = "objectType";
    private static final String CONTENT = "content";
    
    @Inject
    public LuceneEnrichedEntitiesRepository(final Directory directory, final Analyzer analyzer) {
    
        super(directory, analyzer);
    }
    
    @Override
    public void storeEnrichedPerson(final Person person, final List<Activity> activities,
            final List<Club> clubs, final List<Event> events)
            throws EnrichedEntitiesRepositoryException {
    
        String content = getPersonContent(person);
        
        for (final Activity activity : activities) {
            content += activity.getTitle() + " " + activity.getBody() + " ";
        }
        
        for (final Club club : clubs) {
            content += getClubContent(club);
        }
        
        for (final Event event : events) {
            if (new Date().getTime() < event.getEndDate().getTime()) {
                content += getEventContent(event);
            }
        }
        
        final Document doc = new Document();
        
        doc.add(new Field(ID, person.getId(), Field.Store.YES, Field.Index.NOT_ANALYZED,
                TermVector.NO));
        doc.add(new Field(OBJECT_TYPE, Person.class.getSimpleName(), Field.Store.YES,
                Field.Index.NOT_ANALYZED, TermVector.NO));
        doc.add(new Field(CONTENT, content, Field.Store.YES, Field.Index.ANALYZED, TermVector.YES));
        
        try {
            writer.addDocument(doc);
        } catch (final CorruptIndexException e) {
            throw new EnrichedEntitiesRepositoryException(e);
        } catch (final IOException e) {
            throw new EnrichedEntitiesRepositoryException(e);
        }
        
    }
    
    @Override
    public void storeEnrichedClub(final Club club, final List<Person> members)
            throws EnrichedEntitiesRepositoryException {
    
        String content = getClubContent(club);
        
        for (final Person person : members) {
            content += getPersonContent(person);
        }
        
        final Document doc = new Document();
        
        doc.add(new Field(ID, club.getId(), Field.Store.YES, Field.Index.NOT_ANALYZED,
                TermVector.NO));
        doc.add(new Field(OBJECT_TYPE, Club.class.getSimpleName(), Field.Store.YES,
                Field.Index.NOT_ANALYZED, TermVector.NO));
        doc.add(new Field(CONTENT, content, Field.Store.YES, Field.Index.ANALYZED, TermVector.YES));
        
        try {
            writer.addDocument(doc);
        } catch (final CorruptIndexException e) {
            throw new EnrichedEntitiesRepositoryException(e);
        } catch (final IOException e) {
            throw new EnrichedEntitiesRepositoryException(e);
        }
    }
    
    @Override
    public void storeEnrichedEvent(final Event event, final List<Person> members)
            throws EnrichedEntitiesRepositoryException {
    
        if (new Date().getTime() > event.getEndDate().getTime()) {
            LOGGER.warn("Skipping event with id: " + event.getId() + " - Expired on "
                    + event.getEndDate().toGMTString());
            return;
        }
        
        String content = getEventContent(event);
        
        for (final Person person : members) {
            content += getPersonContent(person);
        }
        
        final Document doc = new Document();
        
        doc.add(new Field(ID, event.getId(), Field.Store.YES, Field.Index.NOT_ANALYZED,
                TermVector.NO));
        doc.add(new Field(OBJECT_TYPE, Event.class.getSimpleName(), Field.Store.YES,
                Field.Index.NOT_ANALYZED, TermVector.NO));
        doc.add(new Field(CONTENT, content, Field.Store.YES, Field.Index.ANALYZED, TermVector.YES));
        
        try {
            writer.addDocument(doc);
        } catch (final CorruptIndexException e) {
            throw new EnrichedEntitiesRepositoryException(e);
        } catch (final IOException e) {
            throw new EnrichedEntitiesRepositoryException(e);
        }
    }
    
    @Override
    public Map<String, Double> getPersonRecommendations(final String userId) {
    
        Map<String, Double> ids = new HashMap<String, Double>();
        
        try {
            ids = getSimilarEntitiesIds(userId, Person.class.getSimpleName());
            
        } catch (final EnrichedEntitiesRepositoryException e) {
            LOGGER.error(e.getMessage());
        }
        
        return ids;
    }
    
    @Override
    public Map<String, Double> getClubRecommendations(final String userId) {
    
        Map<String, Double> ids = new HashMap<String, Double>();
        
        try {
            ids = getSimilarEntitiesIds(userId, Club.class.getSimpleName());
            
        } catch (final EnrichedEntitiesRepositoryException e) {
            LOGGER.error(e.getMessage());
        }
        
        return ids;
    }
    
    @Override
    public Map<String, Double> getEventRecommendations(final String userId) {
    
        Map<String, Double> ids = new HashMap<String, Double>();
        
        try {
            ids = getSimilarEntitiesIds(userId, Event.class.getSimpleName());
            
        } catch (final EnrichedEntitiesRepositoryException e) {
            LOGGER.error(e.getMessage());
        }
        
        return ids;
    }
    
    @Override
    public void store(final String document) throws RepositoryException {
    
        throw new UnsupportedOperationException(
                "Custom document writing is perfomed for each document. Call specific entity store method!");
        
    }
    
    @Override
    public String get(final String documentId) throws RepositoryException {
    
        throw new UnsupportedOperationException("NYI");
    }
    
    @Override
    public void delete(final String documentId) throws RepositoryException {
    
        throw new UnsupportedOperationException(
                "Unsupported Operation. This repository is recreated periodically thus complete index directory must be performed");
        
    }
    
    @Override
    public void deleteAll() throws RepositoryException {
    
        throw new UnsupportedOperationException(
                "Unsupported Operation. This repository is recreated periodically thus complete index directory must be performed");
        
    }
    
    @Override
    public List<String> getAllKeys() throws RepositoryException {
    
        try {
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
    
    // return the ids only. the real entities should be queried in the entities
    // repository as this repository contains enriched entities
    private Map<String, Double> getSimilarEntitiesIds(final String id, final String objectType)
            throws EnrichedEntitiesRepositoryException {
    
        try {
            final IndexSearcher searcher = new IndexSearcher(reader);
            final Term referenceDocument = new Term(ID, id);
            final Query singleDocQuery = new TermQuery(referenceDocument);
            TopDocs singleDocumentResults = null;
            
            int documentExampleId = -1;
            
            singleDocumentResults = searcher.search(singleDocQuery, 1);
            if (singleDocumentResults.scoreDocs.length > 0) {
                documentExampleId = singleDocumentResults.scoreDocs[0].doc;
            } else {
                throw new EnrichedEntitiesRepositoryException(
                        "Could not find reference to target recommendation entity with id: " + id);
            }
            
            final MoreLikeThis moreLikeThis = new MoreLikeThis(reader);
            moreLikeThis.setMinTermFreq(1);
            moreLikeThis.setMinDocFreq(1);
            // configure fields used for similarity
            moreLikeThis.setFieldNames(new String[] { CONTENT });
            moreLikeThis.setMinWordLen(3);
            moreLikeThis.setBoost(true);
            
            final Map<String, Double> recommendationDocumentsIds = new HashMap<String, Double>();
            
            final TopScoreDocCollector collector = TopScoreDocCollector.create(MAX_RECOMMENDATIONS,
                    true);
            
            // we make an AND query with content and specific object type we are
            // searching similars for
            final Query contentQuery = moreLikeThis.like(documentExampleId);
            final Query objectTypeQuery = new TermQuery(new Term(OBJECT_TYPE, objectType));
            final BooleanQuery query = new BooleanQuery();
            query.add(new BooleanClause(contentQuery, Occur.MUST));
            query.add(new BooleanClause(objectTypeQuery, Occur.MUST));
            
            searcher.search(query, collector);
            final TopDocs topDocs = collector.topDocs();
            
            for (final ScoreDoc scoredDoc : topDocs.scoreDocs) {
                final Document document = reader.document(scoredDoc.doc);
                final String recommendedId = document.get(ID);
                // filter the original document from results
                if (!id.equals(recommendedId)) {
                    recommendationDocumentsIds.put(recommendedId, (double) scoredDoc.score);
                }
            }
            
            return recommendationDocumentsIds;
            
        } catch (final CorruptIndexException e) {
            LOGGER.error(e.getMessage());
            throw new EnrichedEntitiesRepositoryException("Could not get " + objectType
                    + " recommendations for id: " + id, e);
        } catch (final IOException e) {
            LOGGER.error(e.getMessage());
            throw new EnrichedEntitiesRepositoryException("Could not get " + objectType
                    + " recommendations for id: " + id, e);
        } catch (final EnrichedEntitiesRepositoryException e) {
            LOGGER.error(e.getMessage());
            throw new EnrichedEntitiesRepositoryException("Could not get " + objectType
                    + " recommendations for id: " + id, e);
        }
        
    }
    
    private String getPersonContent(final Person person) {
    
        final String content = person.getAboutMe() + " " + person.getActivitiesString(" ") + " "
                + person.getBooksString(" ") + " " + person.getGender() + " "
                + person.getInterestsString(" ") + " " + person.getLanguagesSpokenString(" ") + " "
                + person.getMoviesString(" ") + " " + person.getMusicString(" ") + " "
                + person.getPets() + " " + person.getTvShowsString(" ");
        
        return content;
    }
    
    private String getEventContent(final Event event) {
    
        final String content = event.getCategory() + " " + event.getName() + " "
                + event.getShortDescription() + " " + event.getDescription() + " ";
        
        return content;
    }
    
    private String getClubContent(final Club club) {
    
        final String content = club.getCategory() + " " + club.getName() + " "
                + club.getShortDescription() + " " + club.getDescription() + " ";
        
        return content;
    }
    
    /*
     * BE SURE WHEN CALLING THIS METHOD THAT THE TARGET DIRECTORY IS CLEANED!!!
     */
    @Override
    public void buildEnrichedEntities(final EntitiesRepository entitiesRepository,
            final SocialNetworkRepository snRepository) throws EnrichedEntitiesRepositoryException {
    
        LOGGER.info("Building Enriched Entities Repository...");
        final long start = System.currentTimeMillis();
        
        List<String> keys;
        try {
            keys = entitiesRepository.getAllKeys();
        } catch (final RepositoryException e) {
            throw new EnrichedEntitiesRepositoryException("Could not build enriched entities", e);
        }
        
        LOGGER.info(keys.size() + " entities to enrich!");
        
        for (final String key : keys) {
            
            try {
                final Entity entity = entitiesRepository.get(key);
                
                if (entity instanceof Person) {
                    
                    final Person person = (Person) entity;
                    final List<Activity> activities = new ArrayList<Activity>();
                    final List<Club> clubs = new ArrayList<Club>();
                    final List<Event> events = new ArrayList<Event>();
                    
                    final Set<String> activityIDs = snRepository.getActivities(person.getId());
                    final Set<String> clubIDs = snRepository.getClubs(person.getId());
                    final Set<String> eventIDs = snRepository.getEvents(person.getId());
                    
                    for (final String id : activityIDs) {
                        activities.add(entitiesRepository.getActivity(id));
                    }
                    for (final String id : clubIDs) {
                        clubs.add(entitiesRepository.getClub(id));
                    }
                    for (final String id : eventIDs) {
                        events.add(entitiesRepository.getEvent(id));
                    }
                    
                    storeEnrichedPerson(person, activities, clubs, events);
                    
                } else if (entity instanceof Club) {
                    
                    final Club club = (Club) entity;
                    final List<Person> members = new ArrayList<Person>();
                    final Set<String> memberIDs = snRepository.getClubMembers(club.getId());
                    
                    for (final String id : memberIDs) {
                        members.add(entitiesRepository.getPerson(id));
                    }
                    
                    storeEnrichedClub(club, members);
                } else if (entity instanceof Event) {
                    final Event event = (Event) entity;
                    final List<Person> members = new ArrayList<Person>();
                    final Set<String> memberIDs = snRepository.getEventMembers(event.getId());
                    
                    for (final String id : memberIDs) {
                        members.add(entitiesRepository.getPerson(id));
                    }
                    
                    storeEnrichedEvent(event, members);
                }
            } catch (final RepositoryException e) {
                LOGGER.error("skipping id: " + key + " " + e.getMessage());
                continue;
            }
            
        }
        
        // Commit everithing
        try {
            
            final long commitStart = System.currentTimeMillis();
            writer.commit();
            final long commitEnd = System.currentTimeMillis();
            LOGGER.info("finished commit in " + ((commitEnd - commitStart) / 1000.00) + " secs");
            
        } catch (final CorruptIndexException e) {
            throw new EnrichedEntitiesRepositoryException("Could not commit during enriching", e);
        } catch (final IOException e) {
            throw new EnrichedEntitiesRepositoryException("Could not commit during enriching", e);
        }
        
        final long end = System.currentTimeMillis();
        LOGGER.info("Finished Enriching in total " + ((end - start) / 1000.00) + " secs");
        
        LOGGER.info("Updating reader...");
        try {
            final IndexReader newReader = IndexReader.openIfChanged(reader);
            if (newReader != null) {
                reader = newReader;
            }
        } catch (final IOException e) {
            throw new EnrichedEntitiesRepositoryException(
                    "Could not update reader after enriching", e);
        }
        LOGGER.info("Updated reader!");
        LOGGER.info("Finished Enriching!");
    }
    
    @Override
    public void shutdown() throws RepositoryException {
    
        this.commit();
        this.close();
        
    }
    
    public void switchToDirectory(final Directory directory, final Analyzer analyzer)
            throws RepositoryException {
    
        try {
            final IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_36, analyzer);
            writer = new IndexWriter(directory, conf);
            reader = IndexReader.open(writer, false);
        } catch (final CorruptIndexException e) {
            throw new RepositoryException("Could not switch repository", e);
        } catch (final LockObtainFailedException e) {
            throw new RepositoryException("Could not switch repository", e);
        } catch (final IOException e) {
            throw new RepositoryException("Could not switch repository", e);
        }
    }
}
