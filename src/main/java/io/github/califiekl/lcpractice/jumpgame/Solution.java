package io.github.califiekl.lcpractice.jumpgame;

public interface Solution {
    int jump(int[] nums);
    default void nullCheck(int[] nums){
        if(null==nums) throw new JumpGameException("input is null");
    }
}
