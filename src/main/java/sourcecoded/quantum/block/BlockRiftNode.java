package sourcecoded.quantum.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sourcecoded.core.util.RandomUtils;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.api.block.Colourizer;
import sourcecoded.quantum.api.energy.ITileRiftHandler;
import sourcecoded.quantum.client.renderer.block.SimpleTileProxy;
import sourcecoded.quantum.item.ItemBlockQuantum;
import sourcecoded.quantum.network.MessageSetPlayerVelocity;
import sourcecoded.quantum.network.NetworkHandler;
import sourcecoded.quantum.tile.TileRiftNode;

import java.util.List;
import java.util.Random;

public class BlockRiftNode extends BlockDyeable implements ITileEntityProvider {

    public BlockRiftNode() {
        super();
        this.setBlockName("blockRiftNode");
        this.setBlockTextureName("riftNodeDefault");
        this.setCreativeTab(null);
        this.setHardness(10F);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z) {
        float min = 0.3F;
        float max = 1F - min;
        this.setBlockBounds(min, min, min, max, max, max);
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return null;
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xo, float yo, float zo) {
        boolean suResult = super.onBlockActivated(world, x, y, z, player, side, xo, yo, zo);
        if (suResult) return true;

        if (player.isSneaking() && player.getHeldItem() == null) {
            TileRiftNode node = (TileRiftNode) world.getTileEntity(x, y, z);
            node.cycleBehaviour(player);
            return true;
        }

        return false;
    }

    public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null) {
            ITileRiftHandler rift = (ITileRiftHandler) tile;
            float perc = (float)rift.getRiftEnergy() / (float)rift.getMaxRiftEnergy();
            return (int)(perc * 15);
        }

        return 0;
    }

    @Override
    public int getRenderType() {
        return SimpleTileProxy.renderID;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileRiftNode();
    }

    @Override
    public void onBlockExploded(World world, int x, int y, int z, Explosion explosion) {
        double dist = Math.sqrt(Math.pow(x - explosion.explosionX, 2) + Math.pow(z - explosion.explosionZ, 2));
        int val = (int) Math.floor(5000 * (explosion.explosionSize / dist) * RandomUtils.nextFloat(0.5F, 1.5F));                         //Explosion val
        TileRiftNode node = (TileRiftNode) world.getTileEntity(x, y, z);
        if (node != null && node.colour == Colourizer.ORANGE) {
            if (node.shockCooldown <= 0 && node.giveRiftEnergy(val) != 0) {
                    if (world.isRemote)
                        for (int i = 0; i < 10; i++)
                            world.spawnParticle("flame", x + 0.5, y + 0.5, z + 0.5, RandomUtils.nextDouble(-0.1, 0.1), 0.1D, RandomUtils.nextDouble(-0.1, 0.1));
            }
            node.shockCooldown = node.maxShockCooldown;
        }
    }

    public int quantityDropped(Random rnd) {
        return 0;
    }

    @SuppressWarnings("unchecked")
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(x - 4, y - 4, z - 4, x + 4, y + 4, z + 4));
        for (Entity entity : entities) {
            Vec3 direction = Vec3.createVectorHelper(entity.posX - x, entity.posY - y, entity.posZ - z);                                  //1.7.10

            Vec3 normal = direction.normalize();

            float force = 2F;

            if (entity.motionX < force)
                entity.motionX += force * normal.xCoord;
            if (entity.motionY < force)
                entity.motionY += force * normal.yCoord;
            if (entity.motionZ < force)
                entity.motionZ += force * normal.zCoord;

            if (entity instanceof EntityPlayer)
                NetworkHandler.wrapper.sendTo(new MessageSetPlayerVelocity(force * normal.xCoord, force * normal.yCoord, force * normal.zCoord), (EntityPlayerMP) entity);
        }
    }

    //Shhhh... it's a secret... between you and me.....
    public float getEnchantPowerBonus(World world, int x, int y, int z) {
        TileRiftNode node = (TileRiftNode) world.getTileEntity(x, y, z);
        if (node.getColour() != Colourizer.RAINBOW) return 0F;

        return ((float)node.getRiftEnergy() / (float)node.getMaxRiftEnergy()) * 15F;
    }
}
