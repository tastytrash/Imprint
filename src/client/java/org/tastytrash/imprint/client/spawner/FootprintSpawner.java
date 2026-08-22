package org.tastytrash.imprint.client.spawner;

import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.tastytrash.imprint.client.ImprintClient;
import org.tastytrash.imprint.client.util.FootprintSizeUtils;

//? if fabric {
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
//? }

import java.util.Map;
import java.util.UUID;

import static org.tastytrash.imprint.client.util.FootprintUtils.*;

public class FootprintSpawner {
    private static final Map<UUID, EntityState> entityStates = Maps.newHashMap();

    private static class EntityState {
        int tickCounter = 0;
        boolean isRightFoot = true;
        boolean wasOnGround = false;
        boolean wasAboveThreshold = false;
        int stepCounter = 0;
    }

    public static void register() {
        //? if fabric {
        ClientTickEvents.END_CLIENT_TICK.register(FootprintSpawner::tick);
        //? }
    }

    public static void tick(Minecraft client) {
        if (client.level == null || client.player == null || client.isPaused()) return;

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
                state.isRightFoot = true;
            }
            state.wasOnGround = isOnGround;

            double speedThreshold = (entity instanceof Player) ? ImprintClient.config.speedThreshold/20 : 0.01;
            double speed = livingEntity.getDeltaMovement().horizontal().length();
            boolean isAboveThreshold = speed > speedThreshold;

            if (isOnGround && (ImprintClient.config.showWhileCrouching || !livingEntity.isCrouching())) {
                if (!state.wasAboveThreshold && isAboveThreshold) {
                    state.tickCounter = calculateDynamicTickInterval(entity, speed);
                }
                state.wasAboveThreshold = isAboveThreshold;

                if (isAboveThreshold) {
                    state.tickCounter++;
                    int dynamicInterval = calculateDynamicTickInterval(entity, speed);
                    if (state.tickCounter >= dynamicInterval) {
                        spawnFootprint(client, livingEntity, state);
                    }
                }
            }
        }

        entityStates.entrySet().removeIf(entry -> {
            Entity entity = client.level.getEntity(entry.getKey());
            return entity == null || !entity.isAlive();
        });
    }

    private static void spawnFootprint(net.minecraft.client.Minecraft client, LivingEntity entity, EntityState state) {
        FootprintSizeUtils.FootprintData footprintData = FootprintSizeUtils.getFootprintData(entity);
        if (footprintData == null) return;
        FootprintSizeUtils.FootprintSize footprintSize = footprintData.size();

        state.tickCounter = 0;
        state.isRightFoot = !state.isRightFoot;

        double moveAngle = Math.atan2(entity.getZ() - entity.zOld, entity.getX() - entity.xOld);
        double sideAngle = moveAngle + (state.isRightFoot ? Math.PI / 2 : -Math.PI / 2);

        double footOffset = footprintData.footOffset();
        double xOffset = Mth.cos(sideAngle) * footOffset;
        double zOffset = Mth.sin(sideAngle) * footOffset;

        double pixelOffset = FootprintSizeUtils.getPixelOffset(footprintSize)/2;

        double x = (Math.floor((entity.getX() - xOffset) * 16) + pixelOffset) / 16;
        double y = Math.floor(entity.getY() * 16) / 16 + 0.001 + entity.getRandom().nextFloat() * 0.001;
        double z = (Math.floor((entity.getZ() - zOffset) * 16) + pixelOffset) / 16;

        if (ImprintClient.config.enabled && isParticleInsideSolidBlock(client.level, new Vec3(x, y - 0.01, z), footprintSize.getBaseScale() * (float) ImprintClient.config.scale + 1 / 8f)) {
            state.stepCounter++;
            client.level.addParticle(footprintSize.getParticleType(), x, y, z, state.stepCounter, 0, 0);
        }
    }
}
