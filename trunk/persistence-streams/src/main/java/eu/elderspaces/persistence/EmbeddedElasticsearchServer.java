package eu.elderspaces.persistence;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.node.Node;

/**
 * Example implementation of an embedded elasticsearch server.
 * 
 * @author Felix Müller
 */
public class EmbeddedElasticsearchServer {
    
    private final Node node;
    private final String dataDirectory;
    
    public EmbeddedElasticsearchServer(final String clusterName, final String dataDirectory) {
    
        this.dataDirectory = dataDirectory;
        
        final ImmutableSettings.Builder elasticsearchSettings = ImmutableSettings.settingsBuilder()
                .put("http.enabled", "false").put("path.data", dataDirectory);
        
        node = nodeBuilder().clusterName(clusterName).local(true)
                .settings(elasticsearchSettings.build()).node();
    }
    
    public Client getClient() {
    
        return node.client();
    }
    
    public void shutdown() {
    
        node.close();
        deleteDataDirectory();
    }
    
    private void deleteDataDirectory() {
    
        try {
            FileUtils.deleteDirectory(new File(dataDirectory));
        } catch (final IOException e) {
            throw new RuntimeException(
                    "Could not delete data directory of embedded elasticsearch server", e);
        }
    }
}
