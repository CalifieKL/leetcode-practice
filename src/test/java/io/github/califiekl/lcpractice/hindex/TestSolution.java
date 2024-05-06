package io.github.califiekl.lcpractice.hindex;

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
    public void throwExceptionIfInputIsNull(){
        solution.hIndex(null);
    }

    @Test
    public void sampleTestCase1(){
        int[] citations = HelperMethod.getIntArray(3,0,6,1,5);
        assertEquals(3, solution.hIndex(citations));
    }
    @Test
    public void sampleTestCase2(){
        int[] citations = HelperMethod.getIntArray(1,3,1);
        assertEquals(1, solution.hIndex(citations));
    }
}
