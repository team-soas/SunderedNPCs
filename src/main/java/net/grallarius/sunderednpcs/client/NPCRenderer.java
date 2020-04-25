package net.grallarius.sunderednpcs.client;

import com.mojang.blaze3d.platform.GlStateManager;
import net.grallarius.sunderednpcs.entity.NPCEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class NPCRenderer extends LivingRenderer<NPCEntity, NPCModel> {


    public NPCRenderer(EntityRendererManager manager) {
        super(manager, new NPCModel(true), 0f);
    }

    //((NPCEntity)manager.pointedEntity).isSmallArms()

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(NPCEntity entity) {
        if (entity.getSkin() != null) {
            return entity.getSkin();
        } else return new ResourceLocation("minecraft", "textures/entity/alex.png");
    }


    @Override
    public void renderName(NPCEntity entity, double x, double y, double z) {
        if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Specials.Pre<>(entity, this, x, y, z))) return;
        if (this.canRenderName(entity)) {
            double d0 = entity.getDistanceSq(this.renderManager.info.getProjectedView());
            float f = entity.shouldRenderSneaking() ? 32.0F : 64.0F;
            if (!(d0 >= (double)(f * f))) {
                String s = entity.getNpc_name();
                GlStateManager.alphaFunc(516, 0.1F);
                this.renderEntityName(entity, x, y, z, s, d0);
            }
        }
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Specials.Post<>(entity, this, x, y, z));
    }

    public static class RenderFactory implements IRenderFactory<NPCEntity>{

        @Override
        public EntityRenderer<? super NPCEntity> createRenderFor(EntityRendererManager manager) {
            return new NPCRenderer(manager);
        }
    }
}
