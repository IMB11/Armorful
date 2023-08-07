package com.mineblock11.armorful.client.illagers.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.util.Identifier;

public class EvokerBipedRenderer extends IllagerBipedRenderer<EvokerEntity> {
    private static final Identifier PILLAGER = new Identifier("textures/entity/illager/evoker.png");

    public EvokerBipedRenderer(Context builder) {
        super(builder);
        this.addFeature(new HeldItemFeatureRenderer<>(this, builder.getHeldItemRenderer()) {
            @Override
            public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, EvokerEntity evokerEntity, float f, float g, float h, float j, float k, float l) {
                if (evokerEntity.isAttacking() || evokerEntity.isSpellcasting()) {
                    super.render(matrixStack, vertexConsumerProvider, i, evokerEntity, f, g, h, j, k, l);
                }
            }
        });
    }

    @Override
    public Identifier getTexture(EvokerEntity p_115720_) {
        return PILLAGER;
    }
}