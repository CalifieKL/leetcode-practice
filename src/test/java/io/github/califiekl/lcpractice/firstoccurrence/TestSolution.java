package io.github.califiekl.lcpractice.firstoccurrence;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class TestSolution {
    protected Solution solution;
    protected abstract Solution getSolution();

    @Before
    public void init(){
        solution = getSolution();
    }

    @Test
    public void shouldHandlePartialMatch(){
        assertEquals(4, solution.strStr("mississippi", "issip"));
    }

}
