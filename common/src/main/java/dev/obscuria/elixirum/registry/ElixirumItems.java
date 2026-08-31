package dev.obscuria.elixirum.registry;

import dev.obscuria.elixirum.Elixirum;
import dev.obscuria.elixirum.common.item.*;
import dev.obscuria.fragmentum.content.registry.DeferredBlock;
import dev.obscuria.fragmentum.content.registry.DeferredItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public interface ElixirumItems
{
    DeferredItem<AlchemistEyeItem> ALCHEMIST_EYE = register("alchemist_eye", AlchemistEyeItem::new);
    DeferredItem<ElixirItem> ELIXIR = register("elixir", ElixirItem::new);
    DeferredItem<SplashElixirItem> SPLASH_ELIXIR = register("splash_elixir", SplashElixirItem::new);
    DeferredItem<ExtractItem> EXTRACT = register("extract", ExtractItem::new);
    DeferredItem<WitchTotemOfUndyingItem> WITCH_TOTEM_OF_UNDYING = register("witch_totem_of_undying", WitchTotemOfUndyingItem::new);
    DeferredItem<BlockItem> GLASS_CAULDRON = register("glass_cauldron", blockItem(ElixirumBlocks.GLASS_CAULDRON, new Item.Properties()));
    DeferredItem<BlockItem> POTION_SHELF = register("potion_shelf", blockItem(ElixirumBlocks.POTION_SHELF, new Item.Properties()));

    private static <T extends Item> DeferredItem<T> register(String name,
                                                             Supplier<T> supplier)
    {
        return Elixirum.REGISTRAR.registerItem(
                Elixirum.key(name),
                supplier);
    }

    private static Supplier<BlockItem>
    blockItem(DeferredBlock<? extends Block> block,
              Item.Properties properties)
    {
        return () -> new BlockItem(block.get(), properties);
    }

    static void acceptTranslations(BiConsumer<String, String> consumer)
    {
        consumer.accept(ALCHEMIST_EYE.get().getDescriptionId(), "Alchemist Eye");
        consumer.accept(ELIXIR.get().getDescriptionId(), "Elixir");
        consumer.accept(SPLASH_ELIXIR.get().getDescriptionId(), "Splash Elixir");
        consumer.accept(EXTRACT.get().getDescriptionId(), "Extract");
        consumer.accept(WITCH_TOTEM_OF_UNDYING.get().getDescriptionId(), "Witch Totem of Undying");
    }

    static void init() {}
}
