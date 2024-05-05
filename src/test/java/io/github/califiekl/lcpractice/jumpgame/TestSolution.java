package io.github.califiekl.lcpractice.jumpgame;

import io.github.califiekl.lcpractice.util.HelperMethod;
import org.junit.Before;
import org.junit.Test;
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
        int[] input=HelperMethod.getIntArray(2,3,1,1,4);
        assertEquals(2, solution.jump(input));
    }
    @Test
    public void sampleTestCase2(){
        int[] input=HelperMethod.getIntArray(2,3,1,0,4);
        assertEquals(2, solution.jump(input));
    }
    @Test
    public void testJumpWithMaxStepInMiddle(){
        int[] input=HelperMethod.getIntArray(1,2,100,1,1,1,1,1);
        assertEquals(3, solution.jump(input));
    }
    @Test
    public void testJumpWithMaxStepInMiddle1(){
        int[] input= HelperMethod.getIntArray(2,1,100,1,1,1,1,1);
        assertEquals(2, solution.jump(input));
    }
    @Test
    public void exceptionCase1(){
        int[] input=HelperMethod.getIntArray(1,2,3);
        assertEquals(2, solution.jump(input));
    }
}
