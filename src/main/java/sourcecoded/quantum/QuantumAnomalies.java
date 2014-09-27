package sourcecoded.quantum;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import sourcecoded.core.configuration.VersionConfig;
import sourcecoded.quantum.api.QuantumAPI;
import sourcecoded.quantum.api.arrangement.ArrangementRegistry;
import sourcecoded.quantum.api.arrangement.ArrangementShapedRecipe;
import sourcecoded.quantum.api.sceptre.SceptreFocusRegistry;
import sourcecoded.quantum.client.renderer.GlowRenderHandler;
import sourcecoded.quantum.client.renderer.RainbowRenderHandler;
import sourcecoded.quantum.entity.EntityEnderishCrystal;
import sourcecoded.quantum.entity.EntityEnergyPacket;
import sourcecoded.quantum.entity.EntityHellishCrystal;
import sourcecoded.quantum.entity.EntityItemJewel;
import sourcecoded.quantum.handler.ConfigHandler;
import sourcecoded.quantum.listeners.ArrangementTableListener;
import sourcecoded.quantum.listeners.BiomeListener;
import sourcecoded.quantum.listeners.SecretListener;
import sourcecoded.quantum.network.NetworkHandler;
import sourcecoded.quantum.proxy.IProxy;
import sourcecoded.quantum.registry.BlockRegistry;
import sourcecoded.quantum.registry.ItemRegistry;
import sourcecoded.quantum.registry.QAItems;
import sourcecoded.quantum.registry.TileRegistry;
import sourcecoded.quantum.sceptre.focus.FocusBind;
import sourcecoded.quantum.sceptre.focus.FocusDematerialization;
import sourcecoded.quantum.sceptre.focus.FocusDiagnostic;
import sourcecoded.quantum.sceptre.focus.FocusHelium;
import sourcecoded.quantum.worldgen.biome.BiomeEndAnomaly;
import sourcecoded.quantum.worldgen.biome.BiomeHellAnomaly;

import java.io.IOException;

import static sourcecoded.quantum.Constants.*;
import static sourcecoded.quantum.handler.ConfigHandler.Properties.*;
import static sourcecoded.quantum.handler.ConfigHandler.getInteger;

@Mod(modid = MODID, name = NAME, version = VERSION, dependencies = "required-after:sourcecodedcore")
public class QuantumAnomalies {

    @SidedProxy(serverSide = Constants.PROXY_COMMON, clientSide = Constants.PROXY_CLIENT)
    public static IProxy proxy;

    public static BiomeEndAnomaly endAnomaly;
    public static BiomeHellAnomaly hellAnomaly;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) throws IOException {
        QuantumAPI.isQAPresent = true;
        ConfigHandler.init(VersionConfig.createNewVersionConfig(event.getSuggestedConfigurationFile(), "0.1", Constants.MODID));

        endAnomaly = new BiomeEndAnomaly(getInteger(END_ANOMALY_ID));
        hellAnomaly = new BiomeHellAnomaly(getInteger(HELL_ANOMALY_ID));

        SceptreFocusRegistry.registerFocus(new FocusDematerialization());
        SceptreFocusRegistry.registerFocus(new FocusHelium());
        SceptreFocusRegistry.registerFocus(new FocusDiagnostic());
        SceptreFocusRegistry.registerFocus(new FocusBind());

        NetworkHandler.initNetwork();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        int id = 0;

        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            FMLCommonHandler.instance().bus().register(GlowRenderHandler.instance());
            FMLCommonHandler.instance().bus().register(RainbowRenderHandler.instance());
            MinecraftForge.EVENT_BUS.register(new BiomeListener());
        }

        BlockRegistry.instance().registerAll();
        ItemRegistry.instance().registerAll();
        TileRegistry.instance().registerAll();

        EntityRegistry.registerModEntity(EntityEnergyPacket.class, "riftPacket", id++, this, 80, 10, true);
        EntityRegistry.registerModEntity(EntityHellishCrystal.class, "hellishCrystal", id++, this, 80, 3, true);
        EntityRegistry.registerModEntity(EntityEnderishCrystal.class, "enderishCrystal", id++, this, 80, 3, true);
        EntityRegistry.registerModEntity(EntityItemJewel.class, "itemJewel", id++, this, 80, 3, true);

        BiomeDictionary.registerBiomeType(endAnomaly, BiomeDictionary.Type.END);
        BiomeDictionary.registerBiomeType(hellAnomaly, BiomeDictionary.Type.NETHER);
        BiomeManager.addSpawnBiome(endAnomaly);
        BiomeManager.addSpawnBiome(hellAnomaly);

        BiomeManager.desertBiomes.add(new BiomeManager.BiomeEntry(hellAnomaly, getInteger(HELL_ANOMALY_WEIGHT)));
        BiomeManager.coolBiomes.add(new BiomeManager.BiomeEntry(endAnomaly, getInteger(END_ANOMALY_WEIGHT)));
        BiomeManager.warmBiomes.add(new BiomeManager.BiomeEntry(endAnomaly, getInteger(END_ANOMALY_WEIGHT)));

        proxy.register();

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ArrangementTableListener());

        FMLCommonHandler.instance().bus().register(new SecretListener());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        Achievements.initAchievements();
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent e) {
        e.registerServerCommand(new CommandSpawnEntity());
        e.registerServerCommand(new DebugCommand());
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
    }

}
