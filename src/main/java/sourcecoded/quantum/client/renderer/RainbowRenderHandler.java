package sourcecoded.quantum.client.renderer;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import sourcecoded.quantum.api.block.Colourizer;

import java.awt.*;

public class RainbowRenderHandler {

    private static RainbowRenderHandler instance;

    public static RainbowRenderHandler instance() {
        if (instance == null)
            instance = new RainbowRenderHandler();
        return instance;
    }

    public Color currentColor;

    public int hue = 0;

    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) return;
        hue+=3;
        if (hue > 360) hue = 0;
        if (currentColor == null) {
            Color color = Color.getHSBColor((float)hue / 360F, 1F, 1F);
            Colourizer.RAINBOW.rgb = new float[] {
                    (float)color.getRed() / 255F,
                    (float)color.getGreen() / 255F,
                    (float)color.getBlue() / 255F
            };
        }

    }
}
