package model;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Value
@RequiredArgsConstructor
public class Solution {

    static Random rand = new Random();
    int[] order;
    Logger logger = LogManager.getLogger(Solution.class);
    @NonFinal
    double fitness = -1; // used to cache fitness

    public double rateSolution() {
        if (fitness != -1) {
            return fitness;
        }
        double totalDistance = 0;
        for (int i = 0; i < order.length - 1; i++) {
            totalDistance += Node.getDistance(order[i], order[i + 1]);
        }
        totalDistance += Node.getDistance(order[order.length - 1], order[0]);
        this.fitness = totalDistance;
        return totalDistance;
    }

    @Override
    public String toString() {
        return String.join(", ", Arrays.toString(this.order));
    }

    public static Solution generateRandomSolution(int nodes) {
        int[] ints = IntStream.range(0, nodes)
                .toArray();

        for (int i = 0; i < ints.length; i++) {
            int randomIndexToSwap = rand.nextInt(ints.length);
            int temp = ints[randomIndexToSwap];
            ints[randomIndexToSwap] = ints[i];
            ints[i] = temp;
        }
        return new Solution(ints);
    }


}
