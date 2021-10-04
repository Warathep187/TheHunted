import java.util.ArrayList;
import java.util.Random;

class Boss {
    private int hpBossAtTheMoment = 100;
    private int atkBoss = 5;
    private int randomAtkForPlayer;
    
    public boolean checkBoss(){ 
        if (this.hpBossAtTheMoment <= 0){
            this.hpBossAtTheMoment = 0;
            return true;
        }
        return false;
    }

    public void aoeAtk(ArrayList players){
        if (checkBoss()){
            
        }else{
            for (int i = 0; i < players.size(); i++){
                Player p = (Player) players.get(i);
                p.decreaseHpPlayerByBoss(atkBoss);
            }
            this.atkBoss += 1;
        }
    }

    public void decreaseHpBoss(Player p){
        if (checkBoss()){

        }else{
            this.hpBossAtTheMoment -= p.ATK;
            p.setScore(p.ATK);
            Random rand = new Random();
            this.randomAtkForPlayer = rand.nextInt(5)+5; //random 5 - 10
            p.increaseAtkPlayer(this.randomAtkForPlayer);
        }
    }

    public int getHpBossAtTheMoment() {
        return this.hpBossAtTheMoment;
    }

    public int getAtkBoss() {
        return this.atkBoss;
    }

    public int getRandomAtkForPlayer() {
        return this.randomAtkForPlayer;
    }
}
