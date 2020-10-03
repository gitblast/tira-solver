package solver.datastruct;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import solver.domain.InfoSet;

/**
 *
 * @author jmammela
 */
public class InfoSetMapTest {
    InfoSetMap map;
    
    @Before
    public void setUp() {
        map = new InfoSetMap();
    }
    

    @Test
    public void startsWithArrayOfLength16() {
        assertEquals(16, map.getValues().length);
        assertEquals(0, map.size());
    }
    
    @Test
    public void expandsWhenValuesReach75PercentCapacity() {
        int length = 16;
        
        assertEquals(length, map.getValues().length);
        
        
        for (int i = 0; i <= length * 0.75; i++) {
            map.put("key" + i, null);
        }
        
        
        assertEquals(length * 2, map.getValues().length);
    }
    
    @Test
    public void getReturnsNullIfNoValueSet() {
        assertEquals(null, map.get("key123"));
        
        assertEquals(16, map.getValues().length);
        
        assertEquals("key123".hashCode() % 16, "key321".hashCode() % 16);
        
        map.put("key123", new InfoSet());
        
        assertEquals(null, map.get("key321"));
    }
    
    @Test
    public void getReturnsValueIfSet() {
        InfoSet i = new InfoSet();
        
        map.put("key123", i);
        
        assertEquals(i, map.get("key123"));
    }
    
    @Test
    public void putAddsValueAndIncreasesSize() {
        assertEquals(0, map.size());
        
        InfoSet i = new InfoSet();
        
        map.put("key123", i);
        
        assertEquals(i, map.get("key123"));
        assertEquals(1, map.size());
    }
    
    @Test
    public void putOverridesValueWithSameKey() {
        assertEquals(0, map.size());
        
        InfoSet i = new InfoSet();
        
        map.put("key123", i);
        
        assertEquals(i, map.get("key123"));
        assertEquals(1, map.size());
        
        InfoSet i2 = new InfoSet();
        
        map.put("key123", i2);
        
        assertEquals(i2, map.get("key123"));
        assertEquals(1, map.size());
    }
    
    @Test
    public void expandDoublesSize() {
        int size = 16;
        
        for (int i = 0; i < 10; i++) {            
            assertEquals(size, map.getValues().length);
            map.expand();
            assertEquals(size * 2, map.getValues().length);
            size *= 2;
        }        
    }
    
    @Test
    public void expandCallsCopyValues() {
        InfoSet i = new InfoSet();
        map.put("key", i);
        
        assertEquals(i, map.get("key"));
        
        map.expand();
        
        assertEquals(i, map.get("key"));
    }
    
    @Test
    public void copyValuesCopiesTheValuesInCurrentIndexToGivenArray() {
        LinkedPairList[] list = new LinkedPairList[map.getValues().length];       
        
        
        InfoSet set = new InfoSet();
        
        map.put("key123", set);
        
        
        int index = 0;
        
        for (int i = 0; i < map.getValues().length; i++) {
            if (map.getValues()[i] != null) {
                index = i;
                
                break;
            }
        }
        
        for (LinkedPairList l : list) {
            assertEquals(null, l);
        }
        
        map.copyValues(list, index);
        
        InfoSet found = null;
        
        for (LinkedPairList l : list) {
            if (l != null) {
                found = l.getHead().getValue();
            }
        }
        
        assertEquals(set, found);
        
        
        int newHash = "key123".hashCode() % list.length;
        
        if (newHash < 0) {
            newHash *= -1;
        }
        
        assertEquals(set, list[newHash].getHead().getValue());
    }
}
