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
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import sourcecoded.core.configuration.VersionConfig;
import sourcecoded.quantum.api.sceptre.SceptreFocusRegistry;
import sourcecoded.quantum.client.renderer.GlowRenderHandler;
import sourcecoded.quantum.entity.EntityEnderishCrystal;
import sourcecoded.quantum.entity.EntityEnergyPacket;
import sourcecoded.quantum.entity.EntityHellishCrystal;
import sourcecoded.quantum.handler.ConfigHandler;
import sourcecoded.quantum.proxy.IProxy;
import sourcecoded.quantum.registry.BlockRegistry;
import sourcecoded.quantum.registry.ItemRegistry;
import sourcecoded.quantum.registry.TileRegistry;
import sourcecoded.quantum.sceptre.FocusBlue;
import sourcecoded.quantum.sceptre.FocusGreen;
import sourcecoded.quantum.sceptre.FocusRed;
import sourcecoded.quantum.worldgen.biome.BiomeEndAnomaly;
import sourcecoded.quantum.worldgen.biome.BiomeHellAnomaly;

import java.io.IOException;

import static sourcecoded.quantum.Constants.*;
import static sourcecoded.quantum.handler.ConfigHandler.*;
import static sourcecoded.quantum.handler.ConfigHandler.Properties.*;

@Mod(modid = MODID, name = NAME, version = VERSION, dependencies = "required-after:sourcecodedcore")
public class QuantumAnomalies {

    @SidedProxy(serverSide = Constants.PROXY_COMMON, clientSide = Constants.PROXY_CLIENT)
    public static IProxy proxy;

    public static final BiomeEndAnomaly endAnomaly = new BiomeEndAnomaly(150);
    public static final BiomeHellAnomaly hellAnomaly = new BiomeHellAnomaly(151);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) throws IOException {
        ConfigHandler.init(VersionConfig.createNewVersionConfig(event.getSuggestedConfigurationFile(), "0.1", Constants.MODID));

        SceptreFocusRegistry.registerFocus(new FocusRed());
        SceptreFocusRegistry.registerFocus(new FocusGreen());
        SceptreFocusRegistry.registerFocus(new FocusBlue());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        int id = 0;

        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
            FMLCommonHandler.instance().bus().register(GlowRenderHandler.instance());

        BlockRegistry.instance().registerAll();
        ItemRegistry.instance().registerAll();
        TileRegistry.instance().registerAll();

        EntityRegistry.registerModEntity(EntityEnergyPacket.class, "riftPacket", id++, this, 80, 10, true);
        EntityRegistry.registerModEntity(EntityHellishCrystal.class, "hellishCrystal", id++, this, 80, 3, true);
        EntityRegistry.registerModEntity(EntityEnderishCrystal.class, "enderishCrystal", id++, this, 80, 3, true);

        BiomeDictionary.registerBiomeType(endAnomaly, BiomeDictionary.Type.END);
        BiomeDictionary.registerBiomeType(hellAnomaly, BiomeDictionary.Type.NETHER);
        BiomeManager.addSpawnBiome(endAnomaly);
        BiomeManager.addSpawnBiome(hellAnomaly);

        BiomeManager.desertBiomes.add(new BiomeManager.BiomeEntry(hellAnomaly, getConfig().getInteger(HELL_ANOMALY_WEIGHT.getCategory(), HELL_ANOMALY_WEIGHT.getName())));
        BiomeManager.coolBiomes.add(new BiomeManager.BiomeEntry(endAnomaly, getConfig().getInteger(END_ANOMALY_WEIGHT.getCategory(), END_ANOMALY_WEIGHT.getName())));
        BiomeManager.warmBiomes.add(new BiomeManager.BiomeEntry(endAnomaly, getConfig().getInteger(END_ANOMALY_WEIGHT.getCategory(), END_ANOMALY_WEIGHT.getName())));

        proxy.register();

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

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
