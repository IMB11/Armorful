package com.mineblock11.armorful.client.illagers.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.math.MathHelper;

public class IllagerBipedModel<T extends IllagerEntity> extends BipedEntityModel<T> {
    public ModelPart nose;
    public ModelPart jacket;
    public ModelPart arms;

    public IllagerBipedModel(ModelPart part) {
        super(part);

        this.jacket = this.body.getChild("jacket");
        this.nose = this.head.getChild("nose");
        this.arms = part.getChild("arms");

        this.hat.visible = false;
    }

    public static TexturedModelData createBodyLayer() {
        ModelData modelData = BipedEntityModel.getModelData(Dilation.NONE, 0.0F);
        ModelPartData model = modelData.getRoot();

        ModelPartData headModel = model.addChild("head",
                ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        headModel.addChild("hat", ModelPartBuilder.create().uv(32, 0).cuboid(-4.0F, -10.0F, -4.0F,
                8.0F, 12.0F, 8.0F, new Dilation(0.45F)), ModelTransform.NONE);

        headModel.addChild("nose",
                ModelPartBuilder.create().uv(24, 0).cuboid(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F),
                ModelTransform.pivot(0.0F, -2.0F, 0.0F));

        ModelPartData bodyModel = model.addChild("body",
                ModelPartBuilder.create().uv(16, 20).cuboid(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        bodyModel.addChild("jacket", ModelPartBuilder.create().uv(0, 38).cuboid(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F,
                6.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData armsModel = model.addChild("arms",
                ModelPartBuilder.create().uv(44, 22).cuboid(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F).uv(40, 38)
                        .cuboid(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F),
                ModelTransform.of(0.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F));

        armsModel.addChild("left_shoulder",
                ModelPartBuilder.create().uv(44, 22).mirrored().cuboid(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F),
                ModelTransform.NONE);

        model.addChild("right_leg",
                ModelPartBuilder.create().uv(0, 22).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                ModelTransform.pivot(-2.0F, 12.0F, 0.0F));

        model.addChild("left_leg",
                ModelPartBuilder.create().uv(0, 22).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                ModelTransform.pivot(2.0F, 12.0F, 0.0F));

        model.addChild("right_arm",
                ModelPartBuilder.create().uv(40, 46).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                ModelTransform.pivot(-5.0F, 2.0F, 0.0F));

        model.addChild("left_arm",
                ModelPartBuilder.create().uv(40, 46).mirrored().cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                ModelTransform.pivot(5.0F, 2.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return Iterables.concat(super.getBodyParts(), ImmutableList.of(this.arms, this.jacket));
    }

    @Override
    public void setAngles(T illagerEntity, float f, float g, float h, float i,
                          float j) {
        super.setAngles(illagerEntity, f, g, h, i, j);

        IllagerEntity.State currentArmPose = illagerEntity.getState();
        boolean isArmsCrossed = currentArmPose == IllagerEntity.State.CROSSED;

        this.arms.visible = isArmsCrossed;
        this.leftArm.visible = !isArmsCrossed;
        this.rightArm.visible = !isArmsCrossed;

        if (isArmsCrossed) {
            this.leftArm.pivotY = 3.0F;
            this.leftArm.pivotZ = -1.0F;
            this.leftArm.pitch = -0.75F;
            this.rightArm.pivotY = 3.0F;
            this.rightArm.pivotZ = -1.0F;
            this.rightArm.pitch = -0.75F;
        }

        this.jacket.copyTransform(this.body);

        boolean isWearingChestplateOrLeggings = illagerEntity.getEquippedStack(EquipmentSlot.CHEST)
                .getItem() instanceof ArmorItem
                || illagerEntity.getEquippedStack(EquipmentSlot.LEGS).getItem() instanceof ArmorItem;

        this.jacket.visible = !isWearingChestplateOrLeggings;
        this.arms.pivotY = 3.0F;
        this.arms.pivotZ = -1.0F;
        this.arms.pitch = -0.75F;

        switch (currentArmPose) {
            case ATTACKING:
                if (illagerEntity.getMainHandStack().isEmpty()) {
                    CrossbowPosing.meleeAttack(this.leftArm, this.rightArm, true, this.handSwingProgress, h);
                } else {
                    CrossbowPosing.meleeAttack(this.rightArm, this.leftArm, illagerEntity, this.handSwingProgress, h);
                }
                break;
            case SPELLCASTING:
                this.rightArm.pivotZ = 0.0F;
                this.rightArm.pivotX = -5.0F;
                this.leftArm.pivotZ = 0.0F;
                this.leftArm.pivotX = 5.0F;
                this.rightArm.pitch = MathHelper.cos(h * 0.6662F) * 0.25F;
                this.leftArm.pitch = MathHelper.cos(h * 0.6662F) * 0.25F;
                this.rightArm.roll = 2.3561945F;
                this.leftArm.roll = -2.3561945F;
                this.rightArm.yaw = 0.0F;
                this.leftArm.yaw = 0.0F;
                break;
            case CELEBRATING:
                this.rightArm.pivotZ = 0.0F;
                this.rightArm.pivotX = -5.0F;
                this.rightArm.pitch = MathHelper.cos(h * 0.6662F) * 0.05F;
                this.rightArm.roll = 2.670354F;
                this.rightArm.yaw = 0.0F;
                this.leftArm.pivotZ = 0.0F;
                this.leftArm.pivotX = 5.0F;
                this.leftArm.pitch = MathHelper.cos(h * 0.6662F) * 0.05F;
                this.leftArm.roll = -2.3561945F;
                this.leftArm.yaw = 0.0F;
                break;
            default:
                break;
        }
    }
}
