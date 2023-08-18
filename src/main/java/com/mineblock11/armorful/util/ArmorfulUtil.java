package com.mineblock11.armorful.util;

import com.mineblock11.armorful.Armorful;
import com.mineblock11.armorful.wolves.WolfDataAccessor;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ArmorfulUtil {
    public static Identifier id(String id) {
        return new Identifier(Armorful.MOD_ID, id);
    }

    public static EntityModelLayer registerEntityModelLayer(String id, EntityModelLayerRegistry.TexturedModelDataProvider texturedModelDataProvider) {
        EntityModelLayer modelLayer = new EntityModelLayer(id(id), id);
        EntityModelLayerRegistry.registerModelLayer(modelLayer, texturedModelDataProvider);
        return modelLayer;
    }

    public static WolfDataAccessor getWolfData(WolfEntity wolf) {
        return ((WolfDataAccessor) wolf);
    }

    public static SoundEvent registerSoundEvent(String name) {
        Identifier id = ArmorfulUtil.id(name);
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }
}
