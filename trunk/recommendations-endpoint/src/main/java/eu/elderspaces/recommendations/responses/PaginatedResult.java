package eu.elderspaces.recommendations.responses;

import java.util.List;

import com.google.common.base.Objects;

public class PaginatedResult<T> {
    
    private int startIndex;
    private int totalResults;
    private List<T> entries;
    
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
    
    public List<T> getEntries() {
    
        return entries;
    }
    
    public void setEntries(final List<T> entries) {
    
        this.entries = entries;
    }
    
    @Override
    public boolean equals(final Object o) {
    
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        final PaginatedResult that = (PaginatedResult) o;
        
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
