package net.grallarius.sunderednpcs.item;

import net.grallarius.sunderednpcs.block.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class NPCWand extends Item {

    public NPCWand() {
        super(new Properties()
                .maxStackSize(1)
                .group(ModBlocks.sqTab.itemGroup));
        setRegistryName("npcwand");
    }

    /**
     * Called when this item is used when targeting a Block
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

        Minecraft mc = Minecraft.getInstance();
        RayTraceResult objectMouseOver = mc.objectMouseOver;

        //mouseover hits an entity, open edit gui on client
        if (objectMouseOver != null && objectMouseOver.getType() == RayTraceResult.Type.ENTITY) {
            // checks if you hit an Entity and if so stores it as target
            EntityRayTraceResult entityRayTraceResult = (EntityRayTraceResult) mc.objectMouseOver;
            Entity target = entityRayTraceResult.getEntity();
/*            if (target instanceof NPCEntity) {
                if (!worldIn.isRemote && ((NPCEntity) target).isEditable()) {
                    //mc.displayGuiScreen(new ClientOnlyScreenExample((NPCEntity) target));

                    ITextComponent displayName = target.getDisplayName();
                    int entityId = target.getEntityId();
                    NPCInventory npcInventory = ((NPCEntity) target).getInventory();

                    NetworkHooks.openGui((ServerPlayerEntity)playerIn, new INamedContainerProvider()
                    {
                        @Override
                        public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity p_createMenu_3_)
                        {
                            return new NPCContainer(windowId, playerInventory, npcInventory, (NPCEntity)playerInventory.player.world.getEntityByID(entityId));
                        }

                        @Override
                        public ITextComponent getDisplayName()
                        {
                            return displayName;
                        }
                    }, extraData -> {
                        extraData.writeVarInt(entityId);
                        extraData.writeVarInt(entityId);
                    });
                }
            }*/
            //mouseover hits a block, create new NPC and spawn particles
        } else if (objectMouseOver != null && objectMouseOver.getType() == RayTraceResult.Type.BLOCK) {
/*
                    Direction direction = context.getFace();
                    BlockState blockstate = world.getBlockState(blockpos);

                    BlockPos blockpos1;
                    if (blockstate.getCollisionShape(world, blockpos).isEmpty()) {
                        blockpos1 = blockpos;
                    } else {
                        blockpos1 = blockpos.offset(direction);
                    }

                    ItemStack itemstack = new ItemStack(Items.EGG);
                    NPC_ENTITY.spawn(world, itemstack, context.getPlayer(), blockpos1, SpawnReason.SPAWN_EGG, true, !Objects.equals(blockpos, blockpos1) && direction == Direction.UP);
*/

            BlockPos pos = new BlockPos(playerIn.posX + objectMouseOver.getHitVec().getX(), playerIn.posY + objectMouseOver.getHitVec().getY(), playerIn.posZ + objectMouseOver.getHitVec().getZ());
            spawnParticles(worldIn, pos, 0);
            return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));

        }
        return new ActionResult<>(ActionResultType.PASS, playerIn.getHeldItem(handIn));
    }


    @OnlyIn(Dist.CLIENT)
    public static void spawnParticles(IWorld worldIn, BlockPos posIn, int data) {
        if (data == 0) {
            data = 15;
        }

            posIn = posIn.up();
            for(int i = 0; i < data; ++i) {
                double d0 = random.nextGaussian() * 0.02D;
                double d1 = random.nextGaussian() * 0.02D;
                double d2 = random.nextGaussian() * 0.02D;
                worldIn.addParticle(ParticleTypes.HAPPY_VILLAGER, (double) ((float) posIn.getX() + random.nextFloat()), (double) posIn.getY() + (double) random.nextFloat(), (double) ((float) posIn.getZ() + random.nextFloat()), d0, d1, d2);
            }

    }
}
