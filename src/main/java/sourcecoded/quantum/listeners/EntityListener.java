package sourcecoded.quantum.listeners;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import sourcecoded.core.util.RandomUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EntityListener {

    //GET SPOOKED
    @SubscribeEvent
    public void sp00ky(EntityJoinWorldEvent event) {
        if (event.entity instanceof EntitySkeleton && RandomUtils.rnd.nextInt(10) == 0) {
            boolean halloween = false;

            Calendar cal = Calendar.getInstance();
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int mon = cal.get(Calendar.MONTH);

            halloween = (day == 31 && mon == Calendar.OCTOBER);

            if (!halloween) return;

            ((EntitySkeleton) event.entity).setCustomNameTag("SPOOKY SCARY SKELETONS");
            event.entity.setCurrentItemOrArmor(4, new ItemStack(Blocks.lit_pumpkin));
        }
    }

}
