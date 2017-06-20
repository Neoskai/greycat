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
import greycat.plugin.NodeState;
import greycat.struct.IntArray;
import greycat.struct.LongLongMap;
import greycat.struct.RelationIndexed;
import greycat.utility.HashHelper;

final class CoreNodeIndex extends BaseNode implements NodeIndex {

    static final String NAME = "NodeIndex";

    CoreNodeIndex(long p_world, long p_time, long p_id, Graph p_graph) {
        super(p_world, p_time, p_id, p_graph);
    }

    @Override
    public void declareAttributes(Callback<NodeIndex> callback, String... attributeNames) {
        final NodeState state = this.phasedState();
        //TODO, re-index in case of previously init index
        state.getOrCreateAt(0, Type.RELATION_INDEXED);
        state.getOrCreateAt(1, Type.LONG_TO_LONG_MAP);
        final String[] casted = attributeNames;
        final IntArray hashes = (IntArray) state.getOrCreateAt(2, Type.INT_ARRAY);
        hashes.init(casted.length);
        for (int i = 0; i < casted.length; i++) {
            hashes.set(i, HashHelper.hash(casted[i]));
        }
        callback.on(this);
    }

    @Override
    public final long size() {
        return ((RelationIndexed) getAt(0)).size();
    }

    @Override
    public final long[] all() {
        return ((RelationIndexed) getAt(0)).all();
    }

    @Override
    public final NodeIndex clear() {
        ((RelationIndexed) getAt(0)).clear();
        return this;
    }

    @Override
    public final NodeIndex update(Node node) {
        final RelationIndexed relationIndexed = (RelationIndexed) getAt(0);
        final LongLongMap reverseMap = (LongLongMap) getAt(1);
        final IntArray hashes = (IntArray) getAt(2);
        reverseMap.put(node.id(), relationIndexed.update(reverseMap.get(node.id()), node, hashes));
        return this;
    }

    @Override
    public final void find(Callback<Node[]> callback, String... query) {
        if (query == null || query.length == 0) {
            final long[] flat = ((RelationIndexed) getAt(0)).all();
            graph().lookupAll(world(), time(), flat, callback);
        } else {
            final NodeState state = this.unphasedState();
            final RelationIndexed ri = (RelationIndexed) state.getAt(0);
            final IntArray hashes = (IntArray) state.getAt(2);
            if (hashes.size() != query.length) {
                throw new RuntimeException("Bad API usage, query param is different than index declaration");
            }
            Query queryObj = _graph.newQuery();
            queryObj.setWorld(_world);
            queryObj.setTime(_time);
            for (int i = 0; i < hashes.size(); i++) {
                queryObj.addRaw(hashes.get(i), query[i]);
            }
            ri.findByQuery(queryObj, callback);
        }
    }

    @Override
    public final void findByQuery(Query query, Callback<Node[]> callback) {
        ((RelationIndexed) getAt(0)).findByQuery(query, callback);
    }

    @Override
    public final long[] select(String... params) {
        if (params == null || params.length == 0) {
            return ((RelationIndexed) getAt(0)).all();
        } else {
            return ((RelationIndexed) getAt(0)).select(params);
        }
    }

}
