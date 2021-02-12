/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Joint Research Centre (JRC) All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package eu.cise.sim.api.history;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
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
            LOGGER.debug("Added id {}, timestamp {}, size {}", id, new Date(timestamp), cacheMap.size());

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

        if (idSet.size() > 0) {
            LOGGER.debug("getCorrelationIdAfter timestamp {}, returned {} id's", new Date(timestamp), idSet.size());
        }

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

        LOGGER.debug("Reduced cache to {} removing id {} timestamp {}", cacheMap.size(), oldestId, oldestTimestamp);
    }
}
