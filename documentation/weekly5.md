# Weekly report #5

This week I got good progress: finished a working version of a counterfactual regret minimization algorithm that can solve sequential limited information games. I have used it to only solve Kuhn's poker so far, but with little tweaking it will be genera-use.

The algoritm stores it's information sets in a hashmap so I also implemented my own hashmap data structure. The hashmap uses a linked list internally, implemented that as well.

I also added close to 100% test coverage for the custom data structures. Now that the algorithm is fully completed, I can also start with the performance testing. Also need to write more unit tests to the algorithm itself.

Also did my peer review.

Hours spent: 17
