package solver.domain;

import java.util.Random;
import solver.domain.Rules;

public class Trainer {
    private int[] overallRegrets;
    private double[] overallStrategyWeights;
    private int actionCount;
    private Rules rules;
    private double[][] opponentStrategies;
    private int trainIterations;

    /**
     * Helper class to solve one shot games with a regret matching algorithm.
     * When called with opponentStrategies provided, will train against given strategies.
     * 
     * @param rules {Rules} an object containing the rule set for the game
     * @param opponentStrategies an array containing strategies for opponents.
     */
    public Trainer(Rules rules, double[][] opponentStrategies) {
        this.rules = rules;
        this.actionCount = rules.getActionCount();
        this.overallRegrets = new int[actionCount];
        this.overallStrategyWeights = new double[actionCount];        
        this.trainIterations = 0;
        this.opponentStrategies = opponentStrategies;
    }    
    
    /**
     * Helper class to solve one shot games with a regret matching algorithm.
     * When called without opponentStrategies provided, will train against itself, converging Nash equilibrium.
     * 
     * @param rules {Rules} an object containing the rule set for the game.
     */
    public Trainer(Rules rules) {
        this.rules = rules;
        this.actionCount = rules.getActionCount();
        this.overallRegrets = new int[actionCount];
        this.overallStrategyWeights = new double[actionCount];
        this.trainIterations = 0;
        this.opponentStrategies = null;
    }
    
    /**
     * Calculates an evenly distributed strategy.
     * 
     * @param actionCount number of possible actions
     * @return an array of doubles representing probabilities.
     */
    public double[] getEvenlyDistributedStrategy(int actionCount) {
        double[] strategy = new double[actionCount];
        
        for (int i = 0; i < actionCount; i++) {
            strategy[i] = 1.0 / actionCount;
        }
        
        return strategy;
    }
    
    /**
     * Calculates a new strategy for player using the current cumulative regret.
     * Only positive regret is included in the calculations.
     * 
     * Strategy here means an array of probabilities for choosing each possible action.
     * If no positive regret exists, an array of evenly distributed probabilities is returned.
     * 
     * @return an array of doubles representing probabilities.
     */
    public double[] getNewStrategy() {
        int positiveRegretTotal = 0;
        double[] strategy = new double[actionCount];
        
        // calculate the total amount of positive regret so far
        for (int i = 0; i < overallRegrets.length; i++) {
            if (overallRegrets[i] > 0) {
                positiveRegretTotal += overallRegrets[i];
                strategy[i] = overallRegrets[i];
            }
        }
        
        
        if (positiveRegretTotal > 0) {
            // if positive regret exists, return normalized positive regrets
            
            for (int i = 0; i < strategy.length; i++) {
                strategy[i] /= 1.0 * positiveRegretTotal;
            }
            
            
            return strategy;
            
        } else {
            // if no positive regret exists, return an even distribution
            
            return getEvenlyDistributedStrategy(actionCount);
        }        
    }
    
    /**
     * Calculates the regret for not having chosen another action for all possible actions.
     * 
     * @param outcome the outcome with the chosen action against the opponent's action.
     * @param opponentAction the action chosen by the opponent.
     * @return an array of integers representing the regret for not having chosen that action.
     */
    public int[] getRegrets(int outcome, int opponentAction) {
        int[] regrets = new int[actionCount];
        
        // compare the outcome of having chosen another action for each possible action to get the individual regrets
        for (int action = 0; action < actionCount; action++) {
            regrets[action] = rules.getOutcome(action, opponentAction) - outcome;
        }
        
        return regrets;
    }
    
    /**
     * Chooses an action. The strategy given as parameter defines the probabilities for choosing each action.
     * 
     * @param strategy an array of doubles representing probabilities.
     * @return the index of the chosen action.
     */
    public int chooseAction(double[] strategy) {
        double num = 1.0 * System.nanoTime() % 10000 / 10000; // poor man's random generator
        
        double total = 0;
        
        for (int action = 0; action < strategy.length - 1; action++) {
            total += strategy[action];
            
            if (num < total) {
                return action;
            }
        }
        
        return strategy.length - 1;
    }
    
    /**
     * Train the algorithm against itself.
     * @param firstAction index of the first action.
     * @param strategy index of the second action.
     */
    public void trainAgainstSelf(int firstAction, double[] strategy) {
        // choose second action
        int secondAction = chooseAction(strategy);

        // get outcome
        int outcome = rules.getOutcome(firstAction, secondAction);

        // get regrets                
        int[] regrets = getRegrets(outcome, secondAction);

        // add regrets to overall regrets
        for (int j = 0; j < regrets.length; j++) {
            overallRegrets[j] += regrets[j];
        }
    }
    
    /**
     * Train the algorithm against opponent's strategies.
     * @param ourAction index of the first action.
     */
    public void trainAgainstOpponents(int ourAction) {
        int opponentCount = opponentStrategies.length;
        
        // choose action for each opponent
        int[] opponentActions = new int[opponentCount];

        for (int j = 0; j < opponentCount; j++) {
            opponentActions[j] = chooseAction(opponentStrategies[j]);
        }

        // calculate the outcome of our action against each opponent's action
        int[] outcomes = new int[opponentCount];

        for (int j = 0; j < opponentCount; j++) {
            outcomes[j] = rules.getOutcome(ourAction, opponentActions[j]);
        }

        // calculate the regrets of having chosen our action against each opponent's action
        int[][] regrets = new int[opponentCount][actionCount];

        for (int j = 0; j < opponentCount; j++) {
            regrets[j] = getRegrets(outcomes[j], opponentActions[j]);


            // add the calculated regrets to overall regrets
            for (int k = 0; k < regrets[j].length; k++) {
                overallRegrets[k] += regrets[j][k];
            }
        }
    }
    
    /**
     * Loops the regret matching algorithm. Number of iterations is given as parameter.
     * If opponent strategies were provided in the constructor, runs against them. Otherwise runs against self.
     * @param iterations amount of iterations.
     */
    public void train(int iterations) {
        int opponentCount = opponentStrategies != null ? opponentStrategies.length : 0;
        
        for (int i = 0; i < iterations; i++) {
            // calculate new strategy from overall regret
            double[] ourStrategy = getNewStrategy();
            
            // add the calculated strategy to the overall strategy weights
            for (int j = 0; j < ourStrategy.length; j++) {
                overallStrategyWeights[j] += ourStrategy[j];
            }
            
            // choose our action
            int ourAction = chooseAction(ourStrategy);
            
            if (opponentCount == 0) {
                // if no strategies for opponents were provided, have the algorithm play against itself (using the same strategy)
                
                trainAgainstSelf(ourAction, ourStrategy);
            } else {
                // if strategies for opponents were provided, calculate against them
                
                trainAgainstOpponents(ourAction);
            }
            
        }
        
        // keep track of how much training has been done so far
        this.trainIterations += iterations;
    }
    
    /**
     * Calculates the optimal strategy.
     * 
     * This is done by calculating the average strategy given current training iterations and overall strategy weights.
     * 
     * The more training has been done, the closer this is to Nash equilibrium.
     * 
     * @return null if no training has been done, otherwise an array of doubles representing probabilities.
     */
    public double[] getOptimalStrategy() {
        if (trainIterations == 0) {
            return null;
        }
        
        double[] optimalStrategy = new double[actionCount];
        
        for (int i = 0; i < actionCount; i++) {
            optimalStrategy[i] = overallStrategyWeights[i] / trainIterations;
        }
        
        return optimalStrategy;
    }
    
    public Rules getRules() {
        return rules;
    }

    public int getTrainIterations() {
        return trainIterations;
    }

    public double[][] getOpponentStrategies() {
        return opponentStrategies;
    }

    public void setOpponentStrategies(double[][] opponentStrategies) {
        this.opponentStrategies = opponentStrategies;
    }
    
    

    public void setOverallRegrets(int[] overallRegrets) {
        this.overallRegrets = overallRegrets;
    }

    public void setOverallStrategyWeights(double[] overallStrategyWeights) {
        this.overallStrategyWeights = overallStrategyWeights;
    }

    public int[] getOverallRegrets() {
        return overallRegrets;
    }
    
    
    
}
