package com.mineblock11.armorful.client.illagers.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.VindicatorEntity;
import net.minecraft.util.Identifier;

public class VindicatorBipedRenderer extends IllagerBipedRenderer<VindicatorEntity> {
    private static final Identifier PILLAGER = new Identifier("textures/entity/illager/vindicator.png");

    public VindicatorBipedRenderer(Context builder) {
        super(builder);
        this.addFeature(new HeldItemFeatureRenderer<>(this, builder.getHeldItemRenderer()) {
            @Override
            public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, VindicatorEntity vindicatorEntity, float f, float g, float h, float j, float k, float l) {
                if (vindicatorEntity.isAttacking()) {
                    super.render(matrixStack, vertexConsumerProvider, i, vindicatorEntity, f, g, h, j, k, l);
                }
            }
        });
    }

    @Override
    public Identifier getTexture(VindicatorEntity entity) {
        return PILLAGER;
    }
}
