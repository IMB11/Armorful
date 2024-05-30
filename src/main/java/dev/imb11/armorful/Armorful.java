package dev.imb11.armorful;

import dev.imb11.armorful.loot.ArmorfulLootTables;
import net.fabricmc.api.ModInitializer;

public class Armorful implements ModInitializer {
    public static final String MOD_ID = "armorful";

    @Override
    public void onInitialize() {
        ArmorfulLootTables.init();
    }
}
