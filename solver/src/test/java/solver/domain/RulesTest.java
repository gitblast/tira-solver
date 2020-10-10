package solver.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RulesTest {
    
    Rules r;
   
    int[][] rockPaperScissorMap = {
        {0, -1, 1},
        {1, 0, -1},
        {-1, 1, 0}
    };
        
    String[] rockPaperScissorLabels = {
        "rock", "paper", "scissors"
    };        
    
    @Before
    public void setUp() {           
        r = new Rules("Rock-Paper-Scissors", rockPaperScissorMap, rockPaperScissorLabels);
    }
    
    @Test
    public void getOutcomeReturnsOutcomeOfActions() {
        for (int i = 0; i < rockPaperScissorMap.length; i++) {
            for (int j = 0; j < rockPaperScissorMap[0].length; j++) {
                assertEquals(rockPaperScissorMap[i][j], r.getOutcome(i, j));
            }
        }
    }
    
    @Test
    public void getLabelReturnsCorrectLabels() {
        for (int i = 0; i < rockPaperScissorLabels.length; i++) {
            assertEquals(rockPaperScissorLabels[i], r.getLabel(i));
        }
    }
    
    @Test
    public void getActionCountReturnsAmountOfActions() {
        assertEquals(rockPaperScissorLabels.length, r.getActionCount());
    }
    
    @Test
    public void getLabelsReturnsLabels() {
        assertArrayEquals(rockPaperScissorLabels, r.getLabels());
    }
}
