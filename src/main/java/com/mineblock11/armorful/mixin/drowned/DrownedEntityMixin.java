package com.mineblock11.armorful.mixin.drowned;

import com.google.common.collect.Maps;
import com.mineblock11.armorful.loot.ArmorfulLootTables;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;

@Mixin(DrownedEntity.class)
public abstract class DrownedEntityMixin extends ZombieEntity {
    private static final Map<EquipmentSlot, Identifier> NATURAL_SPAWN_EQUIPMENT_SLOT_ITEMS = Util.make(Maps.newHashMap(),
            (slotItems) -> {
                slotItems.put(EquipmentSlot.HEAD, ArmorfulLootTables.NATURAL_SPAWN_HELMET);
                slotItems.put(EquipmentSlot.CHEST, ArmorfulLootTables.NATURAL_SPAWN_CHEST);
                slotItems.put(EquipmentSlot.LEGS, ArmorfulLootTables.NATURAL_SPAWN_LEGGINGS);
                slotItems.put(EquipmentSlot.FEET, ArmorfulLootTables.NATURAL_SPAWN_FEET);
            });

    protected DrownedEntityMixin(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initialize", at = @At("HEAD"), cancellable = false)
    public void initializeArmor(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt, CallbackInfoReturnable<EntityData> cir) {
        this.giveArmorNaturally(difficulty);
    }

    public List<ItemStack> getNaturalSpawnItemsFromLootTable(EquipmentSlot slot) {
        if (NATURAL_SPAWN_EQUIPMENT_SLOT_ITEMS.containsKey(slot)) {
            LootTable loot = this.getWorld().getServer().getLootManager().getLootTable(NATURAL_SPAWN_EQUIPMENT_SLOT_ITEMS.get(slot));
            LootContextParameterSet.Builder lootcontext$builder = (new LootContextParameterSet.Builder((ServerWorld) this.getWorld()))
                    .add(LootContextParameters.THIS_ENTITY, this);
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
