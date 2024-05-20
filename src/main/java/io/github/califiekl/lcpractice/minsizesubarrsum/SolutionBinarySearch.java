package io.github.califiekl.lcpractice.minsizesubarrsum;

import io.github.califiekl.lcpractice.util.HelperMethod;

public class SolutionBinarySearch implements Solution{

    @Override
    public int minSubArrayLen(int target, int[] nums) {
        HelperMethod.nullCheck(nums,MinSizeSubArrSumException::new,"cannot calculate on null array");
        if(nums.length==0)
            throw new MinSizeSubArrSumException("cannot calculate on empty array");

        int lowSize=1, highSize=nums.length, minSize=0;
        while(lowSize<=highSize){
            int midSize = (lowSize+highSize)/2;
            if(targetCanBeReachedInWindowOfSize(midSize, nums, target)){
                highSize=midSize-1;
                minSize=midSize;
            }else{
                lowSize=midSize+1;
            }
        }
        return minSize;
    }

    private boolean targetCanBeReachedInWindowOfSize(int size, int[] nums, int target){
        int wStart=0, wEnd=0, runningSum=0;
        while(wEnd<nums.length){
            runningSum+=nums[wEnd];
            if(wEnd-wStart+1==size){
                if(runningSum>=target) return true;
                runningSum-=nums[wStart];
                wStart++;
            }
            wEnd++;
        }
        return false;
    }
}
