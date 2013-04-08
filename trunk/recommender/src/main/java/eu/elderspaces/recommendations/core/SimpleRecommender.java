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
import eu.elderspaces.model.Entity;
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
    public PaginatedResult getRecommendedEntities(final String userId,
            final Class<? extends Entity> type) throws RecommenderException, NonExistentUser {
    
        final Set<Person> userFriends = activitymanager.getFriends(userId);
        
        final Map<Entity, Integer> indirectEntityConnectionMap = Maps.newHashMap();
        for (final Person userFriend : userFriends) {
            final Set<? extends Entity> relatedEntities = getEntities(userFriend.getId(), type);
            for (final Entity relatedEntity : relatedEntities) {
                Integer connectionStrength = indirectEntityConnectionMap.get(relatedEntity);
                if (connectionStrength != null) {
                    indirectEntityConnectionMap.put(relatedEntity, ++connectionStrength);
                } else {
                    indirectEntityConnectionMap.put(relatedEntity, 1);
                }
            }
        }
        
        final Ordering<Entity> valueComparator = Ordering.natural().reverse()
                .onResultOf(Functions.forMap(indirectEntityConnectionMap))
                .compound(Ordering.natural().reverse());
        
        final SortedMap<Entity, Integer> sortedResult = ImmutableSortedMap.copyOf(
                indirectEntityConnectionMap, valueComparator);
        
        final Set<Entity> sortedRecommendedEntities = sortedResult.keySet();
        int resultsSize = 0;
        if (sortedRecommendedEntities.size() < TOTAL_RESULTS) {
            resultsSize = sortedRecommendedEntities.size();
        } else {
            resultsSize = TOTAL_RESULTS;
        }
        
        final List<Entity> result = Lists.newArrayList();
        for (int i = 0; i < resultsSize; i++) {
            result.add(Iterables.get(sortedRecommendedEntities, i));
        }
        
        return new PaginatedResult(START_INDEX, TOTAL_RESULTS, result);
    }
    
    private Set<? extends Entity> getEntities(final String userId, final Class<?> targetClass)
            throws NonExistentUser {
    
        if (targetClass == Person.class) {
            return activitymanager.getFriends(userId);
        } else if (targetClass == Event.class) {
            return activitymanager.getEvents(userId);
        } else {
            return activitymanager.getClubs(userId);
        }
    }
    
}
