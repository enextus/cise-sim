package eu.cise.emulator.app.resource;

import java.io.File;
import java.util.List;

public interface SourceBufferInterface {

    StringBuffer ServiceBufferParameter (String ServiceUri) ;

    StringBuffer PayloadBufferParameter (String PayloadUri) ;

    List<File> ServiceDirectoryListing(String pathParameter);

    List<File> PayloadDirectoryListing(String pathParameter);

}
