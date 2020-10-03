package solver;

import java.util.Arrays;
import java.util.HashMap;
import solver.domain.CFRTrainer;
import solver.domain.InfoSet;
import solver.domain.Trainer;
import solver.domain.Rules;
import solver.ui.TextUI;

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
        
        Trainer t = new Trainer(rps);
//        
//        t.train(10000);
//        
//        double[] d = t.getOptimalStrategy();
//        
//        System.out.println("iteraatioita: " + t.getTrainIterations());
//        System.out.println("");
//        System.out.println("strategia: " + Arrays.toString(d));
        
        TextUI ui = new TextUI();
        
        // ui.start();
        int iter = 100000;
        CFRTrainer cfr = new CFRTrainer();
        long s = System.nanoTime();
        double player1value = cfr.train(iter);
        long l = System.nanoTime();
        
        System.out.println("valmis! aikaa meni " + (l-s)/1e9 + " sekuntia");
        System.out.println("");
        System.out.println("pelaajan 1 ev: " + (player1value / iter));
        System.out.println("");
        
        HashMap<String, InfoSet> infoSets = cfr.getInfoSets();
        
        System.out.println("ACT : [BET , PASS]");
        
        for (String key : infoSets.keySet()) {
            InfoSet i = infoSets.get(key);
            
            double[] d = i.getOptimalStrategy();
            
            System.out.println(key + " : " + Arrays.toString(d));
        }
    }
    
}
