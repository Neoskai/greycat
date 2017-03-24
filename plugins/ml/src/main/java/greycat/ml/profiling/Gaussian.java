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
package greycat.ml.profiling;

import greycat.Node;
import greycat.Type;
import greycat.ml.math.Gaussian1D;
import greycat.struct.DoubleArray;

/**
 * Created by assaad on 20/02/2017.
 */
public class Gaussian {
    public static final String MIN = "profile_min";
    public static final String MAX = "profile_max";
    public static final String AVG = "profile_avg";
    public static final String COV = "profile_cov";
    public static final String STD = "profile_std";
    public static final String SUM = "profile_sum";
    public static final String SUMSQ = "profile_sumsq";
    public static final String TOTAL = "profile_total";
    public static final String PRECISIONS = "profile_precisions"; //Default covariance matrix for a dirac function
    public static final String VALUES = "profile_values";

    public static final String HISTOGRAM_BUCKETS="hist_buckets";
    public static final int HISTOGRAM_BUCKETS_DEF=20;
    public static final String HISTOGRAM_MIN="histogram_min";
    public static final String HISTOGRAM_MAX="histogram_max";
    public static final String HISTOGRAM_VALUES="histogram_values";

    public static void profile(Node host, double value) {
        Double min = host.getWithDefault(MIN, null);
        Double max = host.getWithDefault(MAX, null);
        if (min == null || value < min) {
            host.set(MIN, Type.DOUBLE, value);
        }
        if (max == null || value > max) {
            host.set(MAX, Type.DOUBLE, value);
        }


        long total = host.getWithDefault(TOTAL, 0L) + 1;
        double sum = host.getWithDefault(SUM, 0.0) + value;
        double sumsq = host.getWithDefault(SUMSQ, 0.0) + value * value;


        host.set(TOTAL, Type.LONG, total);
        host.set(SUM, Type.DOUBLE, sum);
        host.set(SUMSQ, Type.DOUBLE, sumsq);
        host.set(AVG, Type.DOUBLE, sum / total);

        if (total > 1) {
            double cov = Gaussian1D.getCovariance(sum, sumsq, total);
            host.set(COV, Type.DOUBLE, cov);
            host.set(STD, Type.DOUBLE, Math.sqrt(cov));
        }
    }

    public static void clearProfile(Node host) {
        host.set(TOTAL, Type.LONG, null);
        host.set(SUM, Type.DOUBLE, null);
        host.set(SUMSQ, Type.DOUBLE, null);
        host.set(AVG, Type.DOUBLE, null);
        host.set(COV, Type.DOUBLE, null);
        host.set(STD, Type.DOUBLE, null);
        host.set(HISTOGRAM_MIN,Type.DOUBLE_ARRAY,null);
        host.set(HISTOGRAM_MAX,Type.DOUBLE_ARRAY,null);
        host.set(HISTOGRAM_VALUES,Type.DOUBLE_ARRAY,null);
    }

    public static void histogram(Node host, double min, double max, double value) {
        if(max<min || value<min || value>max){
            throw new RuntimeException("value is outside min, max range");
        }
        if (max != min) {
            int steps= host.getWithDefault(HISTOGRAM_BUCKETS,HISTOGRAM_BUCKETS_DEF);
            double stepsize = (max - min) / steps;
            DoubleArray hist_min= (DoubleArray) host.getOrCreate(HISTOGRAM_MIN,Type.DOUBLE_ARRAY);
            DoubleArray hist_max= (DoubleArray) host.getOrCreate(HISTOGRAM_MAX,Type.DOUBLE_ARRAY);
            DoubleArray hist_values= (DoubleArray) host.getOrCreate(HISTOGRAM_VALUES,Type.DOUBLE_ARRAY);

            if(hist_min.size()==0){
                hist_min.init(steps);
                hist_max.init(steps);
                hist_values.init(steps);
                for (int i = 0; i < steps; i++) {
                    hist_min.set(i, min + stepsize * i);
                    hist_max.set(i, min + stepsize * (i + 1));
                }
            }

            int index = (int) ((value - min) / stepsize);
            if (index == steps) {
                index--;
            }
            hist_values.set(index,hist_values.get(index)+1);
        }
    }


}
