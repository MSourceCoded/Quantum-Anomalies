package sourcecoded.quantum.vacuum.instability;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import sourcecoded.core.util.RandomUtils;
import sourcecoded.quantum.api.discovery.DiscoveryManager;
import sourcecoded.quantum.api.vacuum.IVacuumRecipe;
import sourcecoded.quantum.api.vacuum.Instability;
import sourcecoded.quantum.discovery.QADiscoveries;
import sourcecoded.quantum.network.MessageSetPlayerVelocity;
import sourcecoded.quantum.network.NetworkHandler;
import sourcecoded.quantum.tile.TileRiftNode;
import sourcecoded.quantum.util.WorldUtils;
import sourcecoded.quantum.util.shapes.CircleCallback;
import sourcecoded.quantum.util.shapes.CircleUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InstabilityHandler {

    public Instability instabilityLevel;

    TileRiftNode node;

    public boolean isAlive = true;
    public int ticker = 0;

    public boolean cataSwitched = false;
    public boolean watcher1 = false;
    public boolean explosionWatcher = true;

    IVacuumRecipe recipe;

    public InstabilityHandler(IVacuumRecipe recipe, Instability instability, TileRiftNode node) {
        this.instabilityLevel = instability;

        this.recipe = recipe;

        this.node = node;

        if (instability == Instability.CATACLYSMIC_SWITCH)
            cataSwitched = RandomUtils.rnd.nextInt(4) == 0;

        explosionWatcher = true;
    }

    public Instability getInstability() {
        return instabilityLevel;
    }

    public void tick() {
        if (!isAlive()) return;
        ticker++;

        if (instabilityLevel == null) {
            isAlive = false;
            return;
        }

        Instability in = getInstability();

        if (in != Instability.CATACLYSMIC_SWITCH)
            handleInstabilityForTick(in);
        else {
            if (!cataSwitched)
                handleInstabilityForTick(in);
            else
                handleCataclysmicSwitch(in);
        }


        if (ticker == 1) {
            int search = 100;
            List<EntityPlayer> players = (List<EntityPlayer>) node.getWorldObj().getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(node.xCoord - search, node.yCoord - search, node.zCoord - search, node.xCoord + search, node.yCoord + search, node.zCoord + search));
            for (EntityPlayer player : players)
                DiscoveryManager.unlockItem(QADiscoveries.Item.VACUUM_INSTABILITY.get().getKey(), player, false);
        }

        if (ticker == 600) isAlive = false;
    }

    @SuppressWarnings("unchecked")
    public void handleInstabilityForTick(Instability in) {
        Random rnd = RandomUtils.rnd;

        if (in.doesExplosion() && rnd.nextInt(50) == 0 && explosionWatcher)
            node.getWorldObj().createExplosion(null, node.xCoord, node.yCoord, node.zCoord, in.explosionSize(), in.explosionFiery);

        if (in.doesDamage() && rnd.nextInt(20) == 0) {
            List<EntityLivingBase> living = node.getWorldObj().getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(node.xCoord - in.damageRadius, node.yCoord - in.damageRadius, node.zCoord - in.damageRadius, node.xCoord + in.damageRadius, node.yCoord + in.damageRadius, node.zCoord + in.damageRadius));
            for (EntityLivingBase livingCurrent : living)
                livingCurrent.attackEntityFrom(new DamageSourceInstability(in), in.getDamage());
        }

        if (in.doesLightning() && rnd.nextInt(30) == 0) {
            EntityLightningBolt lightning = new EntityLightningBolt(node.getWorldObj(), node.xCoord + RandomUtils.nextInt(-30, 30), node.yCoord + RandomUtils.nextInt(-30, 30), node.zCoord + RandomUtils.nextInt(-30, 30));
            node.getWorldObj().addWeatherEffect(lightning);
            node.getWorldObj().setWorldTime(18000);
        }

        if (in.doesPull) {
            List<Entity> living = node.getWorldObj().getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(node.xCoord - in.pullRange, node.yCoord - in.pullRange, node.zCoord - in.pullRange, node.xCoord + in.pullRange, node.yCoord + in.pullRange, node.zCoord + in.pullRange));
            for (Entity entity : living) {

                if (entity instanceof EntityDragon || entity instanceof EntityDragonPart || entity instanceof EntityWither || entity instanceof EntityWitherSkull)
                    continue;

                if (entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode)
                    continue;

                Vec3 direction = Vec3.createVectorHelper(entity.posX - node.xCoord, entity.posY - node.yCoord, entity.posZ - node.zCoord);

                final double distToOrigin = direction.lengthVector();

                double force = in.pullStrength();

                if (force <= 0) continue;
                Vec3 normal = direction.normalize();

                double xV = -force * normal.xCoord;
                double yV = -force * normal.yCoord;
                double zV = -force * normal.zCoord;

                if (entity instanceof EntityPlayer) {
                    NetworkHandler.wrapper.sendTo(new MessageSetPlayerVelocity(xV, yV, zV), (EntityPlayerMP) entity);
                } else {
                    entity.motionX += xV;
                    entity.motionY += yV;
                    entity.motionZ += zV;
                }

            }
        }

        if (in == Instability.DIMENSIONAL_SHIFT || in == Instability.CATACLYSMIC_SWITCH)
            handleDimensionalShiftForTick(in);
    }

    public void handleDimensionalShiftForTick(Instability in) {
        if (!watcher1) {
            watcher1 = true;
            explosionWatcher = false;

            node.getWorldObj().createExplosion(null, node.xCoord, node.yCoord, node.zCoord, in.explosionSize(), in.explosionFiery);

            EntityDragon dragon = new EntityDragon(node.getWorldObj());
            dragon.setPositionAndUpdate(node.xCoord, node.yCoord + 50, node.zCoord);
            node.getWorldObj().spawnEntityInWorld(dragon);
        }

        if (RandomUtils.rnd.nextInt(40) == 0) {
            EntityWither wither = new EntityWither(node.getWorldObj());
            wither.setPositionAndUpdate(node.xCoord, node.yCoord + 30, node.zCoord);
            node.getWorldObj().spawnEntityInWorld(wither);
        }

        if (RandomUtils.rnd.nextInt(60) == 0) {
            int x = node.xCoord + RandomUtils.nextInt(-100, 100);
            int z = node.zCoord + RandomUtils.nextInt(-100, 100);

            int worldHoleRadius = RandomUtils.nextInt(3, 10);

            CircleCallback callback = new CircleCallback() {
                @Override
                public void call(int x1, int z1) {
                    for (int i = 0; i < node.getWorldObj().provider.getActualHeight(); i++)
                        node.getWorldObj().setBlockToAir(x1, i, z1);

                }
            };

            CircleUtils.generateFilled(x, z, worldHoleRadius, callback);
        }
    }

    @SuppressWarnings("unchecked")
    public void handleCataclysmicSwitch(Instability in) {
        watcher1 = true;

        ArrayList<ItemStack> stack = new ArrayList<ItemStack>(recipe.getOutputs());

        for (ItemStack cStack : stack) {
            cStack.stackSize *= 8;
        }

        stack.add(new ItemStack(Blocks.diamond_block, RandomUtils.nextInt(4, 43)));
        stack.add(new ItemStack(Blocks.emerald_block, RandomUtils.nextInt(4, 43)));

        IInventory inventory = null;

        for (IInventory cInventory : node.getVacuumExports()) {
            if (node.getItemsFromInventory(cInventory, true).contains(null)) {
                inventory = cInventory;
                break;
            }
        }

        if (inventory != null) {
            node.insertItems(stack, inventory);
        }

        List<EntityLivingBase> living = node.getWorldObj().getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(node.xCoord - in.damageRadius, node.yCoord - in.damageRadius, node.zCoord - in.damageRadius, node.xCoord + in.damageRadius, node.yCoord + in.damageRadius, node.zCoord + in.damageRadius));
        for (EntityLivingBase livingCurrent : living)
            livingCurrent.heal(in.damageValue);

        List<TileRiftNode> nodes = WorldUtils.searchForTile(node.getWorldObj(), node.xCoord, node.yCoord, node.zCoord, 1000, 1000, 1000, TileRiftNode.class);
        for (TileRiftNode node : nodes)
            node.giveRiftEnergy(node.getMaxRiftEnergy());

        isAlive = false;
    }

    public boolean isAlive() {
        return isAlive;
    }
}

