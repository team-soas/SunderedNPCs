package net.grallarius.sunderednpcs.client;

import net.grallarius.sunderednpcs.entity.NPCEntity;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NPCModel extends PlayerModel<NPCEntity> {

    public NPCModel(boolean smallArms) {
        super(1.0f, smallArms);
    }
}
