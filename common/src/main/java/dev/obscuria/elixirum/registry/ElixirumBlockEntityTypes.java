package dev.obscuria.elixirum.registry;

import dev.obscuria.elixirum.Elixirum;
import dev.obscuria.elixirum.common.block.entity.GlassCauldronEntity;
import dev.obscuria.elixirum.common.block.entity.PotionShelfEntity;
import dev.obscuria.fragmentum.FragmentumFactory;
import dev.obscuria.fragmentum.content.registry.DeferredBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public interface ElixirumBlockEntityTypes
{
    DeferredBlockEntity<GlassCauldronEntity> GLASS_CAULDRON = register("glass_cauldron.json",
            () -> FragmentumFactory.newBlockEntityType(GlassCauldronEntity::new, ElixirumBlocks.GLASS_CAULDRON.get()));
    DeferredBlockEntity<PotionShelfEntity> POTION_SHELF = register("potion_shelf",
            () -> FragmentumFactory.newBlockEntityType(PotionShelfEntity::new, ElixirumBlocks.POTION_SHELF.get()));

    @SuppressWarnings("DataFlowIssue")
    private static <T extends BlockEntity> DeferredBlockEntity<T>
    register(final String name, Supplier<BlockEntityType.Builder<T>> builder)
    {
        return Elixirum.REGISTRAR.registerBlockEntity(
                Elixirum.key(name),
                () -> builder.get().build(null));
    }

    static void init() {}
}
