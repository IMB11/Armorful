package dev.imb11.armorful.mixin.illagers;

import dev.imb11.armorful.util.ArmorfulUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Vex.class)
public abstract class AbstractVexMixin extends Monster {
    protected AbstractVexMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "finalizeSpawn", at = @At("HEAD"), cancellable = false)
    /*? if <=1.20.4 {*/
    public void initializeArmor(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, SpawnGroupData spawnGroupData, net.minecraft.nbt.CompoundTag compoundTag, CallbackInfoReturnable<SpawnGroupData> cir) {
    /*? } else { *//*
    public void initializeArmor(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, SpawnGroupData spawnGroupData, CallbackInfoReturnable<SpawnGroupData> cir) {
    *//*? } */
        if(serverLevelAccessor instanceof ServerLevel) {
            ArmorfulUtil.giveArmorNaturally(this.random, this, difficultyInstance);
        }
    }
}
