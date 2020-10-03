package solver.domain;

import java.util.Arrays;
import java.util.HashMap;

public class CFRTrainer {
    private HashMap<String, InfoSet> infoSets;
    private String[] actions;

    public CFRTrainer() {
        this.infoSets = new HashMap<>();
        this.actions = new String[] {"B", "C"};
    }
    
    public InfoSet getInfoSet(String cardWithHistory) {
        if (!infoSets.containsKey(cardWithHistory)) {            
            infoSets.put(cardWithHistory, new InfoSet());
        }
        
        return infoSets.get(cardWithHistory);
    }
    
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

    public HashMap<String, InfoSet> getInfoSets() {
        return infoSets;
    }
    
    
}
 