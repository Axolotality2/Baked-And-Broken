package BakeOrBreak.GameData;

import java.util.ArrayList;

public class CustomerStatistic {

    ArrayList<OrderStatistic> orderStatistics;
    private int overallRating;
    private int overallSpeed;
    private boolean allergicReaction;

    public CustomerStatistic() {
        overallRating = 0;
        overallSpeed = 0;
        allergicReaction = false;
    }
    
    public ArrayList<OrderStatistic> getorderStatistics() {
        return orderStatistics;
    }
}
