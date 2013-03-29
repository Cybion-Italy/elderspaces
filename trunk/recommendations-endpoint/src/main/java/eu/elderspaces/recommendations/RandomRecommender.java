package eu.elderspaces.recommendations;

import java.util.List;

import com.google.common.collect.Lists;

import eu.elderspaces.recommendations.responses.RecommendationEntry;
import eu.elderspaces.recommendations.responses.RecommendationReport;

public class RandomRecommender implements Recommender {
    
    private static final int TOTAL_RESULTS = 3;
    private static final int START_INDEX = 0;
    
    @Override
    public RecommendationReport getFriends(final String userId) {
    
        final RecommendationReport recommendationReport = new RecommendationReport();
        recommendationReport.setStartIndex(START_INDEX);
        recommendationReport.setTotalResults(TOTAL_RESULTS);
        
        final List<RecommendationEntry> entries = Lists.newLinkedList();
        
        recommendationReport.setEntries(entries);
        
        return recommendationReport;
    }
    
    @Override
    public RecommendationReport getEvents(final String userId) {
    
        final RecommendationReport recommendationReport = new RecommendationReport();
        recommendationReport.setStartIndex(START_INDEX);
        recommendationReport.setTotalResults(TOTAL_RESULTS);
        
        final List<RecommendationEntry> entries = Lists.newLinkedList();
        
        recommendationReport.setEntries(entries);
        
        return recommendationReport;
    }
    
    @Override
    public RecommendationReport getClubs(final String userId) {
    
        final RecommendationReport recommendationReport = new RecommendationReport();
        recommendationReport.setStartIndex(START_INDEX);
        recommendationReport.setTotalResults(TOTAL_RESULTS);
        
        final List<RecommendationEntry> entries = Lists.newLinkedList();
        
        recommendationReport.setEntries(entries);
        
        return recommendationReport;
    }
    
}
