package io.github.califiekl.lcpractice.candies;

import io.github.califiekl.lcpractice.util.HelperMethod;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class TestSolution{
    protected Solution solution;
    protected abstract Solution getSolution();

    @Before
    public void init(){
        solution=getSolution();
    }

    @Test
    public void sampeTestCase1(){
        int[] ratings = HelperMethod.getIntArray(1,0,2);
        assertEquals(5, solution.candy(ratings));
    }

    @Test
    public void sampleTestCase2(){
        int[] ratings = HelperMethod.getIntArray(1,2,2);
        assertEquals(4, solution.candy(ratings));
    }

    @Test
    public void exceptionCase1(){
        int[] ratings = HelperMethod.getIntArray(1,3,2,2,1);
        assertEquals(7, solution.candy(ratings));
    }

    @Test
    public void exceptionCase2(){
        int[] ratings = HelperMethod.getIntArray(1,2,87,87,87,2,1);
        assertEquals(13, solution.candy(ratings));
    }

    @Test
    public void exceptionCase3(){
        int[] ratings = HelperMethod.getIntArray(1,2,87,87,2,1);
        assertEquals(12, solution.candy(ratings));
    }
}
