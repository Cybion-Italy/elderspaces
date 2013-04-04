package eu.elderspaces.activities.persistence;

import java.util.List;

import com.google.common.collect.Lists;

import eu.elderspaces.model.Call;

public class InMemoryActivityRepository implements ActivityRepository {
    
    List<Call> calls = Lists.newLinkedList();
    
    @Override
    public void shutDownRepository() {
    
        // Do nothing
    }
    
    @Override
    public boolean store(final Call call) {
    
        return calls.add(call);
    }
    
}
