package algorithm.genetic;

import lombok.Value;


@Value
public class GeneticAlgortihmParameters {
    int popSize;

    int generations;

    Selection selection;

    double crossoverProbability;

    double mutationProbability;

    Crossover crossover;

    Mutation mutation;

    @Override
    public String toString(){
        return "Popsize: " + popSize +
                " Generations: " + generations +
                " Crossover Probability: " + crossoverProbability +
                " Mutation Probability: " + mutationProbability;
    }

}
