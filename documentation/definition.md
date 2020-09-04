# Project definition

The goal of the project is to build a regret matching algorithm that can solve simple, imperfect information one-shot games such as Rock-Paper-Scissors.

## Algorithms and data structures

The project will use a regret matching algorithm. To implement it, only basic arrays are required.

## Input and output

Input will be:

- number of players
- strategy for each opponent \*
- rules of the game \*\*
- number of iterations used for the training

Output will be the game theory optimal strategy against the given strategies.

\*) The strategy is modeled as the probability to choose each action available for the player. For example in Rock-Paper-Scissors, a strategy might be choosing rock 50% of the time, paper 25% of the time and scissors 25% of the time.

\*\*) The rules of the game include the possible actions a player can choose between and a function mapping the players' choises to an outcome. For example in Rock-Paper-Scissors, the possible actions are choosing rock, paper or scissors, and the function mapping the choises to an outcome returns -1 if player loses, 0 if the players tie and +1 if the player wins. So if the game had 2 players, player 1 chose rock and player 2 chose paper, the mapping function would return -1 for player 1 and +1 for player 2.

There will also be a possibility to play against an untrained AI, and see how it adjusts its strategy as the game goes on.

## Additional info

Project will be written in Java. The documentation will be in English. The author is a student in the Bachelor's Programme in Computer Science.

## Sources

- https://arxiv.org/pdf/1811.00164.pdf
- http://modelai.gettysburg.edu/2013/cfr/cfr.pdf
