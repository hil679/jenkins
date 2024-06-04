package hudson.slaves;

import hudson.XmlFile;
import hudson.model.Node;
import jenkins.model.Jenkins;
import org.apache.commons.lang.builder.EqualsBuilder;

import java.io.File;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;

public class OfflineHistoryUtils {
    private Node node;

    public OfflineHistoryUtils(){}

    public OfflineHistoryUtils(Node node) {
        this.node = node;
    }

    public OfflineCause getRecentHistory() {
        String recent = getRecentDate();
        // if (recent != null)

        XmlFile offlineHistoryXml = node.getOfflineHistoryFile(recent);
        OfflineCause recentOfflineCause = (OfflineCause) Jenkins.XSTREAM.fromXML(offlineHistoryXml.getFile());
        return recentOfflineCause;
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

    public boolean reflectionDiffer(Object o1, Object o2, String... excludeFields) {
//        if (o1 == o2) return true;
        if (o1 == null || o2 == null || o1.getClass() != o2.getClass()) return false;

        EqualsBuilder builder = new EqualsBuilder();
        Field[] fields = o1.getClass().getFields();
        for (Field field : fields) {
            System.out.print("field");
            System.out.println(field);
            if (isExcluded(field.getName(), excludeFields)) continue;
            try {
                field.setAccessible(true);
                System.out.println(field.get(o1).getClass().getName());
                System.out.println(field.get(o1));
                System.out.println(field.get(o2));
                builder.append(field.get(o1), field.get(o2));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return !builder.isEquals();
    }

    private static boolean isExcluded(String fieldName, String... excludeFields) {
        for (String excludeField : excludeFields) {
            if (fieldName.equals(excludeField)) {
                System.out.println(excludeField);
                return true;
            }
        }
        return false;
    }
}
