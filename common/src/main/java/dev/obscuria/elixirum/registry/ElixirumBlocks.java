package dev.obscuria.elixirum.registry;

import dev.obscuria.elixirum.Elixirum;
import dev.obscuria.elixirum.common.block.GlassCauldronBlock;
import dev.obscuria.elixirum.common.block.PotionShelfBlock;
import dev.obscuria.fragmentum.content.registry.DeferredBlock;
import net.minecraft.world.level.block.Block;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public interface ElixirumBlocks
{
    DeferredBlock<GlassCauldronBlock> GLASS_CAULDRON = register("glass_cauldron", GlassCauldronBlock::new);
    DeferredBlock<PotionShelfBlock> POTION_SHELF = register("potion_shelf", PotionShelfBlock::new);

    private static <T extends Block> DeferredBlock<T> register(final String name,
                                                               Supplier<T> supplier)
    {
        return Elixirum.REGISTRAR.registerBlock(
                Elixirum.key(name),
                supplier);
    }

    static void acceptTranslations(BiConsumer<String, String> consumer)
    {
        consumer.accept(GLASS_CAULDRON.get().getDescriptionId(), "Glass Cauldron");
        consumer.accept(POTION_SHELF.get().getDescriptionId(), "Potion Shelf");
    }

    static void init() {}
}
