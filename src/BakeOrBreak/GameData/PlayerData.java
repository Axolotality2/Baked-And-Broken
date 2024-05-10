package BakeOrBreak.GameData;

public class PlayerData {

    private int day, customersServed, totalRating, totalSpeed;
    private float avgRating, avgSpeed;
    
    private void fixAvgs() {
        avgRating = getTotalRating() / customersServed;
        avgSpeed = getTotalSpeed() / customersServed;
    }
    
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getCustomersServed() {
        return customersServed;
    }

    public void setCustomersServed(int customersServed) {
        this.customersServed = customersServed;
        fixAvgs();
    }

    public void setTotalRating(int totalRating) {
        this.totalRating = totalRating;
        fixAvgs();
    }

    public void setTotalSpeed(int totalSpeed) {
        this.totalSpeed = totalSpeed;
        fixAvgs();
    }

    public float getAvgRating() {
        return avgRating;
    }

    public float getAvgSpeed() {
        return avgSpeed;
    }

    public int getTotalRating() {
        return totalRating;
    }

    public int getTotalSpeed() {
        return totalSpeed;
    }
}
