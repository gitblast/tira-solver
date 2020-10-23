# Testing

## Unit testing

`mvn test`

Almost everything except the ui is comprehesively unit tested.

## Mutation testing

`mvn org.pitest:pitest-maven:mutationCoverage`

Runs mutation tests, also generating the coverage report.

## Performance testing

A class called "Tester" can be found in the util -package. The run -method of the class will run the CFR algorithm with several different training iteration counts, keeping track of the running times. The game used in the tests by default is Kuhn Poker, however another game can be provided as a parameter for the run -function.

The method will print out the average running times:

```
With 100 iterations, the average run time was 0.003743608 seconds.

With 1000 iterations, the average run time was 0.002289722 seconds.

With 10000 iterations, the average run time was 0.006795258 seconds.

With 100000 iterations, the average run time was 0.082065488 seconds.

With 1000000 iterations, the average run time was 0.654918904 seconds.
```
