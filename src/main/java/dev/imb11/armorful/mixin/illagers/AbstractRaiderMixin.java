package dev.imb11.armorful.mixin.illagers;

import com.google.common.collect.Maps;
import dev.imb11.armorful.loot.ArmorfulLootTables;
import dev.imb11.armorful.util.ArmorfulUtil;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;

@Mixin(value = Raider.class)
public abstract class AbstractRaiderMixin extends PatrollingMonster {
    @Shadow @Nullable public abstract Raid getCurrentRaid();

    @Unique
    private static final Map<EquipmentSlot, ResourceLocation> EQUIPMENT_SLOT_ITEMS = Util.make(Maps.newHashMap(),
            (slotItems) -> {
                slotItems.put(EquipmentSlot.HEAD, ArmorfulLootTables.ILLAGER_HELMET);
                slotItems.put(EquipmentSlot.CHEST, ArmorfulLootTables.ILLAGER_CHEST);
                slotItems.put(EquipmentSlot.LEGS, ArmorfulLootTables.ILLAGER_LEGGINGS);
                slotItems.put(EquipmentSlot.FEET, ArmorfulLootTables.ILLAGER_FEET);
            });

    protected AbstractRaiderMixin(EntityType<? extends Raider> entityType, Level world) {
        super(entityType, world);
    }

    @Unique
    private static float getWaveArmorChances(int waves) {
        return switch (waves) {
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

    @Inject(method = "finalizeSpawn", at = @At("TAIL"), cancellable = false)
    /*? <1.20.6 {*/
    public void finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, SpawnGroupData entityData, net.minecraft.nbt.CompoundTag nbtData, CallbackInfoReturnable<SpawnGroupData> cir) {
    /*? } else {*//*
    public void finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, SpawnGroupData entityData, CallbackInfoReturnable<SpawnGroupData> cir) {
    *//*?}*/
        if(world instanceof ServerLevel) {
            if (this.getCurrentRaid() != null || spawnReason == MobSpawnType.EVENT) {
                this.giveArmorOnRaids();
            } else {
                ArmorfulUtil.giveArmorNaturally(this.random, this, difficulty);
            }
        }
    }

    @Unique
    public void giveArmorOnRaids() {
        float difficultyChance = this.level().getDifficulty() == Difficulty.HARD ? 0.3F : 0.65F;
        int illagerWaves = this.getCurrentRaid().getGroupsSpawned();
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
                        this.setItemSlot(equipmentslottype, stack);
                    }
                }
            }
        }
    }

    @Unique
    public List<ItemStack> getItemsFromLootTable(EquipmentSlot slot) {
        if (EQUIPMENT_SLOT_ITEMS.containsKey(slot)) {
            /*? >=1.20.6 {*//*
            LootTable loot = this.level().getServer().reloadableRegistries().getLootTable(net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.LOOT_TABLE, EQUIPMENT_SLOT_ITEMS.get(slot)));
            *//*? } else {*/
            LootTable loot = this.level().getServer().getLootData().getLootTable(EQUIPMENT_SLOT_ITEMS.get(slot));
            /*?}*/
            LootParams.Builder lootcontext$builder = (new LootParams.Builder((ServerLevel) this.level()))
                    .withParameter(LootContextParams.THIS_ENTITY, this);
            return loot.getRandomItems(lootcontext$builder.create(ArmorfulLootTables.SLOT));
        }
        return null;
    }
}
