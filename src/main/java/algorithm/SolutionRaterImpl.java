package algorithm;

import model.Solution;

import java.util.Comparator;
import java.util.List;


public class SolutionRaterImpl implements SolutionRater {

    @Override
    public Solution bestSolution(List<Solution> solutionList){
        return solutionList.stream().min(Comparator.comparingDouble(Solution::rateSolution)).orElseThrow();
    }


}
