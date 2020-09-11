package solver;

import java.util.Arrays;
import solver.domain.Trainer;
import solver.domain.Rules;

public class Main {

    /**
     * Main method.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int[][] rockPaperScissorMap = {
            {0, -1, 1},
            {1, 0, -1},
            {-1, 1, 0}
        };
        
        String[] rockPaperScissorLabels = { "rock", "paper", "scissors" };
        
        double[][] strategies = { 
            { 1.0, 0, 0 },
            { 0, 0, 1.0 },
            { 0, 1.0, 0 }
        };
        
        
        Rules rps = new Rules("Rock-Paper-Scissors", rockPaperScissorMap, rockPaperScissorLabels);
        
        Trainer t = new Trainer(rps, strategies);
        
        t.train(10000);
        
        double[] d = t.getOptimalStrategy();
        
        System.out.println("iteraatioita: " + t.getTrainIterations());
        System.out.println("");
        System.out.println("strategia: " + Arrays.toString(d));
    }
    
}
