/// <reference path="graphVisualizer.ts" />
// update CSS file if you modify these values
var idDivGraphVisu = "graphVisu";
var indexVisu = 0;
//To use local storage
// if(savedState != null) {
//     layout = new window.GoldenLayout(JSON.parse(savedState));
// } else {
// layout = new window.GoldenLayout(configLayout, document.getElementById("goldenLayout"));
// }
var defaultConfig = {
    type: 'row',
    content: [{
            type: "column",
            content: [{
                    type: 'component',
                    componentName: 'testComponent',
                    componentState: { label: 'A' }
                }, {
                    type: 'component',
                    componentName: 'nodeDetails',
                    title: "Node details"
                }]
        },
        {
            title: "Graph",
            type: 'component',
            componentName: 'Graph visualizer'
        }]
};
var globalConfig = {
    content: [{
            type: 'stack',
            isClosable: false,
            content: []
        }]
};
var layout;
function addVisu() {
    var elem = document.getElementsByName("graphUrl")[0];
    var url = elem.value;
    elem.value = "";
    var newItemConfig = defaultConfig;
    newItemConfig.title = url;
    layout.root.contentItems[0].addChild(newItemConfig);
}
function initLayout() {
    layout = new window.GoldenLayout(globalConfig, document.getElementById("goldenLayout"));
    layout.registerComponent('testComponent', function (container, componantState) {
        container.getElement().html('<h1>' + componantState.label + '</h1>');
    });
    layout.registerComponent('Graph visualizer', function (container, componentState) {
        container.getElement().html('<div class="' + idDivGraphVisu + '" id="id' + indexVisu + '"></div>');
        container.on('open', initVivaGraph.bind(this, container.parent.parent.parent.config.title, "id" + indexVisu)); //fixmultiple stack
        indexVisu++;
    });
    layout.registerComponent('nodeDetails', function (container, componentState) {
        container.getElement().html('<div><pre id="nodeDetail">No node selected</pre></div>'); //todo fix multiple tab
    });
    layout.init();
}
