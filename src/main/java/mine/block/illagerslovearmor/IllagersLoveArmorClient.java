package mine.block.illagerslovearmor;

import mine.block.illagerslovearmor.client.model.IllagerArmorModel;
import mine.block.illagerslovearmor.client.model.IllagerBipedModel;
import mine.block.illagerslovearmor.client.renderer.EvokerBipedRenderer;
import mine.block.illagerslovearmor.client.renderer.IllusionerBipedRenderer;
import mine.block.illagerslovearmor.client.renderer.PillagerBipedRenderer;
import mine.block.illagerslovearmor.client.renderer.VindicatorBipedRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;

import static mine.block.illagerslovearmor.IllagersLoveArmor.MODID;

@Environment(EnvType.CLIENT)
public class IllagersLoveArmorClient implements ClientModInitializer {
    public static EntityModelLayer BIPEDILLAGER = new EntityModelLayer(
            new Identifier(MODID + "illagerbiped"), "illagerbiped");
    public static EntityModelLayer BIPEDILLAGER_ARMOR_OUTER_LAYER = new EntityModelLayer(
            new Identifier(MODID + "illagerbiped_outerarmor"), "illagerbiped_outerarmor");
    public static EntityModelLayer BIPEDILLAGER_ARMOR_INNER_LAYER = new EntityModelLayer(
            new Identifier(MODID + "illagerbiped_innerarmor"), "illagerbiped_innerarmor");
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
