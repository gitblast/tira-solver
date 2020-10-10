package solver.domain;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jmammela
 */
public class KuhnPokerTest {
    
    KuhnPoker k;
    
    @Before
    public void setUp() {
        k = new KuhnPoker();
    }
    
    
    @Test
    public void possibleActionsIsBorC() {
        String[] actions = k.getPossibleActions();
        
        assertEquals(2, actions.length);
        
        boolean BFound = false;
        boolean CFound = false;
        
        for (int i = 0; i < actions.length; i++) {
            if (actions[i].equals("B")) {
                BFound = true;
            }
            
            if (actions[i].equals("C")) {
                CFound = true;
            }
        }
        
        assertTrue(BFound);
        assertTrue(CFound);
    }
    
    @Test
    public void getRandomCardsReturnsCardsThatArentTheSame() {
        String[] c = k.getRandomCards();
        
        ArrayList<String> possibles = new ArrayList<>();
        
        possibles.add("K");
        possibles.add("Q");
        possibles.add("J");
        
        assertEquals(2, c.length);
        
        for (int i = 0; i < 1000; i++) {
            String[] cards = k.getRandomCards();
            
            String c1 = cards[0];
            String c2 = cards[1];
            
            assertNotEquals(c1, c2);            
            
            
            assertTrue(possibles.contains(c1));
            assertTrue(possibles.contains(c2));
        }
    }
    
    @Test
    public void isTerminalStateWorks() {
        ArrayList<String> terminals = new ArrayList<>();
        
        terminals.add("BC");
        terminals.add("CC");
        terminals.add("CBC");
        terminals.add("BB");
        terminals.add("CBB");
        
        for (int i = 0; i < terminals.size(); i++) {
            assertTrue(k.isTerminalState(terminals.get(i)));
        }
    }
    
    @Test
    public void getOutComeReturnCorrectly() {
        assertEquals(1, k.getOutcome("CBC", null));
        assertEquals(1, k.getOutcome("BC", null));
        
        String[] cards = new String[] { "K", "Q", "J" };
        
        String[] actions = new String[] { "B", "C" };
        
        for (int i = 0; i < actions.length; i++) {
            for (int j = 0; j < actions.length; j++) {
                String his1 = actions[i] + actions[j];
                    
                if (his1.equals("BC")) {
                    continue;
                }
                
                for (int x = 0; x < cards.length; x++) {
                    for (int y = 0; y < cards.length; y++) {
                        
                        if (x == y) continue;
                        
                        int o1 = k.getOutcome(his1, new String[] { cards[x], cards[y] });
                                               
                        if (cards[x].equals("K") || cards[y].equals("J")) {
                            assertTrue(o1 > 0);
                        } else {
                            assertFalse(o1 > 0);
                        }
                    }
                }
                

                if (his1.contains("B")) {            
                    assertEquals(2, Math.abs(k.getOutcome(his1, actions)));
                } else {
                    assertEquals(1, Math.abs(k.getOutcome(his1, actions)));
                }
                
                
                
                for (int l = 0; l < actions.length; l++) {
                    String his = actions[i] + actions[j] + actions[l];
                    
                    if (his.equals("CBC")) {
                        continue;
                    }
                    
                    for (int x = 0; x < cards.length; x++) {
                        for (int y = 0; y < cards.length; y++) {

                            if (x == y) continue;

                            int o1 = k.getOutcome(his, new String[] { cards[x], cards[y] });

                            if (cards[x].equals("J") || cards[y].equals("K")) {
                                assertTrue(o1 > 0);
                            } else {
                                assertFalse(o1 > 0);
                            }
                        }
                    }
                    
                    if (his.contains("B")) {            
                        assertEquals(2, Math.abs(k.getOutcome(his, actions)));
                    } else {
                        assertEquals(1, Math.abs(k.getOutcome(his, actions)));
                    }
                }
            }
        }
    }
}
