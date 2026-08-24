 package org.tastytrash.imprint.particle;

//? if fabric {
/*import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
*///? }
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.tastytrash.imprint.client.ImprintClient;

//? < 26.1 {
 import net.minecraft.client.renderer.RenderType;
//? }

import java.awt.*;

public class FootprintParticle extends SingleQuadParticle {
	private final SpriteSet sprites;

	private static final float BRIGHTNESS_MULTIPLIER = 0.6f;
	private static final float HARDNESS_OFFSET = 0.5f;
	private static final float HARDNESS_SCALE = 4.0f;
	private static final float HARDNESS_MULTIPLIER = 0.8f;

	public FootprintParticle(ClientLevel level, double x, double y, double z, double velX, double velY, double velZ, float yaw, SpriteSet sprites, float size) {
		//? < 26.1 {
		 super(level, x, y, z);
		//? } else {
		/*super(level, x, y, z, 0.0, 0.0, 0.0, sprites.first());
		*///? }

		this.sprites = sprites;
		this.lifetime = Mth.abs((int) (ImprintClient.config.footprintLifetime * 20));
		this.quadSize = size;
		this.gravity = 0.0f;

		BlockPos pos = BlockPos.containing(x, y - 0.01, z);
		BlockState state = level.getBlockState(pos);
		int blockColor = state.getMapColor(level, pos).col;
		float r = ((blockColor >> 16) & 0xFF) / 255.0f;
		float g = ((blockColor >> 8) & 0xFF) / 255.0f;
		float b = (blockColor & 0xFF) / 255.0f;
		float brightness = (0.299f * r + 0.587f * g + 0.114f * b);
		float hardness = state.getDestroySpeed(level, pos);
		float hardnessFactor = Math.min(1.0f, (hardness - HARDNESS_OFFSET) / HARDNESS_SCALE);
		if (hardnessFactor < 0 || hardnessFactor > (1 / HARDNESS_MULTIPLIER)) hardnessFactor = (1 / HARDNESS_MULTIPLIER);
		float alpha = (1.0f - brightness * BRIGHTNESS_MULTIPLIER) * (1.0f - hardnessFactor * HARDNESS_MULTIPLIER);
		alpha *= ImprintClient.config.alpha / 100.0f;
		this.setAlpha(Math.max(alpha, 0.15f));

		if (ImprintClient.config.rainbowMode) {
			float hue = (float) (velX * 0.1) % 1.0f;
			int rgb = Color.HSBtoRGB(hue, 1.0f, 0.8f);
			float r1 = ((rgb >> 16) & 0xFF) / 255.0f;
			float g1 = ((rgb >> 8) & 0xFF) / 255.0f;
			float b1 = (rgb & 0xFF) / 255.0f;
			this.setColor(r1, g1, b1);
		} else {
			int footprintColor = ImprintClient.config.footprintColor;
			float r1 = ((footprintColor >> 16) & 0xFF) / 255.0f;
			float g1 = ((footprintColor >> 8) & 0xFF) / 255.0f;
			float b1 = (footprintColor & 0xFF) / 255.0f;
			this.setColor(r1, g1, b1);
		}

		this.xd = 0.0;
		this.yd = 0.0;
		this.zd = 0.0;

		this.hasPhysics = false;
		this.xo = x;
		this.yo = y;
		this.zo = z;
	}

	@Override
	public void tick() {
		this.age++;
		//? >= 26.1 {
		/*this.setSprite(this.sprites.get(Math.max(0, this.age - (this.lifetime - 5)), 5));
		*///? }
		if (this.age >= this.lifetime) {
			this.remove();
		}
	}

	//? >= 26.1 {
	/*@Override
	protected Layer getLayer() {
		return Layer.TRANSLUCENT;
	}
	*///? }

	//? > 1.20.1 {
	/*@Override
	public FacingCameraMode getFacingCameraMode() {
		return (target, camera, partialTickTime) -> {
			target.set(-0.7071F, 0.0F, 0.0F, 0.7071F);
		};
	}

	*///? } else {
	@Override
	public void render(com.mojang.blaze3d.vertex.VertexConsumer buffer, net.minecraft.client.Camera camera, float partialTicks) {
		// provided by Gemini ai
		net.minecraft.world.phys.Vec3 cameraPos = camera.getPosition();
		float x = (float) (net.minecraft.util.Mth.lerp(partialTicks, this.xo, this.x) - cameraPos.x());
		float y = (float) (net.minecraft.util.Mth.lerp(partialTicks, this.yo, this.y) - cameraPos.y());
		float z = (float) (net.minecraft.util.Mth.lerp(partialTicks, this.zo, this.z) - cameraPos.z());

		float size = this.getQuadSize(partialTicks);
		float u0 = this.getU0(), u1 = this.getU1();
		float v0 = this.getV0(), v1 = this.getV1();
		int light = this.getLightColor(partialTicks);

		buffer.vertex(x - size, y, z - size).uv(u1, v1).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
		buffer.vertex(x - size, y, z + size).uv(u1, v0).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
		buffer.vertex(x + size, y, z + size).uv(u0, v0).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
		buffer.vertex(x + size, y, z - size).uv(u0, v1).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
	}
	//? }

	//? < 26.1 {
	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}

	@Override
	protected float getU0() {
		return this.sprites.get(Math.max(0, this.age - (this.lifetime - 5)), 5).getU0();
	}

	@Override
	protected float getU1() {
		return this.sprites.get(Math.max(0, this.age - (this.lifetime - 5)), 5).getU1();
	}

	@Override
	protected float getV0() {
		return this.sprites.get(Math.max(0, this.age - (this.lifetime - 5)), 5).getV0();
	}

	@Override
	protected float getV1() {
		return this.sprites.get(Math.max(0, this.age - (this.lifetime - 5)), 5).getV1();
	}
	//? }

	//? >= 26.1 && fabric {
	/*@Environment(EnvType.CLIENT)
	public record Factory(SpriteSet sprites, float size) implements ParticleProvider<SimpleParticleType> {

		@Override
		public Particle createParticle(SimpleParticleType parameters, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, RandomSource random) {
			float yaw = (float) Math.toDegrees(Math.atan2(velocityZ, velocityX));
			return new FootprintParticle(world, x, y, z, velocityX, velocityY, velocityZ, yaw, sprites, size);
		}
	}

	*///? } else if fabric {
	/*@Environment(EnvType.CLIENT)
	public record Factory(SpriteSet sprites, float size) implements ParticleProvider<SimpleParticleType> {

		@Nullable
		@Override
		public Particle createParticle(SimpleParticleType particleOptions, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			float yaw = (float) Math.toDegrees(Math.atan2(i, g));
			return new FootprintParticle(clientLevel, d, e, f, g, h, i, yaw, sprites, size);
		}
	}

	*///? } else if neoforge || forge && < 26.1 {
	public record Factory(SpriteSet sprites, float size) implements ParticleProvider<SimpleParticleType> {

		@Override
		public Particle createParticle(SimpleParticleType particleOptions, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			return new FootprintParticle(clientLevel, d, e, f, g, h, i, 0.0f, sprites, size);
		}
	}
	//? } else if neoforge {
		/*public record Factory(SpriteSet sprites, float size) implements ParticleProvider<SimpleParticleType> {

		@Override
		public @org.jspecify.annotations.Nullable Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double v, double v1, double v2, double v3, double v4, double v5, RandomSource randomSource) {
			float yaw = (float) Math.toDegrees(Math.atan2(v5, v3));
			return new FootprintParticle(clientLevel, v, v1, v2, v3, v4, v5, yaw, sprites, size);
		}
	}
	*///? }
}
