package dev.obscuria.elixirum.registry;

import dev.obscuria.elixirum.Elixirum;
import dev.obscuria.elixirum.common.particle.ElixirBubbleParticleOptions;
import dev.obscuria.elixirum.common.particle.ElixirSplashParticleOptions;
import dev.obscuria.fragmentum.content.registry.DeferredParticle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

public interface ElixirumParticleTypes
{
    DeferredParticle<ElixirSplashParticleOptions> ELIXIR_SPLASH = register("elixir_splash", ElixirSplashParticleOptions.TYPE);
    DeferredParticle<ElixirBubbleParticleOptions> ELIXIR_BUBBLE = register("elixir_bubble", ElixirBubbleParticleOptions.TYPE);

    private static <T extends ParticleOptions> DeferredParticle<T>
    register(final String name, ParticleType<T> value)
    {
        return Elixirum.REGISTRAR.registerParticle(
                Elixirum.key(name),
                () -> value);
    }

    static void init() {}
}
