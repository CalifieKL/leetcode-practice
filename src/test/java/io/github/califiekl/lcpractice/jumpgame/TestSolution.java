package io.github.califiekl.lcpractice.jumpgame;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class TestSolution {
    Solution solution;
    @Before
    public void init(){
        solution = getSolution();
    }

    protected abstract Solution getSolution();

    @Test(expected = Exception.class)
    public void throwExceptionIfInputIsNull(){
        solution.jump(null);
    }

    @Test
    public void sampleTestCase1(){
        int[] input=getInput(2,3,1,1,4);
        assertEquals(2, solution.jump(input));
    }
    @Test
    public void sampleTestCase2(){
        int[] input=getInput(2,3,1,0,4);
        assertEquals(2, solution.jump(input));
    }
    @Test
    public void testJumpWithMaxStepInMiddle(){
        int[] input=getInput(1,2,100,1,1,1,1,1);
        assertEquals(3, solution.jump(input));
    }
    @Test
    public void testJumpWithMaxStepInMiddle1(){
        int[] input=getInput(2,1,100,1,1,1,1,1);
        assertEquals(2, solution.jump(input));
    }
    @Test
    public void exceptionCase1(){
        int[] input=getInput(1,2,3);
        assertEquals(2, solution.jump(input));
    }

    private int[] getInput(Integer... args){
        List<Integer> input = new ArrayList<>();
        for(Integer arg: args){
            input.add(arg);
        }
        return input.stream().mapToInt(i->i).toArray();
    }
}
