package sourcecoded.quantum.util.damage;

public class DamageSourceQArrow extends DamageSourceQuantum {

    public DamageSourceQArrow() {
        super("qa.damage.arrow");
        this.setMagicDamage();
        this.setDamageBypassesArmor();
    }

}
