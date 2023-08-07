package com.mineblock11.armorful.client.illagers.renderer;

import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.util.Identifier;

public class PillagerBipedRenderer extends IllagerBipedRenderer<PillagerEntity> {
    private static final Identifier PILLAGER = new Identifier("textures/entity/illager/pillager.png");

    public PillagerBipedRenderer(Context builder) {
        super(builder);
        this.addFeature(new HeldItemFeatureRenderer<>(this, builder.getHeldItemRenderer()));
    }

    @Override
    public Identifier getTexture(PillagerEntity p_115720_) {
        return PILLAGER;
    }
}
