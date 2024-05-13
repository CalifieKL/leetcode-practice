package io.github.califiekl.lcpractice.firstoccurrence;

public class TestSolutionCleaner extends TestSolution{
    @Override
    protected Solution getSolution() {
        return new SolutionCleaner();
    }
}
