/*
 * The MIT License
 *
 * Copyright 2013 Jesse Glick.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package jenkins.widgets;

import hudson.Functions;
import hudson.Util;
import hudson.model.BallColor;
import hudson.model.Node;
import hudson.model.Run;
import hudson.slaves.OfflineCause;
import hudson.slaves.OfflineHistory;
import jenkins.console.ConsoleUrlProvider;
import net.sf.json.JSONObject;
import org.jenkins.ui.icon.Icon;
import org.kohsuke.accmod.Restricted;
import org.kohsuke.accmod.restrictions.DoNotUse;

import java.time.Instant;

@Restricted(DoNotUse.class) // only for nodeOfflineListTable.jelly
public class NodeOfflineListTable extends OfflineListProgressiveRendering{ // extends RunListProgressiveRendering compute참고
    @Override protected void calculate(OfflineHistory offlineHistory, JSONObject element) {
        BallColor blue = BallColor.BLUE;
        BallColor red = BallColor.RED;
        if (offlineHistory.getIsOnline()) {
            element.put("OnlineOffline", blue.getIconName());
        } else {
            element.put("OnlineOffline", red.getIconName());
        }
        element.put("Date", String.valueOf(offlineHistory.getTime()));
        element.put("offlineReason", offlineHistory.getOfflineCause().toString());
        element.put("User", offlineHistory.getUsername());

        System.out.println(element.get("OnlineOffline"));
        System.out.println(element.get("User"));

    }

}