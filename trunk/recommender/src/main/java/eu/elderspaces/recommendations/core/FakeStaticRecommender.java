package eu.elderspaces.recommendations.core;

import java.util.List;

import com.google.common.collect.Lists;

import eu.elderspaces.model.Club;
import eu.elderspaces.model.Entity;
import eu.elderspaces.model.Event;
import eu.elderspaces.model.Person;
import eu.elderspaces.model.recommendations.PaginatedResult;
import eu.elderspaces.recommendations.exceptions.RecommenderException;

public class FakeStaticRecommender implements Recommender {
    
    private static final int TOTAL_RESULTS = 3;
    private static final int START_INDEX = 0;
    
    private static final String FRIEND_ID_1 = "104:elderspaces.iwiw.hu";
    private static final String FRIEND_ID_2 = "110:elderspaces.iwiw.hu";
    private static final String FRIEND_ID_3 = "112:elderspaces.iwiw.hu";
    private static final String DISPLAY_NAME_1 = "ElfogadĂł DezsĹ‘";
    private static final String DISPLAY_NAME_2 = "Maria Orbat‘";
    private static final String DISPLAY_NAME_3 = "Marko Dienig‘";
    private static final String THUMBNAIL_URL_1 = "http://thn1.elderspaces.iwiw.hu/0101//user/00/00/00/10/4/user_104_1238767117477_tn1";
    private static final String THUMBNAIL_URL_2 = "http://thn1.elderspaces.iwiw.hu/0101//user/00/00/00/11/0/user_110_12387671174323_tn1";
    private static final String THUMBNAIL_URL_3 = "http://thn1.elderspaces.iwiw.hu/0101//user/00/00/00/11/2/user_112_123876711432741_tn1";
    
    private static final String EVENT_ID_1 = "1008";
    private static final String EVENT_ID_2 = "453";
    private static final String EVENT_ID_3 = "897";
    private static final String EVENT_SHORT_DESCRIPTION_1 = "nagyon alszik";
    private static final String EVENT_SHORT_DESCRIPTION_2 = "ajanloooom";
    private static final String EVENT_SHORT_DESCRIPTION_3 = "a very good event";
    private static final String EVENT_NAME_1 = "ALBUMTESZTELO_esemeny";
    private static final String EVENT_NAME_2 = "2 hetes a redesign";
    private static final String EVENT_NAME_3 = "another event";
    
    private static final String CLUB_ID_1 = "104:elderspaces.iwiw.hu";
    private static final String CLUB_ID_2 = "110:elderspaces.iwiw.hu";
    private static final String CLUB_ID_3 = "112:elderspaces.iwiw.hu";
    private static final String CLUB_NAME_1 = "Klub neve";
    private static final String CLUB_NAME_2 = "Old Football lovers";
    private static final String CLUB_NAME_3 = "Nineties music lovers";
    private static final String CLUB_DESCRIPTION_1 = "Klub leírása";
    private static final String CLUB_DESCRIPTION_2 = "Club Old Football lovers is the club of all the old time";
    private static final String CLUB_DESCRIPTION_3 = "Club for nineties music true lovers";
    private static final String CLUB_SHORT_DESCRIPTION_1 = "Klub rövid leírása";
    private static final String CLUB_SHORT_DESCRIPTION_2 = "All people interested in old style football";
    private static final String CLUB_SHORT_DESCRIPTION_3 = "All people interested in nineties music";
    private static final String CLUB_CATEGORY_1 = "HOBBY";
    private static final String CLUB_CATEGORY_2 = "SPORT";
    private static final String CLUB_CATEGORY_3 = "MUSIC";
    
    private PaginatedResult getFriends(final String userId) {
    
        final Entity firstFriendEntry = new Person(FRIEND_ID_1, DISPLAY_NAME_1, THUMBNAIL_URL_1);
        final Entity secondFriendEntry = new Person(FRIEND_ID_2, DISPLAY_NAME_2, THUMBNAIL_URL_2);
        final Entity thirdFriendEntry = new Person(FRIEND_ID_3, DISPLAY_NAME_3, THUMBNAIL_URL_3);
        
        final List<Entity> entries = Lists.newArrayList(firstFriendEntry, secondFriendEntry,
                thirdFriendEntry);
        
        return new PaginatedResult(START_INDEX, TOTAL_RESULTS, entries);
    }
    
    private PaginatedResult getEvents(final String userId) {
    
        final Entity firstEventEntry = new Event(EVENT_ID_1, EVENT_NAME_1,
                EVENT_SHORT_DESCRIPTION_1);
        final Entity secondEventEntry = new Event(EVENT_ID_2, EVENT_NAME_2,
                EVENT_SHORT_DESCRIPTION_2);
        final Entity thirdEventEntry = new Event(EVENT_ID_3, EVENT_NAME_3,
                EVENT_SHORT_DESCRIPTION_3);
        
        final List<Entity> entries = Lists.newArrayList(firstEventEntry, secondEventEntry,
                thirdEventEntry);
        
        return new PaginatedResult(START_INDEX, TOTAL_RESULTS, entries);
    }
    
    private PaginatedResult getClubs(final String userId) {
    
        final Entity firstClubEntry = new Club(CLUB_ID_1, CLUB_NAME_1, CLUB_DESCRIPTION_1,
                CLUB_SHORT_DESCRIPTION_1, CLUB_CATEGORY_1);
        final Entity secondClubEntry = new Club(CLUB_ID_2, CLUB_NAME_2, CLUB_DESCRIPTION_2,
                CLUB_SHORT_DESCRIPTION_2, CLUB_CATEGORY_2);
        final Entity thirdClubEntry = new Club(CLUB_ID_3, CLUB_NAME_3, CLUB_DESCRIPTION_3,
                CLUB_SHORT_DESCRIPTION_3, CLUB_CATEGORY_3);
        
        final List<Entity> entries = Lists.newArrayList(firstClubEntry, secondClubEntry,
                thirdClubEntry);
        
        return new PaginatedResult(START_INDEX, TOTAL_RESULTS, entries);
    }
    
    @Override
    public PaginatedResult getRecommendedEntities(final String userId,
            final Class<? extends Entity> type) throws RecommenderException {
    
        if (type == Person.class) {
            return getFriends(userId);
        } else if (type == Event.class) {
            return getEvents(userId);
        } else if (type == Club.class) {
            return getClubs(userId);
        } else {
            throw new RecommenderException("Invalid entity type");
        }
    }
    
}
