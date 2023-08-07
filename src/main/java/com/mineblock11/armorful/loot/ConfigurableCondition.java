package com.mineblock11.armorful.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonSerializer;

import java.util.HashMap;
import java.util.function.Supplier;

public class ConfigurableCondition implements LootCondition {
    public static final LootConditionType CONFIGURABLE_CHANCE = new LootConditionType(new ConfigurableChanceSerializer());
    private static final HashMap<Identifier, Supplier<Boolean>> CONFIG_FETCHERS = new HashMap<>();

    static {
        // Register fetchers here.
    }

    private final Identifier configFetcherID;
    private final Supplier<Boolean> configFetcher;

    public ConfigurableCondition(Identifier configFetcher) {
        this.configFetcherID = configFetcher;
        this.configFetcher = CONFIG_FETCHERS.get(configFetcherID);
    }

    @Override
    public LootConditionType getType() {
        return null;
    }

    @Override
    public boolean test(LootContext lootContext) {
        return configFetcher.get();
    }

    public static class ConfigurableChanceSerializer implements JsonSerializer<ConfigurableCondition> {
        @Override
        public void toJson(JsonObject json, ConfigurableCondition object, JsonSerializationContext context) {
            json.addProperty("config", object.configFetcherID.toString());
        }

        @Override
        public ConfigurableCondition fromJson(JsonObject json, JsonDeserializationContext context) {
            Identifier identifier = Identifier.tryParse(json.get("config").getAsString());
            return new ConfigurableCondition(identifier);
        }
    }
}
