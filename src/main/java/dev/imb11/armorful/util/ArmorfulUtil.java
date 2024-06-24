package dev.imb11.armorful.util;

import com.google.common.collect.Maps;
import dev.imb11.armorful.Armorful;
import dev.imb11.armorful.loot.ArmorfulLootTables;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.Util;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;
import java.util.Map;

public class ArmorfulUtil {
    public static ResourceLocation id(String id) {
        return ResourceLocation.tryBuild(Armorful.MOD_ID, id);
    }

    public static ResourceLocation defaultID(String id) {
        return ResourceLocation.tryBuild("minecraft", id);
    }

    public static ModelLayerLocation registerEntityModelLayer(String id, EntityModelLayerRegistry.TexturedModelDataProvider texturedModelDataProvider) {
        ModelLayerLocation modelLayer = new ModelLayerLocation(id(id), id);
        EntityModelLayerRegistry.registerModelLayer(modelLayer, texturedModelDataProvider);
        return modelLayer;
    }

    public static final Map<EquipmentSlot, ResourceLocation> NATURAL_SPAWN_EQUIPMENT_SLOT_ITEMS = Util.make(Maps.newHashMap(),
            (slotItems) -> {
                slotItems.put(EquipmentSlot.HEAD, ArmorfulLootTables.NATURAL_SPAWN_HELMET);
                slotItems.put(EquipmentSlot.CHEST, ArmorfulLootTables.NATURAL_SPAWN_CHEST);
                slotItems.put(EquipmentSlot.LEGS, ArmorfulLootTables.NATURAL_SPAWN_LEGGINGS);
                slotItems.put(EquipmentSlot.FEET, ArmorfulLootTables.NATURAL_SPAWN_FEET);
            });

    public static List<ItemStack> getNaturalSpawnItemsFromLootTable(LivingEntity entity, EquipmentSlot slot) {
        if (NATURAL_SPAWN_EQUIPMENT_SLOT_ITEMS.containsKey(slot)) {
            /*? >=1.20.6 {*/
            LootTable loot = entity.level().getServer().reloadableRegistries().getLootTable(ResourceKey.create(Registries.LOOT_TABLE, NATURAL_SPAWN_EQUIPMENT_SLOT_ITEMS.get(slot)));
            /*?} else {*/
            /*LootTable loot = entity.level().getServer().getLootData().getLootTable(NATURAL_SPAWN_EQUIPMENT_SLOT_ITEMS.get(slot));
            *//*?}*/
            LootParams.Builder lootcontext$builder = (new LootParams.Builder((ServerLevel) entity.level()))
                    .withParameter(LootContextParams.THIS_ENTITY, entity);
            return loot.getRandomItems(lootcontext$builder.create(ArmorfulLootTables.SLOT));
        }

        return null;
    }

    public static void giveArmorNaturally(RandomSource random, LivingEntity entity, DifficultyInstance difficulty) {
        if (random.nextFloat() < 0.24F * difficulty.getSpecialMultiplier()) {
            float difficultyChance = entity.level().getDifficulty() == Difficulty.HARD ? 0.1F : 0.25F;
            boolean flag = true;

            for (EquipmentSlot slotType : EquipmentSlot.values()) {
                /*? if <1.21 {*/
                /*if (slotType.getType() == EquipmentSlot.Type.ARMOR) {
                *//*?} else {*/
                if (slotType.getType() == EquipmentSlot.Type.HUMANOID_ARMOR) {
                    /*?}*/
                    if (!flag && random.nextFloat() < difficultyChance) {
                        break;
                    }

                    flag = false;
                    for (ItemStack stack : getNaturalSpawnItemsFromLootTable(entity, slotType)) {
                        entity.setItemSlot(slotType, stack);
                    }
                }
            }
        }
    }
}
