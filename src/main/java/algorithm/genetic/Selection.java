package algorithm.genetic;

import model.Solution;

import java.util.List;

public interface Selection {
    Solution selection(List<Solution> population);
}
