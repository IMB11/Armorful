    package com.mineblock11.illagersweararmor.client.renderer;

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
            public void render(MatrixStack p_116352_, VertexConsumerProvider p_116353_, int p_116354_, VindicatorEntity p_116355_,
                               float p_116356_, float p_116357_, float p_116358_, float p_116359_, float p_116360_,
                               float p_116361_) {
                if (p_116355_.isAttacking()) {
                    super.render(p_116352_, p_116353_, p_116354_, p_116355_, p_116356_, p_116357_, p_116358_, p_116359_,
                            p_116360_, p_116361_);
                }

            }
        });
    }

    @Override
    public Identifier getTexture(VindicatorEntity p_115720_) {
        return PILLAGER;
    }
}
