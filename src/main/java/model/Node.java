package model;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.util.List;

@Value
@EqualsAndHashCode(of = "order")
public class Node {
    int order;
    double x;
    double y;
    @NonFinal
    public static double[][] distanceMatrix;

    private double measureDistance(Node other) {
        return Math.sqrt(Math.pow(other.getY() - this.getY(), 2.0) + Math.pow(other.getX() - this.getX(), 2.0));
    }

    private double measureHaversineDistance(Node other) {
        return Haversine.distance(this.x, this.y, other.x, other.y);
    }

    public static double getDistance(int previous, int next) {
        return distanceMatrix[previous][next];
    }

    public static void loadDistanceMatrix(List<Node> nodes) {
        Node.distanceMatrix = new double[nodes.size()][nodes.size()];
        for (int row = 0; row < nodes.size(); row++) {
            for (int col = 0; col < nodes.size(); col++) {
                distanceMatrix[row][col] = nodes.get(row).measureDistance(nodes.get(col));
            }
        }
    }

    public static void loadCoordDistanceMatrix(List<Node> nodes) {
        Node.distanceMatrix = new double[nodes.size()][nodes.size()];
        for (int row = 0; row < nodes.size(); row++) {
            for (int col = 0; col < nodes.size(); col++) {
                distanceMatrix[row][col] = nodes.get(row).measureHaversineDistance(nodes.get(col));
            }
        }
    }
}
