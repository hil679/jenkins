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

import hudson.model.Node;
import hudson.model.Run;
import hudson.util.RunList;
import jenkins.util.ProgressiveRendering;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.kohsuke.accmod.Restricted;
import org.kohsuke.accmod.restrictions.NoExternalUse;

import java.util.ArrayList;
import java.util.List;

/**
 * Makes it possible to incrementally render some information from a {@link RunList}.
 */
@Restricted(NoExternalUse.class)
public abstract class OfflineListProgressiveRendering extends ProgressiveRendering {

    /**
     * Since we cannot predict how many runs there will be, just show an ever-growing progress bar.
     * The first increment will be sized as if this many runs will be in the total,
     * but then like Zeno’s paradox we will never seem to finish until we actually do.
     */
    private static final double MAX_LIKELY_RUNS = 20;
    private final List<JSONObject> results = new ArrayList<>();
    private Node node;

    /** Jelly cannot call a constructor with arguments. */
    public void setNode(Node node) {
        this.node = node;
    }

    @Override protected void compute() throws Exception {
        double decay = 1;
        JSONObject element = new JSONObject();
        calculate(node, element);
        results.add(element);
//        for (Run<?, ?> build : builds) {
//            if (canceled()) {
//                return;
//            }
//            JSONObject element = new JSONObject();
//            calculate(node, element);
//            synchronized (this) {
//                results.add(element);
//            }
//            decay *= 1 - 1 / MAX_LIKELY_RUNS;
//            progress(1 - decay);
//        }
    }

    @Override protected synchronized JSON data() {
        JSONArray d = JSONArray.fromObject(results);
        results.clear();
        return d;
    }

    protected abstract void calculate(Node node, JSONObject element);
}
