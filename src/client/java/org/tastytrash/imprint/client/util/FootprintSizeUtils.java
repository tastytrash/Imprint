package org.tastytrash.imprint.client.util;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
//? if >=26.2 {
import net.minecraft.world.entity.EntityTypes;
//? }
import org.jetbrains.annotations.Nullable;
import org.tastytrash.imprint.client.ImprintClient;
import org.tastytrash.imprint.client.particle.ParticleRegistry;

import java.util.HashMap;
import java.util.Map;

public class FootprintSizeUtils {
    private static final Map<EntityType<?>, FootprintData> ENTITY_FOOTPRINTS = new HashMap<>();

    public record FootprintData(FootprintSize size, double footOffset, int baseTickInterval) {

        public FootprintData(FootprintSize size, double footOffset) {
            this(size, footOffset, ImprintClient.config.tickInterval);
        }
    }

    public enum FootprintSize {
        SMALLEST(ParticleRegistry.FOOTPRINT_SMALLEST, 3 / 32f),
        SMALLER(ParticleRegistry.FOOTPRINT_SMALLER, 4 / 32f),
        SMALL(ParticleRegistry.FOOTPRINT_SMALL, 5 / 32f),
        MEDIUM(ParticleRegistry.FOOTPRINT_MEDIUM, 6 / 32f),
        LARGE(ParticleRegistry.FOOTPRINT_LARGE, 7 / 32f),
        LARGEST(ParticleRegistry.FOOTPRINT_LARGEST, 8 / 32f);

        private final SimpleParticleType particleType;
        private final float baseScale;

        FootprintSize(SimpleParticleType particleType, float baseScale) {
            this.particleType = particleType;
            this.baseScale = baseScale;
        }

        public SimpleParticleType getParticleType() { return particleType; }
        public float getBaseScale() { return baseScale; }
    }

    //? if >=26.2 {
    static {
        // smallest
        register(EntityTypes.FROG, FootprintSize.SMALLEST, 0.20, 2);
        register(EntityTypes.CAT, FootprintSize.SMALLEST, 0.08, 3);
        register(EntityTypes.OCELOT, FootprintSize.SMALLEST, 0.08, 3);

        // smaller
        register(EntityTypes.CHICKEN, FootprintSize.SMALLER, 0.06);
        register(EntityTypes.RABBIT, FootprintSize.SMALLER, 0.08);
        register(EntityTypes.FOX, FootprintSize.SMALLER, 0.12, 3);
        register(EntityTypes.CAVE_SPIDER, FootprintSize.SMALLER, 0.35, 2);
        register(EntityTypes.ENDERMAN, FootprintSize.SMALLER, 0.18);

        // small
        register(EntityTypes.PIG, FootprintSize.SMALL, 0.20, 3);
        register(EntityTypes.SHEEP, FootprintSize.SMALL, 0.22, 3);
        register(EntityTypes.WOLF, FootprintSize.SMALL, 0.16, 3);
        register(EntityTypes.ARMADILLO, FootprintSize.SMALL, 0.18, 3);
        register(EntityTypes.GOAT, FootprintSize.SMALL, 0.24, 3);
        register(EntityTypes.SPIDER, FootprintSize.SMALL, 0.50, 2);
        register(EntityTypes.SKELETON, FootprintSize.SMALL, 0.12);
        register(EntityTypes.WITHER_SKELETON, FootprintSize.SMALL, 0.14);
        register(EntityTypes.STRAY, FootprintSize.SMALL, 0.12);
        register(EntityTypes.BOGGED, FootprintSize.SMALL, 0.12);
        register(EntityTypes.COPPER_GOLEM, FootprintSize.SMALL, 0.20, 5);

        // medium
        register(EntityTypes.PLAYER, FootprintSize.MEDIUM, ImprintClient.config.footOffset);
        register(EntityTypes.COW, FootprintSize.MEDIUM, 0.28, 3);
        register(EntityTypes.MOOSHROOM, FootprintSize.MEDIUM, 0.28, 3);
        register(EntityTypes.ZOMBIE, FootprintSize.MEDIUM, 0.30);
        register(EntityTypes.HUSK, FootprintSize.MEDIUM, 0.30);
        register(EntityTypes.DROWNED, FootprintSize.MEDIUM, 0.30);
        register(EntityTypes.CREEPER, FootprintSize.MEDIUM, 0.20, 2);
        register(EntityTypes.VILLAGER, FootprintSize.MEDIUM, 0.16);
        register(EntityTypes.ZOMBIE_VILLAGER, FootprintSize.MEDIUM, 0.16);
        register(EntityTypes.WANDERING_TRADER, FootprintSize.MEDIUM, 0.16);
        register(EntityTypes.PILLAGER, FootprintSize.MEDIUM, 0.16);
        register(EntityTypes.VINDICATOR, FootprintSize.MEDIUM, 0.16);
        register(EntityTypes.WITCH, FootprintSize.MEDIUM, 0.16);
        register(EntityTypes.EVOKER, FootprintSize.MEDIUM, 0.16);
        register(EntityTypes.PIGLIN, FootprintSize.MEDIUM, 0.30);
        register(EntityTypes.PIGLIN_BRUTE, FootprintSize.MEDIUM, 0.30);
        register(EntityTypes.ZOMBIFIED_PIGLIN, FootprintSize.MEDIUM, 0.30);
        register(EntityTypes.HORSE, FootprintSize.MEDIUM, 0.35, 3);
        register(EntityTypes.DONKEY, FootprintSize.MEDIUM, 0.30, 3);
        register(EntityTypes.MULE, FootprintSize.MEDIUM, 0.32, 3);
        register(EntityTypes.SKELETON_HORSE, FootprintSize.MEDIUM, 0.35, 3);
        register(EntityTypes.ZOMBIE_HORSE, FootprintSize.MEDIUM, 0.35, 3);
        register(EntityTypes.LLAMA, FootprintSize.MEDIUM, 0.32, 3);
        register(EntityTypes.TRADER_LLAMA, FootprintSize.MEDIUM, 0.32, 3);

        // large
        register(EntityTypes.CAMEL, FootprintSize.LARGE, 0.48, 4);
        register(EntityTypes.PANDA, FootprintSize.LARGE, 0.42, 3);
        register(EntityTypes.POLAR_BEAR, FootprintSize.LARGE, 0.45, 3);
        register(EntityTypes.HOGLIN, FootprintSize.LARGE, 0.40, 3);
        register(EntityTypes.ZOGLIN, FootprintSize.LARGE, 0.40, 3);
        register(EntityTypes.IRON_GOLEM, FootprintSize.LARGE, 0.52, 8);

        // largest
        register(EntityTypes.RAVAGER, FootprintSize.LARGEST, 0.75, 4);
        register(EntityTypes.SNIFFER, FootprintSize.LARGEST, 0.70, 2);
        register(EntityTypes.WARDEN, FootprintSize.LARGEST, 0.55);
    }
    //? } else {
    /*
    static {
        // smallest
        register(EntityType.FROG, FootprintSize.SMALLEST, 0.20, 2);
        register(EntityType.CAT, FootprintSize.SMALLEST, 0.08, 3);
        register(EntityType.OCELOT, FootprintSize.SMALLEST, 0.08, 3);

        // smaller
        register(EntityType.CHICKEN, FootprintSize.SMALLER, 0.06);
        register(EntityType.RABBIT, FootprintSize.SMALLER, 0.08);
        register(EntityType.FOX, FootprintSize.SMALLER, 0.12, 3);
        register(EntityType.CAVE_SPIDER, FootprintSize.SMALLER, 0.35, 2);
        register(EntityType.ENDERMAN, FootprintSize.SMALLER, 0.18);

        // small
        register(EntityType.PIG, FootprintSize.SMALL, 0.20, 3);
        register(EntityType.SHEEP, FootprintSize.SMALL, 0.22, 3);
        register(EntityType.WOLF, FootprintSize.SMALL, 0.16, 3);
        register(EntityType.ARMADILLO, FootprintSize.SMALL, 0.18, 3);
        register(EntityType.GOAT, FootprintSize.SMALL, 0.24, 3);
        register(EntityType.SPIDER, FootprintSize.SMALL, 0.50, 2);
        register(EntityType.SKELETON, FootprintSize.SMALL, 0.12);
        register(EntityType.WITHER_SKELETON, FootprintSize.SMALL, 0.14);
        register(EntityType.STRAY, FootprintSize.SMALL, 0.12);
        register(EntityType.BOGGED, FootprintSize.SMALL, 0.12);
        register(EntityType.COPPER_GOLEM, FootprintSize.SMALL, 0.20, 5);

        // medium
        register(EntityType.PLAYER, FootprintSize.MEDIUM, ImprintClient.config.footOffset);
        register(EntityType.COW, FootprintSize.MEDIUM, 0.28, 3);
        register(EntityType.MOOSHROOM, FootprintSize.MEDIUM, 0.28, 3);
        register(EntityType.ZOMBIE, FootprintSize.MEDIUM, 0.30);
        register(EntityType.HUSK, FootprintSize.MEDIUM, 0.30);
        register(EntityType.DROWNED, FootprintSize.MEDIUM, 0.30);
        register(EntityType.CREEPER, FootprintSize.MEDIUM, 0.20, 2);
        register(EntityType.VILLAGER, FootprintSize.MEDIUM, 0.16);
        register(EntityType.ZOMBIE_VILLAGER, FootprintSize.MEDIUM, 0.16);
        register(EntityType.WANDERING_TRADER, FootprintSize.MEDIUM, 0.16);
        register(EntityType.PILLAGER, FootprintSize.MEDIUM, 0.16);
        register(EntityType.VINDICATOR, FootprintSize.MEDIUM, 0.16);
        register(EntityType.WITCH, FootprintSize.MEDIUM, 0.16);
        register(EntityType.EVOKER, FootprintSize.MEDIUM, 0.16);
        register(EntityType.PIGLIN, FootprintSize.MEDIUM, 0.30);
        register(EntityType.PIGLIN_BRUTE, FootprintSize.MEDIUM, 0.30);
        register(EntityType.ZOMBIFIED_PIGLIN, FootprintSize.MEDIUM, 0.30);
        register(EntityType.HORSE, FootprintSize.MEDIUM, 0.35, 3);
        register(EntityType.DONKEY, FootprintSize.MEDIUM, 0.30, 3);
        register(EntityType.MULE, FootprintSize.MEDIUM, 0.32, 3);
        register(EntityType.SKELETON_HORSE, FootprintSize.MEDIUM, 0.35, 3);
        register(EntityType.ZOMBIE_HORSE, FootprintSize.MEDIUM, 0.35, 3);
        register(EntityType.LLAMA, FootprintSize.MEDIUM, 0.32, 3);
        register(EntityType.TRADER_LLAMA, FootprintSize.MEDIUM, 0.32, 3);

        // large
        register(EntityType.CAMEL, FootprintSize.LARGE, 0.48, 4);
        register(EntityType.PANDA, FootprintSize.LARGE, 0.42, 3);
        register(EntityType.POLAR_BEAR, FootprintSize.LARGE, 0.45, 3);
        register(EntityType.HOGLIN, FootprintSize.LARGE, 0.40, 3);
        register(EntityType.ZOGLIN, FootprintSize.LARGE, 0.40, 3);
        register(EntityType.IRON_GOLEM, FootprintSize.LARGE, 0.52, 8);

        // largest
        register(EntityType.RAVAGER, FootprintSize.LARGEST, 0.75, 4);
        register(EntityType.SNIFFER, FootprintSize.LARGEST, 0.70, 2);
        register(EntityType.WARDEN, FootprintSize.LARGEST, 0.55);
    }
         */
    //? }

    private static void register(EntityType<?> type, FootprintSize size, double footOffset) {
        ENTITY_FOOTPRINTS.put(type, new FootprintData(size, footOffset));
    }

    private static void register(EntityType<?> type, FootprintSize size, double footOffset, int baseTickInterval) {
        ENTITY_FOOTPRINTS.put(type, new FootprintData(size, footOffset, baseTickInterval));
    }

    @Nullable
    public static FootprintData getFootprintData(Entity entity) {
        return ENTITY_FOOTPRINTS.get(entity.getType());
    }

    public static double getPixelOffset(FootprintSize size) {
        return (size == FootprintSize.SMALLEST || size == FootprintSize.SMALL || size == FootprintSize.LARGE) ? 1.0 : 0.0;
    }
}