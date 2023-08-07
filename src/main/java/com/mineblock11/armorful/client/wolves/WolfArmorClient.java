package com.mineblock11.armorful.client.wolves;

import com.mineblock11.armorful.client.wolves.model.WolfArmorModel;
import com.mineblock11.armorful.util.ArmorfulUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.registry.Registries;

public class WolfArmorClient implements ClientModInitializer {
    public static final EntityModelLayer WOLF_ARMOR = ArmorfulUtil.registerEntityModelLayer("wolf_armor", () -> WolfArmorModel.getTexturedModelData(new Dilation(0.35f)));

    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.ITEM.register(new LeatherHueProvider(), Registries.ITEM.get(ArmorfulUtil.id("leather_wolf_armor")));
    }
}
