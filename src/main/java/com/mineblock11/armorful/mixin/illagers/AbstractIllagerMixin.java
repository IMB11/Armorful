package com.mineblock11.armorful.mixin.illagers;

import com.google.common.collect.Maps;
import com.mineblock11.armorful.loot.ArmorfulLootTables;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;
import java.util.Map;

@Mixin(IllagerEntity.class)
public abstract class AbstractIllagerMixin extends RaiderEntity {
    private static final Map<EquipmentSlot, Identifier> EQUIPMENT_SLOT_ITEMS = Util.make(Maps.newHashMap(),
            (slotItems) -> {
                slotItems.put(EquipmentSlot.HEAD, ArmorfulLootTables.ILLAGER_HELMET);
                slotItems.put(EquipmentSlot.CHEST, ArmorfulLootTables.ILLAGER_CHEST);
                slotItems.put(EquipmentSlot.LEGS, ArmorfulLootTables.ILLAGER_LEGGINGS);
                slotItems.put(EquipmentSlot.FEET, ArmorfulLootTables.ILLAGER_FEET);
            });
    private static final Map<EquipmentSlot, Identifier> NATURAL_SPAWN_EQUIPMENT_SLOT_ITEMS = Util.make(Maps.newHashMap(),
            (slotItems) -> {
                slotItems.put(EquipmentSlot.HEAD, ArmorfulLootTables.NATURAL_SPAWN_HELMET);
                slotItems.put(EquipmentSlot.CHEST, ArmorfulLootTables.NATURAL_SPAWN_CHEST);
                slotItems.put(EquipmentSlot.LEGS, ArmorfulLootTables.NATURAL_SPAWN_LEGGINGS);
                slotItems.put(EquipmentSlot.FEET, ArmorfulLootTables.NATURAL_SPAWN_FEET);
            });

    protected AbstractIllagerMixin(EntityType<? extends RaiderEntity> entityType, World world) {
        super(entityType, world);
    }

    private static float getWaveArmorChances(int waves) {
        return switch (waves) {
            case 0 -> 0.30f;
            case 1 -> 0.32f;
            case 2 -> 0.34f;
            case 3 -> 0.38f;
            case 4 -> 0.40f;
            case 5 -> 0.42f;
            case 6 -> 0.44f;
            case 7 -> 0.48f;
            default -> 0;
        };
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
                                 SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound nbtData) {
        if (this.getRaid() != null && spawnReason == SpawnReason.EVENT) {
            this.giveArmorOnRaids();
        } else {
            this.giveArmorNaturally(difficulty);
        }
        return super.initialize(world, difficulty, spawnReason, entityData, nbtData);
    }

    public void giveArmorOnRaids() {
        float difficultyChance = this.getWorld().getDifficulty() == Difficulty.HARD ? 0.3F : 0.65F;
        int illagerWaves = this.getRaid().getGroupsSpawned();
        float waveChances = getWaveArmorChances(illagerWaves);
        if (this.getRandom().nextFloat() < waveChances) {
            boolean flag = true;
            for (EquipmentSlot equipmentslottype : EquipmentSlot.values()) {
                if (equipmentslottype.getType() == EquipmentSlot.Type.ARMOR) {
                    if (!flag && this.random.nextFloat() < difficultyChance) {
                        break;
                    }
                    flag = false;
                    for (ItemStack stack : this.getItemsFromLootTable(equipmentslottype)) {
                        this.equipStack(equipmentslottype, stack);
                    }
                }
            }
        }
    }

    public List<ItemStack> getNaturalSpawnItemsFromLootTable(EquipmentSlot slot) {
        if (NATURAL_SPAWN_EQUIPMENT_SLOT_ITEMS.containsKey(slot)) {
            LootTable loot = this.getWorld().getServer().getLootManager().getTable(NATURAL_SPAWN_EQUIPMENT_SLOT_ITEMS.get(slot));
            LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerWorld) this.getWorld()))
                    .parameter(LootContextParameters.THIS_ENTITY, this);
            return loot.generateLoot(lootcontext$builder.build(ArmorfulLootTables.SLOT));
        }

        return null;
    }

    public List<ItemStack> getItemsFromLootTable(EquipmentSlot slot) {
        if (EQUIPMENT_SLOT_ITEMS.containsKey(slot)) {
            LootTable loot = this.getWorld().getServer().getLootManager().getTable(EQUIPMENT_SLOT_ITEMS.get(slot));
            LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerWorld) this.getWorld()))
                    .parameter(LootContextParameters.THIS_ENTITY, this);
            return loot.generateLoot(lootcontext$builder.build(ArmorfulLootTables.SLOT));
        }
        return null;
    }

    protected void giveArmorNaturally(LocalDifficulty difficulty) {
        if (random.nextFloat() < 0.15F * difficulty.getClampedLocalDifficulty()) {
            float difficultyChance = this.getWorld().getDifficulty() == Difficulty.HARD ? 0.1F : 0.25F;
            boolean flag = true;

            for (EquipmentSlot slotType : EquipmentSlot.values()) {
                if (slotType.getType() == EquipmentSlot.Type.ARMOR) {
                    if (!flag && random.nextFloat() < difficultyChance) {
                        break;
                    }

                    flag = false;
                    for (ItemStack stack : getNaturalSpawnItemsFromLootTable(slotType)) {
                        this.equipStack(slotType, stack);
                    }
                }
            }
        }
    }
}
