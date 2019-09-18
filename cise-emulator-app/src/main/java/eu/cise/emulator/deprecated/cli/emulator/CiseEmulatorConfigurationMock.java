package eu.cise.emulator.deprecated.cli.emulator;

import eu.cise.emulator.deprecated.cli.CiseEmulatorConfiguration;

public class CiseEmulatorConfigurationMock {

    static CiseEmulatorConfiguration getMinimalSenderConfiguration() {
        CiseEmulatorConfiguration ciseEmulatorConfiguration = new CiseEmulatorConfiguration();
        //operation x 8
        ciseEmulatorConfiguration.setOperationMode("EGN");
        ciseEmulatorConfiguration.setPublishedId("egn.fr");
        ciseEmulatorConfiguration.setInputDirectory("/tmp/");
        ciseEmulatorConfiguration.setOutputDirectory("/tmp/");
        ciseEmulatorConfiguration.setServiceMode("SOAP");
        ciseEmulatorConfiguration.setSourceMessageDir("/tmp/");
        ciseEmulatorConfiguration.setSourcePayloadDir("/tmp/");
        ciseEmulatorConfiguration.setCounterpartUrl("http://unknown");
        ciseEmulatorConfiguration.setSignatureOnReceive("true");
        ciseEmulatorConfiguration.setSignatureOnSend("true");
        ciseEmulatorConfiguration.setKeyStoreFileName("./test.jks");
        ciseEmulatorConfiguration.setKeyStorePassword("secret");
        ciseEmulatorConfiguration.setCertificateKey("test");
        ciseEmulatorConfiguration.setCertificatePassword("secret");
        return ciseEmulatorConfiguration;
    }

    static CiseEmulatorConfiguration getMinimalCliServerConfiguration() {
        CiseEmulatorConfiguration ciseEmulatorConfiguration = new CiseEmulatorConfiguration();
        //operation x 8
        ciseEmulatorConfiguration.setOperationMode("EGN");
        ciseEmulatorConfiguration.setPublishedId("egn.fr");
        ciseEmulatorConfiguration.setInputDirectory("/tmp/");
        ciseEmulatorConfiguration.setOutputDirectory("/tmp/");
        ciseEmulatorConfiguration.setServiceMode("SOAP");
        ciseEmulatorConfiguration.setSourceMessageDir("/tmp/");
        ciseEmulatorConfiguration.setSourcePayloadDir("/tmp/");
        ciseEmulatorConfiguration.setCounterpartUrl("http://unknown");
        ciseEmulatorConfiguration.setSignatureOnReceive("true");
        ciseEmulatorConfiguration.setSignatureOnSend("true");
        ciseEmulatorConfiguration.setKeyStoreFileName("./test.jks");
        ciseEmulatorConfiguration.setKeyStorePassword("secret");
        ciseEmulatorConfiguration.setCertificateKey("test");
        ciseEmulatorConfiguration.setCertificatePassword("secret");
        return ciseEmulatorConfiguration;
    }
}
