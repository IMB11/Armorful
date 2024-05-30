package dev.imb11.armorful.client;

import dev.imb11.armorful.client.model.IllagerArmorModel;
import dev.imb11.armorful.client.model.IllagerBipedModel;
import dev.imb11.armorful.client.model.WitchBipedModel;
import dev.imb11.armorful.client.renderer.*;
import dev.imb11.armorful.util.ArmorfulUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.world.entity.EntityType;

public class ArmorfulClient implements ClientModInitializer {
    public static final ModelLayerLocation ILLAGER_BIPED = ArmorfulUtil.registerEntityModelLayer("illager_biped", IllagerBipedModel::createBodyLayer);
    public static final ModelLayerLocation ILLAGER_BIPED_OUTER_ARMOR = ArmorfulUtil.registerEntityModelLayer("illager_biped_outer_armor", IllagerArmorModel::createOuterArmorLayer);
    public static final ModelLayerLocation ILLAGER_BIPED_INNER_ARMOR = ArmorfulUtil.registerEntityModelLayer("illager_biped_inner_armor", IllagerArmorModel::createInnerArmorLayer);
    public static ModelLayerLocation WITCH_ARMOR_LAYER = ArmorfulUtil.registerEntityModelLayer("witch_biped", WitchBipedModel::createBodyModel);
    public static ModelLayerLocation VEX_BIPED = ArmorfulUtil.registerEntityModelLayer("vex_biped", IllagerBipedModel::createBodyLayer);
    public static ModelLayerLocation VEX_ARMOR_OUTER_LAYER = ArmorfulUtil.registerEntityModelLayer("vex_outer_armor", IllagerArmorModel::createOuterArmorLayer);
    public static ModelLayerLocation VEX_ARMOR_INNER_LAYER = ArmorfulUtil.registerEntityModelLayer("vex_inner_armor", IllagerArmorModel::createInnerArmorLayer);
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(EntityType.PILLAGER, PillagerBipedRenderer::new);
        EntityRendererRegistry.register(EntityType.EVOKER, EvokerBipedRenderer::new);
        EntityRendererRegistry.register(EntityType.VINDICATOR, VindicatorBipedRenderer::new);
        EntityRendererRegistry.register(EntityType.ILLUSIONER, IllusionerBipedRenderer::new);
        EntityRendererRegistry.register(EntityType.WITCH, WitchBipedRenderer::new);
    }
}
