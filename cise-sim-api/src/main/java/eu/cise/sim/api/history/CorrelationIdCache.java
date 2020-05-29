package eu.cise.sim.api.history;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CorrelationIdCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(CorrelationIdCache.class);

    private final Map<String, Long> cacheMap;
    private final int maxSize;


    public CorrelationIdCache(int maxSize) {
        this.cacheMap = new HashMap<>();
        this.maxSize = maxSize;
    }

    public void init(Map<String, Long> newMap) {

        List<Long> sortedTimestamp = newMap.values().stream().sorted().collect(Collectors.toList());
        long oldestTimestamp = sortedTimestamp.size() > maxSize ? sortedTimestamp.get(maxSize-1) : 0;

        synchronized (cacheMap) {
            cacheMap.clear();
            for (Map.Entry<String, Long> entry : newMap.entrySet()) {
                if (entry.getValue() >= oldestTimestamp) {
                    cacheMap.put(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    public void add(String correlationId, long timestamp) {

        synchronized (cacheMap) {
            cacheMap.put(correlationId, timestamp);
            checkSize();
        }
        LOGGER.info("Added id {}, timestamp {}, size {}", correlationId, timestamp, cacheMap.size());
    }

    public List<String> getCorrelationIdAfter(long timestamp) {

        List<String> corrIdList = new ArrayList<>();

        synchronized (cacheMap) {
            for (Map.Entry<String, Long> entry : cacheMap.entrySet()) {
                if (entry.getValue() > timestamp) {
                    corrIdList.add(entry.getKey());
                }
            }
        }

        LOGGER.info("getCorrelationIdAfter {}, returned {} correlation id", timestamp, corrIdList.size());

        return corrIdList;
    }

    private void checkSize() {

        if (this.cacheMap.size() <= maxSize) {
            return;
        }

        String oldestCorrId = "None";
        long oldestTimestamp = System.currentTimeMillis();
        for (Map.Entry<String, Long> entry : cacheMap.entrySet()) {
            if (entry.getValue() < oldestTimestamp) {
                oldestCorrId = entry.getKey();
                oldestTimestamp = entry.getValue();
            }
        }

        cacheMap.remove(oldestCorrId);

        LOGGER.info("Reduced cache to {} removing id {} timestamp {}", cacheMap.size(), oldestCorrId, oldestTimestamp);
    }
}
