package BakeOrBreak.GameData;

import java.util.ArrayList;

public class LevelStatistic {

    protected final Difficulty difficulty;
    private ArrayList<CustomerStatistic> levelStatistics;
    
    public LevelStatistic(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.levelStatistics = new ArrayList<>();
    }
    
    public ArrayList<CustomerStatistic> getLevelStatistics() {
        return levelStatistics;
    }
}
