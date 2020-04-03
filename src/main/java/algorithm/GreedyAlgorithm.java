package algorithm;

import lombok.Value;
import model.Node;
import model.Solution;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.MathUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Value
public class GreedyAlgorithm implements Algorithm {

     List<Node> nodes;
    Random rnd = new Random();
    private static final Logger logger = LogManager.getLogger(GreedyAlgorithm.class);


    @Override
    public Solution run() {
        Map<Double, Solution> fitness = nodes.stream().map(this::findSolution)
                .collect(Collectors.toMap(Solution::rateSolution,Function.identity(),
                        (val1,val2) -> val1));
        var stats = fitness.keySet().stream().mapToDouble(fit -> fit).summaryStatistics();
        var averageDistance = stats.getAverage();
        var worstSolution = stats.getMax();
        var bestSolution = stats.getMin();
        double standardDeviation = MathUtils.countStandardDeviation(fitness.keySet(), averageDistance);
        logger.info("Average greedy algorithm fitness: " + averageDistance);
        logger.info("Worst greedy algorithm fitness: " + worstSolution);
        logger.info("Standard deviation of greedy algorithm: " + standardDeviation);
        return fitness.get(bestSolution);
    }



    private Solution findSolution(Node starting){
        List<Node> visited = new LinkedList<>();
        Node current = starting;
        visited.add(current);
        boolean[] wasVisited = new boolean[nodes.size()];
        wasVisited[starting.getOrder() -1] = true;

        while (visited.size() < nodes.size()){
            current = findClosestNode(current, wasVisited);
            wasVisited[current.getOrder() -1] = true;
            visited.add(current);
        }
        int[] newOrder = new int[nodes.size()];
        for (int i = 0; i < nodes.size(); i++){
            newOrder[i] = visited.get(i).getOrder() - 1;
        }
        return new Solution(newOrder);
    }

    private Node findClosestNode(final Node current, final boolean[] wasVisited) {
        return nodes.stream()
                .filter(node -> !wasVisited[node.getOrder() - 1]).min((n1, n2) -> {
            var n1Dist = Node.getDistance(n1.getOrder() -1, current.getOrder() -1);
            var n2Dist = Node.getDistance(n2.getOrder() -1, current.getOrder() - 1);
            return Double.compare(n1Dist, n2Dist);
        }).orElse(new Node(0,0,0));
    }


}
