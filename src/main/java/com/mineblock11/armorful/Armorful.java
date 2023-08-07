package com.mineblock11.armorful;

import com.mineblock11.armorful.illagers.client.IllagerArmorClient;
import com.mineblock11.armorful.loot.ArmorfulLootTables;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;

public class Armorful implements ModInitializer, ClientModInitializer {
    public static final String MOD_ID = "armorful";
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
    }
}
