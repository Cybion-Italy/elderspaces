package eu.elderspaces.recommendations.responses;

import java.util.List;

import com.google.common.base.Objects;

public class RecommendationReport {
    
    private int startIndex;
    private int totalResults;
    private List<RecommendationEntry> entries;
    
    public int getStartIndex() {
    
        return startIndex;
    }
    
    public void setStartIndex(final int startIndex) {
    
        this.startIndex = startIndex;
    }
    
    public int getTotalResults() {
    
        return totalResults;
    }
    
    public void setTotalResults(final int totalResults) {
    
        this.totalResults = totalResults;
    }
    
    public List<RecommendationEntry> getEntries() {
    
        return entries;
    }
    
    public void setEntries(final List<RecommendationEntry> entries) {
    
        this.entries = entries;
    }
    
    @Override
    public boolean equals(final Object o) {
    
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        final RecommendationReport that = (RecommendationReport) o;
        
        return Objects.equal(startIndex, that.startIndex)
                && Objects.equal(totalResults, that.totalResults)
                && Objects.equal(entries, that.entries);
        
    }
    
    @Override
    public int hashCode() {
    
        return Objects.hashCode(startIndex, totalResults, entries);
    }
    
    @Override
    public String toString() {
    
        return Objects.toStringHelper(this).addValue(startIndex).addValue(totalResults)
                .addValue(entries).toString();
    }
    
}
