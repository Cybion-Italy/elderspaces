package eu.elderspaces;

import it.cybion.commons.PropertiesHelper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import com.sun.jersey.api.core.ClasspathResourceConfig;
import com.sun.jersey.spi.container.servlet.ServletContainer;

import eu.elderspaces.persistence.EnrichedEntitiesRepository;
import eu.elderspaces.persistence.LuceneEnrichedEntitiesRepository;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class ProductionCacheModule extends AbstractModule {
    
    public Properties properties;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductionCacheModule.class);
    
    @Override
    protected void configure() {
    
        LOGGER.debug("configuring cache...");
        
        final Map<String, String> initParams = new HashMap<String, String>();
        
        initParams.put(ServletContainer.RESOURCE_CONFIG_CLASS,
                ClasspathResourceConfig.class.getName());
        
        final Properties properties = PropertiesHelper.readFromClasspath("/config.properties");
        // binds the keynames as @Named annotations
        Names.bindProperties(binder(), properties);
        this.properties = properties;
        
    }
    
    @Provides
    @Singleton
    private EnrichedEntitiesRepository enrichedEntitiesRepositoryProvider(
            @Named("eu.elderspaces.repository.enriched-entities") final String enrichedEntitiesDirectoryPath)
            throws IOException {
    
        final Directory directory = new SimpleFSDirectory(new File(enrichedEntitiesDirectoryPath));
        final Analyzer analyzer = new WhitespaceAnalyzer(Version.LUCENE_36);
        
        return new LuceneEnrichedEntitiesRepository(directory, analyzer);
    }
}
