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

import greycat.Callback;
import greycat.Constants;
import greycat.NodeIndex;
import greycat.Action;
import greycat.TaskContext;
import greycat.struct.Buffer;

class ActionGlobalIndex implements Action {

    private final String _name;

    ActionGlobalIndex(final String p_indexName) {
        if (p_indexName == null) {
            throw new RuntimeException("indexName should not be null");
        }
        _name = p_indexName;
    }

    @Override
    public void eval(final TaskContext ctx) {
        final String name = ctx.template(_name);
        ctx.graph().indexIfExists(ctx.world(), ctx.time(), name, new Callback<NodeIndex>() {
            @Override
            public void on(NodeIndex resolvedIndex) {
                if (resolvedIndex != null) {
                    ctx.continueWith(ctx.wrap(resolvedIndex));
                } else {
                    ctx.continueWith(ctx.newResult());
                }
            }
        });
    }

    @Override
    public void serialize(final Buffer builder) {
        builder.writeString(CoreActionNames.GLOBAL_INDEX);
        builder.writeChar(Constants.TASK_PARAM_OPEN);
        builder.writeChar(Constants.TASK_PARAM_CLOSE);
    }

    @Override
    public final String name() {
        return CoreActionNames.GLOBAL_INDEX;
    }
}
