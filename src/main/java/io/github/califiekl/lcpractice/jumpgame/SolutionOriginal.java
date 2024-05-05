package io.github.califiekl.lcpractice.jumpgame;

import java.util.*;

public class SolutionOriginal implements Solution{

    private int[] maxReachables;
    private int[] results;

    public SolutionOriginal(){}
    public SolutionOriginal(int nums[]){
        if(null==nums) throw new JumpGameException("cannot create instance: input array is null");
        initializeMaxReachables(nums);
        initializeResultArray(nums);
    }

    private void initializeMaxReachables(int[] nums){
        if(null==nums) throw new JumpGameException("cannot initialize maxReachables: input array is null");
        maxReachables= new int[nums.length];
        for(int i=0;i<nums.length;++i)
            maxReachables[i]=i+nums[i];
    }

    private void initializeResultArray(int[] nums){
        if(null==nums) throw new JumpGameException("cannot initialize result array: input array is null");
        results = new int[nums.length];
        Arrays.fill(results, 0, nums.length, -1);
    }

    @Override
    public int jump(int[] nums) {
        if(null==nums) throw new JumpGameException("cannot jump: input array is null");
        if(null==maxReachables)
            initializeMaxReachables(nums);
        if(null==results)
            initializeResultArray(nums);
        return calculateMinStep(nums, maxReachables, nums.length-1);
    }

    private List<Integer> findAllPossibleLastSteps(int[] maxReachables, int target){
        List<Integer> possibleLastSteps=new ArrayList<>();
        for(int i=0;i<target;++i){
            if(maxReachables[i]>=target)
                possibleLastSteps.add(i);
        }
        return possibleLastSteps;
    }

    private int calculateMinStep(int[] nums, int[] maxReachables, int target){
        if(target<=1) return target;
        if(results[target]!=-1) return results[target];

        List<Integer> possibleLastSteps = findAllPossibleLastSteps(maxReachables, target);

        int globalMin = target; int minAtI;
        for(Integer i: possibleLastSteps){
            minAtI=calculateMinStep(nums, maxReachables, i);
            if(globalMin>minAtI) globalMin=minAtI;
            results[i] = minAtI;
        }
        results[target] = globalMin+1;
        return globalMin+1;
    }
}
