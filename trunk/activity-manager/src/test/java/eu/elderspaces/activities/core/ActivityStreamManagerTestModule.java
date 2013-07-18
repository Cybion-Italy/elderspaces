package eu.elderspaces.activities.core;

import it.cybion.commons.PropertiesHelper;

import java.util.Properties;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.neo4j.test.TestGraphDatabaseFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph;

import eu.elderspaces.model.utils.ActivityStreamObjectMapper;
import eu.elderspaces.persistence.ActivityStreamRepository;
import eu.elderspaces.persistence.BluePrintsSocialNetworkRepository;
import eu.elderspaces.persistence.ElasticSearchActivityStreamRepository;
import eu.elderspaces.persistence.EntitiesRepository;
import eu.elderspaces.persistence.LuceneEntitiesRepository;
import eu.elderspaces.persistence.SocialNetworkRepository;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class ActivityStreamManagerTestModule extends AbstractModule {
    
    @Override
    protected void configure() {
    
        final Properties properties = PropertiesHelper.readFromClasspath("/config.properties");
        Names.bindProperties(binder(), properties);
        
        bind(ActivityStreamRepository.class).to(ElasticSearchActivityStreamRepository.class);
        
        bind(Neo4jGraph.class).toInstance(
                new Neo4jGraph(new TestGraphDatabaseFactory().newImpermanentDatabaseBuilder()
                        .newGraphDatabase()));
        
        bind(SocialNetworkRepository.class).to(BluePrintsSocialNetworkRepository.class);
        
        bind(Directory.class).toInstance(new RAMDirectory());
        bind(Analyzer.class).toInstance(new WhitespaceAnalyzer(Version.LUCENE_36));
        bind(EntitiesRepository.class).to(LuceneEntitiesRepository.class);
        
        bind(ActivityStreamManager.class).to(MultiLayerActivityStreamManager.class);
        
    }
    
    @Provides
    public ObjectMapper loadTheObjectMapper() {
    
        return ActivityStreamObjectMapper.getDefaultMapper();
    }
}
