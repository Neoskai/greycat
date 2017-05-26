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
package greycat;

import greycat.internal.BlackHoleStorage;
import greycat.internal.CoreGraph;
import greycat.scheduler.TrampolineScheduler;
import greycat.internal.ReadOnlyStorage;
import greycat.plugin.Plugin;
import greycat.plugin.Scheduler;
import greycat.plugin.Storage;

/**
 * Creates an instance of a Graph, with several customizable features.
 */
public class GraphBuilder {

    private Storage _storage = null;
    private Scheduler _scheduler = null;
    private Plugin[] _plugins = null;
    private long _memorySize = -1;
    private long _batchSize = -1;
    private boolean _readOnly = false;
    private boolean _deepPriority = true;

    public static GraphBuilder newBuilder() {
        return new GraphBuilder();
    }

    /**
     * Sets the storage system to the given parameter.
     *
     * @param storage the storage system to be used by the graph
     * @return the {@link GraphBuilder}, for a fluent API
     */
    public GraphBuilder withStorage(Storage storage) {
        this._storage = storage;
        return this;
    }

    /**
     * Sets the storage system to the given parameter, in read-only mode.
     *
     * @param storage the storage system to be used by the graph in read-only mode
     * @return the {@link GraphBuilder}, for a fluent API
     */
    public GraphBuilder withReadOnlyStorage(Storage storage) {
        this._storage = storage;
        _readOnly = true;
        return this;
    }

    public GraphBuilder withSaveBatchSize(long numberOfchunks) {
        this._batchSize = numberOfchunks;
        return this;
    }

    /**
     * Sets the maximum size of the memory that can be used before automated unload.
     *
     * @param numberOfElements the number of elements in memory before unloading
     * @return the {@link GraphBuilder}, for a fluent API
     */
    public GraphBuilder withMemorySize(long numberOfElements) {
        this._memorySize = numberOfElements;
        return this;
    }

    /**
     * Sets the scheduler to be used by the graph
     *
     * @param scheduler an instance of scheduler
     * @return the {@link GraphBuilder}, for a fluent API
     */
    public GraphBuilder withScheduler(Scheduler scheduler) {
        this._scheduler = scheduler;
        return this;
    }

    /**
     * Declare a plugin to the graph builder.
     *
     * @param plugin that has to be added
     * @return the {@link GraphBuilder}, for a fluent API
     */
    public GraphBuilder withPlugin(Plugin plugin) {
        if (_plugins == null) {
            _plugins = new Plugin[1];
            _plugins[0] = plugin;
        } else {
            Plugin[] _plugins2 = new Plugin[_plugins.length + 1];
            System.arraycopy(_plugins, 0, _plugins2, 0, _plugins.length);
            _plugins2[_plugins.length] = plugin;
            _plugins = _plugins2;
        }
        return this;
    }

    public GraphBuilder withDeepWorld() {
        _deepPriority = Constants.DEEP_WORLD;
        return this;
    }

    public GraphBuilder withWideWorld() {
        _deepPriority = Constants.WIDE_WORLD;
        return this;
    }

    public Graph build() {
        if (_storage == null) {
            _storage = new BlackHoleStorage();
        }
        if (_readOnly) {
            _storage = new ReadOnlyStorage(_storage);
        }
        if (_scheduler == null) {
            _scheduler = new TrampolineScheduler();
        }
        if (_memorySize == -1) {
            _memorySize = 100000;
        }
        return new CoreGraph(_storage, _memorySize, _batchSize, _scheduler, _plugins, _deepPriority);
    }

}
