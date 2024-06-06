package hudson.slaves;

import hudson.XmlFile;
import hudson.model.Node;
import jenkins.model.Jenkins;
import org.apache.commons.lang.builder.EqualsBuilder;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OfflineHistoryUtils {
    private Node node;

    /**
     * Our logger.
     */
//    private static final Logger LOG = Logger
//            .getLogger(OfflineHistoryUtils.class.getName());

    public OfflineHistoryUtils(){}

    public OfflineHistoryUtils(Node node) {
        this.node = node;
    }

    public XmlFile getRecentHistory() {
        String recent = getRecentDate();
        // if (recent != null)

        XmlFile offlineHistoryXml = node.getOfflineHistoryFile(recent);
//        OfflineCause recentOfflineCause = (OfflineCause) Jenkins.XSTREAM.fromXML(offlineHistoryXml.getFile());
        return offlineHistoryXml;
    }

    public boolean dateFormatCheck(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            dateFormat.setLenient(false);
            dateFormat.parse(date);
            return true;
        } catch(ParseException e) {
            return false;
        }
    }

    private String getRecentDate() {
        String[] historyDate = new File(node.getRootDir(), "offlinehistory").list();
        Arrays.sort(historyDate, Collections.reverseOrder()); //
        for (String date : historyDate) {
            if (dateFormatCheck(date)) {
                System.out.println("date " + date);
                return date;
            }
        }
        return null;
    }

    private boolean checkExistTimeStamp() {
        String[] historyDate = new File(node.getRootDir(), "offlinehistory").list();
        if (historyDate != null)
            return true;
        return false;
    }

    public boolean hasDuplicateHistory(final String history, String... excludedFields) {
        boolean isDuplicated = false;
//        final ArrayList<String> timeStamps = new ArrayList<>(
//                getRevisions(xmlFile).keySet());
        if (checkExistTimeStamp()) {
            final XmlFile lastRevision = getRecentHistory();
            try {
                System.out.println("[check two file]");
                System.out.println(history);
                System.out.println(lastRevision.asString());
                if (history.equals(lastRevision.asString())) {
                    isDuplicated = true;
                }
            } catch (IOException e) {
//                LOG.log(Level.WARNING,
//                        "unable to check for duplicate previous history file: {0}\n{1}",
//                        new Object[]{lastRevision, e});
            }
        }
        return isDuplicated;
    }
//    public boolean reflectionDiffer(Object o1, Object o2, String... excludeFields) {
////        if (o1 == o2) return true;
//        if (o1 == null || o2 == null || o1.getClass() != o2.getClass()) return false;
//
//        EqualsBuilder builder = new EqualsBuilder();
//        Field[] fields = o1.getClass().getFields();
//        for (Field field : fields) {
//            System.out.print("field");
//            System.out.println(field);
//            if (isExcluded(field.getName(), excludeFields)) continue;
//            try {
//                field.setAccessible(true);
//                System.out.println(field.get(o1).getClass().getName());
//                System.out.println(field.get(o1));
//                System.out.println(field.get(o2));
//                builder.append(field.get(o1), field.get(o2));
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        }
//        return !EqualsBuilder.reflectionEquals(o1, o2);
//    }
//
//    private static boolean isExcluded(String fieldName, String... excludeFields) {
//        for (String excludeField : excludeFields) {
//            if (fieldName.equals(excludeField)) {
//                System.out.println(excludeField);
//                return true;
//            }
//        }
//        return false;
//    }
}
