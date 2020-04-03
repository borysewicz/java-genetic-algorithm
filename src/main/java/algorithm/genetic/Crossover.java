package algorithm.genetic;

import model.Solution;

public interface Crossover {

    Solution crossover(Solution sol1, Solution sol2);

}
