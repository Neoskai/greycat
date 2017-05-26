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
package greycatMLTest.regression;

import greycat.*;
import greycat.ml.MLPlugin;
import greycat.ml.regression.PolynomialNode;
import org.junit.Assert;
import org.junit.Test;
import greycat.scheduler.NoopScheduler;

public class PolynomialNodeTest {
    private static final int size = 100;
    private static final double precision = 0.5;

    /**
     * @native ts
     */
    @Test
    public void testConstant() {
        final Graph graph = new GraphBuilder().withPlugin(new MLPlugin()).withScheduler(new NoopScheduler()).build();
        graph.connect(new Callback<Boolean>() {
            @Override
            public void on(Boolean result) {
                /*
                try {
                    BlasMatrixEngine bme = (BlasMatrixEngine) Matrix.defaultEngine();
                    bme.setBlas(new F2JBlas());
                } catch (Exception ignored) {
                }*/
                long[] times = new long[size];
                double[] values = new double[size];
                //test degree 0
                for (int i = 0; i < size; i++) {
                    times[i] = i * 10 + 5000;
                    values[i] = 42.0;
                }
                testPoly(times, values, 1, graph);

                //test degree 1
                for (int i = 0; i < size; i++) {
                    values[i] = 3 * i - 20;
                }
                testPoly(times, values, 1, graph);

                //test degree 2
                for (int i = 0; i < size; i++) {
                    values[i] = 3 * i * i - 99 * i - 20;

                }
                testPoly(times, values, 1, graph);

                //test degree 5
                for (int i = 0; i < size; i++) {
                    values[i] = 2 * i * i * i * i * i - 1000 * i - 100000;
                }
                testPoly(times, values, 8, graph);

            }
        });
    }


    public static void testPoly(long[] times, final double[] values, final int numOfPoly, final Graph graph) {
        PolynomialNode polynomialNode = (PolynomialNode) graph.newTypedNode(0, times[0], PolynomialNode.NAME);
        polynomialNode.set(PolynomialNode.PRECISION, Type.DOUBLE, precision);

        for (int i = 0; i < size; i++) {
            final int ia = i;
            polynomialNode.travelInTime(times[ia], new Callback<PolynomialNode>() {
                @Override
                public void on(PolynomialNode result) {
                    result.learn(values[ia], new Callback<Boolean>() {
                        @Override
                        public void on(Boolean result) {

                        }
                    });
                }
            });
        }

        //System.out.println(polynomialNode);

        for (int i = 0; i < size; i++) {
            final int ia = i;
            polynomialNode.travelInTime(times[ia], new Callback<PolynomialNode>() {
                @Override
                public void on(PolynomialNode result) {
                    result.extrapolate(new Callback<Double>() {
                        @Override
                        public void on(Double v) {
                            Assert.assertTrue(Math.abs(values[ia] - v) <= precision);
                        }
                    });
                }
            });
        }

        polynomialNode.timepoints(Constants.BEGINNING_OF_TIME, Constants.END_OF_TIME, new Callback<long[]>() {
            @Override
            public void on(long[] result) {
                Assert.assertTrue(result.length <= numOfPoly);
            }
        });
    }

}
