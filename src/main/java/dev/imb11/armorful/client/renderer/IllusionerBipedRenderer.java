package dev.imb11.armorful.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Illusioner;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class IllusionerBipedRenderer extends IllagerBipedRenderer<Illusioner> {
    private static final ResourceLocation PILLAGER = new ResourceLocation("textures/entity/illager/illusioner.png");

    public IllusionerBipedRenderer(Context builder) {
        super(builder);
        this.addLayer(new ItemInHandLayer<>(this, builder.getItemInHandRenderer()) {
            @Override
            public void render(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, Illusioner illusionerEntity, float f, float g, float h, float j, float k, float l) {
                if (illusionerEntity.isAggressive() || illusionerEntity.isCastingSpell()) {
                    super.render(matrixStack, vertexConsumerProvider, i, illusionerEntity, f, g, h, j, k, l);
                }
            }
        });
    }

    @Override
    public void render(Illusioner entity, float entityYaw, float partialTicks, PoseStack matrices,
                       MultiBufferSource vertexProvider, int light) {
        if (entity.isInvisible()) {
            Vec3[] avec3 = entity.getIllusionOffsets(partialTicks);
            float f = this.getBob(entity, partialTicks);

            for (int i = 0; i < avec3.length; ++i) {
                matrices.pushPose();
                matrices.translate(avec3[i].x + (double) Mth.cos((float) i + f * 0.5F) * 0.025D,
                        avec3[i].y + (double) Mth.cos((float) i + f * 0.75F) * 0.0125D,
                        avec3[i].z + (double) Mth.cos((float) i + f * 0.7F) * 0.025D);
                super.render(entity, entityYaw, partialTicks, matrices, vertexProvider, light);
                matrices.popPose();
            }
        } else {
            super.render(entity, entityYaw, partialTicks, matrices, vertexProvider, light);
        }

    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Illusioner entity) {
        return PILLAGER;
    }
}
