package dev.imb11.armorful.client.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.item.ArmorItem;
import org.jetbrains.annotations.NotNull;

public class WitchBipedModel<T extends Witch> extends HumanoidModel<T> {
    public final ModelPart nose;
    public final ModelPart arms;
    public final ModelPart jacket;
    public final ModelPart witchHat;
    private boolean holdingItem;

    public WitchBipedModel(ModelPart pRoot) {
        super(pRoot);
        this.leftArm.visible = false;
        this.rightArm.visible = false;
        this.nose = this.head.getChild("nose");
        this.arms = pRoot.getChild("arms");
        this.jacket = this.body.getChild("jacket");
        this.witchHat = this.head.getChild("witchHat");
    }

    public static LayerDefinition createBodyModel() {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition partdefinition1 = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F), PartPose.ZERO);
        PartDefinition partdefinition2 = partdefinition1.addOrReplaceChild("witchHat", CubeListBuilder.create().texOffs(0, 64).addBox(0.0F, 0.0F, 0.0F, 10.0F, 2.0F, 10.0F), PartPose.offset(-5.0F, -10.03125F, -5.0F));
        PartDefinition partdefinition3 = partdefinition2.addOrReplaceChild("hat2", CubeListBuilder.create().texOffs(0, 76).addBox(0.0F, 0.0F, 0.0F, 7.0F, 4.0F, 7.0F), PartPose.offsetAndRotation(1.75F, -4.0F, 2.0F, -0.05235988F, 0.0F, 0.02617994F));
        PartDefinition partdefinition4 = partdefinition3.addOrReplaceChild("hat3", CubeListBuilder.create().texOffs(0, 87).addBox(0.0F, 0.0F, 0.0F, 4.0F, 4.0F, 4.0F), PartPose.offsetAndRotation(1.75F, -4.0F, 2.0F, -0.10471976F, 0.0F, 0.05235988F));
        partdefinition4.addOrReplaceChild("hat4", CubeListBuilder.create().texOffs(0, 95).addBox(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(1.75F, -2.0F, 2.0F, -0.20943952F, 0.0F, 0.10471976F));
        partdefinition2.addOrReplaceChild("hat_rim", CubeListBuilder.create().texOffs(30, 47).addBox(-8.0F, -8.0F, -6.0F, 16.0F, 16.0F, 1.0F), PartPose.rotation((-(float) Math.PI / 2F), 0.0F, 0.0F));
        partdefinition1.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F), PartPose.offset(0.0F, -2.0F, 0.0F));
        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F), PartPose.ZERO);
        body.addOrReplaceChild("jacket", CubeListBuilder.create().texOffs(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 20.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.ZERO);
        partdefinition.addOrReplaceChild("arms", CubeListBuilder.create().texOffs(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F).texOffs(44, 22)
                        .addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, true)
                        .texOffs(40, 38)
                        .addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-2.0F, 12.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(2.0F, 12.0F, 0.0F));
        PartDefinition partdefinition5 = partdefinition1.getChild("nose");
        partdefinition5.addOrReplaceChild("mole", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 3.0F, -6.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, -2.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 64, 128);
    }

    @Override
    protected @NotNull Iterable<ModelPart> bodyParts() {
        return Iterables.concat(super.bodyParts(), ImmutableList.of(this.arms, this.body));
    }

    @Override
    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        this.nose.setPos(0.0F, -2.0F, 0.0F);
        float f = 0.01F * (float) (pEntity.getId() % 10);
        this.nose.xRot = Mth.sin((float) pEntity.tickCount * f) * 4.5F * ((float) Math.PI / 180F);
        this.nose.yRot = 0.0F;
        this.nose.zRot = Mth.cos((float) pEntity.tickCount * f) * 2.5F * ((float) Math.PI / 180F);
        if (this.holdingItem) {
            this.nose.setPos(0.0F, 1.0F, -1.5F);
            this.nose.xRot = -0.9F;
        }
        super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
        this.leftArm.y = 3.0F;
        this.leftArm.z = -1.0F;
        this.leftArm.xRot = -0.75F;
        this.rightArm.y = 3.0F;
        this.rightArm.z = -1.0F;
        this.rightArm.xRot = -0.75F;
        boolean isWearingChestplateOrLeggings = pEntity.getItemBySlot(EquipmentSlot.CHEST)
                .getItem() instanceof ArmorItem
                || pEntity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof ArmorItem;
        this.jacket.visible = !isWearingChestplateOrLeggings;
        if (pEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ArmorItem) {
            this.witchHat.y = -13.0F;
        } else {
            this.witchHat.resetPose();
        }
    }

    public void setHoldingItem(boolean pHoldingItem) {
        this.holdingItem = pHoldingItem;
    }
}