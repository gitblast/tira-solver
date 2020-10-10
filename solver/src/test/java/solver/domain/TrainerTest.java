package solver.domain;

import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TrainerTest {
    
    Trainer t1;
    Trainer t2;
    
    int[][] rockPaperScissorMap = {
        {0, -1, 1},
        {1, 0, -1},
        {-1, 1, 0}
    };

    String[] rockPaperScissorLabels = {
        "rock", "paper", "scissors"
    };      

    double[][] strategies = { 
        { 1.0, 0, 0 },
        { 0, 0, 1.0 },
        { 0, 1.0, 0 }
    };
    
    Rules rps = new Rules("Rock-Paper-Scissors", rockPaperScissorMap, rockPaperScissorLabels);

    @Before
    public void setUp() {
        
        t1 = new Trainer(rps);
        t2 = new Trainer(new Rules("Rock-Paper-Scissors", rockPaperScissorMap, rockPaperScissorLabels), strategies);
    }
    
    @Test
    public void opponentStrategiesIsNullIfNotGivenInConstructor() {
        assertNull(t1.getOpponentStrategies());
        
        assertNotNull(t2.getOpponentStrategies());
        
    }
    
    @Test
    public void getRulesReturnsRules() {
        assertEquals(rps, t1.getRules());
    }
    
    @Test
    public void trainIterationsIsZeroAtBeginning() {
        assertEquals(0, t1.getTrainIterations());
    }
    
    @Test
    public void getOptimalStrategyReturnsNullWithoutTraining() {
        assertNull(null, t1.getOptimalStrategy());
    }
    
    
    
    @Test
    public void trainingIncreasesTrainIterations() {
        t1.train(100);
        
        assertEquals(100, t1.getTrainIterations());
        
        t1.train(200);
        
        assertEquals(300, t1.getTrainIterations());
    }
    
    @Test
    public void getEvenlyDistributedStrategyReturnsEvenlyDistributedStrategy() {
        for (int i = 0; i < 10; i++) {
            double[] s = t1.getEvenlyDistributedStrategy(i);
            
            assertEquals(i, s.length);
            
            for (int j = 0; j < s.length; j++) {
                assertEquals(1.0 / i, s[j], 0.0001);
            }
        }
    }
    
    @Test
    public void getNewStrategyReturnsArrayWithLengthOfPossibleActions() {
        for (int i = 0; i < 10; i++) {
            Trainer t = new Trainer(new Rules("", new int[i][i], new String[i]));
            
            assertEquals(i, t.getNewStrategy().length);
        }
    }
    
    @Test
    public void getNewStrategyReturnsStrategyCalculatedFromOverallRegret() {
        // if no regret exists, expect even distribution
        
        double[] s1 = t1.getNewStrategy();
        
        for (int j = 0; j < s1.length; j++) {
            assertEquals(1.0 / s1.length, s1[j], 0.0001);
        }
        
        // if regret exists, expect matching distribution
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    if (i + j + k == 0) continue;
                    
                    int[] regrets = new int[] { i, j, k };
                    
                    t1.setOverallRegrets(regrets);
                    
                    double[] s2 = t1.getNewStrategy();
                    
                    for (int l = 0; l < s2.length; l++) {
                        assertEquals(1.0 * regrets[l] / (i + j + k), s2[l], 0.0001);
                    }
                }
            }
        }
        
    }
    
    @Test
    public void chooseActionReturnsNumbersOfActionsLength() {
        for (int i = 1; i < 1000; i++) {
            
            int act = t1.chooseAction(new double[i]);
            
            assertTrue(act >= 0);
            assertTrue(act < i);
        }
    }
    
    @Test
    public void getRegretsReturnsRegretsBasedOnRules() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < rockPaperScissorMap.length; j++) {
                int[] regrets = t1.getRegrets(i, j);
                
                for (int k = 0; k < regrets.length; k++) {
                    assertEquals(rockPaperScissorMap[k][j] - i, regrets[k]);
                }
            }
        }
    }
    
    @Test
    public void getOptimalStrategyReturnsAvgIfWeighsNotZero() {        
        
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    if (i + j + k == 0) continue;
                
                    double[] weighs = new double[] { i, j, k };
                    
                    t1 = new Trainer(rps);
                    
                    t1.train(i + j + k);

                    t1.setOverallStrategyWeights(weighs);

                    double[] optimal = t1.getOptimalStrategy();

                    for (int l = 0; l < optimal.length; l++) {
                        assertEquals(1.0 * weighs[l] / (i + j + k), optimal[l], 0.00001);
                    }
                }
                
            }
        }
    }
}
