package sourcecoded.quantum;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.item.Item;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.world.WorldEvent;
import sourcecoded.core.SourceCodedCore;
import sourcecoded.core.configuration.VersionConfig;
import sourcecoded.core.configuration.gui.SourceConfigGuiFactory;
import sourcecoded.core.util.SourceLogger;
import sourcecoded.core.version.VersionChecker;
import sourcecoded.quantum.api.QuantumAPI;
import sourcecoded.quantum.api.sceptre.SceptreFocusRegistry;
import sourcecoded.quantum.client.gui.GuiHandler;
import sourcecoded.quantum.client.renderer.GlowRenderHandler;
import sourcecoded.quantum.client.renderer.RainbowRenderHandler;
import sourcecoded.quantum.client.renderer.WorldLabelRenderer;
import sourcecoded.quantum.client.renderer.fx.helpers.ParticleDispatcher;
import sourcecoded.quantum.crafting.arrangement.ArrangementRecipes;
import sourcecoded.quantum.discovery.DiscoveryHandler;
import sourcecoded.quantum.entity.EntityEnergyPacket;
import sourcecoded.quantum.entity.EntityItemJewel;
import sourcecoded.quantum.entity.EntityItemMagnet;
import sourcecoded.quantum.entity.EntityQuantumArrow;
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
import static sourcecoded.quantum.handler.ConfigHandler.getConfig;
import static sourcecoded.quantum.handler.ConfigHandler.getInteger;

@Mod(modid = MODID, name = NAME, version = VERSION, dependencies = "required-after:sourcecodedcore")
public class QuantumAnomalies {

    @SidedProxy(serverSide = Constants.PROXY_COMMON, clientSide = Constants.PROXY_CLIENT)
    public static IProxy proxy;

    @Mod.Instance(Constants.MODID)
    public static QuantumAnomalies instance;

    public static BiomeEndAnomaly endAnomaly;
    public static BiomeHellAnomaly hellAnomaly;

    public static SourceLogger logger;

    public static Item.ToolMaterial materialRift = EnumHelper.addToolMaterial("rift", 4, 1000, 30F, 15F, 30);

    public static GuiHandler guiHandler;

    SourceConfigGuiFactory factory;

    VersionChecker checker;

    public static boolean isDevEnvironment() {
        return SourceCodedCore.isDevEnv;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) throws IOException {
        logger = new SourceLogger("Quantum Anomalies");

        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
            guiHandler = new GuiHandler();

        QuantumAPI.isQAPresent = true;
        ConfigHandler.init(VersionConfig.createNewVersionConfig(event.getSuggestedConfigurationFile(), "0.5", Constants.MODID));

        endAnomaly = new BiomeEndAnomaly(getInteger(END_ANOMALY_ID));
        hellAnomaly = new BiomeHellAnomaly(getInteger(HELL_ANOMALY_ID));

        SceptreFocusRegistry.registerFocus(new FocusDematerialization());
        SceptreFocusRegistry.registerFocus(new FocusHelium());
        SceptreFocusRegistry.registerFocus(new FocusDiagnostic());
        SceptreFocusRegistry.registerFocus(new FocusBind());
        SceptreFocusRegistry.registerFocus(new FocusDebug());

        NetworkHandler.initNetwork();

        if (ConfigHandler.getBoolean(ConfigHandler.Properties.VERS_ON))
            checker = new VersionChecker(sourcecoded.core.Constants.MODID, "https://raw.githubusercontent.com/MSourceCoded/Quantum-Anomalies/master/version/{MC}.txt", sourcecoded.core.Constants.VERSION, ConfigHandler.getBoolean(ConfigHandler.Properties.VERS_AUTO), ConfigHandler.getBoolean(ConfigHandler.Properties.VERS_SILENT));

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        int entityID = 0;

        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            FMLCommonHandler.instance().bus().register(GlowRenderHandler.instance());
            FMLCommonHandler.instance().bus().register(RainbowRenderHandler.instance());
            FMLCommonHandler.instance().bus().register(ParticleDispatcher.INSTANCE);
            FMLCommonHandler.instance().bus().register(guiHandler);
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

        //BiomeManager.desertBiomes.add(new BiomeManager.BiomeEntry(hellAnomaly, getInteger(HELL_ANOMALY_WEIGHT)));                 13.0.1207
        //BiomeManager.coolBiomes.add(new BiomeManager.BiomeEntry(endAnomaly, getInteger(END_ANOMALY_WEIGHT)));
        //BiomeManager.warmBiomes.add(new BiomeManager.BiomeEntry(endAnomaly, getInteger(END_ANOMALY_WEIGHT)));

        BiomeManager.addBiome(BiomeManager.BiomeType.DESERT, new BiomeManager.BiomeEntry(hellAnomaly, getInteger(HELL_ANOMALY_WEIGHT)));
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(endAnomaly, getInteger(END_ANOMALY_WEIGHT)));
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(endAnomaly, getInteger(END_ANOMALY_WEIGHT)));

        proxy.register();

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ArrangementTableListener());
        MinecraftForge.EVENT_BUS.register(new EnchantmentListener());

        FMLCommonHandler.instance().bus().register(new ServerListener());
        FMLCommonHandler.instance().bus().register(new DiscoveryListener());
        MinecraftForge.EVENT_BUS.register(new ItemTossListener());
        MinecraftForge.EVENT_BUS.register(new EntityListener());
        MinecraftForge.EVENT_BUS.register(new DiscoveryListener());

        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            MinecraftForge.EVENT_BUS.register(new DiscoveryListenerClient());
        }

        FMLInterModComms.sendMessage("Waila", "register", "sourcecoded.quantum.registry.BlockRegistry.wailaRegister");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            KeyBindHandler.initKeybinds();
            NetworkRegistry.INSTANCE.registerGuiHandler(this, guiHandler);
            WorldLabelRenderer.INSTANCE.init();
            factory = SourceConfigGuiFactory.create(Constants.MODID, instance, getConfig());
            factory.inject();
        }

        DiscoveryHandler.init();

    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent e) {
        e.registerServerCommand(new CommandSpawnEntity());
        e.registerServerCommand(new DebugCommand());
        e.registerServerCommand(new DamageCommand());
        e.registerServerCommand(new QACommand());
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
    }

}
