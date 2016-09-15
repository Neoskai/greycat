package org.mwg.core.task;

import org.mwg.Callback;
import org.mwg.plugin.AbstractTaskAction;
import org.mwg.task.TaskContext;

class ActionIndexesNames extends AbstractTaskAction {

    @Override
    public void eval(TaskContext context) {
        context.graph().indexes(context.world(), context.time(), new Callback<String[]>() {
            @Override
            public void on(String[] result) {
                context.continueWith(context.wrap(result));
            }
        });
    }

    @Override
    public String toString() {
        return "indexesNames()";
    }
}
