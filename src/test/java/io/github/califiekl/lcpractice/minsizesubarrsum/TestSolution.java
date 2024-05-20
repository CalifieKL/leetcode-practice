package io.github.califiekl.lcpractice.minsizesubarrsum;

import io.github.califiekl.lcpractice.util.HelperMethod;
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

    @Test(expected = Exception.class)
    public void shouldThrowExceptionIfInputArrayIsNull(){
        solution.minSubArrayLen(-1, null);
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionIfInputArrayIsEmpty(){
        solution.minSubArrayLen(-1, new int[]{});
    }

    @Test
    public void shouldReturnMinSubArrSizeIfTargetIsObtainable(){
        assertEquals(2, solution.minSubArrayLen(7, HelperMethod.getIntArray(2,3,1,2,4,3)));
        assertEquals(1, solution.minSubArrayLen(4, HelperMethod.getIntArray(1,3,5)));
        assertEquals(5,solution.minSubArrayLen(5, HelperMethod.getIntArray(1,1,1,1,1)));
    }

    @Test
    public void shouldReturnZeroIfTargetIsNotObtainable(){
        assertEquals(0, solution.minSubArrayLen(50, HelperMethod.getIntArray(1,2,3,4,5,6)));
    }

}
