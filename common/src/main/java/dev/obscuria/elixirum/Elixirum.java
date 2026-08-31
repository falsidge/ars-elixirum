package dev.obscuria.elixirum;

import dev.obscuria.elixirum.client.ClientAlchemy;
import dev.obscuria.elixirum.common.alchemy.elixir.ConfiguredElixir;
import dev.obscuria.elixirum.common.alchemy.elixir.ElixirPrefix;
import dev.obscuria.elixirum.common.alchemy.essence.Essence;
import dev.obscuria.elixirum.common.alchemy.ingredient.IngredientPreset;
import dev.obscuria.elixirum.common.alchemy.ingredient.Ingredients;
import dev.obscuria.elixirum.network.*;
import dev.obscuria.elixirum.registry.*;
import dev.obscuria.elixirum.server.ServerAlchemy;
import dev.obscuria.elixirum.server.commands.EssenceCommand;
import dev.obscuria.elixirum.server.commands.RegenerateCommand;
import dev.obscuria.elixirum.server.hooks.MinecraftServerHooks;
import dev.obscuria.fragmentum.Fragmentum;
import dev.obscuria.fragmentum.content.network.FragmentumNetworking;
import dev.obscuria.fragmentum.content.network.PayloadRegistrar;
import dev.obscuria.fragmentum.content.registry.FragmentumRegistry;
import dev.obscuria.fragmentum.content.registry.Registrar;
import dev.obscuria.fragmentum.server.FragmentumServerRegistry;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Elixirum
{
    public static final String MODID = "elixirum";
    public static final String DISPLAY_NAME = "Ars Elixirum";
    public static final Logger LOG = LoggerFactory.getLogger(DISPLAY_NAME);
    public static final int WATER_COLOR = FastColor.ARGB32.opaque(-13083194);
    public static final Style STYLE = Style.EMPTY.withFont(Elixirum.key("elixirum"));
    public static final Registrar REGISTRAR = FragmentumRegistry.registrar(MODID);
    public static final PayloadRegistrar PAYLOAD_REGISTRAR = FragmentumNetworking.registrar(MODID);

    public static ResourceLocation key(String name)
    {
        return ResourceLocation.fromNamespaceAndPath(MODID, name);
    }

    public static double getPotionMastery(@Nullable Entity entity)
    {
        return entity instanceof LivingEntity living
                ? living.getAttributeValue(ElixirumAttributes.POTION_MASTERY.holder())
                : 0.0;
    }

    public static double getPotionImmunity(@Nullable Entity entity)
    {
        return entity instanceof LivingEntity living
                ? living.getAttributeValue(ElixirumAttributes.POTION_IMMUNITY.holder())
                : 0.0;
    }

    public static Ingredients getIngredients()
    {
        return Fragmentum.PLATFORM.isClient()
                ? ClientAlchemy.getIngredients()
                : ServerAlchemy.getIngredients();
    }

    @ApiStatus.Internal
    public static void init()
    {
        ElixirumSounds.init();
        ElixirumAttributes.init();
        ElixirumMobEffects.init();
        ElixirumItems.init();
        ElixirumBlocks.init();
        ElixirumEntityTypes.init();
        ElixirumBlockEntityTypes.init();
        ElixirumDataComponents.init();
        ElixirumParticleTypes.init();
        ElixirumRecipeSerializers.init();
        ElixirumCreativeTabs.init();

        registerEvents();

        FragmentumServerRegistry.registerCommand(EssenceCommand::register);
        FragmentumServerRegistry.registerCommand(RegenerateCommand::register);

        REGISTRAR.createSyncedDataRegistry(ElixirumRegistries.ESSENCE, () -> Essence.DIRECT_CODEC);
        REGISTRAR.createSyncedDataRegistry(ElixirumRegistries.ELIXIR_PREFIX, () -> ElixirPrefix.DIRECT_CODEC);
        REGISTRAR.createSyncedDataRegistry(ElixirumRegistries.CONFIGURED_ELIXIR, () -> ConfiguredElixir.DIRECT_CODEC);
        REGISTRAR.createDataRegistry(ElixirumRegistries.INGREDIENT_PRESET, () -> IngredientPreset.DIRECT_CODEC);

        PAYLOAD_REGISTRAR.registerClientbound(
                ClientboundDiscoverPayload.class,
                ClientboundDiscoverPayload.TYPE,
                ClientboundDiscoverPayload.STREAM_CODEC,
                ClientboundDiscoverPayload::handle);
        PAYLOAD_REGISTRAR.registerClientbound(
                ClientboundIngredientsPayload.class,
                ClientboundIngredientsPayload.TYPE,
                ClientboundIngredientsPayload.STREAM_CODEC,
                ClientboundIngredientsPayload::handle);
        PAYLOAD_REGISTRAR.registerClientbound(
                ClientboundProfilePayload.class,
                ClientboundProfilePayload.TYPE,
                ClientboundProfilePayload.STREAM_CODEC,
                ClientboundProfilePayload::handle);

        PAYLOAD_REGISTRAR.registerServerbound(
                ServerboundCollectionActionPayload.class,
                ServerboundCollectionActionPayload.TYPE,
                ServerboundCollectionActionPayload.STREAM_CODEC,
                ServerboundCollectionActionPayload::handle);
        PAYLOAD_REGISTRAR.registerServerbound(
                ServerboundProfilePayload.class,
                ServerboundProfilePayload.TYPE,
                ServerboundProfilePayload.STREAM_CODEC,
                ServerboundProfilePayload::handle);
    }

    private static void registerEvents()
    {
        MinecraftServerHooks.SERVER_STARTED.register(ServerAlchemy::whenServerStarted);
        MinecraftServerHooks.START_DATA_PACK_RELOAD.register(ServerAlchemy::whenResourcesReloaded);
        MinecraftServerHooks.AFTER_SAVE.register(ServerAlchemy::whenServerSaved);
        MinecraftServerHooks.SERVER_STOPPED.register(ServerAlchemy::whenServerStopped);
    }
}