package org.tastytrash.imprint.spawner;

import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
//? > 1.21.1 {
/*import net.minecraft.core.particles.ColorParticleOption;
*///? }
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.tastytrash.imprint.client.ImprintClient;
import org.tastytrash.imprint.util.FootprintSizeUtils;

//? if fabric {
/*import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
*///? } else if neoforge {
/*import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
*///? } else if forge {
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
//? }

import java.util.Map;
import java.util.UUID;

import static org.tastytrash.imprint.util.FootprintUtils.calculateDynamicTickInterval;
import static org.tastytrash.imprint.util.FootprintUtils.isParticleInsideSolidBlock;

//? if neoforge {
/*@EventBusSubscriber(modid = ImprintClient.MOD_ID, value = Dist.CLIENT)
*///? } else if forge {
@Mod.EventBusSubscriber(modid = ImprintClient.MOD_ID, value = Dist.CLIENT)
//? }
public class FootprintSpawner {
	private static final Map<UUID, EntityState> entityStates = Maps.newHashMap();

	private static class EntityState {
		int tickCounter = 0;
		boolean isRightFoot = true;
		boolean wasOnGround = false;
		boolean wasAboveThreshold = false;
		int stepCounter = 0;
		float wetness = 0.0f;
		boolean wasInWater = false;
	}

	public static void register() {
		//? if fabric {
		/*ClientTickEvents.END_CLIENT_TICK.register(client -> {
			tick(client);
		});
		*///? }
	}

	//? if neoforge {
	/*@SubscribeEvent
	public static void onTick(ClientTickEvent.Post event) {
		Minecraft client = Minecraft.getInstance();
		tick(client);
	}
	*///? } else if forge {
	@SubscribeEvent
	public static void onTick(TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			Minecraft client = Minecraft.getInstance();
			tick(client);
		}
	}
	//? }

	public static void tick(Minecraft client) {
		if (client.level == null
				|| client.player == null
				|| client.isPaused()
				|| !ImprintClient.config.enabled
				|| client.player.isVisuallySwimming()
				|| (!ImprintClient.config.showWhileCrawling && client.player.isVisuallyCrawling()))
			return;

		for (Entity entity : client.level.entitiesForRendering()) {
			if (!(entity instanceof LivingEntity livingEntity)) continue;

			boolean shouldProcess = entity == client.player ||
								   (entity instanceof Player && ImprintClient.config.showOtherPlayers) ||
								   (!(entity instanceof Player) && ImprintClient.config.showMobs);
			if (!shouldProcess) continue;

			double maxDistance = ImprintClient.config.maxRenderDistance;
			if (entity.distanceToSqr(client.player) > maxDistance * maxDistance) {
				continue;
			}

			EntityState state = entityStates.computeIfAbsent(entity.getUUID(), uuid -> new EntityState());
			boolean isOnGround = livingEntity.onGround();
			boolean isInWater = livingEntity.isInWater();

			if (ImprintClient.config.enableWetness &&
					((isInWater && !state.wasInWater)
					|| (!isInWater && client.level.isRainingAt(livingEntity.blockPosition())))) {
				state.wetness = 1.0f;
			}
			state.wasInWater = isInWater;

			if (isOnGround && !state.wasOnGround) {
				state.tickCounter = ImprintClient.config.tickInterval;
				state.isRightFoot = true;
			}
			state.wasOnGround = isOnGround;

			double speedThreshold = (entity instanceof Player) ? ImprintClient.config.speedThreshold/20 : 0.01;
			//? >= 1.21.7 {
			 /*double speed = entity.getDeltaMovement().horizontal().length();
			*///? } else {
			Vec3 movement = entity.getDeltaMovement();
			double speed = Math.sqrt(movement.x * movement.x + movement.z * movement.z);
			//? }
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
			//? >= 1.21.7 {
			 /*Entity entity = client.level.getEntity(entry.getKey());
			*///? } else {
			Entity entity = null;
			for (Entity e : client.level.entitiesForRendering()) {
				if (e.getUUID().equals(entry.getKey())) {
					entity = e;
					break;
				}
			}
			//? }
			return entity == null || !entity.isAlive();
		});
	}

	private static void spawnFootprint(Minecraft client, LivingEntity entity, EntityState state) {
		FootprintSizeUtils.FootprintData footprintData = FootprintSizeUtils.getFootprintData(entity);
		if (footprintData == null) return;
		FootprintSizeUtils.FootprintSize footprintSize = footprintData.size();

		state.tickCounter = 0;
		state.isRightFoot = !state.isRightFoot;

		if (state.wetness > 0) {
			state.wetness = Math.max(0, state.wetness - 0.15f);
		}

		double moveAngle = Math.atan2(entity.getZ() - entity.zOld, entity.getX() - entity.xOld);
		double sideAngle = moveAngle + (state.isRightFoot ? Math.PI / 2 : -Math.PI / 2);

		double footOffset = footprintData.footOffset();
		double xOffset = Mth.cos((float) sideAngle) * footOffset;
		double zOffset = Mth.sin((float) sideAngle) * footOffset;

		double pixelOffset = FootprintSizeUtils.getPixelOffset(footprintSize)/2;

		double x = (Math.floor((entity.getX() - xOffset) * 16) + pixelOffset) / 16;
		double y = Math.floor(entity.getY() * 16) / 16 + 0.001 + entity.getRandom().nextFloat() * 0.001;
		double z = (Math.floor((entity.getZ() - zOffset) * 16) + pixelOffset) / 16;

		if (client.level == null) return;

		BlockPos blockPos = entity.getOnPos();
		BlockState blockState = client.level.getBlockState(blockPos);
		BlockState feetBlockState = client.level.getBlockState(entity.blockPosition());
		Block block = blockState.getBlock();

		double yOffset = 0.0;
		if (feetBlockState.is(Blocks.SNOW)) {
			block = Blocks.SNOW_BLOCK;
			blockState = block.defaultBlockState();
			yOffset = 0.125;
		} else if (blockState.is(Blocks.MUD) || blockState.is(Blocks.SOUL_SAND)) {
			yOffset = 0.125;
		}

		if (ImprintClient.config.skipCollisionCheck || isParticleInsideSolidBlock(client.level, new Vec3(x, y - 0.01, z), footprintSize.getBaseScale() + 1/8f)) {
			state.stepCounter++;
			client.level.addParticle(footprintSize.getParticleType(), x, y + yOffset, z, state.wetness, state.stepCounter, 0.0);

			if (ImprintClient.config.enableDustParticles && shouldSpawnDustParticle(block)) {
				spawnDustParticles(client, entity, block, blockState, blockPos, x, y + yOffset, z);
			}

			if (ImprintClient.config.enableWetness && state.wetness > 0) {
				spawnWetParticles(client, entity, block, blockState, blockPos, x, y + yOffset, z, state.wetness);
			}
		}
	}

	private static void spawnDustParticles(Minecraft client, LivingEntity entity, Block block, BlockState blockState, BlockPos blockPos, double x, double y, double z) {
		//? >= 1.21.7 {

		/*double speed = entity.getDeltaMovement().horizontal().length();

		*///? } else {
		Vec3 movement = entity.getDeltaMovement();
		double speed = Math.sqrt(movement.x * movement.x + movement.z * movement.z);
		//? }
		double speedMultiplier = Math.min(speed * 10, 3.0);
		int particleCount = 3 + (int) (speedMultiplier * 3);

		for (int i = 0; i < particleCount; i++) {
			double offsetX = (entity.getRandom().nextFloat() - 0.5) * 0.6;
			double offsetZ = (entity.getRandom().nextFloat() - 0.5) * 0.6;
			double randomHeightOffset = entity.getRandom().nextFloat() * 0.2 + 0.1;

			ParticleOptions particle = getParticleForBlock(block, blockState, blockPos, client);
			assert client.level != null;
			client.level.addParticle(particle, x + offsetX, y + randomHeightOffset, z + offsetZ, 0.0, 0.0, 0.0);
		}
	}

	private static void spawnWetParticles(Minecraft client, LivingEntity entity, Block block, BlockState blockState, BlockPos blockPos, double x, double y, double z, float alphaOffset) {
		int particleCount = Math.max(1, Math.round(alphaOffset * 5));

		for (int i = 0; i < particleCount; i++) {
			double offsetX = (entity.getRandom().nextFloat() - 0.5) * 0.6;
			double offsetZ = (entity.getRandom().nextFloat() - 0.5) * 0.6;
			double randomHeightOffset = entity.getRandom().nextFloat() * 0.3 + 0.1;

			assert client.level != null;
			client.level.addParticle(ParticleTypes.FISHING, x + offsetX, y + randomHeightOffset, z + offsetZ, 0.0, -.05, 0.0);
		}
	}

	//? > 1.21.1 {

	/*private static boolean isLeafBlock(Block block) {
		return block.equals(Blocks.OAK_LEAVES) || block.equals(Blocks.SPRUCE_LEAVES) ||
			   block.equals(Blocks.BIRCH_LEAVES) || block.equals(Blocks.JUNGLE_LEAVES) ||
			   block.equals(Blocks.ACACIA_LEAVES) || block.equals(Blocks.DARK_OAK_LEAVES) ||
			   block.equals(Blocks.MANGROVE_LEAVES) || block.equals(Blocks.CHERRY_LEAVES) ||
			   block.equals(Blocks.PALE_OAK_LEAVES);
	}

	*///? }

	private static boolean shouldSpawnDustParticle(Block block) {
		return block.equals(Blocks.SAND) || block.equals(Blocks.RED_SAND) ||
				block.equals(Blocks.SNOW) || block.equals(Blocks.SNOW_BLOCK) ||
				block.equals(Blocks.GRAVEL) || block.equals(Blocks.SUSPICIOUS_GRAVEL) ||
				block.equals(Blocks.SUSPICIOUS_SAND) || block.equals(Blocks.REDSTONE_BLOCK)
				//? > 1.21.1 {
				 /*|| isLeafBlock(block);
				*///? } else {
				;
				//? }
	}

	private static ParticleOptions getParticleForBlock(Block block, BlockState blockState, BlockPos blockPos, Minecraft client) {
		//? > 1.21.1 {

		/*if (block.equals(Blocks.PALE_OAK_LEAVES)) {
			return ParticleTypes.PALE_OAK_LEAVES;
		} else if (block.equals(Blocks.CHERRY_LEAVES)) {
			return ParticleTypes.CHERRY_LEAVES;
		} else if (isLeafBlock(block)) {
			assert client.level != null;
			return ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, client.level.getClientLeafTintColor(blockPos));
		} else if (block.equals(Blocks.SNOW) || block.equals(Blocks.SNOW_BLOCK)) {
			assert client.level != null;
			return ParticleTypes.SNOWFLAKE;
		} else {
			return new BlockParticleOption(ParticleTypes.FALLING_DUST, blockState);
		}

		*///? } else {

		if (block.equals(Blocks.SNOW) || block.equals(Blocks.SNOW_BLOCK)) {
			assert client.level != null;
			return ParticleTypes.SNOWFLAKE;
		} else {
			return new BlockParticleOption(ParticleTypes.FALLING_DUST, blockState);
		}

		//? }
	}
}
