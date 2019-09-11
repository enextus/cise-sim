package eu.europa.ec.jrc.marex.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InteractIOFile {

    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyyMMdd'T'HHmmss");

    public static String createRef(String serviceId, String typeMessage, StringBuffer content) {
        return createAbsoluteRef(serviceId, "", typeMessage, content);
    }

    public static String createAbsoluteRef(String serviceId, String resultMarker, String typeMessage, StringBuffer msg) {

        String timeMarker = DATE_TIME_FORMAT.format(new Date(System.currentTimeMillis()));
        return createRelativeRef(serviceId, resultMarker, timeMarker, typeMessage, msg);
    }

    public static String createRelativeRef(String serviceId, String resultMarker, String timeMarker, String typeMessage, StringBuffer msg) {
        java.nio.file.Path p1 = null;
        p1 = Paths.get(getFilename(serviceId, resultMarker, timeMarker, typeMessage));
        try (BufferedWriter writer = Files.newBufferedWriter(p1, StandardCharsets.UTF_8)) {
            writer.write(msg.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return timeMarker;
    }

    public static String getFilename(String serviceId, String resultMarker, String timeMarker, String messageModal) {
        return (serviceId + "_" + (timeMarker == null ? "" : timeMarker) + "_" + messageModal + (resultMarker.isEmpty() ? "" : "_") + resultMarker + ".xml");
    }
}
