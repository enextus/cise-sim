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

package eu.cise.sim.dropw.context;

import eu.cise.sim.config.ProxyManager;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class DefaultProxyManager implements ProxyManager {

    private final String host;
    private final String port;

    public static void checkConfiguration(String host, String port) {

        if (!StringUtils.isEmpty(host) || !StringUtils.isEmpty(port)) {
            Pattern p = Pattern.compile("^"
                    + "(([0-9]{1,3}\\.){3})[0-9]{1,3}" // Ip
                    + ":"
                    + "[0-9]{1,5}$"); // Port

            if (!p.matcher(host + ":" + port).matches()) {
                throw new ConfigurationException("PROXY: wrong couple host and port configuration host[" + host + "] port[" + port + "]");
            }
        }
    }

    public DefaultProxyManager(String host, String port) {

        checkConfiguration(host, port);

        this.host = host;
        this.port = port;
    }

    @Override
    public String activate() {

        String result = "PROXY: no proxy configured";
        if (!StringUtils.isEmpty(host)) {

            System.setProperty("http.proxyHost", host);
            System.setProperty("http.proxyPort", port);
            result = "PROXY: activated on host[" + host + "] port[" + port + "]";
        }
        return result;
    }
}