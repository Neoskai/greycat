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
import greycat.struct.*;

public final class TreeProxy implements Tree {

    private final int _relationIndex;
    private Container _target;
    private Tree _elem;

    public TreeProxy(final int _relationIndex, final Container _target, final Tree _relation) {
        this._relationIndex = _relationIndex;
        this._target = _target;
        this._elem = _relation;
    }

    private void check() {
        if (_target != null) {
            _elem = (Tree) _target.rephase().getRawAt(_relationIndex);
            _target = null;
        }
    }

    @Override
    public final TreeResult queryAround(final double[] keys, final int max) {
        return _elem.queryAround(keys, max);
    }

    @Override
    public final TreeResult queryRadius(final double[] keys, final double radius) {
        return _elem.queryRadius(keys, radius);
    }

    @Override
    public final TreeResult queryBoundedRadius(final double[] keys, final double radius, final int max) {
        return _elem.queryBoundedRadius(keys, radius, max);
    }

    @Override
    public final TreeResult queryArea(final double[] min, final double[] max) {
        return _elem.queryArea(min, max);
    }

    @Override
    public final long size() {
        return _elem.size();
    }

    @Override
    public final long treeSize() {
        return _elem.treeSize();
    }

    @Override
    public final void setDistance(final int distanceType) {
        check();
        _elem.setDistance(distanceType);
    }

    @Override
    public final void setResolution(final double[] resolution) {
        check();
        _elem.setResolution(resolution);
    }

    @Override
    public final void setMinBound(final double[] min) {
        check();
        _elem.setMinBound(min);
    }

    @Override
    public final void setMaxBound(final double[] max) {
        check();
        _elem.setMaxBound(max);
    }

    @Override
    public final void insert(final double[] keys, final long value) {
        check();
        _elem.insert(keys, value);
    }

    @Override
    public final String toString() {
        return _elem.toString();
    }

}
