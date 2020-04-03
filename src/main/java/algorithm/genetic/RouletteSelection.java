package algorithm.genetic;

import lombok.Value;
import model.Solution;

import java.util.List;
import java.util.Random;

@Value
public class RouletteSelection implements Selection {
    private static final Random rnd = new Random();

    @Override // stochastic acceptance algorithm + fitness function scaling
    public Solution selection(List<Solution> population) {
        var stats = population.stream().mapToDouble(Solution::rateSolution).summaryStatistics();
        double max = stats.getMax();
        double min = stats.getMin();
        var a = 19;
        double bestFitness = Math.pow((max -  min), a) ;
        while(true){
            int index = rnd.nextInt(population.size());
            var sol = population.get(index);
            var solFitness = Math.pow((max - sol.rateSolution()), a);
            double probability = rnd.nextDouble();
            var solutionChance = solFitness/bestFitness;
            if (solutionChance > probability){
                return sol;
            }
        }
    }


    // Naive implementation
//    @Override
//    public Solution selection(List<Solution> population) {
//        double fitnessSum = population.stream().mapToDouble(Solution::rateSolution).map(d -> 1 / d).sum();
//        double probability = rnd.nextDouble();
//        double nextPoint = 0;
//        double previousPoint = 0;
//        for (Solution sol : population) {
//            double solFitness = 1 / sol.getFitness();
//            nextPoint += solFitness / fitnessSum;
//            if (probability > previousPoint && probability < nextPoint) {
//                return sol;
//            }
//            previousPoint = nextPoint;
//        }
//        return population.get(population.size() - 1);
//    }


}
