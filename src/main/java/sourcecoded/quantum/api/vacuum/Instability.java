package sourcecoded.quantum.api.vacuum;

import sourcecoded.quantum.api.translation.LocalizationUtils;

public enum Instability {
    //No effect
    NONE("none"),
    //Small amount of damage to nearby entities
    MINIMAL("minimal", 1, 10),
    //Slightly larger damage, destroys some items
    SMALL("small", 2, 12),
    //Same damage, destroys more items
    MEDIUM("medium", 2, 12),
    //Higher damage, destroys all items
    HIGH("high", 3, 15),
    //All of the above, as well as Summons lightning strikes. Destroys all catalysts, too
    DISMAL("dismal", 4, 15, 1, 1, false),
    //All of the above, Much higher damage, explosions, pulls entities to the rift node
    CRITICAL("critical", 10, 15, 3, 2, false, true, 0.05D, 7),
    //All of the above, spawns hostile mobs, explosions, larger damage
    EXTREME("extreme", 13, 20, 6, 4, true, true, 0.15D, 11),
    //All of the above, as well as damages any entities in a 200 block range by 100 points.
    CATASTROPHIC("catastrophic", 100, 200, 8, 4, true, true, 0.35D, 20),
    //All of the above, as well as summons a size 100 fiery explosion.
    APOCALYPTIC("apocalyptic", 100, 200, 100, 10, true, true, 0.7D, 30),
    //All of the above, as well as summons the ender dragon & many withers. Pulls in netherrack and end stone.
    //Leaves random void holes in a 100 block radius.
    DIMENSIONAL_SHIFT("dimensional_shift", 100, 200, 100, 10, true, true, 1D, 40),
    //Same effects as Dimensional Shift, but instead will have a 1/4 chance of going horribly-right. No destruction will be done,
    //every player will get healed, the output will octuple, all nodes in a 1000 block radius refilled and the player given
    //some valuable items. Is it worth the risk?
    CATACLYSMIC_SWITCH("cataclysmic_switch", 100, 200, 100, 10, true, true, 1D, 40);

    public int damageValue;
    public int damageRadius;

    public int explosionValue;
    public int explosionShift;
    public boolean explosionFiery;

    public double pullStrength;
    public int pullRange;

    public boolean doesDamage = false;
    public boolean doesExplosion = false;
    public boolean doesLightning = false;
    public boolean doesPull = false;

    public String nameFormat = "qa.vacuum.instability.%s";

    public String instabilityName;

    Instability(String name) {
        this.instabilityName = name;
    }
    Instability(String name, int damageBase, int damageRadius) {
        this(name);
        this.damageValue = damageBase;
        this.damageRadius = damageRadius;

        doesDamage = true;
    }
    Instability(String name, int damageBase, int damageRadius, int explosionSize, int explosionShift, boolean explosionFiery) {
        this(name, damageBase, damageRadius);

        this.explosionValue = explosionSize;
        this.explosionShift = explosionShift;
        this.explosionFiery = explosionFiery;

        doesExplosion = true;
    }
    Instability(String name, int damageBase, int damageRadius, int explosionSize, int explosionShift, boolean explosionFiery, boolean lightning) {
        this(name, damageBase, damageRadius, explosionSize, explosionShift, explosionFiery);

        this.doesLightning = lightning;
    }
    Instability(String name, int damageBase, int damageRadius, int explosionSize, int explosionShift, boolean explosionFiery, boolean lightning, double pullStrength, int pullRange) {
        this(name, damageBase, damageRadius, explosionSize, explosionShift, explosionFiery, lightning);

        this.pullStrength = pullStrength;
        this.pullRange = pullRange;

        this.doesPull = true;
    }

    public String getInstabilityName() {
        String n = String.format(nameFormat, instabilityName) + ".name";
        return LocalizationUtils.translateLocalWithColours(n, n);
    }

    public boolean doesDamage() {
        return doesDamage;
    }

    public int getDamage() {
        return damageValue;
    }

    public int getDamageRadius() {
        return damageRadius;
    }

    public boolean doesExplosion() {
        return doesExplosion;
    }

    public int explosionSize() {
        return explosionValue;
    }

    public int explosionVariation() {
        return explosionShift;
    }

    public boolean isExplosionFiery() {
        return explosionFiery;
    }

    public boolean doesLightning() {
        return doesLightning;
    }

    public boolean pullsEntities() {
        return doesPull;
    }

    public double pullStrength() {
        return pullStrength;
    }

    public int pullRange() {
        return pullRange;
    }
}