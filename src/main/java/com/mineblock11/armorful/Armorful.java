package com.mineblock11.armorful;

import com.mineblock11.armorful.client.illagers.IllagerArmorClient;
import com.mineblock11.armorful.client.wolves.WolfArmorClient;
import com.mineblock11.armorful.loot.ArmorfulLootTables;
import com.mineblock11.armorful.util.ArmorfulUtil;
import com.mineblock11.armorful.wolves.WolfArmorData;
import com.mineblock11.armorful.wolves.WolfInteractionHandler;
import draylar.staticcontent.StaticContent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;

import java.util.ArrayList;

public class Armorful implements ModInitializer, ClientModInitializer {
    public static final String MOD_ID = "armorful";
    public static final SoundEvent WOLF_ARMOR_EQUIP = ArmorfulUtil.registerSoundEvent("entity.wolf.armor");
    public static final ArrayList<Item> WOLF_ARMOR_ITEMS = new ArrayList<>();
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

        StaticContent.load(ArmorfulUtil.id("wolf_armor"), WolfArmorData.class);
        UseEntityCallback.EVENT.register(new WolfInteractionHandler());

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register((itemGroup) -> {
            var result = WOLF_ARMOR_ITEMS.stream().map(Item::getDefaultStack).toList();
            itemGroup.addAfter(Items.DIAMOND_HORSE_ARMOR, result);
        });
    }
}
