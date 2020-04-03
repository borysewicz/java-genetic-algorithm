package algorithm.genetic;

import lombok.Value;
import model.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Value
public class GenethicAlgorithmTuner {
    List<Double> crossovers = List.of(0.95);
    List<Double> mutations = List.of( 0.5);
    List<Integer> tournamentSize = List.of( 5);
    int popSize = 100;
    int gen = 1000;
    List<Node> nodes;
    private static final Logger logger = LogManager.getLogger(GenethicAlgorithmTuner.class);

    public void tuneAlgorithm() {
        Map<Double, GeneticAlgortihmParameters> resultMap = new HashMap<>();
        int runCounter = 0;
        for (Double crossover : crossovers) {
            for (Double mutation : mutations) {
                for (Integer tour : tournamentSize) {
                    logger.info("Tuning counter: " + runCounter);
                    double avg = 0.0;
                    var params = new GeneticAlgortihmParameters(
                            popSize,
                            gen,
                            new TournamentSelection(tour),
                            crossover,
                            mutation,
                            new OrderedCrossover(),
                            new InversionMutation()
                    );
                    for (int runs = 0; runs < 10; runs++) {
                        var alg = new GeneticAlgorithm(nodes, params, "./dumps/dump" + runCounter + ".csv");
                        var res = alg.run().rateSolution();
                        avg += res;
                    }
                    avg = avg / 10;
                    resultMap.put(avg, params);
                    runCounter++;
                    logger.info("Average for run: " + (runCounter - 1) + " = " + avg + ", tournament size: " + tour);
                }
            }
        }
        analyzeResults(resultMap);
    }

    private void analyzeResults(Map<Double, GeneticAlgortihmParameters> resultMap) {
        logger.info("Analyzing results...");
        resultMap.keySet().stream().sorted().limit(5).forEach(fitness -> {
            logger.info("Fitness: " + fitness);
            logger.info("Parameters: " + resultMap.get(fitness).toString());
        });
    }

}
