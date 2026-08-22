package org.tastytrash.imprint.client.util;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.tastytrash.imprint.client.ImprintClient;

public class FootprintUtils {

    public static int calculateDynamicTickInterval(Entity entity, double speed) {
        FootprintSizeUtils.FootprintData data = FootprintSizeUtils.getFootprintData(entity);
        double baseInterval = (data != null) ? data.baseTickInterval() : ImprintClient.config.tickInterval;

        double walkingSpeed = 0.118;
        double speedFactor = Math.max(0.5, walkingSpeed / speed);
        return (int) Math.max(1, baseInterval * speedFactor);
    }

    public static boolean isParticleInsideSolidBlock(ClientLevel world, Vec3 center, double size) {
        Vec3[] corners = new Vec3[] {
                new Vec3(center.x - size / 2, center.y, center.z - size / 2),
                new Vec3(center.x + size / 2, center.y, center.z - size / 2),
                new Vec3(center.x - size / 2, center.y, center.z + size / 2),
                new Vec3(center.x + size / 2, center.y, center.z + size / 2)
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