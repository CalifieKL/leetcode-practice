package io.github.califiekl.lcpractice.jumpgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolutionOriginal implements Solution{
    private int[] maxReachables;
    private Map<Integer, Integer> results = new HashMap<>();

    @Override
    public int jump(int[] nums) {
        if(null==nums) throw new JumpGameException("input array is null");
        if(null==maxReachables)
            maxReachables=getMaxReachables(nums);
        return calculateMinStep(nums, maxReachables, nums.length-1);
    }

    private int[] getMaxReachables(int[] nums){
        int maxReachables[] = new int[nums.length];
        for(int i=0;i<nums.length;++i)
            maxReachables[i]=i+nums[i];
        return maxReachables;
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
        if(results.get(target)!=null) return results.get(target);

        List<Integer> possibleLastSteps = findAllPossibleLastSteps(maxReachables, target);

        int globalMin = target; int minAtI;
        for(Integer i: possibleLastSteps){
            minAtI=calculateMinStep(nums, maxReachables, i);
            if(globalMin>minAtI) globalMin=minAtI;
            results.put(i, minAtI);
        }
        results.put(target, globalMin+1);
        return globalMin+1;
    }
}
