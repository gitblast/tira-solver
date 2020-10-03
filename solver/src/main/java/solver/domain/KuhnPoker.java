package solver.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class KuhnPoker {
    
    public static String[] getRandomCards() {  
        String[] cards = new String[] {"K", "Q", "J"};
        List<String> list = Arrays.asList(cards);
        
        Collections.shuffle(list);
        
        return new String[] { list.get(0), list.get(1) };
    }
    
    
    public static boolean isTerminalState(String history) {
        return history.equals("BC")
                || history.equals("CC")
                || history.equals("CBC")
                || history.equals("BB")
                || history.equals("CBB");
    }
    
    public static int getOutcome(String history, String[] cards) {
        // if opponent folds, player wins 1
        if (history.equals("CBC") || history.equals("BC")) {
            return 1;
        }
        
        // if a bet has been made, player wins or loses 2, otherwise 1
        int outcome = history.contains("B") ? 2 : 1;
        
        int activePlayerIndex = history.length() % 2;
        
        String ourCard = cards[activePlayerIndex];
        
        String opponentCard = cards[activePlayerIndex == 0 ? 1 : 0];
        
        if (ourCard == "K" || opponentCard == "J") {
            return outcome;
        }
        
        return outcome * -1;
    }
}
