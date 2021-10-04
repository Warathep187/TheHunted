import java.util.Random;

public class Dice {
    private int rand;

    public void roleTheDice() {
        Random random = new Random();
        this.rand = random.nextInt(6) + 1;
    }
    
    public int getRandom() {
        return this.rand;
    }
}
