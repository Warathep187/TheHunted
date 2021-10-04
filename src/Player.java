public class Player extends Weapons {
    public int id;
    public int HP = 100;
    public int score = 0;
    public int baseATK = 5;
    public int weaponATK = 0;
    public boolean stunStatus = false;

    public int ATK = baseATK + weaponATK;

    public Player(int id) {
        this.id = id;
    }

    public void setAtfFromEnhance() {
        this.weaponATK = getAtkEnhanceWeapon(this.weaponATK, 10);
        this.ATK = this.baseATK + this.weaponATK;
    }

    public void decreaseHpPlayerByBoss(int atkBoss) {
        this.HP -= atkBoss;
    }

    public void decreaseHpPlayerByPlayer(int damage) {
        this.HP -= damage;
    }

    public void increaseHpPlayer(int restore) {
        this.HP += restore;
    }

    public void attackBoss(Boss boss, Player player) {
        this.ATK = this.baseATK + this.weaponATK;
        boss.decreaseHpBoss(player);
    }

    public void attackPlayer(Player attackedPlayer) {
        attackedPlayer.decreaseHpPlayerByPlayer(this.ATK);
    }

    public void increaseAtkPlayer(int atk) {
        this.baseATK += atk;
        this.ATK = this.weaponATK + this.baseATK;
    }

    public void setStunStatus(boolean x){
        stunStatus = x;
    }

    public boolean isStunStatus() {
        return stunStatus;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int hP) {
        HP = hP;
    }

    public void setATK(int aTK) {
        ATK = aTK;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score += (score * 100);
    }

    public void decreaseScore(int score){
        this.score = score/2;
    }

    public int getBaseATK() {
        return baseATK;
    }

    public int getWeaponATK() {
        return weaponATK;
    }

    public int getATK() {
        return ATK;
    }

    public void setWeaponAtk(int i) {
        this.weaponATK = i;
    }
}
