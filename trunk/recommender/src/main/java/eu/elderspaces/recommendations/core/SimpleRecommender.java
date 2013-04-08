package eu.elderspaces.recommendations.core;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.inject.Inject;
import com.google.inject.internal.Maps;

import eu.elderspaces.activities.core.ActivityManager;
import eu.elderspaces.activities.exceptions.NonExistentUser;
import eu.elderspaces.model.Club;
import eu.elderspaces.model.Event;
import eu.elderspaces.model.Person;
import eu.elderspaces.model.recommendations.PaginatedResult;
import eu.elderspaces.recommendations.exceptions.RecommenderException;

public class SimpleRecommender implements Recommender {
    
    private static final int TOTAL_RESULTS = 3;
    private static final int START_INDEX = 0;
    
    private final ActivityManager activitymanager;
    
    @Inject
    public SimpleRecommender(final ActivityManager activitymanager) {
    
        this.activitymanager = activitymanager;
    }
    
    @Override
    public PaginatedResult<Person> getFriends(final String userId) throws RecommenderException,
            NonExistentUser {
    
        final Set<Person> userFriends = activitymanager.getFriends(userId);
        final Map<Person, Integer> indirectFriendConnectionMap = Maps.newHashMap();
        for (final Person userFriend : userFriends) {
            Integer connectionStrength = indirectFriendConnectionMap.get(userFriend);
            if (connectionStrength != null) {
                indirectFriendConnectionMap.put(userFriend, ++connectionStrength);
            } else {
                indirectFriendConnectionMap.put(userFriend, 1);
            }
        }
        
        final Ordering<Person> valueComparator = Ordering.natural().reverse()
                .onResultOf(Functions.forMap(indirectFriendConnectionMap))
                .compound(Ordering.natural().reverse());
        
        final SortedMap<Person, Integer> sortedResult = ImmutableSortedMap.copyOf(
                indirectFriendConnectionMap, valueComparator);
        
        final Person firstRecommendedFriend = Iterables.get(sortedResult.keySet(), 0);
        final Person secondRecommendedFriend = Iterables.get(sortedResult.keySet(), 1);
        final Person thirdRecommendedFriend = Iterables.get(sortedResult.keySet(), 2);
        final List<Person> recommendedFriends = Lists.newArrayList(firstRecommendedFriend,
                secondRecommendedFriend, thirdRecommendedFriend);
        
        return new PaginatedResult<Person>(START_INDEX, TOTAL_RESULTS, recommendedFriends);
        
    }
    
    @Override
    public PaginatedResult<Event> getEvents(final String userId) throws RecommenderException {
    
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public PaginatedResult<Club> getClubs(final String userId) throws RecommenderException {
    
        // TODO Auto-generated method stub
        return null;
    }
    
}
