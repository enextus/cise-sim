/*
 * Copyright CISE AIS Adaptor (c) 2018, European Union
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


package eu.cise.sim.adaptor;


import static org.aeonbits.owner.Config.Sources;

/**
 * Extending the {AdaptorConfig} configuration object adding properties
 * specific to the Certificate and Signature
 */
@SuppressWarnings("unused")
@Sources({"file:${conf.dir}ais-adaptor.properties",
        "classpath:ais-adaptor.properties"})
public interface CertificateConfig extends AdaptorConfig {

    @DefaultValue("eu.cise.es.gc-ls01")
    @Key("adaptor.id")
    String getAdaptorId();

    @DefaultValue("cisePrivate.jks")
    @Key("signature.private.jks.filename")
    String getPrivateJKSName();

    @DefaultValue("cisecise")
    @Key("signature.private.jks.password")
    String getPrivateJKSPassword();

    @DefaultValue("cisecise")
    @Key("signature.private.key.password")
    String getPrivateKeyPassword();

    @DefaultValue("cisePublic.jks")
    @Key("signature.public.jks.filename")
    String getPublicJKSName();

    @DefaultValue("cisecise")
    @Key("signature.public.jks.password")
    String getPublicJKSPassword();
}
