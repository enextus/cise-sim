package eu.cise.emulator.deprecated.web.app.resource;

import java.io.File;
import java.util.List;

public interface SourceBufferInterface {

    StringBuffer getReferenceFileContent(String payloadUri);

    List<File> getReferenceDirectoryListing(String pathParameter);

}
