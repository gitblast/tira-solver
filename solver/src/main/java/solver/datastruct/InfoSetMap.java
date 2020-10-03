package solver.datastruct;

import solver.datastruct.LinkedPairList.Node;
import solver.domain.InfoSet;

/**
 * HashMap implementation with Strings as keys and InfoSets as values.
 */
public class InfoSetMap {
    private LinkedPairList[] values;
    private int valueCount;

    /**
     * Constructor for InfoSetMap.
     */
    public InfoSetMap() {
        this.values = new LinkedPairList[16];
        this.valueCount = 0;
    }
    
    /**
     * Looks for key in the map, and returns its value if found.
     * @param key String key
     * @return InfoSet if key found, otherwise null.
     */
    public InfoSet get(String key) {
        int hash = key.hashCode() % values.length;
        
        if (hash < 0) {
            hash *= -1;
        }
        
        
        LinkedPairList list = this.values[hash];
        
        if (list == null) {
            return null;
        }
        
        Node node = list.getHead();
        
        while (node != null) {
            
            if (node.getKey().equals(key)) {
                return node.getValue();
            }
            
            node = node.getNext();
        }
        
        return null;
    }

    public LinkedPairList[] getValues() {
        return values;
    }

    /**
     * Returns the amount of values stored in the map.
     * @return amount of values stored.
     */
    public int size() {
        return valueCount;
    }
    
    
    /**
     * Inserts a new value to the map. If a value with the same key is already found, it is replaced.
     * @param key String key.
     * @param value InfoSet to store with the key. 
     */
    public void put(String key, InfoSet value) {
        int hash = key.hashCode() % values.length;
        hash = hash < 0 ? -1 * hash : hash;
        
        if (values[hash] == null) {
            values[hash] = new LinkedPairList();
        }
        
        
        Node node = values[hash].getHead();
        
        while (node != null) {
            if (node.getKey().equals(key)) {
                node.setValue(value);
                
                return;
            }
            
            node = node.getNext();
        }
        
        values[hash].add(key, value);
        
        valueCount++;
        
        if (1.0 * valueCount / values.length > 0.75) {
            this.expand();
        }
    }
    
    /**
     * Doubles the size of the value array.
     */
    public void expand() {
        LinkedPairList[] newValues = new LinkedPairList[values.length * 2];
        
        for (int i = 0; i < values.length; i++) {
            copyValues(newValues, i);
        }
        
        this.values = newValues;
    }
    
    /**
     * Copies the values of the current value array at given index to the given array. Calculates new hashes for each value.
     * @param list List to copy the new values.
     * @param index Index of the value array to copy from.
     */
    public void copyValues(LinkedPairList[] list, int index) {
        if (values[index] == null) {
            return;
        }
        
        Node node = values[index].getHead();
        
        
        
        while (node != null) {
            int hash = node.getKey().hashCode() % list.length;
        
            if (hash < 0) {
                hash *= -1;
            }
            
            if (list[hash] == null) {
                list[hash] = new LinkedPairList();
            }
            
            list[hash].add(node.getKey(), node.getValue());
            
            node = node.getNext();
        }
    }
}

