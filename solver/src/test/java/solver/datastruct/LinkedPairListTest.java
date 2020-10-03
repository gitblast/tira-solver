package solver.datastruct;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import solver.datastruct.LinkedPairList.Node;
import solver.domain.InfoSet;

public class LinkedPairListTest {    
    LinkedPairList list;
    
    @Before
    public void setUp() {
        list = new LinkedPairList();
    }
    
    @Test
    public void hasNullHeadAndTailAfterInit() {
        assertNull(list.getHead());
        assertNull(list.getTail());
        assertEquals(list.getLength(), 0);
    }
    
    @Test
    public void addingSetsNewNodeAsHeadIfListEmpty() {
        assertEquals(list.getLength(), 0);
        assertNull(list.getHead());
        
        list.add("key", null);
        
        assertNotNull(list.getHead());
    }
    
    @Test
    public void addingSetsNewNodeAsTail() {
        assertNull(list.getTail());
        assertEquals(list.getLength(), 0);
        
        InfoSet i = new InfoSet();
        list.add("key", i);
        
        
        Node node = list.getTail();
        
        assertEquals("key", node.getKey());
        assertEquals(i, node.getValue());
        assertNull(node.getNext());
        
        assertEquals(list.getLength(), 1);
    }
    
    @Test
    public void addingSetsAddedNodeAsNextForCurrentTail() {
        InfoSet i = new InfoSet();
        list.add("key", i);
        
        Node firstTail = list.getTail();
        
        assertEquals(null, firstTail.getNext());
        
        list.add("key2", i);
        
        Node newTail = list.getTail();
        
        assertEquals(newTail, firstTail.getNext());
    }
}
