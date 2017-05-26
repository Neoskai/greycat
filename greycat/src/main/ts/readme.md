# GreyCat: the temporal and many-world database for live analytics.

# Usage

```js
var greycat = require('greycat');
var graph = greycat.GraphBuilder.newBuilder().build();
graph.connect(function(){
    var node = graph.newNode(0,0);
    node.set("name", greycat.Type.STRING, "myFirstNode");
    graph.save(null);
});
```

more to come...