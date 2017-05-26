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
package greycat.internal;

import greycat.*;
import greycat.base.BaseNode;
import greycat.struct.RelationIndexed;

final class CoreNodeIndex extends BaseNode implements NodeIndex {

    static final String NAME = "NodeIndex";

    CoreNodeIndex(long p_world, long p_time, long p_id, Graph p_graph) {
        super(p_world, p_time, p_id, p_graph);
    }

    @Override
    public final void init() {
        getOrCreate(CoreConstants.INDEX_ATTRIBUTE, Type.RELATION_INDEXED);
    }

    @Override
    public final long size() {
        return ((RelationIndexed) get(CoreConstants.INDEX_ATTRIBUTE)).size();
    }

    @Override
    public final long[] all() {
        return ((RelationIndexed) get(CoreConstants.INDEX_ATTRIBUTE)).all();
    }

    @Override
    public final NodeIndex addToIndex(Node node, String... attributeNames) {
        ((RelationIndexed) get(CoreConstants.INDEX_ATTRIBUTE)).add(node, attributeNames);
        return this;
    }

    @Override
    public final NodeIndex removeFromIndex(Node node, String... attributeNames) {
        ((RelationIndexed) get(CoreConstants.INDEX_ATTRIBUTE)).remove(node, attributeNames);
        return this;
    }

    @Override
    public final NodeIndex clear() {
        ((RelationIndexed) get(CoreConstants.INDEX_ATTRIBUTE)).clear();
        return this;
    }

    @Override
    public final void find(Callback<Node[]> callback, String... query) {
        if (query == null || query.length == 0) {
            long[] flat = ((RelationIndexed) get(CoreConstants.INDEX_ATTRIBUTE)).all();
            graph().lookupAll(world(), time(), flat, callback);
        } else {
            ((RelationIndexed) get(CoreConstants.INDEX_ATTRIBUTE)).find(callback, world(), time(), query);
        }
    }

    @Override
    public final void findByQuery(Query query, Callback<Node[]> callback) {
        ((RelationIndexed) get(CoreConstants.INDEX_ATTRIBUTE)).findByQuery(query, callback);
    }

    @Override
    public final long[] select(String... params) {
        if (params == null || params.length == 0) {
            return ((RelationIndexed) get(CoreConstants.INDEX_ATTRIBUTE)).all();
        } else {
            return ((RelationIndexed) get(CoreConstants.INDEX_ATTRIBUTE)).select(params);
        }
    }

}
