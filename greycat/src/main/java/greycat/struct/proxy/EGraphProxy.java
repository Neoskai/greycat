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

import greycat.Container;
import greycat.Graph;
import greycat.Node;
import greycat.struct.EGraph;
import greycat.struct.ENode;

public final class EGraphProxy implements EGraph {

    private final int _index;
    private Container _target;
    private EGraph _elem;

    public EGraphProxy(final int _relationIndex, final Container _target, final EGraph _relation) {
        this._index = _relationIndex;
        this._target = _target;
        this._elem = _relation;
    }

    private void check() {
        if (_target != null) {
            _elem = (EGraph) _target.rephase().getRawAt(_index);
            _target = null;
        }
    }

    @Override
    public final int size() {
        return _elem.size();
    }

    @Override
    public final void free() {
        _elem.free();
    }

    @Override
    public final Graph graph() {
        return _elem.graph();
    }

    @Override
    public final ENode newNode() {
        check();
        return _elem.newNode();
    }

    @Override
    public final ENode root() {
        final Container _host = _target;
        if (_host != null) {
            ENode noProxy = _elem.root();
            if (noProxy == null) {
                return null;
            } else {
                return new ENodeProxy(this, noProxy, noProxy.id());
            }
        } else {
            return _elem.root();
        }
    }

    @Override
    public final ENode node(int index) {
        final Container _host = _target;
        if (_host != null) {
            return new ENodeProxy(this, _elem.node(index), index);
        } else {
            return _elem.node(index);
        }
    }

    @Override
    public final EGraph setRoot(ENode eNode) {
        check();
        return _elem.setRoot(eNode);
    }

    @Override
    public final EGraph drop(ENode eNode) {
        check();
        return _elem.drop(eNode);
    }

    EGraph rephase() {
        check();
        return _elem;
    }

    @Override
    public final String toString() {
        return _elem.toString();
    }


}
