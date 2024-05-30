package dev.imb11.armorful.mixin.drowned;

import com.google.common.collect.Maps;
import dev.imb11.armorful.loot.ArmorfulLootTables;
import dev.imb11.armorful.util.ArmorfulUtil;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;

@Mixin(Drowned.class)
public abstract class DrownedEntityMixin extends Zombie {
    protected DrownedEntityMixin(EntityType<? extends Zombie> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "finalizeSpawn", at = @At("HEAD"), cancellable = false)
    public void initializeArmor(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, SpawnGroupData entityData, CompoundTag entityNbt, CallbackInfoReturnable<SpawnGroupData> cir) {
        if(world instanceof ServerLevel) {
            if (spawnReason == MobSpawnType.STRUCTURE) return;
            ArmorfulUtil.giveArmorNaturally(this.random, this, difficulty);
        }
    }
}
