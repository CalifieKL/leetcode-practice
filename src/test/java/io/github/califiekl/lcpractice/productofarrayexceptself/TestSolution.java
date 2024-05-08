package io.github.califiekl.lcpractice.productofarrayexceptself;

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
    public void throwExceptionIfInputArrayIsNull(){ solution.productExceptSelf(null); }

    @Test
    public void sampleTestCase1(){
        int[] nums = HelperMethod.getIntArray(-1,1,0,-3,3);
        assertArraysAreEqual(new int[]{0,0,9,0,0}, solution.productExceptSelf(nums));
    }

    @Test
    public void SampleTestCase2(){
        int[] nums = HelperMethod.getIntArray(1,2,3,4);
        assertArraysAreEqual(new int[]{24, 12, 8, 6}, solution.productExceptSelf(nums));
    }

    private void assertArraysAreEqual(int[] expected, int[] actual){
        if(expected==null || actual==null)
            throw new AssertionError("cannot check on null array");
        assertEquals(expected.length, actual.length);
        for(int i=0;i<expected.length;++i)
            assertEquals(expected[i], actual[i]);
    }


}
