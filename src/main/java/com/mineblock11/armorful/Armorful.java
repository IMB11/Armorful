package com.mineblock11.armorful;

import com.mineblock11.armorful.client.illagers.IllagerArmorClient;
import com.mineblock11.armorful.loot.ArmorfulLootTables;
import com.mineblock11.armorful.util.ArmorfulUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
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

    @Override
    public void onInitializeClient() {
        this.illagerArmorClient = new IllagerArmorClient();

        this.illagerArmorClient.onInitializeClient();
    }

    @Override
    public void onInitialize() {
        ArmorfulLootTables.init();

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register((itemGroup) -> {
            var result = WOLF_ARMOR_ITEMS.stream().map(Item::getDefaultStack).toList();
            itemGroup.addAfter(Items.DIAMOND_HORSE_ARMOR, result);
        });
    }
}
