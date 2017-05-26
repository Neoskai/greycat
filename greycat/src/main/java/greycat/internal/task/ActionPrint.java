/**
 * Copyright 2017 The GreyCat Authors.  All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package greycat.internal.task;

import greycat.Constants;
import greycat.Action;
import greycat.TaskContext;
import greycat.struct.Buffer;

class ActionPrint implements Action {

    private final String _name;
    private final boolean _withLineBreak;

    ActionPrint(final String p_name, boolean withLineBreak) {
        this._name = p_name;
        this._withLineBreak = withLineBreak;
    }

    @Override
    public void eval(final TaskContext ctx) {
        if (_withLineBreak) {
            ctx.append(ctx.template(_name) + '\n');
        } else {
            ctx.append(ctx.template(_name));
        }
        ctx.continueTask();
    }

    @Override
    public void serialize(final Buffer builder) {
        if (_withLineBreak) {
            builder.writeString(CoreActionNames.PRINTLN);
        } else {
            builder.writeString(CoreActionNames.PRINT);
        }
        builder.writeChar(Constants.TASK_PARAM_OPEN);
        TaskHelper.serializeString(_name, builder, true);
        builder.writeChar(Constants.TASK_PARAM_CLOSE);
    }

    @Override
    public final String name() {
        return CoreActionNames.PRINT;
    }

}
