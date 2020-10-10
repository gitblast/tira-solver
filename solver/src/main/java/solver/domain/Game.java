
package solver.domain;

public interface Game {
    /**
     * Returns possible actions for the game.
     * @return array of possible actions.
     */
    public String[] getPossibleActions();    
    /**
     * Returns random cards to be used in the simulations.
     * @return array of random cards.
     */
    public String[] getRandomCards();
    /**
     * Checks whether the given state is terminal.
     * @param history current game state.
     * @return true if state is terminal, otherwise false.
     */
    public boolean isTerminalState(String history);
    /**
     * Returns the outcome of the game with given history and cards.
     * @param history state of the game.
     * @param cards cards of the players.
     * @return integer as outcome of the game.
     */
    public int getOutcome(String history, String[] cards);
}
