import algorithm.Algorithm;
import algorithm.GreedyAlgorithm;
import algorithm.RandomAlgorithm;
import algorithm.genetic.*;
import loaders.DataFileLoader;
import loaders.TspLoader;
import model.Node;
import model.Solution;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class Starter {

    private static boolean isCoord = false;
//            private static final String DATA_SOURCE = "ali535.tsp";
//            private static final String DATA_SOURCE = "kroA200.tsp";
//            private static final String DATA_SOURCE = "kroA150.tsp";
        private static final String DATA_SOURCE = "kroA100.tsp";
//    private static final String DATA_SOURCE = "berlin52.tsp";
//        private static final String DATA_SOURCE = "berlin11_modified.tsp";
    private static final Logger logger = LogManager.getLogger(Starter.class);


    public static void main(String... args) {
        logger.debug("Starting the application");
        logger.info("Preparing to load the data. Data file: " + DATA_SOURCE);
        DataFileLoader<Node> loader = new TspLoader();
        List<Node> nodes = loader.load(DATA_SOURCE);
        if (isCoord){
            Node.loadCoordDistanceMatrix(nodes);
        }else{
            Node.loadDistanceMatrix(nodes);
        }
        logger.info("Data loaded");

//        tuneGeneticAlg(nodes);
        runGeneticAlg(nodes);
//        measureAlgorithm(nodes);
    }

    private static void runGeneticAlg(List<Node> nodes) {
        logger.info("Running genetic algorithm...");
        GeneticAlgortihmParameters params =
                new GeneticAlgortihmParameters(
                        100 , 200, new TournamentSelection(5),
                        0.7, 0.8, new OrderedCrossover(),
                        new InversionMutation());
        Algorithm genetic = new GeneticAlgorithm(nodes, params, "dump.csv");
        Solution geneticBest = genetic.run();
        logger.info("Genetic algorithm finished");
        logger.info("Fitness for genetic algorithm: " + geneticBest.rateSolution());
    }

    private static void tuneGeneticAlg(List<Node> nodes){
        logger.info("TUNING GENETIC ALGORITHM...");
        var tuner = new GenethicAlgorithmTuner(nodes);
        tuner.tuneAlgorithm();
        logger.info("TUNING FINISHED");
    }

    private static void measureAlgorithm(List<Node> nodes){
        var params = new GeneticAlgortihmParameters(
                500, 1000, new TournamentSelection(10),
                0.7, 0.1, new OrderedCrossover(),
                new InversionMutation());
        List<Double> solutions = new ArrayList<>(10);
        for (int i = 0; i < 10; i++){
            var alg = new GeneticAlgorithm(nodes,params,"dump.csv");
            solutions.add(alg.run().getFitness());
        }
        var stats = solutions.stream().mapToDouble(d -> d).summaryStatistics();
        logger.info("Best: " + stats.getMin() + " Worst: " + stats.getMax() +
                " Average: " + stats.getAverage() + " Std: " + MathUtils.countStandardDeviation(solutions, stats.getAverage()));
    }

    private static void runRandomAlg(List<Node> nodes) {
        logger.info("Running random algorithm");
        List<Double> result = new ArrayList<>(10);
        for (int i = 0; i < 10; i++){
            Algorithm alg = new RandomAlgorithm(nodes, 10000);
            Solution bestRandom = alg.run();
            result.add(bestRandom.getFitness());
        }
        var stats = result.stream().mapToDouble(d -> d).summaryStatistics();
        System.out.println("Best in 10 runs: " + stats.getMin());
        System.out.println("Wors in 10 runs: " + stats.getMax());
        System.out.println("Average: " + stats.getAverage());
        System.out.println("Std: " + MathUtils.countStandardDeviation(result, stats.getAverage()));
        logger.info("Random algorithm finished");
    }

    private static void runGreedyAlg(List<Node> nodes) {
        logger.info("Running greedy algorithm");
        Algorithm greedy = new GreedyAlgorithm(nodes);
        Solution greedySol = greedy.run();
        logger.info("Greedy algorithm finished");
        logger.info("Best greedy solution fitness: " + greedySol.rateSolution());
        logger.info("Best greedy solution: " + greedySol.toString());
    }

}
