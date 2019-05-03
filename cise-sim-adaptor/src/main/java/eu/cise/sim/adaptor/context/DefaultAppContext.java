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

package eu.cise.sim.adaptor.context;

import eu.cise.adaptor.*;
import eu.cise.adaptor.dispatch.ErrorCatchingDispatcher;
import eu.cise.adaptor.signature.*;
import eu.cise.adaptor.sources.AisFileStreamGenerator;
import eu.cise.adaptor.translate.AisMsgToVessel;
import eu.cise.adaptor.translate.ServiceProfileReader;
import eu.cise.adaptor.translate.StringFluxToAisMsgFlux;
import eu.cise.adaptor.translate.VesselToPushMessage;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

/**
 *
 */
public class DefaultAppContext implements AppContext {

    private final CertificateConfig config;

    public DefaultAppContext(CertificateConfig config) {
        this.config = config;
    }

    @Override
    public AisStreamGenerator makeSource() {
        return new AisFileStreamGenerator();
    }

    @Override
    public DefaultPipeline makeStreamProcessor() {
        return new DefaultPipeline(
                new StringFluxToAisMsgFlux(),
                new AisMsgToVessel(config),
                new VesselToPushMessage(config, new ServiceProfileReader()),
                config);
    }

    @Override
    public Dispatcher makeDispatcher() {
        return new SignatureDispatcherDecorator(makeRestDispatcher(), makeSignatureService());
    }

    private SignatureService makeSignatureService() {
        DefaultCertificateRegistry registry = makeCertificateRegistry();
        PrivateKeyInfo myPrivateKey
                = new PrivateKeyInfo(config.getAdaptorId(), config.getPrivateKeyPassword());
        X509Certificate privateCertificate
                = registry.findPrivateCertificate(myPrivateKey.keyAlias());
        PrivateKey privateKey
                = registry.findPrivateKey(myPrivateKey.keyAlias(), myPrivateKey.password());
        DomSigner signer = new DefaultDomSigner(privateCertificate, privateKey);
        DomVerifier verifier = new RtiDomVerifier();

        return new DefaultSignatureService(signer, verifier);
    }

    private DefaultCertificateRegistry makeCertificateRegistry() {
        return new DefaultCertificateRegistry(
                new KeyStoreInfo(
                        config.getPrivateJKSName(),
                        config.getPrivateJKSPassword()),
                new KeyStoreInfo(
                        config.getPublicJKSName(),
                        config.getPublicJKSPassword()));
    }

    private Dispatcher makeRestDispatcher() {
        return new ErrorCatchingDispatcher(new RestDispatcher());
    }

}
