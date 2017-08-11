package greycat.utility.json;

import greycat.*;
import greycat.struct.Buffer;
import greycat.struct.DMatrix;
import greycat.struct.EStruct;
import greycat.struct.EStructArray;

public class test {

    public static void main(String[] args) {
        Graph g = new GraphBuilder().build();
        JsonParser parser = new JsonParser(g);

        Buffer b = g.newBuffer();
        b.writeString("[{\"world\":0,\"id\":2,\"nodetype\":\"\", \"times\":[0,1000,1001,1002,1003,1004,1005,1006,1007,1008,1009,1010,1011,1012,1013,1014,1015,1016,1017,1018,1019,1020,1021,1022,1023,1024,1025,1026,1027,1028,1029,1030,1031,1032,1033,1034,1035,1036,1037],\"values\":[[21,{}],[21,{\"value\":[5,0.0]}],[21,{\"value\":[5,0.3]}],[21,{\"value\":[5,0.6]}],[21,{\"value\":[5,0.8999999999999999]}],[21,{\"value\":[5,1.2]}],[21,{\"value\":[5,1.5]}],[21,{\"value\":[5,1.7999999999999998]}],[21,{\"value\":[5,2.1]}],[21,{\"value\":[5,2.4]}],[21,{\"value\":[5,2.6999999999999997]}],[21,{\"value\":[5,3.0]}],[21,{\"value\":[5,3.3]}],[21,{\"value\":[5,3.5999999999999996]}],[21,{\"value\":[5,3.9]}],[21,{\"value\":[5,4.2]}],[21,{\"value\":[5,4.5]}],[21,{\"value\":[5,4.8]}],[21,{\"value\":[5,5.1]}],[21,{\"value\":[5,5.3999999999999995]}],[21,{\"value\":[5,5.7]}],[21,{\"value\":[5,6.0]}],[21,{\"value\":[5,6.3]}],[21,{\"value\":[5,6.6]}],[21,{\"value\":[5,6.8999999999999995]}],[21,{\"value\":[5,7.199999999999999]}],[21,{\"value\":[5,7.5]}],[21,{\"value\":[5,7.8]}],[21,{\"value\":[5,8.1]}],[21,{\"value\":[5,8.4]}],[21,{\"value\":[5,8.7]}],[21,{\"value\":[5,9.0]}],[21,{\"value\":[5,9.299999999999999]}],[21,{\"value\":[5,9.6]}],[21,{\"value\":[5,9.9]}],[21,{\"value\":[5,10.2]}],[21,{\"value\":[5,10.5]}],[21,{\"value\":[5,10.799999999999999]}],[21,{\"value\":[5,11.1]}]]},{\"world\":0,\"id\":2,\"nodetype\":\"\", \"times\":[1038,1039,1040,1041,1042,1043,1044,1045,1046,1047,1048,1049,1050,1051,1052,1053,1054,1055,1056,1057,1058,1059,1060,1061,1062,1063,1064,1065,1066,1067,1068,1069,1070,1071,1072,1073,1074],\"values\":[[21,{\"value\":[5,11.4]}],[21,{\"value\":[5,11.7]}],[21,{\"value\":[5,12.0]}],[21,{\"value\":[5,12.299999999999999]}],[21,{\"value\":[5,12.6]}],[21,{\"value\":[5,12.9]}],[21,{\"value\":[5,13.2]}],[21,{\"value\":[5,13.5]}],[21,{\"value\":[5,13.799999999999999]}],[21,{\"value\":[5,14.1]}],[21,{\"value\":[5,14.399999999999999]}],[21,{\"value\":[5,14.7]}],[21,{\"value\":[5,15.0]}],[21,{\"value\":[5,15.299999999999999]}],[21,{\"value\":[5,15.6]}],[21,{\"value\":[5,15.899999999999999]}],[21,{\"value\":[5,16.2]}],[21,{\"value\":[5,16.5]}],[21,{\"value\":[5,16.8]}],[21,{\"value\":[5,17.099999999999998]}],[21,{\"value\":[5,17.4]}],[21,{\"value\":[5,17.7]}],[21,{\"value\":[5,18.0]}],[21,{\"value\":[5,18.3]}],[21,{\"value\":[5,18.599999999999998]}],[21,{\"value\":[5,18.9]}],[21,{\"value\":[5,19.2]}],[21,{\"value\":[5,19.5]}],[21,{\"value\":[5,19.8]}],[21,{\"value\":[5,20.099999999999998]}],[21,{\"value\":[5,20.4]}],[21,{\"value\":[5,20.7]}],[21,{\"value\":[5,21.0]}],[21,{\"value\":[5,21.3]}],[21,{\"value\":[5,21.599999999999998]}],[21,{\"value\":[5,21.9]}],[21,{\"value\":[5,22.2]}]]},{\"world\":0,\"id\":2,\"nodetype\":\"\", \"times\":[1075,1076,1077,1078,1079,1080,1081,1082,1083,1084,1085,1086,1087,1088,1089,1090,1091,1092,1093,1094,1095,1096,1097,1098,1099],\"values\":[[21,{\"value\":[5,22.5]}],[21,{\"value\":[5,22.8]}],[21,{\"value\":[5,23.099999999999998]}],[21,{\"value\":[5,23.4]}],[21,{\"value\":[5,23.7]}],[21,{\"value\":[5,24.0]}],[21,{\"value\":[5,24.3]}],[21,{\"value\":[5,24.599999999999998]}],[21,{\"value\":[5,24.9]}],[21,{\"value\":[5,25.2]}],[21,{\"value\":[5,25.5]}],[21,{\"value\":[5,25.8]}],[21,{\"value\":[5,26.099999999999998]}],[21,{\"value\":[5,26.4]}],[21,{\"value\":[5,26.7]}],[21,{\"value\":[5,27.0]}],[21,{\"value\":[5,27.3]}],[21,{\"value\":[5,27.599999999999998]}],[21,{\"value\":[5,27.9]}],[21,{\"value\":[5,28.2]}],[21,{\"value\":[5,28.5]}],[21,{\"value\":[5,28.799999999999997]}],[21,{\"value\":[5,29.099999999999998]}],[21,{\"value\":[5,29.4]}],[21,{\"value\":[5,29.7]}]]}]");

        //System.out.println(new String(b.data()));
        //parser.buildGraph(b,0);

        JsonParserV2 parser2 = new JsonParserV2(g);
        //parser2.parse(b);

        Graph graph= GraphBuilder
                .newBuilder()
                .build();
        graph.connect(new Callback<Boolean>() {
            @Override
            public void on(Boolean result) {

                Node node = graph.newNode(0,0);

                EStructArray eg= (EStructArray) node.getOrCreate("egraph", Type.ESTRUCT_ARRAY);
                EStruct en =eg.newEStruct();
                eg.setRoot(en);

                EStruct en2 = eg.newEStruct();
                DMatrix matrix2 =(DMatrix)en2.getOrCreate("matrix2", Type.DMATRIX);
                matrix2.init(3,3);
                matrix2.set(0,0,0);
                matrix2.set(1,1,1);
                matrix2.set(2,2,2);

                DMatrix matrix= (DMatrix)en.getOrCreate("matrix", Type.DMATRIX);

                matrix.init(3,3);
                matrix.set(0,0,0);
                matrix.set(1,1,1);
                matrix.set(2,2,2);


                node.travelInTime(1, new Callback<Node>() {
                    @Override
                    public void on(Node result1) {
                        EStructArray eg= (EStructArray) result1.getOrCreate("egraph", Type.ESTRUCT_ARRAY);
                        EStruct en =eg.root();
                        DMatrix matrix_t1= (DMatrix)en.getOrCreate("matrix", Type.DMATRIX);

                        matrix_t1.set(0,0,10);
                        matrix_t1.set(1,1,11);
                        matrix_t1.set(2,2,12);
                    }
                });

                graph.declareIndex(0,"TestIndex", null);
                graph.index(0,0,"TestIndex", index ->{
                    index.update(node);
                });

                Buffer buffer = graph.newBuffer();
                graph.toJson(buffer);

                parser2.parse(buffer);
            }
        });
    }

}
