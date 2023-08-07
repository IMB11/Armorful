package com.mineblock11.armorful.loot;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mineblock11.armorful.util.ArmorfulUtil;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class ArmorfulLootTables {
    public static final Identifier ILLAGER_HELMET = ArmorfulUtil.id(
            "entities/illager_helmet");
    public static final Identifier ILLAGER_CHEST = ArmorfulUtil.id(
            "entities/illager_chestplate");
    public static final Identifier ILLAGER_LEGGINGS = ArmorfulUtil.id(
            "entities/illager_legs");
    public static final Identifier ILLAGER_FEET = ArmorfulUtil.id(
            "entities/illager_feet");
    public static final Identifier NATURAL_SPAWN_ILLAGER_HELMET = ArmorfulUtil.id(
            "entities/natural_spawn/illager_helmet");
    public static final Identifier NATURAL_SPAWN_ILLAGER_CHEST = ArmorfulUtil.id(
            "entities/natural_spawn/illager_chestplate");
    public static final Identifier NATURAL_SPAWN_ILLAGER_LEGGINGS = ArmorfulUtil.id(
            "entities/natural_spawn/illager_legs");
    public static final Identifier NATURAL_SPAWN_ILLAGER_FEET = ArmorfulUtil.id(
            "entities/natural_spawn/illager_feet");

    public static final BiMap<Identifier, LootContextType> REGISTRY = HashBiMap.create();
    public static final LootContextType SLOT = register("slot", (table) -> table.require(LootContextParameters.THIS_ENTITY));

    public static LootContextType register(String id, Consumer<LootContextType.Builder> lootContextBuilder) {
        LootContextType.Builder LootContextType$builder = new LootContextType.Builder();
        lootContextBuilder.accept(LootContextType$builder);
        LootContextType LootContextType = LootContextType$builder.build();
        Identifier Identifier = ArmorfulUtil.id(id);
        LootContextType LootContextType1 = REGISTRY.put(Identifier, LootContextType);
        if (LootContextType1 != null) {
            throw new IllegalStateException("Loot table parameter set " + Identifier + " is already registered");
        } else {
            return LootContextType;
        }
    }

    public static void init() {
        Registry.register(Registries.LOOT_CONDITION_TYPE, ArmorfulUtil.id("wave"), RaidWaveCondition.WAVE);
        Registry.register(Registries.LOOT_CONDITION_TYPE, ArmorfulUtil.id("configurable"), ConfigurableCondition.CONFIGURABLE_CHANCE);
    }
}
