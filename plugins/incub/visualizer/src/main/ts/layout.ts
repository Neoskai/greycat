import initVivaGraph = org.mwg.plugin.initVivaGraph;

interface Window {
    GoldenLayout?:any;
    monaco?:any;


}

declare function require(name,callback);

module org.mwg.plugins {
    
    import GraphVisu = org.mwg.plugin.GraphVisu;
    var indexVisu = 0;

//To use local storage
// if(savedState != null) {
//     layout = new window.GoldenLayout(JSON.parse(savedState));
// } else {
// layout = new window.GoldenLayout(configLayout, document.getElementById("goldenLayout"));
// }
    let defaultConfig = {
        type: 'row',
        componantState : {
            graphVisu: Object(),
            id: -1,
            url: ""
        },
        content: [{
            type: "column",
            isClosable: false,
            content: [
                {
                    type:'stack',
                    isClosable: false,
                    content: [
                        {
                            type: 'component',
                            isClosable: false,
                            componentName: "Query Editor",
                            componantState : {editor: null}
                        },
                        {
                            type: 'component',
                            isClosable: false,
                            componentName: 'Graph command'
                        }
                    ]
                }
                ,
                {
                    type: 'component',
                    isClosable: false,
                    componentName: 'nodeDetails',
                    title: "Node details"
                }
            ]
        },
            {
                title: "Graph",
                isClosable: false,
                type: 'component',
                componentName: 'Graph visualizer'
            }]
    };

    let globalConfig = {
        content: [{
            type: 'stack',
            isClosable: false,
            content: []
        }]
    };
    export let layout;


    export function addVisu() {
        let elem:any = document.getElementsByName("graphUrl")[0];
        let url:string = elem.value;
        elem.value = "";
        addVisuWithString(url);
    }

    export function addVisuWithString(url : string) {
        let newItemConfig:any = defaultConfig;
        newItemConfig.title = url;

        newItemConfig.componantState.id = 

        layout.root.contentItems[0].addChild(newItemConfig);
    }

    function resizeQueryEditor(editor:any, width: number, height: number) {
        if(editor) {
            editor.layout({
                width: width,
                height: height
            });
        }

    }

    export function initLayout() {
        layout = new window.GoldenLayout(globalConfig, document.getElementById("goldenLayout"));
        layout.registerComponent('Graph command', function (container, componantState) {
            const graphVisu = container.parent.parent.parent.parent.config.componantState.graphVisu;
            container.getElement().html('' +
                `Time <input type="number" min="0" max="20" value="${org.mwg.plugin.INIT_TIME}"  step="1" class="timeWorldSelector" onchange="org.mwg.plugin.updateTime(this.value,org.mwg.plugin.defaultGraphVisu);"/> <br />` +
                `World <input type="number" min="0"  max="20" value="${org.mwg.plugin.INIT_WORLD}" step="1" class="timeWorldSelector" onchange="org.mwg.plugin.updateWorld(this.value,org.mwg.plugin.defaultGraphVisu);"/> <br />` +
                `Depth <input type="number" min="0"  max="20" value="${org.mwg.plugin.INIT_DEPTH}" step="1" class="timeWorldSelector" onchange="org.mwg.plugin.updateDepth(this.value,org.mwg.plugin.defaultGraphVisu);"/>`
            );
            
        });

        layout.registerComponent('Graph visualizer', function (container, componentState) {
            const id = `id${indexVisu}`;
            container.getElement().html(`<div class="graphVisu" id="${id}"></div>`);
            container.on('open', function() {
                const url = container.parent.parent.parent.config.title;
                container.parent.parent.parent.config.componantState.graphVisu = initVivaGraph(url,`${id}`)
            });

            container.on('resize', function () {
                const graphVisu = container.parent.parent.parent.config.componantState.graphVisu;
                if(container.getElement().children("div div").children().length > 0) {
                    graphVisu._graphics.resetScale();
                    graphVisu._renderer.resume();
                }

            });

            indexVisu++;
        });

        layout.registerComponent('nodeDetails', function (container, componentState) {
            container.getElement().html('<div><pre id="nodeDetail">No node selected</pre></div>'); //todo fix multiple tab
        });

        layout.registerComponent('Query Editor',function(c, componentState){
            c.getElement().html(
                '<div id="queryEditor">' +
                    '<div id="monaco-run">' +
                        '<button>Execute</button>' +
                    '</div>' +
                    '<div id="monaco"></div>' +
                '</div>');
            c.on('open', function(){
                require(['vs/editor/editor.main'], function() {
                    c._config.componantState.editor = window.monaco.editor.create(document.getElementById('monaco'), {
                        value: [
                            '// Type here your task to show specific part of your graph',
                            'function execute(graphVisu: org.mwg.plugin.GraphVisu) {',
                            '\t//TODO write here',
                            '}'
                        ].join('\n'),
                        language: 'javascript'
                    });
                });
                resizeQueryEditor(c._config.componantState.editor,c.width,c.height);
            });

            c.on('resize',function() {
                const editor = c._config.componantState.editor;
                resizeQueryEditor(editor,c.width,c.height);
            });



        });

        layout.on('initialised',function () {
            var param = window.location.search.slice(1);
            if(param != null) {
                var paramsArray = param.split('&');
                if(paramsArray.length > 1) {
                    throw "The url should contain one parameter : q.";
                }
                var query = paramsArray[0].split('=');
                if (query[0] != "q") {
                    throw "The url should contain one parameter: q";
                }

                org.mwg.plugins.addVisuWithString(query[1]);
            }
        });

        layout.init();



    }

}
