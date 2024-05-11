package io.github.califiekl.lcpractice.trap;

import io.github.califiekl.lcpractice.util.HelperMethod;

public class SolutionOriginal implements Solution{

    @Override
    public int trap(int[] height) {
        HelperMethod.nullCheck(height, TrapException::new, "cannot calculate because height array is null");
        int length = height.length;
        if(length < 3) return 0;

        int maxHeight = 0;
        int convexArea = 0;
        int totalArea = 0;

        int areaOfConvexFromLeft = height[0];
        int currMaxFromLeft = height[0];
        int areaOfConvexFromRight = height[length-1];
        int currMaxFromRight = height[length-1];

        int occupiedAreaInConvex = height[0];

        int j;
        for(int i = 1; i < length; ++i){
            j = (length-1)-i;
            if(height[i]>currMaxFromLeft) currMaxFromLeft=height[i];
            if(height[j]>currMaxFromRight) currMaxFromRight=height[j];
            areaOfConvexFromLeft+=currMaxFromLeft;
            areaOfConvexFromRight+=currMaxFromRight;
            occupiedAreaInConvex+=height[i];
        }

        maxHeight = currMaxFromLeft;
        totalArea = length*maxHeight;
        convexArea = areaOfConvexFromLeft+areaOfConvexFromRight-totalArea;

        return convexArea-occupiedAreaInConvex;
    }
}
