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