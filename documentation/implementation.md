# Implementation

The project contains two algorithms: Regret matching and counterfactual regret minimization (which uses regret matching internally).

The counterfactual regret matching algorithm can be used to solve sequential, imperfect information games such as poker. The regret matching algorithm can solve simple one shot games such as Rock-Paper-Scissors.

## Regret matching

A regret matching algorithm is implemented with having the algorithm play the game we want to solve in a loop. After every game it will update its strategy, keeping track of the results and the regret of not having chosen another available action.

In a regret matching algorithm a game is represented as the number of players, a set of possible actions (ie. rock, paper and scissors) and a mapping function that takes the actions of the players and maps it into a number representing the outcome of the game. For example, if in Rock-Paper-Scissors player1 chose rock and player2 chose paper, the function would return -1 for player1 and 1 for player2. A tie would return 0 for both.

The algorithm starts by calculating a strategy, strategy in this case meaning the probabilities of choosing each possible action. The way the strategy is calculated is explained later in this document. This calculated strategy is then added to the cumulative strategy so far.

The algorithm then chooses its action using this strategy. If for example the strategy for a game of RPS would be 80% rock, 10% paper, 10% scissors, it will pick a random number between 0 and 1 and choose rock if the random number is below 0.8, paper if below 0.9 and so on.

Then the action for the "opponent" will be chosen. The strategy for the opponent can be either provided (this way the algorithm will find the optimal strategy against that strategy) or we can have the algorithm play against itself. When playing against itself it will find the general optimal strategy.

The choice of the algorithm will then be compared with the choice of the opponent, using the mapping function. It will then calculate the regret of not having chosen another action for each possible action. The regret is calculated by comparing the true outcome with the outcome of having chosen another action, for example if we chose rock and the opponent chose paper, our outcome will be -1. Had we chosen paper, the outcome would be 0, hence our regret of not having chosen paper is 1. Same way the regret of not having chosen scissors is 2 (instead of -1 the outcome would be 1).

These calculated regrets are added to the cumulative regrets after each training iteration. This cumulated regret is used to calculate the strategy discussed earlier in this document. This is done by dividing the positive regret of each action with the total amount of positive regret. So for example if the cumulative regrets for rock, paper and scissors would be 1, 2 and 3, the strategy would be 1/6 rock, 2/6, paper and 3/6 scissors. If no positive regret exists (like in the first iteration), an even distribution is used.

This loop is then repeated for as long as wanted. The more training iterations are made, the closer the results of the algorithm are to game theory optimal (the algorithm converges to Nash equilibrium). After the training iterations, the optimal strategy can be calculated by dividing the cumulative strategies for each action with the number of training iterations.

## Counterfactual regret minimization (CFR)

The CFR -algorithm can be used to solve sequential imperfect information games. The algorithm is used in all the modern solvers for poker.

In sequential games there are several possible game states (whereas in one shot games the game is over after one action). These states together with any additional information the player has (ie. his own cards in poker) form so called information sets. For each possible combination of game state and player-specific information, an information set exists.

The CFR uses regret matching to calculate the optimal strategy for each individual information set. For very simple games this can be very fast, for example the implemented CFR solves Kuhn's poker, a game with only 12 information sets, in a fraction of a second. However as the complexity of the game increases, the time (and memory requirements) for the algorithm rapidly grow. For example, in Texas Holdem limit heads up, there are 10^14 information sets and solving the game even with very powerful computers take several days.
