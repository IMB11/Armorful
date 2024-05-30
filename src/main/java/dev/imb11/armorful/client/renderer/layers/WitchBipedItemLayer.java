package dev.imb11.armorful.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.imb11.armorful.client.model.WitchBipedModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.CrossedArmsItemLayer;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class WitchBipedItemLayer<T extends Witch> extends CrossedArmsItemLayer<T, WitchBipedModel<T>> {
    public WitchBipedItemLayer(RenderLayerParent<T, WitchBipedModel<T>> p_234926_, ItemInHandRenderer p_234927_) {
        super(p_234926_, p_234927_);
    }

    public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        ItemStack itemstack = pLivingEntity.getMainHandItem();
        pMatrixStack.pushPose();
        if (itemstack.is(Items.POTION)) {
            this.getParentModel().getHead().translateAndRotate(pMatrixStack);
            this.getParentModel().nose.translateAndRotate(pMatrixStack);
            pMatrixStack.translate(0.0625F, 0.25F, 0.0F);
            pMatrixStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
            pMatrixStack.mulPose(Axis.XP.rotationDegrees(140.0F));
            pMatrixStack.mulPose(Axis.ZP.rotationDegrees(10.0F));
            pMatrixStack.translate(0.0F, -0.4F, 0.4F);
        }

        super.render(pMatrixStack, pBuffer, pPackedLight, pLivingEntity, pLimbSwing, pLimbSwingAmount, pPartialTicks, pAgeInTicks, pNetHeadYaw, pHeadPitch);
        pMatrixStack.popPose();
    }
}