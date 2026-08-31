package dev.obscuria.elixirum.registry;

import dev.obscuria.elixirum.Elixirum;
import dev.obscuria.elixirum.common.entity.ThrownElixirProjectile;
import dev.obscuria.fragmentum.content.registry.DeferredEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public interface ElixirumEntityTypes
{
    DeferredEntity<ThrownElixirProjectile> THROWN_ELIXIR = register("thrown_elixir",
            () -> EntityType.Builder.<ThrownElixirProjectile>of(ThrownElixirProjectile::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10));

    private static <T extends Entity> DeferredEntity<T>
    register(final String name,
             Supplier<EntityType.Builder<T>> builder)
    {
        return Elixirum.REGISTRAR.registerEntity(
                Elixirum.key(name),
                () -> builder.get().build(name));
    }

    static void acceptTranslations(BiConsumer<String, String> consumer)
    {
        consumer.accept(THROWN_ELIXIR.get().getDescriptionId(), "Thrown Elixir");
    }

    static void init() {}
}
