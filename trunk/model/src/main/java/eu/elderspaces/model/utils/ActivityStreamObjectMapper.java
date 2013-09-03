package eu.elderspaces.model.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class ActivityStreamObjectMapper {
    
    public static DateFormat EVENT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static DateFormat ACTIVITY_STREAM_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    
    public static ObjectMapper getDefaultMapper() {
    
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
        final SimpleModule module = new SimpleModule("EventDateDeserializerModule", new Version(1,
                16, 0, null));
        module.addDeserializer(Date.class, new DateJsonDateDeserializer());
        
        mapper.registerModule(module);
        
        return mapper;
    }
    
}
