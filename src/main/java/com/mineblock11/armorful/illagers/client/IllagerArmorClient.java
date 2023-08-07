package com.mineblock11.armorful.illagers.client;

import com.mineblock11.armorful.illagers.client.model.IllagerArmorModel;
import com.mineblock11.armorful.illagers.client.model.IllagerBipedModel;
import com.mineblock11.armorful.illagers.client.renderer.EvokerBipedRenderer;
import com.mineblock11.armorful.illagers.client.renderer.IllusionerBipedRenderer;
import com.mineblock11.armorful.illagers.client.renderer.PillagerBipedRenderer;
import com.mineblock11.armorful.illagers.client.renderer.VindicatorBipedRenderer;
import com.mineblock11.armorful.util.ArmorfulUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.EntityType;

public class IllagerArmorClient implements ClientModInitializer {
    public static final EntityModelLayer ILLAGER_BIPED = ArmorfulUtil.registerEntityModelLayer("illager_biped", IllagerBipedModel::createBodyLayer);
    public static final EntityModelLayer ILLAGER_BIPED_OUTER_ARMOR = ArmorfulUtil.registerEntityModelLayer("illager_biped_outer_armor", IllagerArmorModel::createOuterArmorLayer);
    public static final EntityModelLayer ILLAGER_BIPED_INNER_ARMOR = ArmorfulUtil.registerEntityModelLayer("illager_biped_inner_armor", IllagerArmorModel::createInnerArmorLayer);

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(EntityType.PILLAGER, PillagerBipedRenderer::new);
        EntityRendererRegistry.register(EntityType.EVOKER, EvokerBipedRenderer::new);
        EntityRendererRegistry.register(EntityType.VINDICATOR, VindicatorBipedRenderer::new);
        EntityRendererRegistry.register(EntityType.ILLUSIONER, IllusionerBipedRenderer::new);
    }
}
