package sourcecoded.quantum.client.renderer.fx.helpers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import static org.lwjgl.opengl.GL11.*;
import static net.minecraft.client.renderer.ActiveRenderInfo.*;

public enum ParticleDispatcher {
    INSTANCE;

    public Vector<EntityFX> particles = new Vector<EntityFX>();

    public void addParticleToRenderQueue(EntityFX fx) {
        particles.add(fx);
    }

    @SubscribeEvent
    public void onRenderTick(RenderWorldLastEvent event) {
        float partialTicks = event.partialTicks;
        Tessellator tess = Tessellator.instance;

        EntityPlayer player = Minecraft.getMinecraft().thePlayer;

        glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        glDepthMask(false);
        glEnable(GL11.GL_BLEND);
        glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        glAlphaFunc(GL11.GL_GREATER, 0.003921569F);

        glPushMatrix();
        for (EntityFX particle : particles) {
            particle.renderParticle(tess, partialTicks, rotationX, rotationXZ, rotationZ, rotationYZ, rotationXY);
        }
        glPopMatrix();

        glDisable(GL11.GL_BLEND);
        glDepthMask(true);
        glAlphaFunc(GL11.GL_GREATER, 0.1F);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            Iterator<EntityFX> it = particles.iterator();
            while (it.hasNext()) {
                EntityFX particle = it.next();
                particle.onUpdate();
                if (particle.isDead) it.remove();
            }
        }
    }

}
