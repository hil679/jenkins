package hudson.slaves;

import hudson.model.Node;
import hudson.slaves.OfflineCause;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.kohsuke.stapler.export.ExportedBean;

import java.lang.reflect.Field;

@ExportedBean
public class OfflineHistory {
    private OfflineCause offlineCause;
    private boolean isOnline;



}
