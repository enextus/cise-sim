package eu.cise.emulator.app.resource;

import java.io.File;
import java.util.List;

public interface SourceBufferInterface {

    StringBuffer serviceBufferParameter(String serviceUri);

    StringBuffer payloadBufferParameter(String payloadUri);

    List<File> serviceDirectoryListing(String pathParameter);

    List<File> payloadDirectoryListing(String pathParameter);

}
