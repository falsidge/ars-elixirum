package dev.obscuria.elixirum.registry;


import dev.obscuria.elixirum.Elixirum;
import dev.obscuria.elixirum.common.effect.GrowMobEffect;
import dev.obscuria.elixirum.common.effect.ShrinkMobEffect;
import dev.obscuria.fragmentum.content.registry.DeferredMobEffect;
import net.minecraft.world.effect.MobEffect;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public interface ElixirumMobEffects
{
    DeferredMobEffect<GrowMobEffect> GROW = register("grow", GrowMobEffect::new);
    DeferredMobEffect<ShrinkMobEffect> SHRINK = register("shrink", ShrinkMobEffect::new);

    private static <T extends MobEffect> DeferredMobEffect<T>
    register(final String name,
             Supplier<MobEffect> supplier)
    {
        return Elixirum.REGISTRAR.registerMobEffect(
                Elixirum.key(name),
                supplier);
    }

    static void acceptTranslations(BiConsumer<String, String> consumer)
    {
        consumer.accept(GROW.get().getDescriptionId(), "Grow");
        consumer.accept(SHRINK.get().getDescriptionId(), "Shrink");
    }

    static void init() {}
}
