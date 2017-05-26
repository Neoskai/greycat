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

import greycat.Node;
import greycat.Action;
import greycat.Constants;
import greycat.base.BaseNode;
import greycat.TaskContext;
import greycat.TaskResult;
import greycat.struct.Buffer;

class ActionTimeSensitivity implements Action {

    private final String _delta;
    private final String _offset;

    ActionTimeSensitivity(final String delta, final String offset) {
        this._delta = delta;
        this._offset = offset;
    }

    @Override
    public void eval(final TaskContext ctx) {
        final TaskResult previousResult = ctx.result();
        final long parsedDelta = Long.parseLong(ctx.template(_delta));
        final long parsedOffset = Long.parseLong(ctx.template(_offset));
        for (int i = 0; i < previousResult.size(); i++) {
            Object loopObj = previousResult.get(i);
            if (loopObj instanceof BaseNode) {
                Node loopNode = (Node) loopObj;
                loopNode.setTimeSensitivity(parsedDelta, parsedOffset);
            }
        }
        ctx.continueTask();
    }

    @Override
    public void serialize(final Buffer builder) {
        builder.writeString(CoreActionNames.SET_ATTRIBUTE);
        builder.writeChar(Constants.TASK_PARAM_OPEN);
        builder.writeString(_delta);
        builder.writeChar(Constants.TASK_PARAM_SEP);
        builder.writeString(_offset);
        builder.writeChar(Constants.TASK_PARAM_CLOSE);
    }

    @Override
    public final String name() {
        return CoreActionNames.TIME_SENSITIVITY;
    }

}

