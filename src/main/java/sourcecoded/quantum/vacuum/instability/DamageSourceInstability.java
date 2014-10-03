package sourcecoded.quantum.vacuum.instability;

import net.minecraft.util.DamageSource;
import sourcecoded.quantum.api.vacuum.Instability;
import sourcecoded.quantum.util.damage.DamageSourceQuantum;

public class DamageSourceInstability extends DamageSourceQuantum {

    public DamageSourceInstability(Instability instability) {
        super("qa.vacuum.instability");
        this.setMagicDamage();
        this.setDamageBypassesArmor();
    }

}
