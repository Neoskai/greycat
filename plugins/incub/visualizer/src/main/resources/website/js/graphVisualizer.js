/// <reference path="layout.ts" />
/// <reference path="mwg/mwg.d.ts" />
/// <reference path="mwg/mwg.ws.d.ts" />
var WSClient = org.mwg.plugin.WSClient;
var Actions = org.mwg.task.Actions;
var Type = org.mwg.Type;
var HeapRelationship = org.mwg.core.chunk.heap.HeapRelationship;
var CoreDeferCounterSync = org.mwg.core.utility.CoreDeferCounterSync;
var GraphVisu = (function () {
    function GraphVisu(url) {
        this._time = 0;
        this._world = 0;
        this._depth = 10;
        this._mapTypeColor = new Object();
        this._previousSelect = -1;
        this._graph = new org.mwg.GraphBuilder()
            .withStorage(new org.mwg.plugin.WSClient(url))
            .build();
        this._graphVisu = window.Viva.Graph.graph();
        this._mapTypeColor['default'] = 0x009ee8ff;
    }
    return GraphVisu;
}());
function printNodeDetails(nodeId, graphVisu) {
    Actions
        .setTime(graphVisu._time + "")
        .setWorld(graphVisu._world + "")
        .inject("{\n")
        .asGlobalVar("string")
        .lookup(nodeId + "")
        .asGlobalVar("node")
        .then(function (context) {
        var res = context.variable("string").get(0);
        var n = context.variable("node").get(0);
        res += "  _id=" + n.id() + "\n";
        res += "  _type=" + (n.nodeTypeName() || 'default') + "\n";
        context.setGlobalVariable("string", res);
        context.continueTask();
    })
        .subTasks([Actions.propertiesWithTypes(Type.BOOL),
        Actions.propertiesWithTypes(Type.INT),
        Actions.propertiesWithTypes(Type.DOUBLE),
        Actions.propertiesWithTypes(Type.LONG),
        Actions.propertiesWithTypes(Type.STRING),
        Actions.propertiesWithTypes(Type.RELATION)])
        .foreach(Actions.then(function (context) {
        var res = context.variable("string").get(0);
        var n = context.variable("node").get(0);
        if (typeof context.result().get(0) != "number") {
            res += "  " + context.result().get(0) + "=" + n.get(context.result().get(0)) + "\n";
        }
        else {
            res += "  " + context.result().get(0) + "=" + n.getByIndex(context.result().get(0)) + "\n";
        }
        context.setGlobalVariable("string", res);
        context.continueTask();
    }))
        .fromVar("node")
        .propertiesWithTypes(Type.LONG_TO_LONG_ARRAY_MAP)
        .ifThen(function (context) {
        return context.result().size() > 0;
    }, Actions.then(function (context) {
        var n = context.variable("node").get(0);
        var map = n.get(context.result().get(0));
        var index = 0;
        var res = context.variable("string").get(0);
        res += "  " + context.result().get(0) + "=[";
        map.each(function (key, value) {
            res += (value + "");
            if ((index + 1) < map.size()) {
                res += ",";
            }
            index++;
        });
        res += "]\n";
        context.setGlobalVariable("string", res);
        context.continueTask();
    }))
        .fromVar("string")
        .execute(graphVisu._graph, function (result) {
        document.getElementById("nodeDetail").innerHTML = result.get(0) + "}";
    });
}
function connect(graphVisu, idDiv) {
    graphVisu._graph.connect(function (succeed) {
        if (succeed) {
            var graphics = window.Viva.Graph.View.webglGraphics();
            graphics.node(function (node) {
                return window.Viva.Graph.View.webglSquare(12, graphVisu._mapTypeColor[node.data._type]);
            });
            window.Viva.Graph.webglInputEvents(graphics, graphVisu._graphVisu)
                .click(function (selectedNode) {
                console.log("Selected");
                if (selectedNode.id != graphVisu._previousSelect) {
                    printNodeDetails(selectedNode.id, graphVisu);
                    var selectedNodeUI = graphVisu._renderer.getGraphics().getNodeUI(selectedNode.id);
                    var currentColor = selectedNodeUI.color;
                    selectedNodeUI.color = 0xFFA500ff;
                    if (graphVisu._previousSelect != -1) {
                        var previousSelected = graphVisu._renderer.getGraphics().getNodeUI(graphVisu._previousSelect);
                        previousSelected.color = graphVisu._previousColor;
                    }
                    graphVisu._previousSelect = selectedNode.id;
                    graphVisu._previousColor = currentColor;
                }
            });
            graphVisu._renderer = window.Viva.Graph.View.renderer(graphVisu._graphVisu, {
                container: document.getElementById(idDiv),
                graphics: graphics
            });
            graphVisu._renderer.run();
            //
            // setTimeout(function(){
            //     graphVisu._renderer.pause();
            // },10000);
            drawGraph(graphVisu);
        }
        else {
            console.error("Problem during connection.");
        }
    });
}
function drawGraph(graphVisu) {
    var task = Actions.newTask();
    graphVisu._graphVisu.clear();
    task
        .indexesNames()
        .foreach(Actions
        .fromIndexAll("{{result}}")
        .asGlobalVar("toVisit")
        .foreach(Actions.then(function (context) {
        var node = context.resultAsNodes().get(0);
        var id = node.id();
        var nodeType = node.nodeTypeName() || 'default';
        graphVisu._graphVisu.addNode(id, { _type: nodeType });
        if (graphVisu._mapTypeColor[nodeType] == null) {
            graphVisu._mapTypeColor[nodeType] = getRandomColor();
        }
        context.continueTask();
    }))
        .fromVar("toVisit")
        .loop("1", graphVisu._depth + "", Actions
        .defineVar("nextToVisit")
        .fromVar("toVisit")
        .foreach(Actions
        .asGlobalVar("currentNode")
        .then(function (context) {
        var node = context.result().get(0);
        context.addToGlobalVariable("alreadyVisit", node.id());
        context.continueTask();
    })
        .propertiesWithTypes(Type.RELATION)
        .foreach(Actions.asVar("relationName")
        .fromVar("currentNode")
        .traverse("{{relationName}}")
        .ifThenElse(function (context) {
        return context.result().size() > 0;
    }, Actions.foreach(Actions.then(function (context) {
        var alreadyVisit = context.variable("alreadyVisit");
        var srcNode = context.variable("currentNode").get(0).id();
        var result = context.resultAsNodes().get(0);
        var alreadyVisited = false;
        for (var i = 0; i < alreadyVisit.size(); i++) {
            alreadyVisited = alreadyVisited || (result.id() == alreadyVisit.get(i));
            if (alreadyVisited) {
                break;
            }
        }
        if (!alreadyVisited) {
            var nodeType = result.nodeTypeName() || 'default';
            if (graphVisu._mapTypeColor[nodeType] == null) {
                graphVisu._mapTypeColor[nodeType] = getRandomColor();
            }
            graphVisu._graphVisu.addNode(result.id(), { _type: nodeType });
            var nextToVisit = context.variable("nextToVisit");
            var alreadyAdded = false;
            for (var ntv = 0; ntv < nextToVisit.size(); ntv++) {
                alreadyAdded = alreadyAdded || (result.id() == nextToVisit.get(ntv).id());
                if (alreadyAdded) {
                    break;
                }
            }
            if (!alreadyAdded) {
                context.addToGlobalVariable("nextToVisit", result);
            }
        }
        graphVisu._graphVisu.addLink(srcNode, result.id());
        context.continueTask();
    })), Actions.then(function (context) {
        var node = context.variable("currentNode").get(0);
        var hashReation = context.variable("relationName").get(0);
        node.relByIndex(hashReation, function (nodes) {
            var alreadyVisit = context.variable("alreadyVisit");
            var srcNode = context.variable("currentNode").get(0).id();
            for (var i = 0; i < nodes.length; i++) {
                var result = nodes[i];
                var alreadyVisited = false;
                for (var i = 0; i < alreadyVisit.size(); i++) {
                    alreadyVisited = alreadyVisited || (result.id() == alreadyVisit.get(i));
                    if (alreadyVisited) {
                        break;
                    }
                }
                if (!alreadyVisited) {
                    var nodeType = result.nodeTypeName() || 'default';
                    if (graphVisu._mapTypeColor[nodeType] == null) {
                        graphVisu._mapTypeColor[nodeType] = getRandomColor();
                    }
                    graphVisu._graphVisu.addNode(result.id(), { _type: nodeType });
                    var nextToVisit = context.variable("nextToVisit");
                    var alreadyAdded = false;
                    for (var ntv = 0; ntv < nextToVisit.size(); ntv++) {
                        alreadyAdded = alreadyAdded || (result.id() == nextToVisit.get(ntv).id());
                        if (alreadyAdded) {
                            break;
                        }
                    }
                    if (!alreadyAdded) {
                        context.addToGlobalVariable("nextToVisit", result);
                    }
                }
                graphVisu._graphVisu.addLink(srcNode, result.id());
            }
            context.continueTask();
        });
    })))
        .fromVar("currentNode")
        .propertiesWithTypes(Type.LONG_TO_LONG_ARRAY_MAP)
        .foreach(Actions.asVar("relationName")
        .fromVar("currentNode")
        .traverseIndexAll("{{relationName}}")
        .foreach(Actions.then(function (context) {
        var alreadyVisit = context.variable("alreadyVisit");
        var srcNode = context.variable("currentNode").get(0).id();
        var result = context.resultAsNodes().get(0);
        var alreadyVisited = false;
        for (var i = 0; i < alreadyVisit.size(); i++) {
            alreadyVisited = alreadyVisited || (result.id() == alreadyVisit.get(i));
            if (alreadyVisited) {
                break;
            }
        }
        if (!alreadyVisited) {
            var nodeType = result.nodeTypeName() || 'default';
            if (graphVisu._mapTypeColor[nodeType] == null) {
                graphVisu._mapTypeColor[nodeType] = getRandomColor();
            }
            graphVisu._graphVisu.addNode(result.id(), { _type: nodeType });
            var nextToVisit = context.variable("nextToVisit");
            var alreadyAdded = false;
            for (var ntv = 0; ntv < nextToVisit.size(); ntv++) {
                alreadyAdded = alreadyAdded || (result.id() == nextToVisit.get(ntv).id());
                if (alreadyAdded) {
                    break;
                }
            }
            if (!alreadyAdded) {
                context.addToGlobalVariable("nextToVisit", result);
            }
        }
        graphVisu._graphVisu.addLink(srcNode, result.id());
        context.continueTask();
    }))))
        .fromVar("nextToVisit")
        .asGlobalVar("toVisit")
        .fromVar("nextToVisit")
        .clear()
        .asGlobalVar("nextToVisit")
        .then(function (context) {
        var nextToVist = context.variable("toVisit");
        var toShow = "Next round: [";
        for (var i = 0; i < nextToVist.size(); i++) {
            toShow += nextToVist.get(i).id() + ", ";
        }
        toShow += "]";
        console.log(toShow);
        context.continueTask();
    })))
        .execute(graphVisu._graph, function () {
        console.log("Draw ended");
    });
}
function internal_initVivaGraph(url, idDiv) {
    connect(new GraphVisu(url), idDiv);
}
function initVivaGraph(url, idDiv) {
    if (document.getElementById(idDiv) == null) {
        setTimeout(internal_initVivaGraph, 5, url, idDiv);
    }
    else {
        internal_initVivaGraph(url, idDiv);
    }
}
function getRandomColor() {
    var letters = '789ABCD'.split('');
    var color = "#";
    for (var i = 0; i < 6; i++) {
        color += letters[Math.round(Math.random() * 6)];
    }
    return color;
}
