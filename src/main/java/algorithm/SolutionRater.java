package algorithm;

import model.Node;
import model.Solution;

import java.util.List;

public interface SolutionRater {

    Solution bestSolution (List<Solution> solutionList);

}
