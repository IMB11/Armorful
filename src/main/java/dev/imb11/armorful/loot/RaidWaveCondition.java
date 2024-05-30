package dev.imb11.armorful.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record RaidWaveCondition(int wave) implements LootItemCondition {
    public static final Codec<RaidWaveCondition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("wave").forGetter(RaidWaveCondition::wave)
    ).apply(instance, RaidWaveCondition::new));

    public static final LootItemConditionType WAVE = new LootItemConditionType(CODEC);

    @Override
    public @NotNull LootItemConditionType getType() {
        return WAVE;
    }

    @Override
    public boolean test(LootContext lootContext) {
        Raider raider = (Raider) lootContext.getParamOrNull(LootContextParams.THIS_ENTITY);
        assert raider != null;
        return Objects.requireNonNull(raider.getCurrentRaid()).getGroupsSpawned() == wave;
    }
}
