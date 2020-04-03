package algorithm.genetic;

import model.Solution;

import java.util.*;

public class OrderedCrossover implements Crossover {

    private final Random rnd;

    public OrderedCrossover() {
        this.rnd = new Random();
    }

    @Override
    public Solution crossover(Solution sol1, Solution sol2) {
        var size = sol1.getOrder().length;
        var firstIndex = rnd.nextInt(size);
        var secondIndex = rnd.nextInt(size);
        if (firstIndex > secondIndex) {
            var temp = firstIndex;
            firstIndex = secondIndex;
            secondIndex = temp;
        }
        var newOrder = new int[size];
        // fill the cutout part
        System.arraycopy(sol1.getOrder(), firstIndex, newOrder, firstIndex, secondIndex - firstIndex + 1);
        HashSet<Integer> ints = new HashSet<>(secondIndex - firstIndex + 2);
        for (int i = firstIndex; i <= secondIndex; i++) {
            ints.add(newOrder[i]);
        }
        int insertIndex = 0;
        int parentIndex = 0;
        for (; insertIndex < firstIndex; parentIndex++) {
            if (!ints.contains(sol2.getOrder()[parentIndex])) {
                newOrder[insertIndex] = sol2.getOrder()[parentIndex];
                insertIndex++;
            }
        }
        insertIndex = secondIndex + 1;
        for (; insertIndex < size; parentIndex++) {
            if (!ints.contains(sol2.getOrder()[parentIndex])) {
                newOrder[insertIndex] = sol2.getOrder()[parentIndex];
                insertIndex++;
            }
        }
        return new Solution(newOrder);
    }
}
