package sourcecoded.quantum;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.item.Item;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.world.WorldEvent;
import sourcecoded.core.configuration.VersionConfig;
import sourcecoded.core.util.SourceLogger;
import sourcecoded.quantum.api.QuantumAPI;
import sourcecoded.quantum.api.sceptre.SceptreFocusRegistry;
import sourcecoded.quantum.client.renderer.GlowRenderHandler;
import sourcecoded.quantum.client.renderer.RainbowRenderHandler;
import sourcecoded.quantum.client.renderer.fx.helpers.ParticleDispatcher;
import sourcecoded.quantum.crafting.arrangement.ArrangementRecipes;
import sourcecoded.quantum.entity.*;
import sourcecoded.quantum.handler.ConfigHandler;
import sourcecoded.quantum.handler.KeyBindHandler;
import sourcecoded.quantum.listeners.*;
import sourcecoded.quantum.network.NetworkHandler;
import sourcecoded.quantum.proxy.IProxy;
import sourcecoded.quantum.registry.BlockRegistry;
import sourcecoded.quantum.registry.ItemRegistry;
import sourcecoded.quantum.registry.QAEnchant;
import sourcecoded.quantum.registry.TileRegistry;
import sourcecoded.quantum.sceptre.focus.*;
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

    public static SourceLogger logger;

    public static Item.ToolMaterial materialRift = EnumHelper.addToolMaterial("rift", 4, 1000, 30F, 15F, 30);

    public static boolean isDevEnvironment() {
        return Constants.BUILD_STATUS.equals("NOT_BUILT");
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) throws IOException {
        logger = new SourceLogger("Quantum Anomalies");

        QuantumAPI.isQAPresent = true;
        ConfigHandler.init(VersionConfig.createNewVersionConfig(event.getSuggestedConfigurationFile(), "0.1", Constants.MODID));

        endAnomaly = new BiomeEndAnomaly(getInteger(END_ANOMALY_ID));
        hellAnomaly = new BiomeHellAnomaly(getInteger(HELL_ANOMALY_ID));

        SceptreFocusRegistry.registerFocus(new FocusDematerialization());
        SceptreFocusRegistry.registerFocus(new FocusHelium());
        SceptreFocusRegistry.registerFocus(new FocusDiagnostic());
        SceptreFocusRegistry.registerFocus(new FocusBind());
        SceptreFocusRegistry.registerFocus(new FocusDebug());

        NetworkHandler.initNetwork();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        int entityID = 0;

        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            FMLCommonHandler.instance().bus().register(GlowRenderHandler.instance());
            FMLCommonHandler.instance().bus().register(RainbowRenderHandler.instance());
            FMLCommonHandler.instance().bus().register(ParticleDispatcher.INSTANCE);
            MinecraftForge.EVENT_BUS.register(ParticleDispatcher.INSTANCE);
            MinecraftForge.EVENT_BUS.register(new BiomeListener());
        }

        BlockRegistry.instance().registerAll();
        ItemRegistry.instance().registerAll();
        TileRegistry.instance().registerAll();
        QAEnchant.register();

        ArrangementRecipes.init();

        EntityRegistry.registerModEntity(EntityEnergyPacket.class, "riftPacket", entityID++, this, 80, 10, true);
        EntityRegistry.registerModEntity(EntityItemJewel.class, "itemJewel", entityID++, this, 80, 3, true);
        EntityRegistry.registerModEntity(EntityItemMagnet.class, "itemMagnet", entityID++, this, 80, 3, true);
        EntityRegistry.registerModEntity(EntityQuantumArrow.class, "quantumArrow", entityID++, this, 80, 3, true);

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
        MinecraftForge.EVENT_BUS.register(new EnchantmentListener());

        FMLCommonHandler.instance().bus().register(new SecretListener());
        FMLCommonHandler.instance().bus().register(new ServerListener());
        MinecraftForge.EVENT_BUS.register(new ItemTossListener());

        FMLInterModComms.sendMessage("Waila", "register", "sourcecoded.quantum.registry.BlockRegistry.wailaRegister");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        Achievements.initAchievements();

        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
            KeyBindHandler.initKeybinds();
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent e) {
        e.registerServerCommand(new CommandSpawnEntity());
        e.registerServerCommand(new DebugCommand());
        e.registerServerCommand(new DamageCommand());
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
    }

}
