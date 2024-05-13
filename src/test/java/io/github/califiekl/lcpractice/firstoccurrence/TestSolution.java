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
    public void shouldthrowExceptionIfAnyOfInputIsNull(){
        int exceptionHit=0;
        try{solution.strStr(null, "a");}catch(Exception ex){exceptionHit++;}
        try{solution.strStr("a", null);}catch(Exception ex){exceptionHit++;}
        try{solution.strStr(null, null);}catch(Exception ex){exceptionHit++;}
        assertEquals(3, exceptionHit);

    }
    @Test
    public void shouldHandlePartialMatch(){
        assertEquals(4, solution.strStr("mississippi", "issip"));
    }

    @Test
    public void shouldReturnFirstOccurrence(){
        assertEquals(0, solution.strStr("sadthingssad", "sad"));
    }

    @Test
    public void shouldHandleRepetativePattenInNeedle(){
        assertEquals(2, solution.strStr("hello", "ll"));
    }

}
