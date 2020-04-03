package utils;

import java.util.Collection;

public class MathUtils {

    public static double countStandardDeviation(Collection<Double> keySet, double averageDistance) {
        double std = 0.0;
        for (double val : keySet){
            std += (val - averageDistance) * (val - averageDistance);
        }
        std = std / keySet.size();
        return Math.sqrt(std);
    }

}
