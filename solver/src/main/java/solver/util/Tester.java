
package solver.util;

import java.util.ArrayList;
import solver.domain.CFRTrainer;
import solver.domain.Game;
import solver.domain.KuhnPoker;

/**
 * Performance tests
 */
public class Tester {

    public Tester() {
    }
    
    /**
     * Runs the CFR algorithm for Kuhn's poker with different iteration sizes
     */
    public void run() {
        int[] iterations = new int[] { 100, 1000, 10000, 100000, 1000000 };
        
        for (int i = 0; i < iterations.length; i++) {
            ArrayList<Long> times = new ArrayList<>();
            
            for (int j = 0; j < 100; j++) {
                Game kuhn = new KuhnPoker();
        
                CFRTrainer cfr = new CFRTrainer(kuhn);

                long start = System.nanoTime();

                cfr.train(iterations[i]);

                long stop = System.nanoTime();
                
                times.add(stop-start);
            }
            
            long sum = 0;
            
            for (int j = 0; j < times.size(); j++) {
                sum += times.get(i);
            }
            
            double avg = 1.0 * sum / times.size();
            
            printResult(avg, iterations[i]);
        }
    }
    
    public void printResult(double avg, int iterations) {
        System.out.println("With " + iterations + " iterations, the average run time was " + avg/1e9 + " seconds.");
        System.out.println("");
    }
    
}
