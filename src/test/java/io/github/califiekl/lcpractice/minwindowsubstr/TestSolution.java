package io.github.califiekl.lcpractice.minwindowsubstr;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class TestSolution {
    protected Solution solution;
    protected abstract Solution getSolution();

    @Before
    public void init(){
        solution=getSolution();
    }

    @Test
    public void shouldReturnEmptyStringWhenTargetLongerThanOriginal(){
        assertEquals("", solution.minWindow("","a"));
    }

    @Test
    public void shouldReturnEmptyStringWhenTargetNotInOriginal(){
        assertEquals("", solution.minWindow("a","aa"));
    }
    @Test
    public void shouldReturnMinimumSubStringWithTargetLetter(){
        assertEquals("BANC",solution.minWindow("ADOBECODEBANC", "ABC"));
    }
    @Test
    public void shouldReturnOriginalIfOriginalAndTargetAreTheSame(){
        assertEquals("AA",solution.minWindow("AA", "AA"));
        assertEquals("ABCDE",solution.minWindow("ABCDE", "ABCDE"));
    }
}
