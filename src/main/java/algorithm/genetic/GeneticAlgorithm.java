package algorithm.genetic;

import algorithm.Algorithm;
import algorithm.SolutionRaterImpl;
import loaders.CsvDataDumper;
import model.Node;
import model.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GeneticAlgorithm implements Algorithm {

    private List<Solution> population;
    private List<Double> bestFitnesses;
    private List<Double> worstFitnesses;
    private List<Double> avgFitnesses;
    private CsvDataDumper dumper;
    private final List<Node> nodes;
    private final GeneticAlgortihmParameters params;
    private final Random rnd;


    public GeneticAlgorithm(List<Node> nodes, GeneticAlgortihmParameters parameters, String dumpFile) {
        this.params = parameters;
        this.nodes = nodes;
        this.population = initializePopulation();
        this.rnd = new Random();
        this.bestFitnesses = new ArrayList<>(parameters.getPopSize());
        this.worstFitnesses = new ArrayList<>(parameters.getPopSize());
        this.avgFitnesses = new ArrayList<>(parameters.getPopSize());
        this.dumper = new CsvDataDumper(dumpFile);
    }

    @Override
    public Solution run() {

        for (int i = 0; i < params.getGenerations(); i++) {
            this.population = Stream.generate(this::createChild)
                    .limit(params.getPopSize())
                    .collect(Collectors.toList());
            var stats = this.population.stream().mapToDouble(Solution::rateSolution).summaryStatistics();
            this.bestFitnesses.add(i, stats.getMin());
            this.worstFitnesses.add(i, stats.getMax());
            this.avgFitnesses.add(i, stats.getAverage());
        }
        this.dumper.dumpValues(bestFitnesses, avgFitnesses, worstFitnesses);
        return new SolutionRaterImpl().bestSolution(population);
    }

    private Solution createChild() {
        var s1 = params.getSelection().selection(population);
        var s2 = params.getSelection().selection(population);
        var crossProbability = rnd.nextDouble();
        Solution child = crossProbability < params.getCrossoverProbability() ? params.getCrossover().crossover(s1, s2) : s1;
        var mutation = rnd.nextDouble();
        child = mutation < params.getMutationProbability() ? params.getMutation().mutate(child) : child;
        return child;
    }

    private List<Solution> initializePopulation() {
        return Stream.generate(() -> Solution.generateRandomSolution(nodes.size()))
                .limit(this.params.getPopSize())
                .collect(Collectors.toList());
    }


}
