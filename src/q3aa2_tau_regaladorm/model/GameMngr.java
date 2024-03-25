package q3aa2_tau_regaladorm.model;

import java.util.HashMap;
import javafx.scene.input.KeyCode;

public class GameMngr {

    private static GameMngr gameMngr = new GameMngr();
    private PlayerData playerData;
    private LevelMngr levelMngr;

    public GameMngr() {
        this.playerData = new PlayerData();
        this.levelMngr = new LevelMngr();
    }

    public static class ControlMngr {

        enum Action {
            PAUSE,
            TOGGLE_PANTRY,
            SWAP_WORKSTATION,
            RELEASE_CONTENTS
        }

        private final HashMap<KeyCode, Action> keyMap;

        public ControlMngr() {
            this.keyMap = new HashMap<>();
            this.keyMap.put(KeyCode.ESCAPE, Action.PAUSE);
            this.keyMap.put(KeyCode.P, Action.TOGGLE_PANTRY);
            this.keyMap.put(KeyCode.TAB, Action.SWAP_WORKSTATION);
            this.keyMap.put(KeyCode.SPACE, Action.RELEASE_CONTENTS);
        }

        public HashMap getKeyMap() {
            return keyMap;
        }
    }

    public class LevelMngr {

        private Difficulty difficulty;
        private final long CLOSING_TIME = 600;
        private final int daysPerDifficulty = 10, gameDays = 30;
        private int day;
        private long time;
        private Level level;

        public LevelMngr() {
            this.day = 0;
            this.time = 0;
            this.difficulty = null;
            this.level = null;
        }

        private void startDay() {
            time = 0;
            this.level = new Level(difficulty);
        }

        private void startBlankDay() {
            time = 0;
            this.level = new Level(Difficulty.EASY);
        }

        private void endDay() {
            playerData.setCustomersServed(playerData.getCustomersServed() + level.getCustomersServed());
            playerData.setDay(playerData.getDay() + 1);
            playerData.setTotalRating(playerData.getTotalRating() + level.getTotalRating());
            playerData.setTotalSpeed(playerData.getTotalSpeed() + level.getTotalSpeed());
        }

        private void checkRating() {
            if (day < gameDays) { // After 8 weeks, the final average rating will determine victory or defeat for the player
                if (playerData.getAvgRating() < 2) {
                    lose();
                } else {
                    win();
                }
            } else if (day % 5 == 0) { // After a week has ended, the player is informed of their progress
                if (playerData.getAvgRating() < 2) {
                    System.out.println("Not looking so good, but there's always room for improvement, isn't there?");
                } else {
                    System.out.println("Impressive! Keep up the good work!");
                }
            } 
        }

        public void tick() {
            time++;
        }

        public Level getDay() {
            return level;
        }

        public long getTime() {
            return time;
        }

        public void setDifficulty(Difficulty d) {
            difficulty = d;
        }

        public void setDefaultDifficulty() throws Exception {
            switch ((int) (day / daysPerDifficulty)) {
                case 1:
                    difficulty = Difficulty.EASY;
                    break;
                case 2:
                    difficulty = Difficulty.MEDIUM;
                    break;
                case 3:
                    difficulty = Difficulty.HARD;
                    break;
                default:
                    throw new Exception();
            }
        }
    }

    private void win() {
        
    }
    
    private void lose() {
        
    }
    
    public static GameMngr getGameManager() {
        return gameMngr;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public LevelMngr getLevelMngr() {
        return levelMngr;
    }
}
