import java.util.ArrayList;
import java.util.Random;

class Area {
    public int[] playerPosition;
    private ArrayList<Integer> generalBlock = new ArrayList<>();
    private ArrayList<Integer> specialBlock = new ArrayList<>();
    private ArrayList<Integer> trapBlock = new ArrayList<>();
    private ArrayList<Integer> moveBlock = new ArrayList<>();
    
    public Area(int numPlayer){
        this.playerPosition = new int[numPlayer];
        for (int i = 0; i <  numPlayer; i++){
            this.playerPosition[i] = 0;
        }
        generalBlock.add(1);
        generalBlock.add(2);
        generalBlock.add(5);
        generalBlock.add(7);
        generalBlock.add(9);
        generalBlock.add(10);
        generalBlock.add(13);
        generalBlock.add(15);
        generalBlock.add(17);
        generalBlock.add(18);
        generalBlock.add(21);
        generalBlock.add(23);
        generalBlock.add(25);
        generalBlock.add(26);
        generalBlock.add(29);
        generalBlock.add(31);

        specialBlock.add(4);
        specialBlock.add(12);
        specialBlock.add(20);
        specialBlock.add(28);

        trapBlock.add(3);
        trapBlock.add(11);
        trapBlock.add(19);
        trapBlock.add(27);

        moveBlock.add(6);
        moveBlock.add(14);
        moveBlock.add(22);
        moveBlock.add(30);
    }

    public void moveTo(int currentTurn, Player p, int randomNumber){
        if (p.isStunStatus()  == false){
            this.playerPosition[currentTurn] += randomNumber;
            if (this.playerPosition[currentTurn] > 31){
                this.playerPosition[currentTurn] -=32;
            }
        }
        p.setStunStatus(false);
    }

    public int checkAttacked(int currentTurn) {
        for(int i=0; i<this.playerPosition.length; i++) {
            if(this.playerPosition[currentTurn] == this.playerPosition[i] && i != currentTurn) {
                return i;
            }
        }
        return -1;
    }

    public void activeSpecialBlock(Player p, Boss boss, String choose){
        if (choose.equals("W")){
            p.setAtfFromEnhance();
        }else{
            p.attackBoss(boss, p);
        }
    }

    public void activeTrapBlock(Player p){
        p.decreaseHpPlayerByPlayer(20);
    }

    public void activeMoveBlock(int currentTurn){
        Random random = new Random();
        int rand = random.nextInt(2) + 1;
        if (rand == 1){
            this.playerPosition[currentTurn] +=1;
        }else{
            this.playerPosition[currentTurn] -=1;
        }
    }

    public void activeStunBlock(Player p) {
        p.setStunStatus(true);
    }

    public void activeMagicDoorBlock(int currentTurn) {
        //Choose channel to go
        Random rand = new Random();
        int channel = rand.nextInt(this.generalBlock.size());
        playerPosition[currentTurn] = this.generalBlock.get(channel);
    }

    public void activeHealBlock(Player p) {
        p.increaseHpPlayer(50);
    }

    public ArrayList<Integer> getGeneralBlock() {
        return generalBlock;
    }
    
    public ArrayList<Integer> getSpecialBlock() {
        return specialBlock;
    }
    
    public ArrayList<Integer> getTrapBlock() {
        return trapBlock;
    }
    
    public ArrayList<Integer> getMoveBlock() {
        return moveBlock;
    }

    public void resetPlayerPosition(int id) {
        playerPosition[id] = 0;
    }

    public int getPlayerPosition(int currentTurn) {
        return playerPosition[currentTurn];
    }
}