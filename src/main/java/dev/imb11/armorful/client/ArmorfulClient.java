package dev.imb11.armorful.client;

import dev.imb11.armorful.client.model.IllagerArmorModel;
import dev.imb11.armorful.client.model.IllagerBipedModel;
import dev.imb11.armorful.client.model.WitchBipedModel;
import dev.imb11.armorful.client.renderer.*;
import dev.imb11.armorful.client.renderer.layers.VexArmorLayer;
import dev.imb11.armorful.util.ArmorfulUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.model.VexModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Vex;

public class ArmorfulClient implements ClientModInitializer {
    public static final ModelLayerLocation ILLAGER_BIPED = ArmorfulUtil.registerEntityModelLayer("illager_biped", IllagerBipedModel::createBodyLayer);
    public static final ModelLayerLocation ILLAGER_BIPED_OUTER_ARMOR = ArmorfulUtil.registerEntityModelLayer("illager_biped_outer_armor", IllagerArmorModel::createOuterArmorLayer);
    public static final ModelLayerLocation ILLAGER_BIPED_INNER_ARMOR = ArmorfulUtil.registerEntityModelLayer("illager_biped_inner_armor", IllagerArmorModel::createInnerArmorLayer);
    public static ModelLayerLocation WITCH_ARMOR_LAYER = ArmorfulUtil.registerEntityModelLayer("witch_biped", WitchBipedModel::createBodyModel);
//    public static ModelLayerLocation VEX_BIPED = ArmorfulUtil.registerEntityModelLayer("vex_biped", VexModel::createBodyLayer);
//    public static ModelLayerLocation VEX_ARMOR_OUTER_LAYER = new ModelLayerLocation(ArmorfulUtil.id("vex_armor_outer"), "vex_armor_outer");
//    public static ModelLayerLocation VEX_ARMOR_INNER_LAYER = new ModelLayerLocation(ArmorfulUtil.id("vex_armor_inner"), "vex_armor_inner");
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(EntityType.PILLAGER, PillagerBipedRenderer::new);
        EntityRendererRegistry.register(EntityType.EVOKER, EvokerBipedRenderer::new);
        EntityRendererRegistry.register(EntityType.VINDICATOR, VindicatorBipedRenderer::new);
        EntityRendererRegistry.register(EntityType.ILLUSIONER, IllusionerBipedRenderer::new);
        EntityRendererRegistry.register(EntityType.WITCH, WitchBipedRenderer::new);

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if(entityType == EntityType.VEX) {
                registrationHelper.register(new VexArmorLayer((RenderLayerParent<Vex, VexModel>) entityRenderer, context.getModelSet()));
            }
        });
    }
}
