package io.github.califiekl.lcpractice.productofarrayexceptself;

import io.github.califiekl.lcpractice.util.HelperMethod;

import java.util.Arrays;

public class SolutionNoExtraSpace implements Solution{
    int[] resultArray;
    @Override
    public int[] productExceptSelf(int[] nums) {
        HelperMethod.nullCheck(nums, ProductOfArrayExceptSelfException::new, "cannot calculate product with null array");
        calulateProduct(nums);
        return resultArray;
    }

    private void initiateResultArray(int size){
        resultArray = new int[size];
        Arrays.fill(resultArray,0,size,1);
    }

    private void calulateProduct(int[] nums){
        if(null==resultArray)
            initiateResultArray(nums.length);
        int leftSideTrailingProduct = 1;
        int rightSideTrailingProduct = 1;
        for(int i = 0, j = nums.length-1;i<nums.length; i++,j--){
            resultArray[i]*=leftSideTrailingProduct;
            leftSideTrailingProduct*=nums[i];

            resultArray[j]*=rightSideTrailingProduct;
            rightSideTrailingProduct*=nums[j];
        }
    }
}
