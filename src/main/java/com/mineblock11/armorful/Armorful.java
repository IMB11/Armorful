package com.mineblock11.armorful;

import com.mineblock11.armorful.client.illagers.IllagerArmorClient;
import com.mineblock11.armorful.client.wolves.WolfArmorClient;
import com.mineblock11.armorful.loot.ArmorfulLootTables;
import com.mineblock11.armorful.util.ArmorfulUtil;
import com.mineblock11.armorful.wolves.WolfArmorData;
import com.mineblock11.armorful.wolves.WolfInteractionHandler;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.RewriteResult;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.types.Type;
import draylar.staticcontent.StaticContent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Optional;

public class Armorful implements ModInitializer, ClientModInitializer {
    public static final String MOD_ID = "armorful";
    public static final SoundEvent WOLF_ARMOR_EQUIP = ArmorfulUtil.registerSoundEvent("entity.wolf.armor");
    @Environment(EnvType.CLIENT)
    public IllagerArmorClient illagerArmorClient;
    @Environment(EnvType.CLIENT)
    public WolfArmorClient wolfArmorClient;

    @Override
    public void onInitializeClient() {
        this.illagerArmorClient = new IllagerArmorClient();
        this.wolfArmorClient = new WolfArmorClient();

        this.illagerArmorClient.onInitializeClient();
        this.wolfArmorClient.onInitializeClient();
    }

    @Override
    public void onInitialize() {
        ArmorfulLootTables.init();

        StaticContent.load(new Identifier("wolveswitharmor", "wolf_armor"), WolfArmorData.class);
        UseEntityCallback.EVENT.register(new WolfInteractionHandler());
    }
}
