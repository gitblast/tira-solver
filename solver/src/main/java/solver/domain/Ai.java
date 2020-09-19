package solver.domain;

import java.util.Random;

public class Ai {
    private Rules rules;
    private int[] opponentActions;
    private int rounds;
    private Trainer trainer;

    public Ai(Rules rules) {
        this.rules = rules;
        this.opponentActions = new int[rules.getActionCount()];
        this.rounds = 0;
        this.trainer = new Trainer(rules);
    }
    
    public double[] getStrategy() {
        double[] strategy = trainer.getOptimalStrategy();
        
        if (strategy == null) {
            strategy = trainer.getEvenlyDistributedStrategy(rules.getActionCount());
        }
        
        return strategy;
    }
    
    public int getAction() {
        double[] strategy = getStrategy();
        
        return trainer.chooseAction(strategy);
    }
    
    public void storeOpponentAction(int action) {
        opponentActions[action]++;
        rounds++;
        
        double[] strategy = new double[opponentActions.length];
        
        for (int i = 0; i < opponentActions.length; i++) {
            strategy[i] = opponentActions[i] / rounds;
        }
        
        double[][] strategies = { strategy };
                
        trainer.setOpponentStrategies(strategies);
        
        trainer.train(rounds);
    }
}
