package sourcecoded.quantum.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.client.renderer.block.AdvancedTileProxy;
import sourcecoded.quantum.client.renderer.block.SimpleTileProxy;
import sourcecoded.quantum.client.renderer.entity.RenderEnderishCrystal;
import sourcecoded.quantum.client.renderer.entity.RenderEnergyPacket;
import sourcecoded.quantum.client.renderer.entity.RenderHellishCrystal;
import sourcecoded.quantum.client.renderer.item.ItemDepthRenderer;
import sourcecoded.quantum.client.renderer.item.ItemSceptreRenderer;
import sourcecoded.quantum.client.renderer.item.ItemStickRenderer;
import sourcecoded.quantum.client.renderer.item.TexDepthMap;
import sourcecoded.quantum.client.renderer.tile.*;
import sourcecoded.quantum.entity.EntityEnderishCrystal;
import sourcecoded.quantum.entity.EntityEnergyPacket;
import sourcecoded.quantum.entity.EntityHellishCrystal;
import sourcecoded.quantum.registry.QAItems;
import sourcecoded.quantum.tile.*;

public class ClientProxy implements IProxy {

    @Override
    public void register() {
        registerRenderers();
    }

    void registerRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(EntityEnergyPacket.class, new RenderEnergyPacket());
        RenderingRegistry.registerEntityRenderingHandler(EntityHellishCrystal.class, new RenderHellishCrystal());
        RenderingRegistry.registerEntityRenderingHandler(EntityEnderishCrystal.class, new RenderEnderishCrystal());

        RenderingRegistry.registerBlockHandler(new SimpleTileProxy());
        RenderingRegistry.registerBlockHandler(new AdvancedTileProxy());

        MinecraftForgeClient.registerItemRenderer(QAItems.SCEPTRE.getItem(), new ItemSceptreRenderer());

        MinecraftForgeClient.registerItemRenderer(QAItems.ENTROPIC_STAR.getItem(), new ItemDepthRenderer(
                new TexDepthMap(new ResourceLocation(Constants.MODID, "textures/items/star/L0.png"), 0.02F),
                new TexDepthMap(new ResourceLocation(Constants.MODID, "textures/items/star/L1.png"), 0.06F),
                new TexDepthMap(new ResourceLocation(Constants.MODID, "textures/items/star/L2.png"), 0.08F),
                new TexDepthMap(new ResourceLocation(Constants.MODID, "textures/items/star/L3.png"), 0.1F)
        ));

        MinecraftForgeClient.registerItemRenderer(QAItems.RIFT_AXE.getItem(), new ItemDepthRenderer(
                new TexDepthMap(new ResourceLocation(Constants.MODID, "textures/items/tools/axe_0.png"), 0.07F),
                new TexDepthMap(new ResourceLocation(Constants.MODID, "textures/items/tools/axe_1.png"), 0.02F),
                new TexDepthMap(new ResourceLocation(Constants.MODID, "textures/items/tools/axe_2.png"), 0.04F),
                new TexDepthMap(new ResourceLocation(Constants.MODID, "textures/items/tools/axe_3.png"), 0.1F)
        ));

        MinecraftForgeClient.registerItemRenderer(QAItems.RIFT_PICKAXE.getItem(), new ItemDepthRenderer(
                new TexDepthMap(new ResourceLocation(Constants.MODID, "textures/items/tools/pickaxe_0.png"), 0.07F),
                new TexDepthMap(new ResourceLocation(Constants.MODID, "textures/items/tools/pickaxe_1.png"), 0.02F),
                new TexDepthMap(new ResourceLocation(Constants.MODID, "textures/items/tools/pickaxe_2.png"), 0.04F),
                new TexDepthMap(new ResourceLocation(Constants.MODID, "textures/items/tools/pickaxe_3.png"), 0.1F)
        ));

        MinecraftForgeClient.registerItemRenderer(QAItems.RIFT_SHOVEL.getItem(), new ItemDepthRenderer(
                new TexDepthMap(new ResourceLocation(Constants.MODID, "textures/items/tools/shovel_0.png"), 0.07F),
                new TexDepthMap(new ResourceLocation(Constants.MODID, "textures/items/tools/shovel_1.png"), 0.02F),
                new TexDepthMap(new ResourceLocation(Constants.MODID, "textures/items/tools/shovel_2.png"), 0.04F),
                new TexDepthMap(new ResourceLocation(Constants.MODID, "textures/items/tools/shovel_3.png"), 0.1F)
        ));

        MinecraftForgeClient.registerItemRenderer(QAItems.RIFT_SWORD.getItem(), new ItemDepthRenderer(
                new TexDepthMap(new ResourceLocation(Constants.MODID, "textures/items/tools/sword_0.png"), 0.07F),
                new TexDepthMap(new ResourceLocation(Constants.MODID, "textures/items/tools/sword_1.png"), 0.02F),
                new TexDepthMap(new ResourceLocation(Constants.MODID, "textures/items/tools/sword_2.png"), 0.04F),
                new TexDepthMap(new ResourceLocation(Constants.MODID, "textures/items/tools/sword_3.png"), 0.08F)
        ));

        MinecraftForgeClient.registerItemRenderer(QAItems.INJECTED_STICK.getItem(), new ItemStickRenderer(
                new TexDepthMap(new ResourceLocation(Constants.MODID, "textures/items/tools/stick_0.png"), 0.09F),
                new TexDepthMap(new ResourceLocation(Constants.MODID, "textures/items/tools/stick_1.png"), 0.04F)
        ));

        ClientRegistry.bindTileEntitySpecialRenderer(TileInjectedStone.class, new TESRInjectedStone());
        ClientRegistry.bindTileEntitySpecialRenderer(TileInjectedGlass.class, new TESRInjectedGlass());
        ClientRegistry.bindTileEntitySpecialRenderer(TileDebug.class, new TESRDebug());
        ClientRegistry.bindTileEntitySpecialRenderer(TileCornerstone.class, new TESRCornerstone());
        ClientRegistry.bindTileEntitySpecialRenderer(TileRiftSmelter.class, new TESRRiftSmelter());
        ClientRegistry.bindTileEntitySpecialRenderer(TileRiftInjector.class, new TESRRiftInjector());
        ClientRegistry.bindTileEntitySpecialRenderer(TileManipulation.class, new TESRManipulation());
        ClientRegistry.bindTileEntitySpecialRenderer(TileSync.class, new TESRSync());
        ClientRegistry.bindTileEntitySpecialRenderer(TileArrangement.class, new TESRArrangement());
        ClientRegistry.bindTileEntitySpecialRenderer(TileRiftNode.class, new TESRRiftNode());
        ClientRegistry.bindTileEntitySpecialRenderer(TilePlayer.class, new TESRPlayer());
    }
}
