package net.grallarius.sunderednpcs.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

import static net.grallarius.sunderednpcs.entity.ModEntities.SITTABLE_ENTITY;

public class SittableEntity extends Entity {

    private BlockPos source;

    public SittableEntity(World world) {
        super(SITTABLE_ENTITY, world);
        this.noClip = true;
    }

    private SittableEntity(World world, BlockPos source, double yOffset) {
        this(world);
        this.source = source;
        this.setPosition(source.getX() + 0.5, source.getY() + yOffset, source.getZ() + 0.5);
    }

    @Override
    protected void registerData() {}

    @Override
    public void tick() {
        super.tick();
        if(source == null)
        {
            source = this.getPosition();
        }
        if(!this.world.isRemote)
        {
            if(this.getPassengers().isEmpty() || this.world.isAirBlock(source))
            {
                this.remove();
                world.updateComparatorOutputLevel(getPosition(), world.getBlockState(getPosition()).getBlock());
            }
        }
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {}

    @Override
    protected void writeAdditional(CompoundNBT compound) {}

    @Override
    public double getMountedYOffset() {
        return 0.0;
    }

    public BlockPos getSource() {
        return source;
    }

    @Override
    protected boolean canBeRidden(Entity entity) {
        return true;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public static boolean create(World world, BlockPos pos, double yOffset, PlayerEntity player) {
        if(!world.isRemote)
        {
            List<SittableEntity> seats = world.getEntitiesWithinAABB(SittableEntity.class, new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1.0, pos.getY() + 1.0, pos.getZ() + 1.0));
            if(seats.isEmpty())
            {
                SittableEntity seat = new SittableEntity(world, pos, yOffset);
                world.addEntity(seat);
                player.startRiding(seat, false);
            }
        }
        return true;
    }
}
