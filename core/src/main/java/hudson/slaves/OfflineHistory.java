package hudson.slaves;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.model.Node;
import hudson.model.User;
import hudson.slaves.OfflineCause;
import jenkins.model.Jenkins;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.jvnet.localizer.Localizable;
import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;

import java.lang.reflect.Field;
import java.util.Date;

@ExportedBean
public class OfflineHistory {
    private transient Node node;
//    private transient User user;
    private OfflineCause offlineCause;
    private String username;
    private boolean isOnline;
    protected final long timestamp = System.currentTimeMillis();

    public OfflineHistory(Node node) {
        this.node = node;
        this.offlineCause = setOfflineCause();
        this.isOnline = isOnline(node);
        this.username = setUsername();
    }

    @NonNull
    @Exported
    public final Date getTime() {
        return new Date(timestamp);
    }

    @Exported
    public boolean getIsOnline() {
        return this.isOnline;
    }

    @Exported
    public OfflineCause getOfflineCause() {
        return this.offlineCause;
    }

    @Exported
    public String getUsername() {
        return this.username;
    }

    private boolean isOnline(Node node) {
        return node.toComputer().isOnline();
    }

    private OfflineCause setOfflineCause() {
        System.out.println(this.isOnline);
        if (this.isOnline || this.offlineCause == null)
            return new OfflineCause.ByCLI("-");
        return this.node.getTemporaryOfflineCause();
    }

    private String setUsername() {
        if (this.offlineCause != null) {
            System.out.print("setUser: ");
            System.out.println(this.offlineCause.equals(OfflineCause.UserCause.class));
            if (this.offlineCause instanceof OfflineCause.UserCause) {
                OfflineCause.UserCause userCause = (OfflineCause.UserCause) offlineCause;
                this.username = userCause.getUser().getFullName();
            } else {
                this.username = User.getUnknown().getFullName();
            }
        }
        else {
            this.username = User.current().getFullName();
        }
        return this.username;
    }

}
