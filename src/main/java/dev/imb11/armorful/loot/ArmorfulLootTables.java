package dev.imb11.armorful.loot;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import dev.imb11.armorful.util.ArmorfulUtil;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class ArmorfulLootTables {
    public static final ResourceLocation ILLAGER_HELMET = ArmorfulUtil.id(
            "entities/illager_helmet");
    public static final ResourceLocation ILLAGER_CHEST = ArmorfulUtil.id(
            "entities/illager_chestplate");
    public static final ResourceLocation ILLAGER_LEGGINGS = ArmorfulUtil.id(
            "entities/illager_legs");
    public static final ResourceLocation ILLAGER_FEET = ArmorfulUtil.id(
            "entities/illager_feet");
    public static final ResourceLocation NATURAL_SPAWN_HELMET = ArmorfulUtil.id(
            "entities/natural_spawn/helmet");
    public static final ResourceLocation NATURAL_SPAWN_CHEST = ArmorfulUtil.id(
            "entities/natural_spawn/chestplate");
    public static final ResourceLocation NATURAL_SPAWN_LEGGINGS = ArmorfulUtil.id(
            "entities/natural_spawn/legs");
    public static final ResourceLocation NATURAL_SPAWN_FEET = ArmorfulUtil.id(
            "entities/natural_spawn/feet");

    public static final BiMap<ResourceLocation, LootContextParamSet> REGISTRY = HashBiMap.create();
    public static final LootContextParamSet SLOT = register("slot", (table) -> table.required(LootContextParams.THIS_ENTITY));

    public static LootContextParamSet register(String id, Consumer<LootContextParamSet.Builder> lootContextBuilder) {
        LootContextParamSet.Builder LootContextType$builder = new LootContextParamSet.Builder();
        lootContextBuilder.accept(LootContextType$builder);
        LootContextParamSet LootContextType = LootContextType$builder.build();
        ResourceLocation Identifier = ArmorfulUtil.id(id);
        LootContextParamSet LootContextType1 = REGISTRY.put(Identifier, LootContextType);
        if (LootContextType1 != null) {
            throw new IllegalStateException("Loot table parameter set " + Identifier + " is already registered");
        } else {
            return LootContextType;
        }
    }

    public static void init() {
        Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, ArmorfulUtil.id("wave"), RaidWaveCondition.WAVE);
    }
}
