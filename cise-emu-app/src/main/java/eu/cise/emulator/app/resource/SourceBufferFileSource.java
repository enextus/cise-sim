package eu.cise.emulator.app.resource;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class SourceBufferFileSource implements SourceBufferInterface {


    private URL stringtoURL(String stringUrl) throws MalformedURLException {
        URL url = new URL((stringUrl.trim().startsWith("file://") ? stringUrl : "file://" + stringUrl));
        return url;
    }

    private URI urltouri(URL url) throws URISyntaxException {
        return url.toURI();
    }

    private URL getFileURL() {
        return getClass().getResource("banner.txt");
    }

    /**
     * getDirectoryListing :return List of File within directory : by using  lambda and file nio
     */
    private List<File> getDirectoryListing(String originpath) {
        List<File> directoryListing = new ArrayList<>();
        try (Stream<Path> walk = Files.walk(Paths.get(originpath))) {

            directoryListing = walk.filter(Files::isRegularFile)
                    .map(x -> x.toFile()).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return directoryListing;
    }

    private StringBuffer appendBufferFromUri(StringBuffer strBuffer, URI uri) throws FileNotFoundException {
        File file = new File(uri);
        if (file.exists()) {
            FileInputStream in = new FileInputStream(file);
            try (FileChannel channel = in.getChannel()) {
                ByteBuffer byteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
                Charset charset = Charset.defaultCharset();
                CharsetDecoder decoder = charset.newDecoder();
                CharBuffer charBuffer = decoder.decode(byteBuffer);
                channel.close();
                strBuffer.append(charBuffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return strBuffer;
    }


    @Override
    public StringBuffer serviceBufferParameter(String serviceStringUri) {
        StringBuffer returnable = new StringBuffer();
        URL serviceurl = null;
        URI serviceUri = null;
        try {
            serviceurl = stringtoURL(serviceStringUri);
            serviceUri = urltouri(serviceurl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            appendBufferFromUri(returnable, serviceUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return returnable;
    }


    @Override
    public StringBuffer payloadBufferParameter(String payloadStringUri) {
        StringBuffer returnable = new StringBuffer();
        URL payloadUrl = null;
        URI payloadUri = null;
        try {
            payloadUrl = stringtoURL(payloadStringUri);
            payloadUri = urltouri(payloadUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        try {
            appendBufferFromUri(returnable, payloadUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return returnable;
    }

    @Override
    public List<File> serviceDirectoryListing(String pathParameter) {
        return getDirectoryListing(pathParameter);
    }

    @Override
    public List<File> payloadDirectoryListing(String pathParameter) {
        return getDirectoryListing(pathParameter);
    }
}
