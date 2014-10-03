package sourcecoded.quantum.util;

import sourcecoded.core.util.RandomUtils;

public class MathUtils {

    public static int getProductionAmount(int standard, float boost) {
        boost -= 1F;
        int amount = (int) Math.floor(boost);
        float newPercentage = boost - (float)amount;

        if (RandomUtils.nextFloat(0F, 1F) <= newPercentage) amount++;

        return amount + standard;
    }

}
