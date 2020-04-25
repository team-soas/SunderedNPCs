package net.grallarius.sunderednpcs.entity;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class SittableRenderer extends EntityRenderer<SittableEntity> {

    public SittableRenderer(EntityRendererManager manager)
    {
        super(manager);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(SittableEntity entity) {
        return null;
    }

    @Override
    public void doRender(SittableEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {}
}
