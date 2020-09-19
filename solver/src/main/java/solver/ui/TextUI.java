package solver.ui;

import java.util.Scanner;
import solver.domain.Ai;
import solver.domain.Rules;
import solver.domain.Trainer;




public class TextUI {    
    public void start() {
        int[][] rpsMap = {
            {0, -1, 1},
            {1, 0, -1},
            {-1, 1, 0}
        };
        
        String[] rpsLabels = { "rock", "paper", "scissors" };        
        
        Rules rps = new Rules("Rock-Paper-Scissors", rpsMap, rpsLabels);
        
        int[][] rpslsMap = {
            {0, -1, 1, 1, -1},
            {1, 0, -1, -1, 1},
            {-1, 1, 0, 1, -1},
            {-1, 1, -1, 0, 1},
            {1, -1, 1, -1, 0}
        };
        
        String[] rpslsLabels = { "rock", "paper", "scissors", "lizard", "spock" };        
        
        Rules rpsls = new Rules("Rock-Paper-Scissors-Lizard-Spock", rpslsMap, rpslsLabels);
        
        Trainer t;
        
        Scanner s = new Scanner(System.in);
        
        System.out.println("Available games:");
        System.out.println("");
        System.out.println("1 - Rock-Paper-Scissors");
        System.out.println("2 - Rock-Paper-Scissors-Lizard-Spock");
        System.out.println("");
        
        while (true) {
            System.out.print("Select game: ");
            String selection = s.nextLine();
            
            System.out.println("");
            
            if (selection.equals("1")) {
                t = new Trainer(rps);
                System.out.println("Playing Rock-Paper-Scissors!");
                System.out.println("");
                
                break;
            } else if (selection.equals("2")) {
                t = new Trainer(rpsls);
                System.out.println("Playing Rock-Paper-Scissors-Lizard-Spock!");
                System.out.println("");
                
                break;
            } else {
                System.out.println("Invalid selection.");
                System.out.println("");
            }
        }
        
        
        loop: while (true) {
            System.out.println("Available commands:");
            System.out.println("");
            System.out.println("p - play against ai");
            // System.out.println("t - train ai");
            System.out.println("x - exit");
            System.out.println("");
            System.out.print("Command: ");     
            String command = s.nextLine();
                   
            System.out.println("");
            
            switch (command) {
                case "x": {
                    System.out.println("Goodbye!");
                    break loop;
                }
                case "p": {
                    play(t.getRules(), s);             
                    
                    break;
                }
                default: {
                    System.out.println("Invalid command.");
                    System.out.println("");
                }
            }
            
            
        }
    }
    
    public void play(Rules rules, Scanner s) {
        String[] labels = rules.getLabels();
        
        Ai ai = new Ai(rules);     
        
        int[] scores = new int[2];
        
        int next = ai.getAction();
        
        loop: while (true) {
            
            System.out.println("Available actions:");
            System.out.println("");
            System.out.println("s - show ai strategy");
            System.out.println("n - show ai next move");
            System.out.println("x - exit game");
            System.out.println("");
            for (int i = 0; i < labels.length; i++) {
                System.out.println(i + " - play " + labels[i]);
            }
            System.out.println("");
            
            System.out.print("Select action: ");
            String action = s.nextLine();
            System.out.println("");
            
            if (action.equals("x")) {
                break;
            }
            
            if (action.equals("s")) {
                double[] strat = ai.getStrategy();
                System.out.println("Ai strategy: ");
                for (int i = 0; i < strat.length; i++) {
                    System.out.print(labels[i] + " " + Math.round(strat[i]*1000000)/10000 + "%   ");
                }
                System.out.println("");
            }
            
            if (action.equals("n")) {
                System.out.println("Ai will choose " + labels[next]);
            }
            
            for (int i = 0; i < labels.length; i++) {
                if (action.equals(String.valueOf(i))) {
                    System.out.println("You chose " + labels[i] + ". Ai chose " + labels[next]);
                    int outcome = rules.getOutcome(i, next);
                    
                    if (outcome < 0) {
                        scores[1]++;
                    } else if (outcome > 0) {
                        scores[0]++;
                    }
                    
                    System.out.println("");
                    System.out.println("SCORE NOW:");
                    System.out.println("YOU " + scores[0] + " - " + scores[1] + " AI");
                    System.out.println("");
                    
                    ai.storeOpponentAction(i);
                    next = ai.getAction();
                }
            }
            
            System.out.println("");
        }
    }
}
