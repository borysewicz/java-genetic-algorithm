package algorithm.genetic;

import model.Node;
import model.Solution;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class TournamentSelection implements Selection {

    private final int tournamentSize;
    private final Random rnd;

    public TournamentSelection(int tournamentSize){
        this.tournamentSize = tournamentSize;
        this.rnd = new Random();
    }

    @Override
    public Solution selection(List<Solution> population) {
       return Stream.generate(() -> selectRandomSolution(population)).limit(tournamentSize)
                .min(Comparator.comparingDouble(Solution::rateSolution))
                .orElseThrow();
    }

    private Solution selectRandomSolution(List<Solution> population){
        var index = rnd.nextInt(population.size());
        return population.get(index);
    }

}
