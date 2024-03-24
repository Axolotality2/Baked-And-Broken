package q3aa2_tau_regaladorm.model;

public class Week {
    private static int weekCount;
    private int difficulty, currDayIndex;
    private Level[] days;

    public void startWeek() {
        weekCount++;
        difficulty = 1;

        if ((weekCount % 2) == 0) {
            difficulty++;
        }
    }

    public void endWeek() {
        currDayIndex = 0;
        checkRating();
    }

    public void endDay() {
        currDayIndex++;
    }

    private void checkRating() {
        // After a week has ended, the player is informed of their progress
        if (weekCount < 8) {
            if (PlayerManager.getAverageRating() < 2) {
                System.out.println("Not looking so good, but there's always room for improvement, isn't there?");
            }
            else {
                System.out.println("Impressive! Keep up the good work!");
            }
        }
        // After 8 weeks, the final average rating will determine victory or defeat for the player
        else if (weekCount == 8) {
            if (PlayerManager.getAverageRating() < 2) {
                PlayerManager.lose();
            }
            else {
                PlayerManager.win();
            }
        }
    }

    public Level getCurrDay() {
        return days[currDayIndex];
    }
}