// TODO: PORT
//package tallestegg.illagersweararmor.client.renderer.layers;
//
//import com.google.common.collect.Maps;
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import net.minecraft.client.model.HumanoidModel;
//import net.minecraft.client.model.VexModel;
//import net.minecraft.client.model.geom.EntityModelSet;
//import net.minecraft.client.model.geom.ModelLayers;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.RenderType;
//import net.minecraft.client.renderer.entity.ItemRenderer;
//import net.minecraft.client.renderer.entity.RenderLayerParent;
//import net.minecraft.client.renderer.entity.layers.RenderLayer;
//import net.minecraft.client.renderer.texture.OverlayTexture;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.entity.EquipmentSlot;
//import net.minecraft.world.entity.monster.Vex;
//import net.minecraft.world.item.ArmorItem;
//import net.minecraft.world.item.ItemStack;
//
//import javax.annotation.Nullable;
//import java.util.Map;
//
//public class VexArmorLayer extends RenderLayer<Vex, VexModel> {
//    private static final Map<String, ResourceLocation> ARMOR_LOCATION_CACHE = Maps.newHashMap();
//    private final HumanoidModel innerModel;
//    private final HumanoidModel outerModel;
//
//    public VexArmorLayer(RenderLayerParent<Vex, VexModel> pRenderer, EntityModelSet modelSets) {
//        super(pRenderer);
//        this.innerModel = new HumanoidModel<>(modelSets.bakeLayer(ModelLayers.ARMOR_STAND_INNER_ARMOR));
//        this.outerModel = new HumanoidModel<>(modelSets.bakeLayer(ModelLayers.ARMOR_STAND_OUTER_ARMOR));
//    }
//
//    public void copyPropertiesTo(HumanoidModel pModel) {
//        pModel.head.copyFrom(this.getParentModel().root().getChild("head"));
//        //  pModel.hat.copyFrom(this.getParentModel().root().getChild("head"));
//        pModel.body.copyFrom(this.getParentModel().root().getChild("body"));
//        pModel.rightArm.copyFrom(this.getParentModel().root().getChild("body").getChild("right_arm"));
//        pModel.leftArm.copyFrom(this.getParentModel().root().getChild("body").getChild("left_arm"));
//        pModel.rightLeg.copyFrom(this.getParentModel().root().getChild("body"));
//        pModel.leftLeg.copyFrom(this.getParentModel().root().getChild("body"));
//    }
//
//    @Override
//    public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, Vex pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
//        this.copyPropertiesTo(innerModel);
//        this.copyPropertiesTo(outerModel);
//        this.renderArmorPiece(pMatrixStack, pBuffer, pLivingEntity, EquipmentSlot.CHEST, pPackedLight, this.getArmorModel(EquipmentSlot.CHEST));
//        this.renderArmorPiece(pMatrixStack, pBuffer, pLivingEntity, EquipmentSlot.LEGS, pPackedLight, this.getArmorModel(EquipmentSlot.LEGS));
//        this.renderArmorPiece(pMatrixStack, pBuffer, pLivingEntity, EquipmentSlot.FEET, pPackedLight, this.getArmorModel(EquipmentSlot.FEET));
//        this.renderArmorPiece(pMatrixStack, pBuffer, pLivingEntity, EquipmentSlot.HEAD, pPackedLight, this.getArmorModel(EquipmentSlot.HEAD));
//    }
//
//    private void renderArmorPiece(PoseStack pPoseStack, MultiBufferSource pBuffer, Vex pLivingEntity, EquipmentSlot pSlot, int p_117123_, HumanoidModel pModel) {
//        ItemStack itemstack = pLivingEntity.getItemBySlot(pSlot);
//        if (itemstack.getItem() instanceof ArmorItem) {
//            ArmorItem armoritem = (ArmorItem) itemstack.getItem();
//            if (armoritem.getEquipmentSlot() == pSlot) {
//                this.setPartVisibility(pModel, pSlot);
//                net.minecraft.client.model.Model model = getArmorModelHook(pLivingEntity, itemstack, pSlot, pModel);
//                boolean flag = this.usesInnerModel(pSlot);
//                boolean flag1 = itemstack.hasFoil();
//                if (armoritem instanceof net.minecraft.world.item.DyeableLeatherItem) {
//                    int i = ((net.minecraft.world.item.DyeableLeatherItem) armoritem).getColor(itemstack);
//                    float f = (float) (i >> 16 & 255) / 255.0F;
//                    float f1 = (float) (i >> 8 & 255) / 255.0F;
//                    float f2 = (float) (i & 255) / 255.0F;
//                    this.renderModel(pPoseStack, pBuffer, p_117123_, flag1, model, f, f1, f2, this.getArmorResource(pLivingEntity, itemstack, pSlot, null));
//                    this.renderModel(pPoseStack, pBuffer, p_117123_, flag1, model, 1.0F, 1.0F, 1.0F, this.getArmorResource(pLivingEntity, itemstack, pSlot, "overlay"));
//                } else {
//                    this.renderModel(pPoseStack, pBuffer, p_117123_, flag1, model, 1.0F, 1.0F, 1.0F, this.getArmorResource(pLivingEntity, itemstack, pSlot, null));
//                }
//            }
//            if (armoritem.getEquipmentSlot() == EquipmentSlot.CHEST) {
//                pPoseStack.pushPose();
//                pPoseStack.popPose();
//            }
//        }
//    }
//
//    protected void setPartVisibility(HumanoidModel pModel, EquipmentSlot pSlot) {
//        pModel.setAllVisible(false);
//        switch (pSlot) {
//            case HEAD:
//                pModel.head.visible = true;
//                pModel.hat.visible = true;
//                pModel.head.xScale = 0.8F;
//                pModel.head.yScale = 0.8F;
//                pModel.head.zScale = 0.8F;
//                pModel.head.y -= 13.0F;
//                break;
//            case CHEST:
//                pModel.body.visible = true;
//                pModel.rightArm.visible = true;
//                pModel.leftArm.visible = true;
//                pModel.rightArm.y -= -11.5F;
//                pModel.rightArm.z += 0.25F;
//                pModel.rightArm.x -= 3.0F;
//                pModel.rightArm.xScale = 0.8F;
//                pModel.rightArm.yScale = 0.8F;
//                pModel.rightArm.zScale = 0.8F;
//                pModel.leftArm.xScale = 0.8F;
//                pModel.leftArm.yScale = 0.8F;
//                pModel.leftArm.zScale = 0.8F;
//                pModel.leftArm.y -= -11.5F;
//                pModel.leftArm.z += 0.25F;
//                pModel.leftArm.x -= -3.0F;
//                pModel.body.xScale = 0.8F;
//                pModel.body.yScale = 0.8F;
//                pModel.body.zScale = 0.8F;
//                pModel.body.y -= 9.0F;
//                break;
//            case LEGS:
//                pModel.body.visible = true;
//                pModel.rightLeg.visible = true;
//                pModel.leftLeg.visible = true;
//                pModel.body.y -= 6.0F;
//                pModel.body.z -= -0.6F;
//                pModel.body.xScale = 0.6F;
//                pModel.body.yScale = 0.6F;
//                pModel.body.zScale = 0.75F;
//                pModel.rightLeg.xScale = 0.6F;
//                pModel.rightLeg.yScale = 0.6F;
//                pModel.rightLeg.zScale = 0.7F;
//                pModel.rightLeg.y = 20.0F;
//                pModel.rightLeg.x = -1.5F;
//                pModel.rightLeg.z = 1.5F;
//                pModel.leftLeg.xScale = 0.6F;
//                pModel.leftLeg.yScale = 0.6F;
//                pModel.leftLeg.zScale = 0.7F;
//                pModel.leftLeg.y = 20.0F;
//                pModel.leftLeg.x = 1.5F;
//                pModel.leftLeg.z = 1.5F;
//                break;
//            case FEET:
//                pModel.rightLeg.visible = true;
//                pModel.leftLeg.visible = true;
//                pModel.rightLeg.y = 20.0F;
//                pModel.rightLeg.xScale = 0.6F;
//                pModel.rightLeg.yScale = 0.6F;
//                pModel.rightLeg.zScale = 0.7F;
//                pModel.rightLeg.x = -1.5F;
//                pModel.rightLeg.z = 1.5F;
//                pModel.leftLeg.xScale = 0.6F;
//                pModel.leftLeg.yScale = 0.6F;
//                pModel.leftLeg.zScale = 0.7F;
//                pModel.leftLeg.y = 20.0F;
//                pModel.leftLeg.x = 1.5F;
//                pModel.leftLeg.z = 1.5F;
//        }
//
//    }
//
//    private void renderModel(PoseStack pPoseStack, MultiBufferSource pBuffer, int p_117109_, ArmorItem p_117110_, boolean p_117111_, HumanoidModel pModel, boolean p_117113_, float p_117114_, float p_117115_, float p_117116_, @Nullable String p_117117_) {
//        renderModel(pPoseStack, pBuffer, p_117109_, p_117111_, pModel, p_117114_, p_117115_, p_117116_, this.getArmorLocation(p_117110_, p_117113_, p_117117_));
//    }
//
//    private void renderModel(PoseStack pPoseStack, MultiBufferSource pBuffer, int p_117109_, boolean p_117111_, net.minecraft.client.model.Model pModel, float p_117114_, float p_117115_, float p_117116_, ResourceLocation armorResource) {
//        VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(pBuffer, RenderType.armorCutoutNoCull(armorResource), false, p_117111_);
//        pModel.renderToBuffer(pPoseStack, vertexconsumer, p_117109_, OverlayTexture.NO_OVERLAY, p_117114_, p_117115_, p_117116_, 1.0F);
//    }
//
//    private HumanoidModel getArmorModel(EquipmentSlot pSlot) {
//        return this.usesInnerModel(pSlot) ? this.innerModel : this.outerModel;
//    }
//
//    private boolean usesInnerModel(EquipmentSlot pSlot) {
//        return pSlot == EquipmentSlot.LEGS;
//    }
//
////    /*=================================== FORGE START =========================================*/
////
////    /**
////     * Hook to allow item-sensitive armor model. for HumanoidArmorLayer.
////     */
////    protected net.minecraft.client.model.Model getArmorModelHook(Vex entity, ItemStack itemStack, EquipmentSlot slot, HumanoidModel model) {
////        return net.minecraftforge.client.ForgeHooksClient.getArmorModel(entity, itemStack, slot, model);
////    }
////
////    /**
////     * More generic ForgeHook version of the above function, it allows for Items to have more control over what texture they provide.
////     *
////     * @param entity Entity wearing the armor
////     * @param stack  ItemStack for the armor
////     * @param slot   Slot ID that the item is in
////     * @param type   Subtype, can be null or "overlay"
////     * @return ResourceLocation pointing at the armor's texture
////     */
////    public ResourceLocation getArmorResource(net.minecraft.world.entity.Entity entity, ItemStack stack, EquipmentSlot slot, @Nullable String type) {
////        ArmorItem item = (ArmorItem) stack.getItem();
////        String texture = item.getMaterial().getName();
////        String domain = "minecraft";
////        int idx = texture.indexOf(':');
////        if (idx != -1) {
////            domain = texture.substring(0, idx);
////            texture = texture.substring(idx + 1);
////        }
////        String s1 = String.format(java.util.Locale.ROOT, "%s:textures/models/armor/%s_layer_%d%s.png", domain, texture, (usesInnerModel(slot) ? 2 : 1), type == null ? "" : String.format(java.util.Locale.ROOT, "_%s", type));
////
////        s1 = net.minecraftforge.client.ForgeHooksClient.getArmorTexture(entity, stack, s1, slot, type);
////        ResourceLocation resourcelocation = ARMOR_LOCATION_CACHE.get(s1);
////
////        if (resourcelocation == null) {
////            resourcelocation = new ResourceLocation(s1);
////            ARMOR_LOCATION_CACHE.put(s1, resourcelocation);
////        }
////
////        return resourcelocation;
////    }
////    /*=================================== FORGE END ===========================================*/
//}