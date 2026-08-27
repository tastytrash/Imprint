package org.tastytrash.imprint.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import net.minecraft.resources.Identifier;
import org.tastytrash.imprint.client.ImprintClient;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class FootprintDataLoader {
    private static final Gson GSON = new Gson();

    public static void load() {
        try (InputStream stream = FootprintDataLoader.class.getResourceAsStream("/assets/imprint/footprints.json")) {
            if (stream == null) {
                ImprintClient.LOGGER.warn("footprints.json not found, using estimation only");
                return;
            }

            JsonObject root = GSON.fromJson(new InputStreamReader(stream), JsonObject.class);

            if (root.has("ignored_entities")) {
                JsonArray ignoredArray = root.getAsJsonArray("ignored_entities");
                for (JsonElement element : ignoredArray) {
                    String entityIdStr = element.getAsString();
                    Identifier entityId = Identifier.tryParse(entityIdStr);
                    if (entityId != null) {
                        FootprintUtil.registerIgnoredEntity(entityId);
                    } else {
                        ImprintClient.LOGGER.warn("Invalid ignored entity ID: {}", entityIdStr);
                    }
                }
                ImprintClient.LOGGER.info("Loaded {} ignored entity definitions", ignoredArray.size());
            }

            if (root.has("entities")) {
                JsonObject entities = root.getAsJsonObject("entities");

                for (Map.Entry<String, JsonElement> entry : entities.entrySet()) {
                    Identifier entityId = Identifier.tryParse(entry.getKey());
                    if (entityId == null) {
                        ImprintClient.LOGGER.warn("Invalid entity ID: {}", entry.getKey());
                        continue;
                    }

                    JsonObject entityData = entry.getValue().getAsJsonObject();
                    String sizeStr = entityData.get("size").getAsString();
                    double footOffset = entityData.get("foot_offset").getAsDouble();
                    int tickInterval = entityData.has("tick_interval")
                        ? entityData.get("tick_interval").getAsInt()
                        : ImprintClient.config.tickInterval;

                    FootprintUtil.FootprintSize size = FootprintUtil.FootprintSize.valueOf(sizeStr.toUpperCase());
                    FootprintUtil.FootprintData data = new FootprintUtil.FootprintData(size, footOffset, tickInterval);
                    FootprintUtil.registerData(entityId, data);
                }

                ImprintClient.LOGGER.info("Loaded {} entity footprint definitions", entities.size());
            }
        } catch (Exception e) {
            ImprintClient.LOGGER.error("Failed to load footprints.json, using estimation only", e);
        }
    }
}
