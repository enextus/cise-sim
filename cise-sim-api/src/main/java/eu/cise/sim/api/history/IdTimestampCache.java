package eu.cise.sim.api.history;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class IdTimestampCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(IdTimestampCache.class);

    private final Map<String, Long> cacheMap;
    private final int maxSize;

    public IdTimestampCache(int maxSize) {
        this.cacheMap = new HashMap<>();
        this.maxSize = maxSize;
    }

    public void init(Map<String, Long> newMap) {

        List<Long> sortedTimestamp = newMap.values().stream()
                .sorted()
                .collect(Collectors.toList());

        long oldestTimestamp = sortedTimestamp.size() > maxSize ? sortedTimestamp.get(sortedTimestamp.size() - maxSize) : 0;

        synchronized (cacheMap) {
            cacheMap.clear();
            cacheMap.putAll(newMap.entrySet().stream()
                    .filter(entry -> entry.getValue() >= oldestTimestamp)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
       }

        LOGGER.info("init with size {} and oldestTimestamp {}", cacheMap.size(), oldestTimestamp);
    }

    public void add(String id, long timestamp) {

        synchronized (cacheMap) {
            cacheMap.put(id, timestamp);
            LOGGER.info("Added id {}, timestamp {}, size {}", id, timestamp, cacheMap.size());

            checkSize();
        }
    }

    public Set<String> getIdAfter(long timestamp) {

        Set<String> idSet;

        synchronized (cacheMap) {

           idSet = cacheMap.entrySet().stream()
                    .filter(entry -> entry.getValue() > timestamp)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toSet());
        }

        LOGGER.info("getCorrelationIdAfter timestamp {}, returned {} id's", timestamp, idSet.size());

        return idSet;
    }

    private void checkSize() {

        if (this.cacheMap.size() <= maxSize) {
            return;
        }

        String oldestId = "None";
        long oldestTimestamp = System.currentTimeMillis();
        for (Map.Entry<String, Long> entry : cacheMap.entrySet()) {
            if (entry.getValue() < oldestTimestamp) {
                oldestId = entry.getKey();
                oldestTimestamp = entry.getValue();
            }
        }

        cacheMap.remove(oldestId);

        LOGGER.info("Reduced cache to {} removing id {} timestamp {}", cacheMap.size(), oldestId, oldestTimestamp);
    }
}
