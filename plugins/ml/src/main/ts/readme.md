# GreyCat Machine Learning plugin

# Usage

```js
var greycat = require('greycat');
var greycatMLPlugin = require('greycat-ml');
var graph = greycat.GraphBuilder.newBuilder()
    .withPlugin(new greycatMLPlugin())
    .build();
graph.connect(function(){
    var node = graph.newNode(0,0);
    node.set("name", greycat.Type.STRING, "myFirstNode");
    graph.save(null);
});
```
more to come...