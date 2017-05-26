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
package greycat.struct.proxy;

import greycat.Callback;
import greycat.Container;
import greycat.Node;
import greycat.Query;
import greycat.struct.Relation;
import greycat.struct.RelationIndexed;

public final class RelationIndexedProxy implements RelationIndexed {

    private final int _index;
    private Container _target;
    private RelationIndexed _elem;

    public RelationIndexedProxy(final int _relationIndex, final Container _target, final RelationIndexed _relation) {
        this._index = _relationIndex;
        this._target = _target;
        this._elem = _relation;
    }

    private void check() {
        if (_target != null) {
            _elem = (RelationIndexed) _target.rephase().getRawAt(_index);
            _target = null;
        }
    }

    @Override
    public final int size() {
        return _elem.size();
    }

    @Override
    public final long getByIndex(final int index) {
        return _elem.getByIndex(index);
    }

    @Override
    public final long[] all() {
        return _elem.all();
    }

    @Override
    public final void find(final Callback<Node[]> callback, final long world, final long time, final String... params) {
        _elem.find(callback, world, time, params);
    }

    @Override
    public final long[] select(final String... params) {
        return _elem.select(params);
    }

    @Override
    public final long[] selectByQuery(final Query query) {
        return _elem.selectByQuery(query);
    }

    @Override
    public final void findByQuery(final Query query, final Callback<Node[]> callback) {
        _elem.findByQuery(query, callback);
    }

    @Override
    public final RelationIndexed add(final Node node, final String... attributeNames) {
        check();
        return _elem.add(node, attributeNames);
    }

    @Override
    public final RelationIndexed remove(final Node node, final String... attributeNames) {
        check();
        return _elem.remove(node, attributeNames);
    }

    @Override
    public final RelationIndexed clear() {
        check();
        return _elem.clear();
    }

    @Override
    public final String toString() {
        return _elem.toString();
    }

}
