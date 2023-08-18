package com.mineblock11.armorful.client.illagers.renderer;

import com.mineblock11.armorful.client.illagers.IllagerArmorClient;
import com.mineblock11.armorful.client.illagers.model.IllagerArmorModel;
import com.mineblock11.armorful.client.illagers.model.IllagerBipedModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.UseAction;

public abstract class IllagerBipedRenderer<T extends IllagerEntity> extends MobEntityRenderer<T, IllagerBipedModel<T>> {
    public IllagerBipedRenderer(EntityRendererFactory.Context builder) {
        super(builder, new IllagerBipedModel<>(builder.getPart(IllagerArmorClient.ILLAGER_BIPED)), 0.5F);
        this.addFeature(new HeadFeatureRenderer<>(this, builder.getModelLoader(), builder.getHeldItemRenderer()));
        this.addFeature(new ElytraFeatureRenderer<>(this, builder.getModelLoader()));
        this.addFeature(new ArmorFeatureRenderer<>(this,
                new IllagerArmorModel<>(builder.getPart(IllagerArmorClient.ILLAGER_BIPED_INNER_ARMOR)),
                new IllagerArmorModel<>(builder.getPart(IllagerArmorClient.ILLAGER_BIPED_OUTER_ARMOR))
                ));
    }

    @Override
    protected void scale(T entity, MatrixStack matrices, float amount) {
        matrices.scale(0.9375F, 0.9375F, 0.9375F);
    }

    @Override
    public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
                       VertexConsumerProvider bufferIn, int packedLightIn) {
        this.setModelVisibilities(entityIn);
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    private void setModelVisibilities(T entity) {
        IllagerBipedModel<T> illagerModel = this.getModel();
        ItemStack itemstack = entity.getMainHandStack();
        ItemStack itemstack1 = entity.getOffHandStack();
        BipedEntityModel.ArmPose bipedmodel$armpose = this.getArmPose(entity, itemstack, itemstack1,
                Hand.MAIN_HAND);
        BipedEntityModel.ArmPose bipedmodel$armpose1 = this.getArmPose(entity, itemstack, itemstack1,
                Hand.OFF_HAND);
        if (entity.getMainArm() == Arm.RIGHT) {
            illagerModel.rightArmPose = bipedmodel$armpose;
            illagerModel.leftArmPose = bipedmodel$armpose1;
        } else {
            illagerModel.rightArmPose = bipedmodel$armpose1;
            illagerModel.leftArmPose = bipedmodel$armpose;
        }
    }

    private BipedEntityModel.ArmPose getArmPose(T entity, ItemStack itemStackMain, ItemStack itemStackOff, Hand handIn) {
        BipedEntityModel.ArmPose armPose = BipedEntityModel.ArmPose.EMPTY;
        ItemStack mainItemStack = handIn == Hand.MAIN_HAND ? itemStackMain : itemStackOff;

        if (!mainItemStack.isEmpty()) {
            armPose = BipedEntityModel.ArmPose.ITEM;
            UseAction useaction = mainItemStack.getUseAction();
            switch (useaction) {
                case BLOCK:
                    armPose = BipedEntityModel.ArmPose.BLOCK;
                    break;
                case BOW:
                    armPose = BipedEntityModel.ArmPose.BOW_AND_ARROW;
                    break;
                case SPEAR:
                    armPose = BipedEntityModel.ArmPose.THROW_SPEAR;
                    break;
                case CROSSBOW:
                    if (handIn == entity.getActiveHand()) {
                        armPose = BipedEntityModel.ArmPose.CROSSBOW_CHARGE;
                    }
                    break;
                default:
                    armPose = BipedEntityModel.ArmPose.EMPTY;
                    break;
            }
        } else {
            boolean hasCrossbowMain = itemStackMain.getItem() instanceof CrossbowItem;
            boolean hasCrossbowOff = itemStackOff.getItem() instanceof CrossbowItem;
            if (hasCrossbowMain) {
                armPose = BipedEntityModel.ArmPose.CROSSBOW_HOLD;
            }

            if (hasCrossbowOff && itemStackMain.getItem().getUseAction(itemStackMain) == UseAction.NONE) {
                armPose = BipedEntityModel.ArmPose.CROSSBOW_HOLD;
            }
        }
        return armPose;
    }
}
