    package org.tastytrash.imprint.client.particle;

    import net.fabricmc.api.EnvType;
    import net.fabricmc.api.Environment;
    import net.minecraft.client.multiplayer.ClientLevel;
    import net.minecraft.client.particle.Particle;
    import net.minecraft.client.particle.ParticleProvider;
    import net.minecraft.client.particle.SingleQuadParticle;
    import net.minecraft.client.particle.SpriteSet;
    import net.minecraft.core.BlockPos;
    import net.minecraft.core.particles.SimpleParticleType;
    import net.minecraft.world.level.block.state.BlockState;
    import net.minecraft.util.RandomSource;
    import org.jspecify.annotations.NonNull;
    import org.tastytrash.imprint.client.ImprintClient;
    import java.awt.Color;

    public class FootprintParticle extends SingleQuadParticle {
        private final SpriteSet sprites;

        private static final float BRIGHTNESS_MULTIPLIER = 0.6f;
        private static final float HARDNESS_OFFSET = 0.5f;
        private static final float HARDNESS_SCALE = 4.0f;
        private static final float HARDNESS_MULTIPLIER = 0.8f;

        public FootprintParticle(ClientLevel level, double x, double y, double z, double velX, double velY, double velZ, float yaw, SpriteSet sprites, float size) {
            super(level, x, y, z, 0.0, 0.0, 0.0, sprites.first());

            this.sprites = sprites;
            this.lifetime = ImprintClient.config.footprintLifetime;
            this.quadSize = size * (float) ImprintClient.config.scale;
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
            this.setAlpha(alpha);

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

            this.setSprite(this.sprites.get(Math.max(0, this.age - (this.lifetime * 9) / 10), this.lifetime / 10));

            if (this.age >= this.lifetime) {
                this.remove();
            }
        }

        @Override
        protected @NonNull Layer getLayer() {
            return Layer.TRANSLUCENT;
        }

        @Override
        public @NonNull FacingCameraMode getFacingCameraMode() {
            return (target, camera, partialTickTime) -> {
                target.set(-0.7071F, 0.0F, 0.0F, 0.7071F);
            };
        }

        @Environment(EnvType.CLIENT)
        public record Factory(SpriteSet sprites, float size) implements ParticleProvider<SimpleParticleType> {

            @Override
            public Particle createParticle(@NonNull SimpleParticleType parameters, @NonNull ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, @NonNull RandomSource random) {
                float yaw = (float) Math.toDegrees(Math.atan2(velocityZ, velocityX));
                return new FootprintParticle(world, x, y, z, velocityX, velocityY, velocityZ, yaw, sprites, size);
            }
        }
    }