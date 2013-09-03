package eu.elderspaces.model.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class DateJsonDateDeserializer extends JsonDeserializer<Date> {
    
    @Override
    public Date deserialize(final JsonParser jsonparser,
            final DeserializationContext deserializationcontext) throws IOException,
            JsonProcessingException {
    
        DateFormat format;
        final String date = jsonparser.getText();
        try {
            
            if (date.contains("T")) {
                format = ActivityStreamObjectMapper.ACTIVITY_STREAM_FORMAT;
            } else if (isTimeMillis(date)) {
                return new Date(Long.parseLong(date));
            } else {
                format = ActivityStreamObjectMapper.EVENT_DATE_FORMAT;
            }
            
            return format.parse(date);
        } catch (final ParseException e) {
            throw new RuntimeException(e);
        }
        
    }
    
    private boolean isTimeMillis(final String date) {
    
        try {
            Long.parseLong(date);
            return true;
        } catch (final Exception e) {
            return false;
        }
    }
}