package com.mineblock11.illagersweararmor;

import com.mineblock11.illagersweararmor.client.model.IllagerArmorModel;
import com.mineblock11.illagersweararmor.client.model.IllagerBipedModel;
import com.mineblock11.illagersweararmor.client.renderer.EvokerBipedRenderer;
import com.mineblock11.illagersweararmor.client.renderer.IllusionerBipedRenderer;
import com.mineblock11.illagersweararmor.client.renderer.PillagerBipedRenderer;
import com.mineblock11.illagersweararmor.client.renderer.VindicatorBipedRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class IllagersWearArmorClient implements ClientModInitializer {
    public static EntityModelLayer BIPEDILLAGER = new EntityModelLayer(
            new Identifier(IllagersWearArmor.MODID + "illagerbiped"), "illagerbiped");
    public static EntityModelLayer BIPEDILLAGER_ARMOR_OUTER_LAYER = new EntityModelLayer(
            new Identifier(IllagersWearArmor.MODID + "illagerbiped_outerarmor"), "illagerbiped_outerarmor");
    public static EntityModelLayer BIPEDILLAGER_ARMOR_INNER_LAYER = new EntityModelLayer(
            new Identifier(IllagersWearArmor.MODID + "illagerbiped_innerarmor"), "illagerbiped_innerarmor");
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(BIPEDILLAGER, IllagerBipedModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(BIPEDILLAGER_ARMOR_OUTER_LAYER, IllagerArmorModel::createOuterArmorLayer);
        EntityModelLayerRegistry.registerModelLayer(BIPEDILLAGER_ARMOR_INNER_LAYER, IllagerArmorModel::createInnerArmorLayer);

        EntityRendererRegistry.register(EntityType.PILLAGER, PillagerBipedRenderer::new);
        EntityRendererRegistry.register(EntityType.EVOKER, EvokerBipedRenderer::new);
        EntityRendererRegistry.register(EntityType.VINDICATOR, VindicatorBipedRenderer::new);
        EntityRendererRegistry.register(EntityType.ILLUSIONER, IllusionerBipedRenderer::new);
    }
}
