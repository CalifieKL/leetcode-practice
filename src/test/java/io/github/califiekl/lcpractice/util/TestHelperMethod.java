package io.github.califiekl.lcpractice.util;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestHelperMethod {

    @Test
    public void nullCheckShouldThrowExceptionIfInputIsNull(){
        boolean exceptionThrown = false;
        try{
            HelperMethod.nullCheck(null, RuntimeException::new, "test error message");
        } catch (Exception ex){
            exceptionThrown = true;
            assertTrue(ex instanceof RuntimeException);
            assertTrue("test error message".equals(ex.getMessage()));
        }
        assertTrue(exceptionThrown);
    }
    @Test
    public void nullCheckShouldDoNothingIfInputIsNotNull(){
        boolean exceptionThrown = false;
        try{
            HelperMethod.nullCheck(new Object(), RuntimeException::new, "test error message");
        } catch (Exception ex){
            exceptionThrown = true;
        }
        assertTrue(!exceptionThrown);
    }
}
