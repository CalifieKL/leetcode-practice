package io.github.califiekl.lcpractice.jumpgame;

import io.github.califiekl.lcpractice.util.HelperMethod;

public class SolutionOptimal implements Solution {

    private int[] maxReachables;

    public SolutionOptimal(){}
    public SolutionOptimal(int[] nums){
        HelperMethod.nullCheck(nums, JumpGameException::new, "cannot instantiate with null input");
        initializeMaxReachables(nums);
    }

    private void initializeMaxReachables(int[] nums){
        HelperMethod.nullCheck(nums, JumpGameException::new, "cannot initialize maxReachables with null input");
        maxReachables = new int[nums.length];
        maxReachables[0]=nums[0];
        for(int i=1;i<nums.length;++i)
            maxReachables[i]=Math.max(i+nums[i], maxReachables[i-1]);
    }

    @Override
    public int jump(int[] nums) {
        HelperMethod.nullCheck(nums, JumpGameException::new, "cannot jump: input array is null");
        initializeMaxReachables(nums);

        int jump = 0;
        int currentPosition = 0;
        while(currentPosition<nums.length-1){
            jump++;
            currentPosition = maxReachables[currentPosition];
        }

        return jump;
    }


}
