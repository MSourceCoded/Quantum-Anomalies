package sourcecoded.quantum.client.renderer.fx.helpers;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.world.World;
import sourcecoded.core.util.RandomUtils;
import sourcecoded.quantum.api.block.Colourizer;
import sourcecoded.quantum.client.renderer.fx.FXPortalFragment;
import sourcecoded.quantum.client.renderer.fx.FXRiftInjection;
import sourcecoded.quantum.client.renderer.fx.FXRiftNode;
import sourcecoded.quantum.client.renderer.fx.FXRiftNodeOrbit;

public class FXManager {

    public static FXRiftNode riftNodeFX1Larger(float sizeBase, World world, double x, double y, double z, Colourizer colour) {
//        float r = RandomUtils.nextFloat(0.8F, 0.95F);
//        float b = RandomUtils.nextFloat(0.85F, 1F);

        FXRiftNode.DataHolder data = new FXRiftNode.DataHolder(colour.rgb[0], colour.rgb[1], colour.rgb[2], 0.1F, sizeBase);
        FXRiftNode nodeFX = new FXRiftNode(world, x, y, z, data, false);
        FMLClientHandler.instance().getClient().effectRenderer.addEffect(nodeFX);
        return nodeFX;
    }

    public static FXRiftNode riftNodeFX1Smaller(float sizeBase, World world, double x, double y, double z) {
        float size = sizeBase * 0.45F;

        FXRiftNode.DataHolder data = new FXRiftNode.DataHolder(0F, 0F, 0F, 0.07F, size);
        data.fadeLength = 0.4F;
        FXRiftNode nodeFX = new FXRiftNode(world, x, y, z, data, false);
        FMLClientHandler.instance().getClient().effectRenderer.addEffect(nodeFX);
        return nodeFX;
    }

    public static FXRiftInjection injectionFX(float size, World world, double x, double y, double z, boolean reversed, Colourizer colour) {
        FXRiftInjection.DataHolder data = new FXRiftInjection.DataHolder(colour.rgb[0], colour.rgb[1], colour.rgb[2], 0.25F, size);
        data.fadeLength = 0.4F;
        if (reversed) {
            data.xRad *= -1;
            data.zRad *= -1;
        }
        FXRiftInjection nodeFX = new FXRiftInjection(world, x, y, z, data);
        FMLClientHandler.instance().getClient().effectRenderer.addEffect(nodeFX);
        return nodeFX;
    }

    public static FXPortalFragment portalFX1Fragment(World world, int x, int y, int z) {
        float size = RandomUtils.nextFloat(0.02F, 0.04F);

        float r = RandomUtils.nextFloat(0F, 0.5F);
        float g = RandomUtils.nextFloat(0F, 0.5F);
        float b = RandomUtils.nextFloat(0F, 0.5F);

        FXPortalFragment.DataHolder data = new FXPortalFragment.DataHolder(r, g, b, 0.3F, size);
        data.fadeLength = 0F;

        data.xRadius = RandomUtils.nextFloat(-1F, 1F);
        data.yRadius = RandomUtils.nextFloat(-1F, 1F);
        data.zRadius = RandomUtils.nextFloat(-1F, 1F);

        FXPortalFragment fx = new FXPortalFragment(world, x + 0.5, y + 0.5, z + 0.5, data);
        FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
        return fx;
    }

    public static FXRiftNode portalFX1Hole(float sizeBase, World world, int x, int y, int z) {
        FXRiftNode.DataHolder data = new FXRiftNode.DataHolder(0.0F, 0.0F, 0.0F, 0.2F, sizeBase);

        data.fadeLength = 0.15F;
        FXRiftNode nodeFX = new FXRiftNode(world, x + 0.5, y + 0.5, z + 0.5, data, true);
        FMLClientHandler.instance().getClient().effectRenderer.addEffect(nodeFX);
        return nodeFX;
    }

    public static FXRiftNode portalFX2Filler(float sizeBase, World world, int x, int y, int z) {
        FXRiftNode.DataHolder data = new FXRiftNode.DataHolder(0.2F, 0.2F, 0.2F, 0.4F, sizeBase / 1.2F);

        data.fadeLength = 0.2F;
        FXRiftNode nodeFX = new FXRiftNode(world, x + 0.5, y + 0.5, z + 0.5, data, false);
        FMLClientHandler.instance().getClient().effectRenderer.addEffect(nodeFX);
        return nodeFX;
    }

    public static FXRiftNodeOrbit orbitingFX1(float size, World world, int x, int y, int z, Colourizer colour) {
//        float r = RandomUtils.nextFloat(0.5F, 1F);
//        float g = RandomUtils.nextFloat(0F, 0.1F);
//        float b = RandomUtils.nextFloat(0.5F, 1F);

        float bright = RandomUtils.nextFloat(0.1F, 0.3F);

        FXRiftNodeOrbit.DataHolder data = new FXRiftNodeOrbit.DataHolder(colour.rgb[0], colour.rgb[1], colour.rgb[2], bright, size);

        data.xRad = RandomUtils.nextFloat(1.5F, 1F);
        data.yRad = RandomUtils.nextFloat(2F, -2F);
        data.zRad = data.xRad;

        data.fadeLength = 0.05F;
        FXRiftNodeOrbit nodeFX = new FXRiftNodeOrbit(world, x + 0.5, y + 0.5, z + 0.5, data);
        FMLClientHandler.instance().getClient().effectRenderer.addEffect(nodeFX);
        return nodeFX;
    }
}
