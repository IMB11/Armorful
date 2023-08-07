package com.mineblock11.armorful.illagers.client.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.IllusionerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class IllusionerBipedRenderer extends IllagerBipedRenderer<IllusionerEntity> {
    private static final Identifier PILLAGER = new Identifier("textures/entity/illager/illusioner.png");

    public IllusionerBipedRenderer(Context builder) {
        super(builder);
        this.addFeature(new HeldItemFeatureRenderer<>(this, builder.getHeldItemRenderer()) {
            @Override
            public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, IllusionerEntity illusionerEntity, float f, float g, float h, float j, float k, float l) {
                if (illusionerEntity.isAttacking() || illusionerEntity.isSpellcasting()) {
                    super.render(matrixStack, vertexConsumerProvider, i, illusionerEntity, f, g, h, j, k, l);
                }
            }
        });
    }

    @Override
    public void render(IllusionerEntity entity, float entityYaw, float partialTicks, MatrixStack matrices,
                       VertexConsumerProvider vertexProvider, int light) {
        if (entity.isInvisible()) {
            Vec3d[] avec3 = entity.getMirrorCopyOffsets(partialTicks);
            float f = this.getAnimationProgress(entity, partialTicks);

            for (int i = 0; i < avec3.length; ++i) {
                matrices.push();
                matrices.translate(avec3[i].x + (double) MathHelper.cos((float) i + f * 0.5F) * 0.025D,
                        avec3[i].y + (double) MathHelper.cos((float) i + f * 0.75F) * 0.0125D,
                        avec3[i].z + (double) MathHelper.cos((float) i + f * 0.7F) * 0.025D);
                super.render(entity, entityYaw, partialTicks, matrices, vertexProvider, light);
                matrices.pop();
            }
        } else {
            super.render(entity, entityYaw, partialTicks, matrices, vertexProvider, light);
        }

    }

    @Override
    public Identifier getTexture(IllusionerEntity entity) {
        return PILLAGER;
    }

    @Override
    protected boolean isVisible(IllusionerEntity entity) {
        return true;
    }
}
