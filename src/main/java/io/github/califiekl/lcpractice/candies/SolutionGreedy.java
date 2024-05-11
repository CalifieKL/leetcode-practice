package io.github.califiekl.lcpractice.candies;

import java.util.Arrays;

public class SolutionGreedy implements Solution {

    @Override
    public int candy(int[] ratings) {
        int length = ratings.length;
        int processedRatingsFromLeft[] = new int[length];
        int processedRatingsFromRight[] = new int[length];
        int totalCandies = 0;
        Arrays.fill(processedRatingsFromLeft, 1);
        Arrays.fill(processedRatingsFromRight, 1);

        for(int i = 1; i < length; ++i)
            if(ratings[i]>ratings[i-1])
                processedRatingsFromLeft[i]=processedRatingsFromLeft[i-1]+1;

        for(int j = length-2; j >= 0; --j)
            if(ratings[j+1]<ratings[j])
                processedRatingsFromRight[j]=processedRatingsFromRight[j+1]+1;

        for(int k = 0; k < length; ++k)
            totalCandies+=Math.max(processedRatingsFromLeft[k], processedRatingsFromRight[k]);

        return totalCandies;
    }
}
