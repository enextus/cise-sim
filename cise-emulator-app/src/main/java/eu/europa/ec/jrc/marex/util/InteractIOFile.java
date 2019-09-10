package eu.europa.ec.jrc.marex.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InteractIOFile {


    public static String createRef(String ServiceId,String TypeMessage, StringBuffer content) {
        return createAbsoluteRef(ServiceId, "", TypeMessage, content);
    }

    public static String createAbsoluteRef(String ServiceId, String ResultMarker, String TypeMessage, StringBuffer msg) {
        SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
        String timeMarker = DATE_TIME_FORMAT.format(new Date(System.currentTimeMillis()));
        return createRelativeRef(ServiceId, ResultMarker, timeMarker, TypeMessage, msg);
    }

    public static String createRelativeRef(String ServiceId, String ResultMarker, String TimeMarker, String TypeMessage, StringBuffer msg) {
        java.nio.file.Path p1 = null;
        p1 = Paths.get(getFilename(ServiceId, ResultMarker, TimeMarker, TypeMessage));
        try (BufferedWriter writer = Files.newBufferedWriter(p1, Charset.forName("UTF-8"))) {
            writer.write(msg.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return TimeMarker;
    }

    public static String getFilename(String ServiceId,  String ResultMarker, String timeMarker, String messageModal) {
        return ( (timeMarker==null?"":timeMarker) +"_"+ServiceId + "_" +messageModal+ (ResultMarker.isEmpty() ? "" : "_") + ResultMarker+".xml");
    }


}