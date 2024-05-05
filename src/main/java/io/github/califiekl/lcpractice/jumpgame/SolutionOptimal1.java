package io.github.califiekl.lcpractice.jumpgame;

public class SolutionOptimal1 implements Solution {

    private int[] maxReachables;

    public SolutionOptimal1(){}
    public SolutionOptimal1(int[] nums){
        nullCheck(nums);
        initializeMaxReachables(nums);
    }

    private void initializeMaxReachables(int[] nums){
        nullCheck(nums);
        maxReachables = new int[nums.length];
        maxReachables[0]=nums[0];
        for(int i=1;i<nums.length;++i)
            maxReachables[i]=Math.max(i+nums[i], maxReachables[i-1]);
    }

    @Override
    public int jump(int[] nums) {
        nullCheck(nums);
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
