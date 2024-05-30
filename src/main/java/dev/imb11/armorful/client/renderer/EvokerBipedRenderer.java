package dev.imb11.armorful.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Evoker;

public class EvokerBipedRenderer extends IllagerBipedRenderer<Evoker> {
    private static final ResourceLocation PILLAGER = new ResourceLocation("textures/entity/illager/evoker.png");

    public EvokerBipedRenderer(Context builder) {
        super(builder);
        this.addLayer(new ItemInHandLayer<>(this, builder.getItemInHandRenderer()) {
            @Override
            public void render(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, Evoker evokerEntity, float f, float g, float h, float j, float k, float l) {
                if (evokerEntity.isAggressive() || evokerEntity.isCastingSpell()) {
                    super.render(matrixStack, vertexConsumerProvider, i, evokerEntity, f, g, h, j, k, l);
                }
            }
        });
    }

    @Override
    public ResourceLocation getTextureLocation(Evoker entity) {
        return PILLAGER;
    }
}