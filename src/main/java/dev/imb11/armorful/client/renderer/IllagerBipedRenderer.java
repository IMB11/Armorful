package dev.imb11.armorful.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.imb11.armorful.client.ArmorfulClient;
import dev.imb11.armorful.client.model.IllagerArmorModel;
import dev.imb11.armorful.client.model.IllagerBipedModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;

public abstract class IllagerBipedRenderer<T extends AbstractIllager> extends MobRenderer<T, IllagerBipedModel<T>> {
    public IllagerBipedRenderer(EntityRendererProvider.Context builder) {
        super(builder, new IllagerBipedModel<>(builder.bakeLayer(ArmorfulClient.ILLAGER_BIPED)), 0.5F);
        this.addLayer(new CustomHeadLayer<>(this, builder.getModelSet(), builder.getItemInHandRenderer()));
        this.addLayer(new ElytraLayer<>(this, builder.getModelSet()));
        this.addLayer(new HumanoidArmorLayer<>(this,
                new IllagerArmorModel<>(builder.bakeLayer(ArmorfulClient.ILLAGER_BIPED_INNER_ARMOR)),
                new IllagerArmorModel<>(builder.bakeLayer(ArmorfulClient.ILLAGER_BIPED_OUTER_ARMOR)),
                Minecraft.getInstance().getModelManager()));
    }

    @Override
    protected void scale(T entity, PoseStack matrices, float amount) {
        matrices.scale(0.9375F, 0.9375F, 0.9375F);
    }

    @Override
    public void render(T entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn,
                       MultiBufferSource bufferIn, int packedLightIn) {
        this.setModelVisibilities(entityIn);
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    private void setModelVisibilities(T entity) {
        IllagerBipedModel<T> illagerModel = this.getModel();
        ItemStack itemstack = entity.getMainHandItem();
        ItemStack itemstack1 = entity.getOffhandItem();
        HumanoidModel.ArmPose bipedmodel$armpose = this.getArmPose(entity, itemstack, itemstack1,
                InteractionHand.MAIN_HAND);
        HumanoidModel.ArmPose bipedmodel$armpose1 = this.getArmPose(entity, itemstack, itemstack1,
                InteractionHand.OFF_HAND);
        if (entity.getMainArm() == HumanoidArm.RIGHT) {
            illagerModel.rightArmPose = bipedmodel$armpose;
            illagerModel.leftArmPose = bipedmodel$armpose1;
        } else {
            illagerModel.rightArmPose = bipedmodel$armpose1;
            illagerModel.leftArmPose = bipedmodel$armpose;
        }
    }

    private HumanoidModel.ArmPose getArmPose(T entity, ItemStack itemStackMain, ItemStack itemStackOff, InteractionHand handIn) {
        HumanoidModel.ArmPose armPose = HumanoidModel.ArmPose.EMPTY;
        ItemStack mainItemStack = handIn == InteractionHand.MAIN_HAND ? itemStackMain : itemStackOff;

        if (!mainItemStack.isEmpty()) {
            armPose = HumanoidModel.ArmPose.ITEM;
            UseAnim useaction = mainItemStack.getUseAnimation();
            switch (useaction) {
                case BLOCK:
                    armPose = HumanoidModel.ArmPose.BLOCK;
                    break;
                case BOW:
                    armPose = HumanoidModel.ArmPose.BOW_AND_ARROW;
                    break;
                case SPEAR:
                    armPose = HumanoidModel.ArmPose.THROW_SPEAR;
                    break;
                case CROSSBOW:
                    if (handIn == entity.getUsedItemHand()) {
                        armPose = HumanoidModel.ArmPose.CROSSBOW_CHARGE;
                    }
                    break;
                default:
                    armPose = HumanoidModel.ArmPose.EMPTY;
                    break;
            }
        } else {
            boolean hasCrossbowMain = itemStackMain.getItem() instanceof CrossbowItem;
            boolean hasCrossbowOff = itemStackOff.getItem() instanceof CrossbowItem;
            if (hasCrossbowMain) {
                armPose = HumanoidModel.ArmPose.CROSSBOW_HOLD;
            }

            if (hasCrossbowOff && itemStackMain.getItem().getUseAnimation(itemStackMain) == UseAnim.NONE) {
                armPose = HumanoidModel.ArmPose.CROSSBOW_HOLD;
            }
        }
        return armPose;
    }
}
