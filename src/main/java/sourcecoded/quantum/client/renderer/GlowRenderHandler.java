package sourcecoded.quantum.client.renderer;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class GlowRenderHandler {

    private static GlowRenderHandler instance;

    public static GlowRenderHandler instance() {
        if (instance == null)
            instance = new GlowRenderHandler();
        return instance;
    }

    public float brightness;

    public float scaler;

    public float brightnessMaximum = 0.9F;
    public float brightnessMinimum = 0.25F;
    public float brightnessStep;

    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            brightness += brightnessStep;
            if (brightness >= brightnessMaximum)
                brightnessStep = -0.01F;
            else if (brightness <= brightnessMinimum)
                brightnessStep = 0.01F;

            scaler += brightnessStep;
            if (scaler >= 1F)
                brightnessStep = -0.01F;
            else if (scaler <= 0F)
                brightnessStep = 0.01F;
        }
    }

}
