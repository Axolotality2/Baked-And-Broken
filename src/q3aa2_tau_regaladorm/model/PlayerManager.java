package q3aa2_tau_regaladorm.model;

public class PlayerManager {

    private static Customer currCustomer;
    private static Week currWeek;
    private static int customersServed, totalRating;
    private static float averageRating;

    private static void addRating(int rating) {
        totalRating += rating;
        averageRating = totalRating / customersServed;
    }

    public static void submitProduct(Product product) throws Exception {
        int remainingTime = getCurrTime();
        int rating = currCustomer.rateProduct(product, remainingTime);

        addRating(rating);
    }

    public static void lose() {
        System.out.println("I've received reports, and I'm afraid the business won't be thriving with you here. Goodbye.");
    }

    public static void win() {
        System.out.println("Congratulations, you've proven yourself to be great help here for my business. You can keep the job.");
    }

    public static void endDay() {
        customersServed += currWeek.getCurrDay().getCustomersServed();
    }

    public static Customer getCurrCustomer() {
        return currCustomer;
    }

    public static int getCustomersServed() {
        return customersServed;
    }

    public static int getTotalRating() {
        return totalRating;
    }

    public static float getAverageRating() {
        return averageRating;
    }

    public static Week getCurrWeek() {
        return currWeek;
    }

    public static int getCurrTime() {
        return currWeek.getCurrDay().getTime();
    }
}