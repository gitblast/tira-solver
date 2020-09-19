package solver.domain;

public class Rules {
    private String name;
    private int[][] outcomeMap;
    private String[] labels;

    /**
     * Representation of a one shot game.
     * For each possible action there is a row in the outcome map, with an array containing the outcome against each possible action.
     * 
     * @param name name of the game
     * @param outcomeMap 2d array of integers, must be of shape n * n
     * @param labels string descriptions of the possible actions
     */
    public Rules(String name, int[][] outcomeMap, String[] labels) {
        this.name = name;
        this.outcomeMap = outcomeMap;
        this.labels = labels;
    }
    
    /**
     * Returns the outcome of the first action against the second action.
     * 
     * @param firstAction the index of the first action
     * @param secondAction the index of the second action
     * @return the outcome dictated by the outcome map
     */
    public int getOutcome(int firstAction, int secondAction) {        
        return outcomeMap[firstAction][secondAction];
    }
    
    /**
     * Returns the label for the action.
     * @param action the index of the action
     * @return string label for the action
     */
    public String getLabel(int action) {
        return labels[action];
    }
    
    /**
     * Returns the total number of possible actions.
     * @return number of possible actions
     */
    public int getActionCount() {
        return outcomeMap.length;
    }

    public String[] getLabels() {
        return labels;
    }
    
    
}
