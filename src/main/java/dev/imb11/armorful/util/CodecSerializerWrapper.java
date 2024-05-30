/*?if <1.20.2 {*//*
package dev.imb11.armorful.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.world.level.storage.loot.Serializer;
import org.jetbrains.annotations.NotNull;

public class CodecSerializerWrapper<T> implements Serializer<T> {
    public final Codec<T> wrappedCodec;

    public CodecSerializerWrapper(Codec<T> wrappedCodec) {
        this.wrappedCodec = wrappedCodec;
    }

    @Override
    public void serialize(JsonObject jsonObject, T object, JsonSerializationContext jsonSerializationContext) {
        DataResult<JsonElement> element = wrappedCodec.encodeStart(JsonOps.INSTANCE, object);
        // Place all keys from element into JsonObject.
        element.result().ifPresent(jsonElement -> {
            JsonObject json = jsonElement.getAsJsonObject();
            json.entrySet().forEach(entry -> jsonObject.add(entry.getKey(), entry.getValue()));
        });
    }

    @Override
    public @NotNull T deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
        return wrappedCodec.parse(JsonOps.INSTANCE, jsonObject)
                .result()
                .orElseThrow(() -> new IllegalStateException("Failed to deserialize JSON: " + jsonObject.toString()));
    }
}
*//*?}*/