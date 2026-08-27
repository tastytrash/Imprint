package org.tastytrash.imprint.util;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.tastytrash.imprint.client.ImprintClient;
import org.tastytrash.imprint.config.ImprintConfig;
import org.tastytrash.imprint.particle.ParticleRegistry;

//? >= 26.1 {
import net.minecraft.world.entity.monster.zombie.Zombie;
//? } else {
/*import net.minecraft.world.entity.monster.Zombie;
*///? }

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FootprintUtil {
    private static final Map<Identifier, FootprintData> ENTITY_FOOTPRINTS = new HashMap<>();
    private static final Set<Identifier> IGNORED_ENTITIES = new HashSet<>();

    public record FootprintData(FootprintSize size, double footOffset, int baseTickInterval) {}

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

    public static void loadFromJson() {
        FootprintDataLoader.load();
    }

    public static void registerData(Identifier entityId, FootprintData data) {
        ENTITY_FOOTPRINTS.put(entityId, data);
    }

    public static void registerIgnoredEntity(Identifier entityId) {
        IGNORED_ENTITIES.add(entityId);
    }

    @Nullable
    public static FootprintData getFootprintData(Entity entity) {
        if (shouldIgnoreEntity(entity)) return null;

        Identifier entityId = EntityType.getKey(entity.getType());
        FootprintData data = ENTITY_FOOTPRINTS.get(entityId);

        if (data == null) {
            data = estimateFootprintData(entity);
        }

        if (entity instanceof Player) {
            FootprintSize size = configToFootprintSize(ImprintClient.config.footprintSizes);
            return new FootprintData(size, ImprintClient.config.footOffset, data.baseTickInterval());
        }

        if (entity instanceof Zombie zombie && zombie.isBaby()) {
            return new FootprintUtil.FootprintData(FootprintSize.SMALL, data.footOffset() * 0.5, 4);
        }

        return data;
    }

    private static boolean shouldIgnoreEntity(Entity entity) {
        if (!(entity instanceof LivingEntity)) return true;

        Identifier entityId = EntityType.getKey(entity.getType());

        if (IGNORED_ENTITIES.contains(entityId)) return true;

        if (!entity.onGround() && !entity.isInWater()) return true;

        return false;
    }

    private static FootprintData estimateFootprintData(Entity entity) {
        float width = entity.getBbWidth();

        FootprintSize size;
        double footOffset;

        if (width < 0.4f) {
            size = FootprintSize.SMALLEST;
            footOffset = width * 0.5;
        } else if (width < 0.6f) {
            size = FootprintSize.SMALLER;
            footOffset = width * 0.4;
        } else if (width < 0.9f) {
            size = FootprintSize.SMALL;
            footOffset = width * 0.35;
        } else if (width < 1.2f) {
            size = FootprintSize.MEDIUM;
            footOffset = width * 0.3;
        } else if (width < 1.8f) {
            size = FootprintSize.LARGE;
            footOffset = width * 0.25;
        } else {
            size = FootprintSize.LARGEST;
            footOffset = width * 0.2;
        }

        int tickInterval = ImprintClient.config.tickInterval;
        return new FootprintData(size, footOffset, tickInterval);
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

    public static int calculateDynamicTickInterval(Entity entity, double speed) {
        FootprintData data = getFootprintData(entity);
        double baseInterval;

        if (entity instanceof Player) {
            baseInterval = ImprintClient.config.tickInterval;
        } else {
            baseInterval = (data != null) ? data.baseTickInterval() : ImprintClient.config.tickInterval;
        }

        double baseWalkingSpeed = 0.216;
        double speedFactor = Math.max(0.5, baseWalkingSpeed / speed);
        return (int) Math.max(1, baseInterval * speedFactor);
    }

    public static boolean isParticleInsideSolidBlock(ClientLevel world, Vec3 center, double size) {
        double halfSize = size / 2;
        Vec3[] corners;

        if (ImprintClient.config.simplifiedCollisionCheck) {
            corners = new Vec3[] {
                    new Vec3(center.x - halfSize, center.y, center.z - halfSize),
                    new Vec3(center.x + halfSize, center.y, center.z + halfSize)
            };
        } else {
            corners = new Vec3[] {
                    new Vec3(center.x - halfSize, center.y, center.z - halfSize),
                    new Vec3(center.x + halfSize, center.y, center.z - halfSize),
                    new Vec3(center.x - halfSize, center.y, center.z + halfSize),
                    new Vec3(center.x + halfSize, center.y, center.z + halfSize)
            };
        }

        for (Vec3 corner : corners) {
            BlockPos cornerPos = BlockPos.containing(corner.x, corner.y, corner.z);
            BlockState cornerState = world.getBlockState(cornerPos);

            if (cornerState.isAir()) return false;

            if (cornerState.getFluidState().isSource()) return true;

            VoxelShape cornerShape = cornerState.getCollisionShape(world, cornerPos);
            if (cornerShape.isEmpty()) return false;

            Vec3 localPoint = new Vec3(corner.x - cornerPos.getX(), corner.y - cornerPos.getY(), corner.z - cornerPos.getZ());
            if (cornerShape.toAabbs().stream().noneMatch(aabb -> aabb.contains(localPoint))) {
                return false;
            }
        }
        return true;
    }
}
