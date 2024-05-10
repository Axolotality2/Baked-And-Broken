package BakeOrBreak.GameData;

public class Difficulty {
    
    private WeightDist<Integer> complexity;
    private WeightDist<Integer> orderSize;
    private WeightDist<Integer> allergyCount;
    
    public Difficulty(int difficulty) {
        complexity = new WeightDist<>();
        orderSize = new WeightDist<>();
        allergyCount = new WeightDist<>();
        
        for (int i = 1; i <= difficulty * 10; i++) {
            complexity.addEntry(i, i);
        }
        
        for (int i = 1; i <= difficulty; i++) {
            orderSize.addEntry(i, difficulty - i);
        }
        
        for (int i = 1; i <= difficulty; i++) {
            allergyCount.addEntry(i, difficulty - i);
        }
    }
    
    public int getComplexity() {
        return complexity.pickRandom();
    }
    
    public int getOrderSize() {
        return orderSize.pickRandom();
    }
    
    public int getAllergyCount() {
        return allergyCount.pickRandom();
    }
}
