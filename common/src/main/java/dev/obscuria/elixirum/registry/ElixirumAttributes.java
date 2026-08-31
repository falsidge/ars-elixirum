package dev.obscuria.elixirum.registry;

import dev.obscuria.elixirum.Elixirum;
import dev.obscuria.fragmentum.content.registry.DeferredAttribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

import java.util.function.BiConsumer;

public interface ElixirumAttributes
{

    DeferredAttribute POTION_MASTERY = register("potion_mastery");
    DeferredAttribute POTION_IMMUNITY = register("potion_immunity");

    private static DeferredAttribute register(String name)
    {
        final var description = "attribute.elixirum.%s".formatted(name);
        return Elixirum.REGISTRAR.registerAttribute(Elixirum.key(name),
                () -> new RangedAttribute(description, 0, 0, 3600));
    }

    static void acceptTranslations(BiConsumer<String, String> consumer)
    {
        consumer.accept(POTION_MASTERY.get().getDescriptionId(), "Potion Mastery");
        consumer.accept(POTION_IMMUNITY.get().getDescriptionId(), "Potion Immunity");
    }

    static void init() {}
}
