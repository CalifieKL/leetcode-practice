package io.github.califiekl.lcpractice.minsizesubarrsum;

import io.github.califiekl.lcpractice.util.HelperMethod;

public class SolutionSlidingWindow implements Solution{

    @Override
    public int minSubArrayLen(int target, int[] nums) {
        HelperMethod.nullCheck(nums,MinSizeSubArrSumException::new,"cannot calculate on null array");
        if(nums.length==0)
            throw new MinSizeSubArrSumException("cannot calculate on empty array");

        int wStart=0, wEnd=0;
        int runningSum=0;
        int minSize = Integer.MAX_VALUE;

        while(wEnd<nums.length){
            runningSum+=nums[wEnd];
            while (runningSum>=target){
                minSize=Math.min(wEnd-wStart+1, minSize);
                runningSum-=nums[wStart];
                wStart++;
            }
            wEnd++;
        }
        return minSize>nums.length?0:minSize;
    }
}
