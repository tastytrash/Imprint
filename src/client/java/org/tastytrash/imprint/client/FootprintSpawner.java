package org.tastytrash.imprint.client;

import com.google.common.collect.Maps;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;
import java.util.UUID;

public class FootprintSpawner {
    private static final Map<UUID, EntityState> entityStates = Maps.newHashMap();

    private static class EntityState {
        int tickCounter = 0;
        boolean isLeftFoot = true;
        boolean wasOnGround = false;
        int stepCounter = 0;
    }

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.level == null || client.player == null) return;

            for (Entity entity : client.level.entitiesForRendering()) {
                if (!(entity instanceof LivingEntity livingEntity)) continue;
                boolean shouldProcess = entity == client.player || 
                                       (entity instanceof Player && ImprintClient.config.showOtherPlayers) || 
                                       (!(entity instanceof Player) && ImprintClient.config.showMobs);
                if (!shouldProcess) continue;

                EntityState state = entityStates.computeIfAbsent(entity.getUUID(), uuid -> new EntityState());
                boolean isOnGround = livingEntity.onGround();

                if (isOnGround && !state.wasOnGround) {
                    state.tickCounter = ImprintClient.config.tickInterval;
                    state.isLeftFoot = true;
                }
                state.wasOnGround = isOnGround;

                double speedThreshold = (entity instanceof Player) ? ImprintClient.config.speedThreshold : ImprintClient.config.mobSpeedThreshold;
                double speed = livingEntity.getDeltaMovement().horizontal().length();

                if (isOnGround && (ImprintClient.config.showWhileCrouching || !livingEntity.isCrouching()) && speed > speedThreshold) {
                    state.tickCounter++;
                    if (state.tickCounter >= ImprintClient.config.tickInterval) {
                        spawnFootprint(client, livingEntity, state);
                    }
                }
            }

            entityStates.entrySet().removeIf(entry -> {
                Entity entity = client.level.getEntity(entry.getKey());
                return entity == null || !entity.isAlive();
            });
        });
    }

    private static void spawnFootprint(net.minecraft.client.Minecraft client, LivingEntity entity, EntityState state) {
        state.tickCounter = 0;
        state.isLeftFoot = !state.isLeftFoot;

        double moveAngle = Math.atan2(entity.getZ() - entity.zOld, entity.getX() - entity.xOld);
        double sideAngle = moveAngle + (state.isLeftFoot ? Math.PI / 2 : -Math.PI / 2);
        double xOffset = Mth.cos(sideAngle) * ImprintClient.config.footOffset;
        double zOffset = Mth.sin(sideAngle) * ImprintClient.config.footOffset;

        double x = Math.floor((entity.getX() - xOffset) * 16) / 16;
        double y = Math.floor(entity.getY() * 16) / 16 + 0.001 + entity.getRandom().nextFloat() * 0.001;
        double z = Math.floor((entity.getZ() - zOffset) * 16) / 16;

        if (ImprintClient.config.enabled && isParticleInsideSolidBlock(client.level, new Vec3(x, y - 0.01, z), 0.1875f * (float) ImprintClient.config.particleSettings.scale + 1 / 8f)) {
            state.stepCounter++;
            client.level.addParticle(ParticleRegistry.FOOTPRINT, x, y, z, state.stepCounter, 0, 0);
        }
    }

    public static boolean isParticleInsideSolidBlock(ClientLevel world, Vec3 center, double size) {
        Vec3[] corners = new Vec3[] {
            new Vec3(center.x - size/2, center.y, center.z - size/2),
            new Vec3(center.x + size/2, center.y, center.z - size/2),
            new Vec3(center.x - size/2, center.y, center.z + size/2),
            new Vec3(center.x + size/2, center.y, center.z + size/2)
        };

        for (Vec3 corner : corners) {
            if (!isPointInsideSolidBlock(world, corner)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPointInsideSolidBlock(ClientLevel world, Vec3 point) {
        BlockPos pos = BlockPos.containing(point.x, point.y, point.z);
        BlockState state = world.getBlockState(pos);

        if (state.isAir()) return false;

        VoxelShape collisionShape = state.getCollisionShape(world, pos);
        if (collisionShape.isEmpty()) return false;

        Vec3 localPoint = new Vec3(point.x - pos.getX(), point.y - pos.getY(), point.z - pos.getZ());
        return collisionShape.toAabbs().stream().anyMatch(aabb -> aabb.contains(localPoint));
    }
}
