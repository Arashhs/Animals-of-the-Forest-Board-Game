import java.util.HashMap;
import java.util.Map;

/**
 * This class simulates animal behaviours. It is used to define special animal types. It is meaningless if we make an object of this class so it is abstart
 * @author Arash
 */
public abstract class Animal {
    private int attackPrimary;
    private int attackSecondary;
    private int HP;
    private int SP;
    private int fullSP;
    private String attack1; //attackPrimary name
    private String attack2; //attackSecondary name
    private boolean isAlive; //true: alive | false: dead
    private Map<String, Integer> attacks; //Maps attacks name to their attack damage

    /**
     *
     * @param attackPrimary first attack damage
     * @param attackSecondary Second attack damage
     * @param HP Health points
     * @param SP Stamina points
     * @param attack1 first attack name
     * @param attack2 second attack name
     */

    public Animal(int attackPrimary, int attackSecondary, int HP, int SP, String attack1, String attack2) {
        this.attackPrimary = attackPrimary;
        this.attackSecondary = attackSecondary;
        this.HP = HP;
        this.SP = SP;
        this.fullSP = SP;
        this.attack1 = attack1;
        this.attack2 = attack2;
        isAlive = true;
        attacks = new HashMap<String, Integer>();
        attacks.put(attack1, attackPrimary);
        attacks.put(attack2, attackSecondary);
    }

    /**
     * Calculates animal's SP after a successful attack
     * @param stamina the amount of stamina is used for successful attack
     */
    public void loseSP(int stamina) {
        SP -= stamina;
    }

    /**
     * Calculates animal's HP after being attacked
     * @param health amount of health the animal under attack has lost
     */
    public void loseHP(int health) {
        HP -= health;
    }

    /**
     * Restores full SP to an animal
     */
    public void restoreSP() {
        this.SP = fullSP;
    }

    /**
     *
     * @param attackDamage amount of damage animal wants to deal in an attack
     * @return true: Animal has enough SP to attack | false: animal doesn't have enough SP to attack
     */
    public boolean checkSPforAttack(int attackDamage) {
        if (attackDamage > this.SP)
            return false;
        else
            return true;
    }

    /**
     *
     * @return Animal's details
     */
    @Override
    public String toString() {
        if (attackSecondary != 0)
            return this.getClass().getName() + "\n" + "HP: " + HP + "  SP: " + SP + "  Primary damage type - damage: " + attack1 + " - " + attackPrimary + "  Secondary attack type - damage: " + attack2 + " - " + attackSecondary + "  is Alive? " + isAlive + "\n";
        else
            return this.getClass().getName() + "\n" + "HP: " + HP + "  SP: " + SP + "  Primary damage type - damage: " + attack1 + " - " + attackPrimary + "  is Alive? " + isAlive + "\n";
    }

    /* *********** These are getter and setter methods ********** * Start */
    public int getAttackPrimary() {
        return attackPrimary;
    }

    public void setAttackPrimary(int attackPrimary) {
        this.attackPrimary = attackPrimary;
    }

    public int getAttackSecondary() {
        return attackSecondary;
    }

    public void setAttackSecondary(int attackSecondary) {
        this.attackSecondary = attackSecondary;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getSP() {
        return SP;
    }

    public void setSP(int SP) {
        this.SP = SP;
    }

    public String getAttack1() {
        return attack1;
    }

    public void setAttack1(String attack1) {
        this.attack1 = attack1;
    }

    public String getAttack2() {
        return attack2;
    }

    public void setAttack2(String attack2) {
        this.attack2 = attack2;
    }

    public Map<String, Integer> getAttacks() {
        return attacks;
    }

    public int getFullSP() {
        return fullSP;
    }

    public void setFullSP(int fullSP) {
        this.fullSP = fullSP;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
    /* *********** These are getter and setter methods ********** * End */
}
