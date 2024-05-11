package io.github.califiekl.lcpractice.trap;

import io.github.califiekl.lcpractice.util.HelperMethod;
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

    @Test(expected = Exception.class)
    public void shouldThrowExceptionIfInputIsNull(){ solution.trap(null); }

    @Test
    public void shouldReturn0ifHeightLengthIsLessThan2(){
        int[] height = HelperMethod.getIntArray(1,1);
        assertEquals(0, solution.trap(height));
        height[1]+=1;
        assertEquals(0, solution.trap(height));
        height[0]+=2;
        assertEquals(0, solution.trap(height));
        assertEquals(0, solution.trap(new int[]{1}));
        assertEquals(0, solution.trap(new int[0]));
    }

    @Test
    public void shouldReturn0ForConvexHeights(){
        int[] leftSteps = HelperMethod.getIntArray(1,4,5,7,9);
        int[] rightSteps = HelperMethod.getIntArray(10,8,6,4,3,1);
        int[] rectangle = HelperMethod.getIntArray(3,3,3,3,3,3,3);
        assertEquals(0, solution.trap(leftSteps));
        assertEquals(0, solution.trap(rightSteps));
        assertEquals(0, solution.trap(rectangle));
    }

    @Test
    public void ordinaryCasesWithoutTrailingOrLeadingZeros(){
        int[] height1 = HelperMethod.getIntArray(1,0,2,1,0,1,3,2,1,2,1);
        int[] height2 = HelperMethod.getIntArray(4,2,0,3,2,5);
        assertEquals(6, solution.trap(height1));
        assertEquals(9, solution.trap(height2));
    }

    @Test
    public void shouldGiveSameResultWithLeadingOrTrailingzeros(){
        int[] height1 = HelperMethod.getIntArray(0,0,1,0,2,1,0,1,3,2,1,2,1);
        int[] height2 = HelperMethod.getIntArray(4,2,0,3,2,5,0,0);
        assertEquals(6, solution.trap(height1));
        assertEquals(9, solution.trap(height2));

    }
}
