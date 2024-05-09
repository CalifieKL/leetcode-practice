package io.github.califiekl.lcpractice.candies;

public class SolutionOriginal implements Solution{

    @Override
    public int candy(int[] ratings) {
        int totalCandies=0;
        int trendStart=0;
        Trend prevTrend=null; Trend currTrend=null;
        int trendLength;
        for (int i=1;i<ratings.length;++i){
            currTrend = Trend.getCurrentTrend(ratings[i],ratings[i-1]);
            if(currTrend.isDifferentFrom(prevTrend)||i==ratings.length-1){
                trendLength=i-trendStart;
                if(Trend.Flat.equals(prevTrend)){
                    totalCandies+=(trendLength-2);
                    trendStart=i-1;
                    if(i==ratings.length-1)
                        totalCandies+=3;
                } else {
                    totalCandies+=((trendLength+1)*trendLength/2);
                    trendStart=i;
                }

            }
            prevTrend=currTrend;
        }
        if(trendStart==ratings.length-1){
            if(ratings[trendStart]>ratings[trendStart-1]) return totalCandies+2;
            else return totalCandies+1;
        }

        return totalCandies;
    }
    public enum Trend{
        Upward, Downward, Flat;
        public static Trend getCurrentTrend(int prev, int curr){
            if(prev<curr) return Upward;
            else if(prev>curr) return Downward;
            return Flat;
        }
        public boolean isDifferentFrom(Trend prevTrend){
            if(prevTrend==null) return false;
            return !this.equals(prevTrend);
        }

    }
}
