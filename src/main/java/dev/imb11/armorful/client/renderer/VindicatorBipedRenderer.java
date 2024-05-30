package dev.imb11.armorful.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Vindicator;
import org.jetbrains.annotations.NotNull;

public class VindicatorBipedRenderer extends IllagerBipedRenderer<Vindicator> {
    private static final ResourceLocation PILLAGER = new ResourceLocation("textures/entity/illager/vindicator.png");

    public VindicatorBipedRenderer(Context builder) {
        super(builder);
        this.addLayer(new ItemInHandLayer<>(this, builder.getItemInHandRenderer()) {
            @Override
            public void render(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, Vindicator vindicatorEntity, float f, float g, float h, float j, float k, float l) {
                if (vindicatorEntity.isAggressive()) {
                    super.render(matrixStack, vertexConsumerProvider, i, vindicatorEntity, f, g, h, j, k, l);
                }
            }
        });
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Vindicator entity) {
            return PILLAGER;
    }
}
