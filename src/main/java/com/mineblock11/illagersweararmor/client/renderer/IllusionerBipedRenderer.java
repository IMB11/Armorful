package com.mineblock11.illagersweararmor.client.renderer;

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
            public void render(MatrixStack p_116352_, VertexConsumerProvider p_116353_, int p_116354_, IllusionerEntity p_116355_,
                               float p_116356_, float p_116357_, float p_116358_, float p_116359_, float p_116360_,
                               float p_116361_) {
                if (p_116355_.isSpellcasting() || p_116355_.isAttacking()) {
                    super.render(p_116352_, p_116353_, p_116354_, p_116355_, p_116356_, p_116357_, p_116358_, p_116359_,
                            p_116360_, p_116361_);
                }
            }
        });
    }

    @Override
    public void render(IllusionerEntity p_114952_, float p_114953_, float p_114954_, MatrixStack p_114955_,
            VertexConsumerProvider p_114956_, int p_114957_) {
        if (p_114952_.isInvisible()) {
            Vec3d[] avec3 = p_114952_.getMirrorCopyOffsets(p_114954_);
            float f = this.getAnimationProgress(p_114952_, p_114954_);

            for (int i = 0; i < avec3.length; ++i) {
                p_114955_.push();
                p_114955_.translate(avec3[i].x + (double) MathHelper.cos((float) i + f * 0.5F) * 0.025D,
                        avec3[i].y + (double) MathHelper.cos((float) i + f * 0.75F) * 0.0125D,
                        avec3[i].z + (double) MathHelper.cos((float) i + f * 0.7F) * 0.025D);
                super.render(p_114952_, p_114953_, p_114954_, p_114955_, p_114956_, p_114957_);
                p_114955_.pop();
            }
        } else {
            super.render(p_114952_, p_114953_, p_114954_, p_114955_, p_114956_, p_114957_);
        }

    }

    @Override
    public Identifier getTexture(IllusionerEntity p_115720_) {
        return PILLAGER;
    }

    @Override
    protected boolean isVisible(IllusionerEntity p_114959_) {
        return true;
    }
}
