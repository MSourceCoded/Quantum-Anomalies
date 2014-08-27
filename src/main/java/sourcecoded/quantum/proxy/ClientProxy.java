package sourcecoded.quantum.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraftforge.client.MinecraftForgeClient;
import sourcecoded.quantum.client.renderer.block.AdvancedTileProxy;
import sourcecoded.quantum.client.renderer.block.SimpleTileProxy;
import sourcecoded.quantum.client.renderer.entity.RenderEnderishCrystal;
import sourcecoded.quantum.client.renderer.entity.RenderEnergyPacket;
import sourcecoded.quantum.client.renderer.entity.RenderHellishCrystal;
import sourcecoded.quantum.client.renderer.item.ItemSceptreRenderer;
import sourcecoded.quantum.client.renderer.tile.*;
import sourcecoded.quantum.entity.EntityEnderishCrystal;
import sourcecoded.quantum.entity.EntityEnergyPacket;
import sourcecoded.quantum.entity.EntityHellishCrystal;
import sourcecoded.quantum.registry.ItemRegistry;
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

        MinecraftForgeClient.registerItemRenderer(ItemRegistry.instance().getItemByName("itemSceptre"), new ItemSceptreRenderer());

        ClientRegistry.bindTileEntitySpecialRenderer(TileInfusedStone.class, new TESRInfusedStone());
        ClientRegistry.bindTileEntitySpecialRenderer(TileInfusedGlass.class, new TESRInfusedGlass());
        ClientRegistry.bindTileEntitySpecialRenderer(TileDebug.class, new TESRDebug());
        ClientRegistry.bindTileEntitySpecialRenderer(TileCornerstone.class, new TESRCornerstone());
        ClientRegistry.bindTileEntitySpecialRenderer(TileRiftSmelter.class, new TESRRiftSmelter());
        ClientRegistry.bindTileEntitySpecialRenderer(TileRiftInjector.class, new TESRRiftInjector());
    }
}
