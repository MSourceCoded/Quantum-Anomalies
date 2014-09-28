package sourcecoded.quantum.vacuum.instability;

import net.minecraft.util.DamageSource;
import sourcecoded.quantum.api.vacuum.Instability;

public class DamageSourceInstability extends DamageSource {

    public DamageSourceInstability(Instability instability) {
        super("qa.vacuum.instability");
        this.setMagicDamage();
    }

}
