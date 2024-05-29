package io.github.califiekl.lcpractice.minwindowsubstr;

import org.junit.Before;

public abstract class TestSolution {
    protected Solution solution;
    protected abstract Solution getSolution();

    @Before
    public void init(){
        solution=getSolution();
    }
}
