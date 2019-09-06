package eu.europa.ec.jrc.marex.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InteractIOFile {


    public static String createRef(String prefix,String messageModal, StringBuffer msg) {
        return createAbsoluteRef(prefix, "", messageModal, msg);
    }

    public static String createAbsoluteRef(String prefix, String postfix, String messageModal, StringBuffer msg) {
        SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
        String timeMarker = DATE_TIME_FORMAT.format(new Date(System.currentTimeMillis()));
        return createRelativeRef(prefix, postfix, timeMarker, messageModal, msg);
    }

    public static String createRelativeRef(String prefix, String postfix, String timeMarker, String messageModal, StringBuffer msg) {
        java.nio.file.Path p1 = null;
        p1 = Paths.get(getFilename(prefix, postfix, timeMarker, messageModal));
        try (BufferedWriter writer = Files.newBufferedWriter(p1, Charset.forName("UTF-8"))) {
            writer.write(msg.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return timeMarker;
    }

    public static String getFilename(String prefix,  String postfix, String timeMarker, String messageModal) {
        return (prefix + "_" + (timeMarker==null?"":timeMarker) +"_"+messageModal+"_"+ (postfix.isEmpty() ? "" : "_") + postfix+".xml");
    }


}