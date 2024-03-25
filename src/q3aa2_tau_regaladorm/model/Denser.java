package q3aa2_tau_regaladorm.model;

public class Denser extends Level {
    public Denser(Difficulty difficulty, int initialCustomerCount) {
        super(difficulty, initialCustomerCount);
        
        for (int i = 0; i < difficulty.getCustomerCountTable().pickRandom() / 4; i++)
            addCustomer();
    }
}
