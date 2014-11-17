package sourcecoded.quantum.handler;

import net.minecraftforge.common.config.Configuration;
import sourcecoded.core.configuration.VersionConfig;

public class ConfigHandler {

    private static VersionConfig config;

    public static void init(VersionConfig config) {
        ConfigHandler.config = config;
        startCategories();
        startProperties();
    }

    static void startCategories() {
        Categories[] cats = Categories.values();    //Meow :3
        for (Categories cat : cats)
            getRawConfig().addCustomCategoryComment(cat.getName(), cat.getComment());

        getConfig().saveConfig();
    }

    static void startProperties() {
        Properties[] props = Properties.values();
        for (Properties prop : props) {
            Object value = prop.getDefault();

            if (value instanceof Integer)
                getConfig().createProperty(prop.getCategory(), prop.getName(), (Integer)value);
            else if (value instanceof Double)
                getConfig().createProperty(prop.getCategory(), prop.getName(), (Double)value);
            else if (value instanceof String)
                getConfig().createProperty(prop.getCategory(), prop.getName(), (String)value);
            else if (value instanceof Boolean)
                getConfig().createProperty(prop.getCategory(), prop.getName(), (Boolean) value);

            if (prop.getComment() != null)
                getConfig().setComment(prop.getCategory(), prop.getName(), prop.getComment());
        }

        getConfig().saveConfig();
    }

    public static int getInteger(Properties prop) {
        return config.getInteger(prop.getCategory(), prop.getName());
    }

    public static double getDouble(Properties prop) {
        return config.getDouble(prop.getCategory(), prop.getName());
    }

    public static String getString(Properties prop) {
        return config.getString(prop.getCategory(), prop.getName());
    }

    public static Boolean getBoolean(Properties prop) {
        return config.getBool(prop.getCategory(), prop.getName());
    }

    public static VersionConfig getConfig() {
        return config;
    }

    public static Configuration getRawConfig() {
        return config.config;
    }

    public static enum Categories {
        INTEGRATION("integration", "For integration with other mods and APIs"),
        WORLD_GEN("world_gen", "Changes how the WorldGen in the mod works. Mostly requires a new world to take effect"),
        RENDERING("rendering", "Changes how things look. Change these settings for performance/quality settings"),
        ID("identification", "Change these values to resolve ID conflicts with other mods"),
        ;

        private String categoryName, comment;

        Categories(String categoryName, String comment) {
            this.categoryName = categoryName;
            this.comment = comment;
        }

        public String getName() {
            return categoryName.toLowerCase();
        }

        public String getComment() {
            return comment;
        }

        public String toString() {
            return getName();
        }
    }

    public static enum Properties {
        /* Integration */
        //REDSTONE_FLUX(Categories.INTEGRATION, "baby_kicker_mode", "Enables RF integration >.>", false),

        /* World Gen */
        END_ANOMALY_WEIGHT(Categories.WORLD_GEN, "end_anomaly_biome_weight", "The weight used for generating End Anomaly biomes. Recommended that you increase this if you use Biome-Gen mods (e.g. 60-90 for Biomes o' Plenty)", 7),
        HELL_ANOMALY_WEIGHT(Categories.WORLD_GEN, "hell_anomaly_biome_weight", "The weight used for generating Hell Anomaly biomes. Recommended that you increase this if you use Biome-Gen mods (e.g. 30-60 for Biomes o' Plenty)", 3),

        END_ANOMALY_ID(Categories.WORLD_GEN, "end_anomaly_biome_id", "The ID used for the End Anomaly Biome", 150),
        HELL_ANOMALY_ID(Categories.WORLD_GEN, "hell_anomaly_biome_id", "The ID used for the Hell Anomaly Biome", 151),

        /* Rendering */
        PARTICLE_RANGE_HIGH(Categories.RENDERING, "particle_range_high", "The range (in blocks) particles with a HIGH priority should be rendered.", 64.0D),
        PARTICLE_RANGE_LOW(Categories.RENDERING, "particle_range_low", "The range (in blocks) particles with a LOW priority should be rendered.", 40.0D),
        BLOCK_LABEL_DISTANCE(Categories.RENDERING, "block_label_dist", "The range (in blocks sq) block labels should be rendered.", 100.0D),

        /* SCREENSHOT */
        ENCHANT_ID_DECEPTION(Categories.ID, "enchantment_id_deception", "The ID (0-256) for the Deception Enchantment. Change this if another mod conflicts with this", 130),
        ENCHANT_ID_RANGE(Categories.ID, "enchantment_id_range", "The ID (0-256) for the Range Enchantment. Change this if another mod conflicts with this", 131),
        ;

        String category, propertyName, comment;
        Object defaultValue;

        Properties(String category, String propertyName, String comment, Object def) {
            this.category = category;
            this.propertyName = propertyName;
            this.comment = comment;
            this.defaultValue = def;
        }

        Properties(Categories category, String propertyName, String comment, Object def) {
            this(category.getName(), propertyName, comment, def);
        }

        public String getCategory() {
            return category;
        }

        public String getName() {
            return propertyName.toLowerCase();
        }

        public String getComment() {
            return comment;
        }

        public Object getDefault() {
            return defaultValue;
        }

        public String toString() {
            return getName();
        }
    }
}
