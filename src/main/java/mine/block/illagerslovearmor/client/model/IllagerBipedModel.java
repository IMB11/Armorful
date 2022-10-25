package mine.block.illagerslovearmor.client.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.math.MathHelper;

public class IllagerBipedModel<T extends IllagerEntity> extends BipedEntityModel<T> {
    public ModelPart nose = this.head.getChild("nose");
    public ModelPart jacket = this.body.getChild("jacket");
    public ModelPart arms;

    public IllagerBipedModel(ModelPart part) {
        super(part);
        this.arms = part.getChild("arms");
        this.hat.visible = false;
    }

    public static TexturedModelData createBodyLayer() {
        ModelData meshdefinition = BipedEntityModel.getModelData(Dilation.NONE, 0.0F);
        ModelPartData partdefinition = meshdefinition.getRoot();
        ModelPartData partdefinition1 = partdefinition.addChild("head",
                ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        partdefinition1.addChild("hat", ModelPartBuilder.create().uv(32, 0).cuboid(-4.0F, -10.0F, -4.0F,
                8.0F, 12.0F, 8.0F, new Dilation(0.45F)), ModelTransform.NONE);
        partdefinition1.addChild("nose",
                ModelPartBuilder.create().uv(24, 0).cuboid(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F),
                ModelTransform.pivot(0.0F, -2.0F, 0.0F));
        ModelPartData body = partdefinition.addChild("body",
                ModelPartBuilder.create().uv(16, 20).cuboid(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        body.addChild("jacket", ModelPartBuilder.create().uv(0, 38).cuboid(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F,
                6.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData partdefinition2 = partdefinition.addChild("arms",
                ModelPartBuilder.create().uv(44, 22).cuboid(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F).uv(40, 38)
                        .cuboid(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F),
                ModelTransform.of(0.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F));
        partdefinition2.addChild("left_shoulder",
                ModelPartBuilder.create().uv(44, 22).mirrored().cuboid(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F),
                ModelTransform.NONE);
        partdefinition.addChild("right_leg",
                ModelPartBuilder.create().uv(0, 22).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                ModelTransform.pivot(-2.0F, 12.0F, 0.0F));
        partdefinition.addChild("left_leg",
                ModelPartBuilder.create().uv(0, 22).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                ModelTransform.pivot(2.0F, 12.0F, 0.0F));
        partdefinition.addChild("right_arm",
                ModelPartBuilder.create().uv(40, 46).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                ModelTransform.pivot(-5.0F, 2.0F, 0.0F));
        partdefinition.addChild("left_arm",
                ModelPartBuilder.create().uv(40, 46).mirrored().cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                ModelTransform.pivot(5.0F, 2.0F, 0.0F));
        return TexturedModelData.of(meshdefinition, 64, 64);
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return Iterables.concat(super.getBodyParts(), ImmutableList.of(this.arms, this.jacket));
    }

    @Override
    public void setAngles(T p_102928_, float p_102929_, float p_102930_, float p_102931_, float p_102932_,
            float p_102933_) {
        super.setAngles(p_102928_, p_102929_, p_102930_, p_102931_, p_102932_, p_102933_);
        IllagerEntity.State armpose = p_102928_.getState();
        boolean flag = armpose == IllagerEntity.State.CROSSED;
        this.arms.visible = flag;
        this.leftArm.visible = !flag;
        this.rightArm.visible = !flag;
        if (flag) {
            this.leftArm.pivotY = 3.0F;
            this.leftArm.pivotZ = -1.0F;
            this.leftArm.pitch = -0.75F;
            this.rightArm.pivotY = 3.0F;
            this.rightArm.pivotZ = -1.0F;
            this.rightArm.pitch = -0.75F;
        }
        this.jacket.copyTransform(this.body);
        boolean isWearingChestplateOrLeggings = p_102928_.getEquippedStack(EquipmentSlot.CHEST)
                .getItem() instanceof ArmorItem
                || p_102928_.getEquippedStack(EquipmentSlot.LEGS).getItem() instanceof ArmorItem;
        this.jacket.visible = !isWearingChestplateOrLeggings;
        this.arms.pivotY = 3.0F;
        this.arms.pivotZ = -1.0F;
        this.arms.pitch = -0.75F;
        switch (armpose) {
        case ATTACKING:
            if (p_102928_.getMainHandStack().isEmpty()) {
                CrossbowPosing.meleeAttack(this.leftArm, this.rightArm, true, this.handSwingProgress, p_102931_);
            } else {
                CrossbowPosing.meleeAttack(this.rightArm, this.leftArm, p_102928_, this.handSwingProgress, p_102931_);
            }
            break;
        case SPELLCASTING:
            this.rightArm.pivotZ = 0.0F;
            this.rightArm.pivotX = -5.0F;
            this.leftArm.pivotZ = 0.0F;
            this.leftArm.pivotX = 5.0F;
            this.rightArm.pitch = MathHelper.cos(p_102931_ * 0.6662F) * 0.25F;
            this.leftArm.pitch = MathHelper.cos(p_102931_ * 0.6662F) * 0.25F;
            this.rightArm.roll = 2.3561945F;
            this.leftArm.roll = -2.3561945F;
            this.rightArm.yaw = 0.0F;
            this.leftArm.yaw = 0.0F;
            break;
        case CELEBRATING:
            this.rightArm.pivotZ = 0.0F;
            this.rightArm.pivotX = -5.0F;
            this.rightArm.pitch = MathHelper.cos(p_102931_ * 0.6662F) * 0.05F;
            this.rightArm.roll = 2.670354F;
            this.rightArm.yaw = 0.0F;
            this.leftArm.pivotZ = 0.0F;
            this.leftArm.pivotX = 5.0F;
            this.leftArm.pitch = MathHelper.cos(p_102931_ * 0.6662F) * 0.05F;
            this.leftArm.roll = -2.3561945F;
            this.leftArm.yaw = 0.0F;
            break;
        default:
            break;
        }
    }
}
