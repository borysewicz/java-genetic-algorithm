package algorithm;

import lombok.Value;
import model.Node;
import model.Solution;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Value
public class RandomAlgorithm implements Algorithm {

    private static final Logger logger = LogManager.getLogger(RandomAlgorithm.class);
    List<Node> nodes;
    int popSize;

    public RandomAlgorithm(List<Node> nodes, int popSize) {
        this.nodes = nodes;
        this.popSize = popSize;
    }

    @Override
    public Solution run() {
        logger.info("Random algotithm trial size: " + this.popSize);
        List<Solution> solutions = Stream.generate(() -> Solution.generateRandomSolution(nodes.size())).limit(this.popSize)
                .collect(Collectors.toList());
        SolutionRater rater = new SolutionRaterImpl();
        return rater.bestSolution(solutions);
    }


}
