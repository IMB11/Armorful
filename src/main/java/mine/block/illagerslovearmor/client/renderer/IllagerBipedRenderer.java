package mine.block.illagerslovearmor.client.renderer;

import mine.block.illagerslovearmor.IllagersLoveArmorClient;
import mine.block.illagerslovearmor.client.model.IllagerArmorModel;
import mine.block.illagerslovearmor.client.model.IllagerBipedModel;
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
        super(builder, new IllagerBipedModel<>(builder.getPart(IllagersLoveArmorClient.BIPEDILLAGER)), 0.5F);
        this.addFeature(new HeadFeatureRenderer<>(this, builder.getModelLoader(), builder.getHeldItemRenderer()));
        this.addFeature(new ElytraFeatureRenderer<>(this, builder.getModelLoader()));
        this.addFeature(new ArmorFeatureRenderer<>(this,
                new IllagerArmorModel<>(builder.getPart(IllagersLoveArmorClient.BIPEDILLAGER_ARMOR_INNER_LAYER)),
                new IllagerArmorModel<>(builder.getPart(IllagersLoveArmorClient.BIPEDILLAGER_ARMOR_OUTER_LAYER)),
                MinecraftClient.getInstance().getBakedModelManager()));
    }

    @Override
    protected void scale(T p_114919_, MatrixStack p_114920_, float p_114921_) {
        p_114920_.scale(0.9375F, 0.9375F, 0.9375F);
    }

    @Override
    public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
            VertexConsumerProvider bufferIn, int packedLightIn) {
        this.setModelVisibilities(entityIn);
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    private void setModelVisibilities(T entityIn) {
        IllagerBipedModel<T> illagerModel = this.getModel();
        ItemStack itemstack = entityIn.getMainHandStack();
        ItemStack itemstack1 = entityIn.getOffHandStack();
        BipedEntityModel.ArmPose bipedmodel$armpose = this.getArmPose(entityIn, itemstack, itemstack1,
                Hand.MAIN_HAND);
        BipedEntityModel.ArmPose bipedmodel$armpose1 = this.getArmPose(entityIn, itemstack, itemstack1,
                Hand.OFF_HAND);
        if (entityIn.getMainArm() == Arm.RIGHT) {
            illagerModel.rightArmPose = bipedmodel$armpose;
            illagerModel.leftArmPose = bipedmodel$armpose1;
        } else {
            illagerModel.rightArmPose = bipedmodel$armpose1;
            illagerModel.leftArmPose = bipedmodel$armpose;
        }
    }

    private BipedEntityModel.ArmPose getArmPose(T entityIn, ItemStack itemStackMain, ItemStack itemStackOff,
            Hand handIn) {
        BipedEntityModel.ArmPose bipedmodel$armpose = BipedEntityModel.ArmPose.EMPTY;
        ItemStack itemstack = handIn == Hand.MAIN_HAND ? itemStackMain : itemStackOff;
        if (!itemstack.isEmpty()) {
            bipedmodel$armpose = BipedEntityModel.ArmPose.ITEM;
            UseAction useaction = itemstack.getUseAction();
            switch (useaction) {
            case BLOCK:
                bipedmodel$armpose = BipedEntityModel.ArmPose.BLOCK;
                break;
            case BOW:
                bipedmodel$armpose = BipedEntityModel.ArmPose.BOW_AND_ARROW;
                break;
            case SPEAR:
                bipedmodel$armpose = BipedEntityModel.ArmPose.THROW_SPEAR;
                break;
            case CROSSBOW:
                if (handIn == entityIn.getActiveHand()) {
                    bipedmodel$armpose = BipedEntityModel.ArmPose.CROSSBOW_CHARGE;
                }
                break;
            default:
                bipedmodel$armpose = BipedEntityModel.ArmPose.EMPTY;
                break;
            }
        } else {
            boolean flag1 = itemStackMain.getItem() instanceof CrossbowItem;
            boolean flag2 = itemStackOff.getItem() instanceof CrossbowItem;
            if (flag1) {
                bipedmodel$armpose = BipedEntityModel.ArmPose.CROSSBOW_HOLD;
            }

            if (flag2 && itemStackMain.getItem().getUseAction(itemStackMain) == UseAction.NONE) {
                bipedmodel$armpose = BipedEntityModel.ArmPose.CROSSBOW_HOLD;
            }
        }
        return bipedmodel$armpose;
    }
}
