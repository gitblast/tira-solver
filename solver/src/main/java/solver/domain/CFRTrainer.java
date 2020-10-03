package solver.domain;

import solver.datastruct.InfoSetMap;

public class CFRTrainer {
    private InfoSetMap infoSets;
    private String[] actions;

    /**
     * Constructor.
     */
    public CFRTrainer() {
        this.infoSets = new InfoSetMap();
        this.actions = new String[] {"B", "C"};
    }
    
    /**
     * Returns the InfoSet stored with given String. If no InfoSet is found, one is created.
     * @param cardWithHistory String of player's card appended with current history string.
     * @return InfoSet matching search string.
     */
    public InfoSet getInfoSet(String cardWithHistory) {
        if (infoSets.get(cardWithHistory) == null) {            
            infoSets.put(cardWithHistory, new InfoSet());
        }
        
        return infoSets.get(cardWithHistory);
    }
    
    /**
     * Recursive counterfactual regret minimization algorithm.
     * @param cards Array of possible cards.
     * @param history String representation of the game history so far.
     * @param reachProbs Array of doubles representing the reach probabilities to the current node for each player.
     * @param activePlayerIndex Index of the player with turn.
     * @return counterfactual value of the current node.
     */
    public double CFR(String[] cards, String history, double[] reachProbs, int activePlayerIndex) {
        if (KuhnPoker.isTerminalState(history)) {
            return KuhnPoker.getOutcome(history, cards) * 1.0;
        }
        
        String ownCard = cards[activePlayerIndex];
        
        InfoSet infoSet = getInfoSet(ownCard + history);
        
        double[] strategy = infoSet.getNewStrategy(reachProbs[activePlayerIndex]);
        
        int opponentIndex = activePlayerIndex == 0 ? 1 : 0;
        
        double[] counterfactualValues = new double[strategy.length];
        
        for (int i = 0; i < strategy.length; i++) {
            double[] newReachProbs = new double[reachProbs.length];
            
            for (int j = 0; j < reachProbs.length; j++) {
                newReachProbs[j] = reachProbs[j];
            }
            
            newReachProbs[activePlayerIndex] *= strategy[i];
            
            counterfactualValues[i] = -1 * CFR(cards, history + actions[i], newReachProbs, opponentIndex);
        }
        
        double overallValue = 0;
        
        for (int i = 0; i < counterfactualValues.length; i++) {
            overallValue += counterfactualValues[i] * strategy[i];
        }
        
        for (int i = 0; i < counterfactualValues.length; i++) {
            infoSet.addRegret(i, reachProbs[opponentIndex] * (counterfactualValues[i] - overallValue));
        }
        
        return overallValue;
    }
    
    
    /**
     * Trains the algorithm against itself.
     * @param iterations number of training iterations.
     * @return expected value for player 1.
     */
    public double train(int iterations) {
        double value = 0;
        
        for(int i = 0; i < iterations; i++) {
            String[] cards = KuhnPoker.getRandomCards();
            String history = "";
            double[] reachProbs = new double[] {1, 1};          
            value += CFR(cards, history, reachProbs, 0);
            
        }
        
        return value;
    }

    public InfoSetMap getInfoSets() {
        return infoSets;
    }
    
    
}
 