package algorithm.genetic;

import model.Solution;

import java.util.Random;

public class InversionMutation implements Mutation {

    private final Random random;

    public InversionMutation(){
        this.random = new Random();
    }

    @Override
    public Solution mutate(Solution solution) {
        var size = solution.getOrder().length;
        var firstIndex = random.nextInt(size);
        var secondIndex = random.nextInt(size);
        if (firstIndex > secondIndex){
            var temp = firstIndex;
            firstIndex = secondIndex;
            secondIndex = temp;
        }
        while (secondIndex > firstIndex){
            swap(solution,firstIndex,secondIndex);
            firstIndex++;
            secondIndex--;
        }
        return solution;
    }

    private void swap(Solution sol, int firstIndex, int secondIndex){
        var temp = sol.getOrder()[firstIndex];
        sol.getOrder()[firstIndex] = sol.getOrder()[secondIndex];
        sol.getOrder()[secondIndex] = temp;
    }
}
