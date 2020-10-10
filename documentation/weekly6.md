# Weekly report #6

This week I made my CFR algorithm general use (the game is given as parameter), also improved testing a lot. However, I had some trouble testing the recursive algorithm, might need to split it up to even smaller pieces. Overall test coverage is looking good. The only thing left to implement is a custom random generator, which will be easy.

I also added performance testing for my CFR algorithm. A class called Tester can be found in the util package. It tests the CFR algorithm with different counts of training iterations, keeping track of the running times. I would like to implement a test for the memory use of the algorithm, but didn't know how.

Next week, apart from small fixing, the only thing left to do is the GUI.

Hours spent: 12
