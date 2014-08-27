package sourcecoded.quantum.api.block;

import net.minecraft.util.EnumChatFormatting;

/**
 * Implement this on any blocks that you want to
 * act as multipliers for rift devices.
 *
 * All floats returned are in the form of
 * decimal format
 *
 * e.g. 1.2F translates to 20% increase
 * e.g. 0.8F translates to 20% decrease
 */
public strictfp interface IRiftMultiplier {

    /**
     * Gets the amount of multiplication to affect
     * the object calling the method
     */
    public float getRiftMultiplication(RiftMultiplierTypes type);

    /**
     * The types of Rift mu
     */
    public static enum RiftMultiplierTypes {

        /**
         * The speed boost
         */
        SPEED(EnumChatFormatting.RED, EnumChatFormatting.GREEN),

        /**
         * The energy usage
         */
        ENERGY_USAGE(EnumChatFormatting.RED, EnumChatFormatting.GREEN),

        /**
         * Efficiency of production increase
         */
        PRODUCTION(EnumChatFormatting.GREEN, EnumChatFormatting.RED);

        public EnumChatFormatting positive;
        public EnumChatFormatting negative;

        RiftMultiplierTypes(EnumChatFormatting positive, EnumChatFormatting negative) {
            this.positive = positive;
            this.negative = negative;
        }
    }

}

