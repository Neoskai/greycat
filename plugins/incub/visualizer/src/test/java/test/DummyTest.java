package test;

/**
 * Created by ludovicmouline on 15/09/16.
 */
public class DummyTest {

//    public static void main(String[] args) {
//        Graph g = new GraphBuilder()
//                .withPlugin(new MLPlugin())
//                .build();
//
//        g.connect(isConnected -> {
//            Node root = g.newNode(0,0);
//            root.setProperty("name",Type.INT,1);
//            root.setProperty("int",Type.INT,1);
//            root.setProperty("double",Type.DOUBLE,2.);
//            root.setProperty("string",Type.STRING,"Une String");
//            root.setProperty("long",Type.LONG,1557456454894856454L);
//            root.setProperty("bool",Type.BOOL,false);
//            g.index("INDEX",root,"name",null);
//            root.add("child",g.newTypedNode(0,0, PolynomialNode.NAME));
//            root.add("child",g.newNode(0,0));
//            root.add("child",g.newNode(0,0));
//            root.add("child",g.newNode(0,0));
//
//            Node idx1 = g.newNode(0,0);
//            idx1.setProperty("name", Type.STRING,"idx1");
//
//            Node idx2 = g.newNode(0,0);
//            idx2.setProperty("name", Type.STRING,"idx2");
//
//            Node idx3 = g.newNode(0,0);
//            idx3.setProperty("name", Type.STRING,"idx3");
//
//            root.index("idx",idx1,"name",null);
//            root.index("idx",idx2,"name",null);
//            root.index("idx",idx3,"name",null);
//
//            Node previous = root;
//            for(int i=0;i<20;i++) {
//                Node next = g.newNode(0,0);
//                previous.add("next",next);
//                previous = next;
//            }
//
//
//            for(int i=0;i<10;i++) {
//                final int ii = i;
//                idx3.jump(i, new Callback<Node>() {
//                    @Override
//                    public void on(Node newIdx3) {
//                        newIdx3.setProperty("value",Type.INT,ii);
//                    }
//                });
//            }
//
//            for(int i=0;i<10;i++) {
//                final int ii = i;
//                final long newWorld = g.fork(i);
//                g.lookup(newWorld, 0, idx1.id(), new Callback<Node>() {
//                    @Override
//                    public void on(Node newIdx1) {
//                        newIdx1.setProperty("v",Type.INT,ii);
//                    }
//                });
//            }
//
//        });
//
//        final WSServer server = new WSServer(g,5678);
//        server.start();
//
//
//        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
//            @Override
//            public void run() {
//                server.stop();
//            }
//        }));
//
//
//    }

}

//ws://localhost:5678