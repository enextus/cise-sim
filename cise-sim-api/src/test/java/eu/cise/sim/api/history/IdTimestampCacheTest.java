package eu.cise.sim.api.history;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IdTimestampCacheTest {

    private int maxSize = 3;

    @Test
    public void init() {

        IdTimestampCache cache = getDemoCache();
        assertNotNull(cache);
        assertEquals(cache.getIdAfter(0).size(), maxSize);
        assertEquals(cache.getIdAfter(9999).size(), 0);
    }

    @Test
    public void add() {
        IdTimestampCache cache = getDemoCache();

        cache.add("e", 50L);
        cache.add("f", 52L);
        cache.add("f", 59L);

        Set idSet = cache.getIdAfter(25);
        assertEquals(cache.getIdAfter(0).size(), maxSize);
    }

    @Test
    public void getCorrelationIdAfter() {
    }

    private IdTimestampCache getDemoCache() {


        IdTimestampCache cache = new IdTimestampCache(maxSize);

        Map<String, Long> myData = new HashMap<>();
        myData.put("a", 10L);
        myData.put("b", 40L);
        myData.put("c", 30L);
        myData.put("d", 20L);

        cache.init(myData);

        return cache;
    }
}