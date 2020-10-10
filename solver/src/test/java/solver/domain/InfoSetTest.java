package solver.domain;

import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class InfoSetTest {
    
    InfoSet set;
    
    @Before
    public void setUp() {
        set = new InfoSet();
    }
    
    @Test
    public void constructorWorks() {
        assertEquals(2, set.getActionCount());
        assertEquals(2, set.getOverallRegrets().length);
        assertEquals(2, set.getOverallStrategyWeights().length);
    }
    
        
    @Test
    public void addRegretAddsToOverall() {
        double[] overall = set.getOverallRegrets();
        
        Random r = new Random();
        
        for (int i = 0; i < overall.length; i++) {
            assertEquals(0, overall[i], 0.0000001);
        }
        
        
        
        for (int i = 0; i < overall.length; i++) {
            for (int j = 0; j < 10; j++) {
                double randomDouble = r.nextDouble();
                double initial = overall[i];


                set.addRegret(i, randomDouble);

                assertEquals(initial + randomDouble, overall[i], 0.0000001);
            }
        }
    }
    
    @Test
    public void getEvenlyDistributedWorks() {
        double[] strat = set.getEvenlyDistributedStrategy(set.getActionCount());
        
        double even = 1.0 / set.getActionCount();
        
        for (int i = 0; i < strat.length; i++) {
            assertEquals(even, strat[i], 0.0000001);
        }
    }
    
    @Test
    public void getNewStrategyReturnsArrayWithLengthOfPossibleActions() {
        assertEquals(set.getActionCount(), set.getEvenlyDistributedStrategy(set.getActionCount()).length);
    }
    
    @Test
    public void getNewStrategyReturnsStrategyCalculatedFromOverallRegret() {
        // if no regret exists, expect even distribution
        
        double[] s1 = set.getNewStrategy(1);
        
        for (int j = 0; j < s1.length; j++) {
            assertEquals(1.0 / s1.length, s1[j], 0.0001);
        }
        
        // if regret exists, expect matching distribution
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i + j == 0) continue;

                double[] regrets = new double[] { i, j};

                set.setOverallRegrets(regrets);
                double[] s2 = set.getNewStrategy(1);

                for (int l = 0; l < s2.length; l++) {
                    assertEquals(1.0 * regrets[l] / (i + j), s2[l], 0.0001);
                            
                }
                
            }
        }
        
    }
    
    @Test
    public void getNewStrategyIncreasesOverallWeighsAdjustedWithReachProb() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i + j == 0) continue;

                double[] regrets = new double[] { i, j };

                set.setOverallRegrets(regrets);

                double reach = new Random().nextDouble();
                double[] weighs = set.getOverallStrategyWeights();
                double[] weighsFirst = new double[weighs.length];
                for (int x = 0; x < weighs.length; x++) {
                    weighsFirst[x] = weighs[x];
                }

                double[] s2 = set.getNewStrategy(reach);
                
                double[] weighsNow = set.getOverallStrategyWeights();

                for (int l = 0; l < s2.length; l++) {
                    assertEquals(weighsNow[l], weighsFirst[l] + s2[l] * reach, 0.00001);
                }
                
            }
        }
    }
    
    @Test
    public void getOptimalStrategyReturnsEvenIfStrategyWeighSumZero() {
        double[] s1 = set.getOptimalStrategy();
        
        for (int i = 0; i < s1.length; i++) {
            assertEquals(1.0 / s1.length, s1[i], 0.00001); 
        }
    }
    
    @Test
    public void getOptimalStrategyReturnsAvgIfWeighsNotZero() {        
        
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i + j == 0) continue;
                
                double[] weighs = new double[] { i, j };
                
                set.setOverallStrategyWeights(weighs);
                
                double[] optimal = set.getOptimalStrategy();
                
                for (int l = 0; l < optimal.length; l++) {
                    assertEquals(1.0 * weighs[l] / (i + j), optimal[l], 0.00001);
                }
            }
        }
    }
    
}
