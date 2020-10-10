
package solver.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import solver.datastruct.InfoSetMap;

public class CFRTrainerTest {
    
    CFRTrainer t;
    
    Game kuhn = new KuhnPoker();
    
    @Before
    public void setUp() {
        t = new CFRTrainer(kuhn);
    }
    
    
    @Test
    public void constructor() {
        assertEquals(InfoSetMap.class, t.getInfoSets().getClass());
        assertEquals(kuhn, t.getGame());
    }
    
    @Test
    public void getInfoSetCreatesNewIfNotFoundAndReturns() {
        assertEquals(0, t.getInfoSets().size());
        
        InfoSet i = t.getInfoSet("test");
        
        assertEquals(1, t.getInfoSets().size());
        
        InfoSet x = new InfoSet();
        
        t.getInfoSets().put("test", x);
        
        assertEquals(1, t.getInfoSets().size());
        assertEquals(x, t.getInfoSet("test"));
    }
    
    @Test
    public void CFRHaltsOnTerminalStates() {
        double d = t.CFR(null, "CBC", null, 0);
        
        assertEquals(1, d, 0.00001);
    }
    
    @Test
    public void test() {
        
    }
}
