package eu.cise.sim.app.resources;


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


public class SourceBufferFileSource implements SourceBufferInterface {


    private URL StringtoURL(String stringUrl) throws MalformedURLException {
            URL url = new URL((stringUrl.trim().startsWith("file://") ? stringUrl : "file://" + stringUrl));
        return url;
    }

    private URI URLtoURI(URL url) throws URISyntaxException {
            return url.toURI();
    }

    private URL getFileURL() {
        return getClass().getResource("banner.txt");
    }



 private StringBuffer AppendBufferFromUri(StringBuffer strBuffer, URI uri) throws FileNotFoundException{
        File file = new File(uri);
        if (file.exists()){
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
    public StringBuffer ServiceBufferParameter(String ServiceStringUri)  {
        StringBuffer returnable = new StringBuffer();
        URL ServiceUrl = null;
        URI ServiceUri = null;
        try {
             ServiceUrl = StringtoURL(ServiceStringUri);
             ServiceUri = URLtoURI(ServiceUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            AppendBufferFromUri(returnable,ServiceUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return returnable;
    }


    @Override
    public StringBuffer PayloadBufferParameter(String PayloadStringUri)  {
        StringBuffer returnable = new StringBuffer();
        URL PayloadUrl = null;
        URI PayloadUri = null;
        try {
            PayloadUrl = StringtoURL(PayloadStringUri);
            PayloadUri = URLtoURI(PayloadUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        try {
            AppendBufferFromUri(returnable,PayloadUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return returnable;
    }
}
