# Weekly report #2

This week I started to implement the regret matching algorithm. I wrote a couple of classes, configured checkstyle and added tests for most of the code so far. I was able to get the algorithm up and running fairly fast and it already works as intended in rock-paper-scissors. It can also solve different variations of the game, the possible actions and their respective outcomes can be given as input. As I was able to get this far this fast, I think I will look into counterfactual regret matching algorithms.

This was the first time I have worked with mutation tests. I think I get the basic consept of it but still don't understand it fully. Also I see around 50% of the mutation tests are failing, but I don't know how to figure out what I need to fix for them not to fail. Also running pitest produces some warning messages, don't know why:

> WARNING: An illegal reflective access operation has occurred.
>
> WARNING: Illegal reflective access by org.pitest.reloc.xstream.core.util.Fields (file:/home/user/.m2/repository/org/pitest/pitest/1.1.8/pitest-1.1.8.jar) to field java.util.TreeMap.comparator

Overall made good progress this week and got everything up and running without major problems so far.

Time spent: 15h
