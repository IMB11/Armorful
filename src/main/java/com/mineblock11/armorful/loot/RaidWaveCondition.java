package com.mineblock11.armorful.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.JsonSerializer;

public class RaidWaveCondition implements LootCondition {
    public static final LootConditionType WAVE = new LootConditionType(new WaveSerializer());
    public final int wave;

    public RaidWaveCondition(int wave) {
        this.wave = wave;
    }

    @Override
    public LootConditionType getType() {
        return WAVE;
    }

    @Override
    public boolean test(LootContext lootContext) {
        RaiderEntity raider = (RaiderEntity) lootContext.get(LootContextParameters.THIS_ENTITY);
        return raider.getRaid().getGroupsSpawned() == wave;
    }

    public static class WaveSerializer implements JsonSerializer<RaidWaveCondition> {
        @Override
        public void toJson(JsonObject json, RaidWaveCondition condition, JsonSerializationContext serializer) {
            json.addProperty("wave", condition.wave);
        }

        @Override
        public RaidWaveCondition fromJson(JsonObject jsonObject, JsonDeserializationContext deserializationContext) {
            return new RaidWaveCondition(jsonObject.get("wave").getAsInt());
        }
    }
}
