/// <reference path="mwg/mwg.d.ts" />
/// <reference path="mwg/mwg.ws.d.ts" />

interface Window {
    Viva? : any
}



module org.mwg.plugin.visualizer.taskRegistry {
    import Actions = org.mwg.task.Actions;
    import TaskContext = org.mwg.task.TaskContext;
    import Task = org.mwg.task.Task;
    import TaskResult = org.mwg.task.TaskResult;

    export const timeVar : string = "time";
    export const worldVar : string = "world";
    export const nodeIdVar : string = "nodeId";


    const writeIdType = Actions.then(function(context : TaskContext) {
        let res : string = context.variable("string").get(0);
        let n : org.mwg.Node = context.variable("node").get(0);
        res += "  _id=" + n.id() + "\n";
        res += "  _type=" + (n.nodeTypeName() || 'default') + "\n";
        context.setGlobalVariable("string",res);
        context.continueTask();
    });
    const writeAtt = Actions.then(function(context : TaskContext) {
        let res : string = context.variable("string").get(0);
        let n : org.mwg.Node = context.variable("node").get(0);

        res += "  " + context.result().get(0) + "=";
        if(typeof context.result().get(0) != "number") {
            var prop = n.get(context.result().get(0));
            if(prop instanceof Float64Array || prop instanceof Int32Array) {
                res += "[" + prop + "]";
            } else {
                res += prop;
            }

        } else {
            res += n.getByIndex(context.result().get(0));
        }
        res += "\n";

        context.setGlobalVariable("string",res);
        context.continueTask();
    });

    const writeIndexRel = Actions.then(function(context :TaskContext) {
        let n : org.mwg.Node = context.variable("node").get(0);
        let map : org.mwg.struct.LongLongArrayMap = n.get(context.result().get(0));

        let index : number = 0;
        let res : String = context.variable("string").get(0);
        res += "  " + context.result().get(0) + "=[";
        map.each(function (key:number, value:number) {
            res += (value + "");
            if((index + 1) < map.size() ) {
                res += ",";
            }
            index++;
        });
        res += "]\n";

        context.setGlobalVariable("string",res);
        context.continueTask();
    });

    export const printNodeTask : Task = Actions
        .setTime(`{{${timeVar}}}`)
        .setWorld(`{{${worldVar}}}`)
        .inject("{\n")
        .asGlobalVar("string")
        .lookup(`{{${nodeIdVar}}}`)
        .asGlobalVar("node")
        .ifThen(function(context : TaskContext){return context.result().size() > 0},
            writeIdType
                .subTasks([Actions.propertiesWithTypes(Type.BOOL),
                    Actions.propertiesWithTypes(Type.INT),
                    Actions.propertiesWithTypes(Type.DOUBLE),
                    Actions.propertiesWithTypes(Type.LONG),
                    Actions.propertiesWithTypes(Type.STRING),
                    Actions.propertiesWithTypes(Type.RELATION),
                    Actions.propertiesWithTypes(Type.INT_ARRAY),
                    Actions.propertiesWithTypes(Type.LONG_ARRAY),
                    Actions.propertiesWithTypes(Type.DOUBLE_ARRAY)
                ]
                )
                .foreach(writeAtt)
                .fromVar("node")
                .propertiesWithTypes(Type.LONG_TO_LONG_ARRAY_MAP)
                .ifThen(function(context : TaskContext) {
                    return context.result().size() > 0;
                },writeIndexRel)
                .fromVar("string"));


    function getRandomColor() {
        var letters = '789ABCD'.split('');
        var color = "#";
        for (var i = 0; i < 6; i++ ) {
            color += letters[Math.round(Math.random() * 6)];
        }
        return color;
    }


    const addIndexedNode = Actions.then(function(context : TaskContext) {
        let node : org.mwg.Node = context.resultAsNodes().get(0);
        let graphVisu : GraphVisu = <GraphVisu> context.variable(graphVisuVar).get(0);
        let id : number = node.id();

        let nodeType : string = node.nodeTypeName() || 'default';
        graphVisu._graphVisu.addNode(id,{_type: nodeType});
        if(graphVisu._mapTypeColor[nodeType] == null) {
            graphVisu._mapTypeColor[nodeType] = getRandomColor();
        }
        context.continueTask();
    });

    const visitRel = Actions.then(function(context : TaskContext) {
        var alreadyVisit : TaskResult<number> = context.variable("alreadyVisit");
        var srcNode : number = context.variable("currentNode").get(0).id();
        var result : org.mwg.Node = context.resultAsNodes().get(0);

        var alreadyVisited : boolean = false;
        for(var i=0;i<alreadyVisit.size();i++) {
            alreadyVisited = alreadyVisited || (result.id() == alreadyVisit.get(i))
            if(alreadyVisited) {
                break;
            }
        }

        let graphVisu : GraphVisu = <GraphVisu> context.variable(graphVisuVar).get(0);
        if(!alreadyVisited) {
            let nodeType : string = result.nodeTypeName() || 'default';
            if(graphVisu._mapTypeColor[nodeType] == null) {
                graphVisu._mapTypeColor[nodeType] = getRandomColor();
            }

            graphVisu._graphVisu.addNode(result.id(),{_type: nodeType});
            let nextToVisit : TaskResult<org.mwg.Node> = context.variable("nextToVisit");
            let alreadyAdded :boolean = false;
            for(let ntv=0;ntv<nextToVisit.size();ntv++) {
                alreadyAdded = alreadyAdded || (result.id() == nextToVisit.get(ntv).id());
                if(alreadyAdded) {
                    break;
                }
            }
            if(!alreadyAdded) {
                context.addToGlobalVariable("nextToVisit", result);
            }
        }

        graphVisu._graphVisu.addLink(srcNode,result.id());
        context.continueTask();

    });

    const visitByIndex = Actions.then(function(context: TaskContext) {
        let node : org.mwg.Node = context.variable("currentNode").get(0);
        let hashReation : number = context.variable("relationName").get(0);


        node.relByIndex(hashReation, function(nodes: Array<org.mwg.Node>) {
            let alreadyVisit : TaskResult<number> = context.variable("alreadyVisit");
            let srcNode : number = context.variable("currentNode").get(0).id();

            for(let i=0;i<nodes.length;i++) {
                let result : org.mwg.Node = nodes[i];
                let alreadyVisited : boolean = false;
                for(let i=0;i<alreadyVisit.size();i++) {
                    alreadyVisited = alreadyVisited || (result.id() == alreadyVisit.get(i))
                    if(alreadyVisited) {
                        break;
                    }
                }

                let graphVisu : GraphVisu = <GraphVisu> context.variable(graphVisuVar).get(0);
                if(!alreadyVisited) {
                    let nodeType : string = result.nodeTypeName() || 'default';
                    if(graphVisu._mapTypeColor[nodeType] == null) {
                        graphVisu._mapTypeColor[nodeType] = getRandomColor();
                    }

                    graphVisu._graphVisu.addNode(result.id(),{_type: nodeType});
                    let nextToVisit : TaskResult<org.mwg.Node> = context.variable("nextToVisit");
                    var alreadyAdded :boolean = false;
                    for(let ntv=0;ntv<nextToVisit.size();ntv++) {
                        alreadyAdded = alreadyAdded || (result.id() == nextToVisit.get(ntv).id());
                        if(alreadyAdded) {
                            break;
                        }
                    }
                    if(!alreadyAdded) {
                        context.addToGlobalVariable("nextToVisit", result);
                    }
                }

                graphVisu._graphVisu.addLink(srcNode,result.id());
            }
            context.continueTask();
        });

    });


    const visitRelIndex = Actions.then(function(context : TaskContext){
        var alreadyVisit : TaskResult<number> = context.variable("alreadyVisit");
        var srcNode : number = context.variable("currentNode").get(0).id();
        var result : org.mwg.Node = context.resultAsNodes().get(0);

        var alreadyVisited : boolean = false;
        for(var i=0;i<alreadyVisit.size();i++) {
            alreadyVisited = alreadyVisited || (result.id() == alreadyVisit.get(i));
            if(alreadyVisited) {
                break;
            }
        }

        let graphVisu : GraphVisu = <GraphVisu> context.variable(graphVisuVar).get(0);
        if(!alreadyVisited) {
            var nodeType : string = result.nodeTypeName() || 'default';
            if(graphVisu._mapTypeColor[nodeType] == null) {
                graphVisu._mapTypeColor[nodeType] = getRandomColor();
            }

            graphVisu._graphVisu.addNode(result.id(),{_type: nodeType});
            var nextToVisit : TaskResult<org.mwg.Node> = context.variable("nextToVisit");
            var alreadyAdded :boolean = false;
            for(var ntv=0;ntv<nextToVisit.size();ntv++) {
                alreadyAdded = alreadyAdded || (result.id() == nextToVisit.get(ntv).id());
                if(alreadyAdded) {
                    break;
                }
            }
            if(!alreadyAdded) {
                context.addToGlobalVariable("nextToVisit", result);
            }
        }

        graphVisu._graphVisu.addLink(srcNode,result.id());
        context.continueTask();
    });

    export const depthVar : string = "depth";
    export const graphVisuVar : string = "graphVisu";
    export const drawGraphTask : Task = Actions
        .setTime(`{{${timeVar}}}`)
        .setWorld(`{{${worldVar}}}`)
        .indexesNames()
        .foreach(
            Actions
                .fromIndexAll("{{result}}")
                .asGlobalVar("toVisit")
                .foreach(addIndexedNode)
                .fromVar("toVisit")
                .loop("1",`{{${depthVar}}}`,
                    Actions
                        .defineVar("nextToVisit")
                        .fromVar("toVisit")
                        .foreach(
                            Actions
                                .asGlobalVar("currentNode")
                                .then(function(context : TaskContext) {
                                    let node : org.mwg.Node = context.result().get(0);
                                    context.addToGlobalVariable("alreadyVisit",node.id())
                                    context.continueTask();
                                })
                                .propertiesWithTypes(Type.RELATION)
                                .foreach(
                                    Actions.asVar("relationName")
                                        .fromVar("currentNode")
                                        .traverse("{{relationName}}")
                                        .ifThenElse(function (context:TaskContext) : boolean {
                                                return context.result().size() > 0;
                                            },  Actions.foreach(visitRel),
                                            visitByIndex
                                        )
                                )
                                .fromVar("currentNode")
                                .propertiesWithTypes(Type.LONG_TO_LONG_ARRAY_MAP)
                                .foreach(
                                    Actions.asVar("relationName")
                                        .fromVar("currentNode")
                                        .traverseIndexAll("{{relationName}}")
                                        .foreach(visitRelIndex)
                                )

                        )
                        .fromVar("nextToVisit")
                        .asGlobalVar("toVisit")
                        // .fromVar("nextToVisit")
                        .clear()
                        .asGlobalVar("nextToVisit")
                )
        );
}

module org.mwg.plugin {
    import timeVar = org.mwg.plugin.visualizer.taskRegistry.timeVar;
    import worldVar = org.mwg.plugin.visualizer.taskRegistry.worldVar;
    import nodeIdVar = org.mwg.plugin.visualizer.taskRegistry.nodeIdVar;
    import printNodeTask = org.mwg.plugin.visualizer.taskRegistry.printNodeTask;
    import depthVar = org.mwg.plugin.visualizer.taskRegistry.depthVar;
    import graphVisuVar = org.mwg.plugin.visualizer.taskRegistry.graphVisuVar;
    import drawGraphTask = org.mwg.plugin.visualizer.taskRegistry.drawGraphTask;

    import WSClient = org.mwg.plugin.WSClient;
    import Task = org.mwg.task.Task;
    import Actions = org.mwg.task.Actions;
    import TaskContext = org.mwg.task.TaskContext;
    import Type = org.mwg.Type;
    import TaskResult = org.mwg.task.TaskResult;
    import Graph = org.mwg.Graph;
    import HeapRelationship = org.mwg.core.chunk.heap.HeapRelationship;
    import CoreDeferCounterSync = org.mwg.core.utility.CoreDeferCounterSync;

    export const INIT_DEPTH = 10;
    export const INIT_TIME = 0;
    export const INIT_WORLD = 0;

    export var defaultGraphVisu : GraphVisu;

    export class GraphVisu {
        _graph : org.mwg.Graph;
        _graphVisu : any;
        _time : number = INIT_TIME;
        _world : number = INIT_WORLD;
        _depth : number = INIT_DEPTH; //todo delete


        _mapTypeColor : Object = new Object();

        _previousSelect : number = -1;
        _previousColor : any;

        _renderer : any;

        _graphics : any;

        constructor(url : string) {
            this._graph = new org.mwg.GraphBuilder()
                .withStorage(new org.mwg.plugin.WSClient(url))
                .build();

            this._graphVisu = window.Viva.Graph.graph();

            this._mapTypeColor['default'] = 0x009ee8ff;
        }
    }


    function printNodeDetails(nodeId: number, graphVisu : GraphVisu) {
        Actions
            .inject(nodeId)
            .asGlobalVar(nodeIdVar)
            .inject(graphVisu._time)
            .asGlobalVar(timeVar)
            .inject(graphVisu._world)
            .asGlobalVar(worldVar)
            .subTask(printNodeTask)
            .execute(graphVisu._graph, function(result : TaskResult<string>) {
                document.getElementById("nodeDetail").innerHTML = result.get(0) + "}";
            });
    }

    function selectNode(nodeID : number, graphVisu : GraphVisu) {
        if(nodeID != graphVisu._previousSelect) {
            printNodeDetails(nodeID, graphVisu);
            var selectedNodeUI = graphVisu._renderer.getGraphics().getNodeUI(nodeID);
            if(selectedNodeUI != null) {
                var currentColor = selectedNodeUI.color;
                selectedNodeUI.color = 0xFFA500ff;

                if (graphVisu._previousSelect != -1) {
                    var previousSelected = graphVisu._renderer.getGraphics().getNodeUI(graphVisu._previousSelect);
                    previousSelected.color = graphVisu._previousColor;
                }
                graphVisu._previousSelect = nodeID;
                graphVisu._previousColor = currentColor;
            }
        }

    }

    function connect(graphVisu : GraphVisu, idDiv : string) {

        graphVisu._graph.connect(function (succeed : boolean){
            if(succeed) {
                graphVisu._graphics = window.Viva.Graph.View.webglGraphics();
                graphVisu._graphics.node(function(node){
                    return window.Viva.Graph.View.webglSquare(12,graphVisu._mapTypeColor[node.data._type]);
                });

                window.Viva.Graph.webglInputEvents(graphVisu._graphics,graphVisu._graphVisu)
                    .click(function(selectedNode : any) {
                        console.log("click");
                        selectNode(selectedNode.id,graphVisu);
                    });

                graphVisu._renderer = window.Viva.Graph.View.renderer(graphVisu._graphVisu, {
                    layout: window.Viva.Graph.Layout.forceDirected(graphVisu._graphVisu, {}),
                    container: document.getElementById(idDiv),
                    graphics: graphVisu._graphics
                });
                graphVisu._renderer.run();
                //
                // setTimeout(function(){
                //     graphVisu._renderer.pause();
                // },10000);



                drawGraph(graphVisu);
            } else {
                console.error("Problem during connection.")
            }
        });
    }



    function drawGraph(graphVisu : GraphVisu) {
        graphVisu._graphVisu.clear();
        Actions
            .inject(graphVisu._time)
            .asGlobalVar(timeVar)
            .inject(graphVisu._world)
            .asGlobalVar(worldVar)
            .inject(graphVisu._depth)
            .asGlobalVar(depthVar)
            .inject(graphVisu)
            .asGlobalVar(graphVisuVar)
            .subTask(drawGraphTask)
            .execute(graphVisu._graph,function() {
                if(graphVisu._previousSelect != -1) {
                    let nodeId = graphVisu._previousSelect;
                    graphVisu._previousSelect = -1;
                    selectNode(nodeId,graphVisu);
                }
            });
    }

    export function initVivaGraph(url: string, idDiv : string) {
        // let graphVisu = new GraphVisu(url);
        defaultGraphVisu = new GraphVisu(url);
        if(document.getElementById(idDiv) == null) {
            setTimeout(connect,5,defaultGraphVisu,idDiv)
        } else {
            connect(defaultGraphVisu,idDiv);
        }

        return defaultGraphVisu;
    }

    export function updateTime(time : number, graphVisu : GraphVisu) {
        graphVisu._time = time;
        drawGraph(graphVisu);

    }

    export function updateWorld(world : number, graphVisu : GraphVisu) {
        graphVisu._world = world;
        drawGraph(graphVisu);

    }

    export function updateDepth(depth : number, graphVisu : GraphVisu) {
        graphVisu._depth = depth;
        drawGraph(graphVisu);

    }

}

