package dev.imb11.armorful.client.renderer;

import dev.imb11.armorful.util.ArmorfulUtil;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Pillager;
import org.jetbrains.annotations.NotNull;

public class PillagerBipedRenderer extends IllagerBipedRenderer<Pillager> {
    private static final ResourceLocation PILLAGER = ArmorfulUtil.defaultID("textures/entity/illager/pillager.png");

    public PillagerBipedRenderer(Context builder) {
        super(builder);
        this.addLayer(new ItemInHandLayer<>(this, builder.getItemInHandRenderer()));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Pillager entity) {
        return PILLAGER;
    }
}
