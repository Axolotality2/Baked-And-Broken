package main.Core.CustomerGen;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import main.Exceptions.MultiHandlerException;

/**
 *
 * @author user
 */
public class CustomerHandler {

    private static final int INIT_CUSTOMER_COUNT = 3;
    private final ArrayList<Customer> cue;
    private final Difficulty difficulty;
    private ArrayList<Score> levelScore;
    private boolean started;

    public CustomerHandler(Difficulty difficulty) {
        this.cue = new ArrayList<>();
        this.difficulty = difficulty;
        this.started = false;
    }

    public void start() throws MultiHandlerException {
        if (isStarted()) {
            throw new MultiHandlerException();
        }

        started = true;
        for (int i = cue.size(); i < INIT_CUSTOMER_COUNT; i++) {
            insertCustomer();
        }

        shiftIn();
    }

    public void stop() {
        started = false;

        while (cue.size() > 1) {
            cue.remove(1); // Clear cue except for current customer;
        }
    }

    protected void shiftIn() {
        if (!isStarted()) {
            return;
        }

        Timer timer = new Timer();

        try {
            timer.schedule( // Schedule leave
                    new TimerTask() {
                @Override
                public void run() {
                    shiftOut();
                }
            },
                    cue.get(0).getOrder().getPrepTime());
        } catch (IndexOutOfBoundsException ex) {
            insertCustomer();
            shiftIn();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void shiftOut() {
        getLevelScore().add(cue.get(0).getScore());
        cue.remove(0);
        cue.add(new Customer(getDifficulty()));
        shiftIn();
    }

    protected void insertCustomer() {
        Customer customer = new Customer(getDifficulty());
        customer.setHandler(this);
        cue.add(customer);
    }

    /**
     * @return the cue
     */
    public ArrayList<Customer> getCue() {
        return cue;
    }

    /**
     * @return the level score
     */
    public ArrayList<Score> getLevelScore() {
        return levelScore;
    }

    /**
     * @return if the handler is running
     */
    public boolean isStarted() {
        return started;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
    
}
