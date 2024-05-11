package io.github.califiekl.lcpractice.candies;

import io.github.califiekl.lcpractice.util.HelperMethod;

import java.util.ArrayList;
import java.util.List;

public class SolutionOriginal implements Solution{

    @Override
    public int candy(int[] ratings) {
        if(ratings.length==1) return 1;
        if(ratings.length==2)
            if(ratings[0]!=ratings[1]) return 3;
            else return 2;

        List<int[]> processedRatings = new ArrayList<>();
        int prevTrend = getTrend(ratings[0], ratings[1]);
        int currTrend = Integer.MIN_VALUE;
        int trendStart = 1;
        for(int i=1;i<ratings.length-1;++i){
            currTrend = getTrend(ratings[i], ratings[i+1]);
            if(currTrend!=prevTrend){
                processedRatings.add(getInterval(i-trendStart, prevTrend));
                trendStart = i+1;
            }
            else if(i==ratings.length-2){
                processedRatings.add(getInterval(i-trendStart+1, prevTrend));
            }
            prevTrend = currTrend;
        }


        int[] currInteval=processedRatings.get(0);
        int[] nextInteval = null;
        int totalCandies=currInteval[1]>=0?1:currInteval[0]+2;
        for(int i=0;i<processedRatings.size()-1;++i){
            currInteval = processedRatings.get(i);
            nextInteval = processedRatings.get(i+1);
            if(currInteval[0]==0){
                if(currInteval[1]==0)
                    totalCandies+=(nextInteval[1]>0?1:2);
                else
                    totalCandies+=(currInteval[1]>0?2:1);
            } else {
                totalCandies+=getIntervalCandies(currInteval);
                totalCandies+=getExtremumCandies(currInteval, nextInteval);
            }
        }

        int[] lastInteval=processedRatings.get(processedRatings.size()-1);
        if(lastInteval[0]==0)
            if(lastInteval[1]==0)
                totalCandies+=(currTrend>0?1:2);
            else
                totalCandies+=(lastInteval[1]>0?2:1);
        else totalCandies+=getIntervalCandies(lastInteval);

        totalCandies+=currTrend>0?lastInteval[0]+2:1;

        return totalCandies;
    }

    private int getTrend(Integer prev, Integer curr){
        if(prev<curr) return 1;
        else if(prev>curr) return -1;
        return 0;
    }

    private int[] getInterval(int length, int trend){
        return new int[]{length, trend};
    }

    private int getIntervalCandies(int[] inteval){
        if(inteval[1]==0) return inteval[0];
        return (inteval[0]*(inteval[0]+3)/2);
    }

    private int getExtremumCandies(int[] currInteval, int[] nextInteval){
        if(nextInteval[1]>currInteval[1]) return 1;
        if(nextInteval[1]==0) return currInteval[0]+2;
        if(currInteval[1]==0) return nextInteval[0]+2;
        return Math.max(currInteval[0], nextInteval[0])+2;
    }
}
