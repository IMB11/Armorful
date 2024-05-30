package dev.imb11.armorful.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.imb11.armorful.client.ArmorfulClient;
import dev.imb11.armorful.client.model.IllagerArmorModel;
import dev.imb11.armorful.client.model.WitchBipedModel;
import dev.imb11.armorful.client.renderer.layers.WitchBipedItemLayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Witch;

public class WitchBipedRenderer<T extends Witch> extends MobRenderer<T, WitchBipedModel<T>> {
    private static final ResourceLocation WITCH_LOCATION = new ResourceLocation("textures/entity/witch.png");

    public WitchBipedRenderer(EntityRendererProvider.Context builder) {
        super(builder, new WitchBipedModel(builder.bakeLayer(ArmorfulClient.WITCH_ARMOR_LAYER)), 0.5F);
        this.addLayer(new WitchBipedItemLayer<>(this, builder.getItemInHandRenderer()));
        this.addLayer(new CustomHeadLayer<>(this, builder.getModelSet(), builder.getItemInHandRenderer()));
        this.addLayer(new ElytraLayer<>(this, builder.getModelSet()));
        this.addLayer(new HumanoidArmorLayer(this,
                new IllagerArmorModel<>(builder.bakeLayer(ArmorfulClient.ILLAGER_BIPED_INNER_ARMOR)),
                new IllagerArmorModel<>(builder.bakeLayer(ArmorfulClient.ILLAGER_BIPED_OUTER_ARMOR)), builder.getModelManager()));
    }

    @Override
    public void render(T pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        this.model.setHoldingItem(!pEntity.getMainHandItem().isEmpty());
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(T pEntity) {
        return WITCH_LOCATION;
    }

    @Override
    protected void scale(T pLivingEntity, PoseStack pMatrixStack, float pPartialTickTime) {
        float f = 0.9375F;
        pMatrixStack.scale(0.9375F, 0.9375F, 0.9375F);
    }
}