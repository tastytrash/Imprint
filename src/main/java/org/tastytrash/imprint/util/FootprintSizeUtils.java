package org.tastytrash.imprint.util;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
//? if >=26.2 {
/*import static net.minecraft.world.entity.EntityTypes.*;
*///? } else {
import static net.minecraft.world.entity.EntityType.*;
//? }

//? if >= 26.1 {
/*import net.minecraft.world.entity.monster.zombie.Zombie;
*///? } else {
import net.minecraft.world.entity.monster.Zombie;
//? }

import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import org.tastytrash.imprint.client.ImprintClient;
import org.tastytrash.imprint.config.ImprintConfig;
import org.tastytrash.imprint.particle.ParticleRegistry;

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

    static {
        // smallest
        register(CHICKEN, FootprintSize.SMALLEST, 0.08, 4);
        register(FROG, FootprintSize.SMALLEST, 0.20, 2);
        register(CAT, FootprintSize.SMALLEST, 0.08, 2);
        register(OCELOT, FootprintSize.SMALLEST, 0.08, 3);
        register(CAVE_SPIDER, FootprintSize.SMALLEST, 0.35, 2);
        //? > 1.20.1 {
        /*register(ARMADILLO, FootprintSize.SMALLEST, 0.18, 2);
        *///? }

        // smaller
        register(RABBIT, FootprintSize.SMALLER, 0.08);
        register(FOX, FootprintSize.SMALLER, 0.12, 3);
        //? if >=26.2 {
        /*register(SPIDER, FootprintSize.SMALLER, 0.50, 2);
        *///? } else {
        register(CAVE_SPIDER, FootprintSize.SMALLER, 0.35, 2);
        register(SPIDER, FootprintSize.SMALL, 0.50, 2);
        //? }
        register(ENDERMAN, FootprintSize.SMALLER, 0.18);

        // small
        register(PIG, FootprintSize.SMALL, 0.20, 3);
        register(SHEEP, FootprintSize.SMALL, 0.22, 3);
        register(WOLF, FootprintSize.SMALL, 0.16, 3);
        register(GOAT, FootprintSize.SMALL, 0.24, 3);

        register(SKELETON, FootprintSize.SMALL, 0.12);
		register(CREEPER, FootprintSize.SMALL, 0.20, 2);
        register(WITHER_SKELETON, FootprintSize.SMALL, 0.14);
        register(STRAY, FootprintSize.SMALL, 0.12);
        //? > 1.20.1 {
        /*register(BOGGED, FootprintSize.SMALL, 0.12);
        *///? }
        //? >=1.21.9 {
        /*register(COPPER_GOLEM, FootprintSize.SMALL, 0.20, 5);
        *///? }

        // medium
        register(PLAYER, FootprintSize.MEDIUM, ImprintClient.config.footOffset);
        register(COW, FootprintSize.MEDIUM, 0.28, 3);
        register(MOOSHROOM, FootprintSize.MEDIUM, 0.28, 3);
        register(ZOMBIE, FootprintSize.MEDIUM, 0.30);
        register(HUSK, FootprintSize.MEDIUM, 0.30);
        register(DROWNED, FootprintSize.MEDIUM, 0.30);
        register(VILLAGER, FootprintSize.MEDIUM, 0.16);
        register(ZOMBIE_VILLAGER, FootprintSize.MEDIUM, 0.16);
        register(WANDERING_TRADER, FootprintSize.MEDIUM, 0.16);
        register(PILLAGER, FootprintSize.MEDIUM, 0.16);
        register(VINDICATOR, FootprintSize.MEDIUM, 0.16);
        register(WITCH, FootprintSize.MEDIUM, 0.16);
        register(EVOKER, FootprintSize.MEDIUM, 0.16);
        register(PIGLIN, FootprintSize.MEDIUM, 0.30);
        register(PIGLIN_BRUTE, FootprintSize.MEDIUM, 0.30);
        register(ZOMBIFIED_PIGLIN, FootprintSize.MEDIUM, 0.30);
        register(HORSE, FootprintSize.MEDIUM, 0.35, 3);
        register(DONKEY, FootprintSize.MEDIUM, 0.30, 3);
        register(MULE, FootprintSize.MEDIUM, 0.32, 3);
        register(SKELETON_HORSE, FootprintSize.MEDIUM, 0.35, 3);
        register(ZOMBIE_HORSE, FootprintSize.MEDIUM, 0.35, 3);
        register(LLAMA, FootprintSize.MEDIUM, 0.32, 3);
        register(TRADER_LLAMA, FootprintSize.MEDIUM, 0.32, 3);

        // large
        register(CAMEL, FootprintSize.LARGE, 0.48, 4);
        register(PANDA, FootprintSize.LARGE, 0.42, 3);
        register(POLAR_BEAR, FootprintSize.LARGE, 0.45, 3);
        register(HOGLIN, FootprintSize.LARGE, 0.40, 3);
        register(ZOGLIN, FootprintSize.LARGE, 0.40, 3);
        register(IRON_GOLEM, FootprintSize.LARGE, 0.52, 8);

        // largest
        register(RAVAGER, FootprintSize.LARGEST, 0.75, 4);
        register(SNIFFER, FootprintSize.LARGEST, 0.70, 2);
        register(WARDEN, FootprintSize.LARGEST, 0.55);
    }

    private static void register(EntityType<?> type, FootprintSize size, double footOffset) {
        ENTITY_FOOTPRINTS.put(type, new FootprintData(size, footOffset));
    }

    private static void register(EntityType<?> type, FootprintSize size, double footOffset, int baseTickInterval) {
        ENTITY_FOOTPRINTS.put(type, new FootprintData(size, footOffset, baseTickInterval));
    }

    @Nullable
    public static FootprintData getFootprintData(Entity entity) {
        FootprintData data = ENTITY_FOOTPRINTS.get(entity.getType());
        if (data == null) return null;

        if (entity instanceof Player) {
            FootprintSize size = configToFootprintSize(ImprintClient.config.footprintSizes);
            return new FootprintData(size, ImprintClient.config.footOffset, data.baseTickInterval());
        }

        if (entity instanceof Zombie zombie && zombie.isBaby()) {
            return new FootprintData(FootprintSize.SMALL, data.footOffset() * 0.5, 4);
        }

        return data;
    }

    private static FootprintSize configToFootprintSize(ImprintConfig.FootprintSizes configSize) {
        return switch (configSize) {
            case Tiny -> FootprintSize.SMALLEST;
            case Smaller -> FootprintSize.SMALLER;
            case Small -> FootprintSize.SMALL;
            case Medium -> FootprintSize.MEDIUM;
            case Big -> FootprintSize.LARGE;
            case Large -> FootprintSize.LARGEST;
        };
    }

    public static double getPixelOffset(FootprintSize size) {
        return (size == FootprintSize.SMALLEST || size == FootprintSize.SMALL || size == FootprintSize.LARGE) ? 1.0 : 0.0;
    }
}
