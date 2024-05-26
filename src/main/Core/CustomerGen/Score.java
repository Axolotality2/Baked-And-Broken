package main.Core.CustomerGen;

import java.util.ArrayList;

/**
 *
 * @author user
 */
public class Score {
    
    private final ArrayList<Integer> ratings;
    private final ArrayList<Integer> speeds;
    private double meanRating, meanSpeed;
    private boolean allergicReaction;
    
    public Score() {
        ratings = new ArrayList<>();
        speeds = new ArrayList<>();
        meanRating = 0d;
        meanSpeed = 0d;
        allergicReaction = false;
    }
    
    public void addScore(int rating, int speed) {
        double totalRating = 0d;
        double totalSpeed = 0d;
        
        getRatings().add(rating);
        getSpeeds().add(speed);
        
        for (int i = 0; i < getRatings().size(); i++) {
            totalRating += getRatings().get(i);
            totalSpeed += getSpeeds().get(i);
        }
        
        meanRating = totalRating / getRatings().size();
        meanSpeed = totalSpeed / getSpeeds().size();
    }
    
    public void allergenize() {
        allergicReaction = true;
    }

    /**
     * @return the ratings
     */
    public ArrayList<Integer> getRatings() {
        return new ArrayList<>(ratings);
    }

    /**
     * @return the speeds
     */
    public ArrayList<Integer> getSpeeds() {
        return new ArrayList<>(speeds);
    }

    /**
     * @return the meanRating
     */
    public double getMeanRating() {
        return meanRating;
    }

    /**
     * @return the meanSpeed
     */
    public double getMeanSpeed() {
        return meanSpeed;
    }

    /**
     * @return the allergicReaction
     */
    public boolean hadAllergicReaction() {
        return allergicReaction;
    }
}
