package solver.domain;

public class InfoSet {
    private double[] overallRegrets;
    private double[] overallStrategyWeights;
    private int actionCount;
    private int trainIterations;

    /**
     * Constructor.
     */
    public InfoSet() {
        this.actionCount = 2;
        this.overallRegrets = new double[actionCount];
        this.overallStrategyWeights = new double[actionCount];        
        this.trainIterations = 0;
    } 
    
    /**
     * Adds to the cumulative regret of the information set.
     * @param actionIndex index of the action.
     * @param sumToAdd sum to add to the cumulative regret.
     */
    public void addRegret(int actionIndex, double sumToAdd) {
        overallRegrets[actionIndex] += sumToAdd;
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
     * @param reachProbability probability of reaching this information set.
     * @return an array of doubles representing probabilities.
     */
    public double[] getNewStrategy(double reachProbability) {
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
            // if positive regret exists, use normalized positive regrets
            for (int i = 0; i < strategy.length; i++) {
                strategy[i] /= 1.0 * positiveRegretTotal;
            }
            
        } else {
            // if no positive regret exists, use an even distribution
            
            strategy = getEvenlyDistributedStrategy(actionCount);
            
        }    
        
        
        for (int i = 0; i < strategy.length; i++) {
            // keep track of overall strategy weighed by the reach probabilities
            overallStrategyWeights[i] += reachProbability * strategy[i];
        }
        
        return strategy;
    }
    
    /**
     * Calculates optimal strategy for the information set.
     * @return array of doubles representing probabilities.
     */
    public double[] getOptimalStrategy() {
        double sum = 0;
        double[] strat = new double[overallStrategyWeights.length];
        
        for (int i = 0; i < overallStrategyWeights.length; i++) {
            sum += overallStrategyWeights[i];
        }
        
        if (sum > 0) {
            for (int i = 0; i < overallStrategyWeights.length; i++) {
                strat[i] = overallStrategyWeights[i] / sum;
            }
        } else {
            strat = getEvenlyDistributedStrategy(actionCount);
        }
        
        return strat;
    }
}
