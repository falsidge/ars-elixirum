package dev.obscuria.elixirum;

import dev.obscuria.elixirum.client.ElixirumLayers;
import dev.obscuria.elixirum.client.hooks.ClientHooks;
import dev.obscuria.elixirum.client.model.ModelGlassCauldron;
import dev.obscuria.elixirum.client.particle.ElixirBubbleParticle;
import dev.obscuria.elixirum.client.particle.ElixirSplashParticle;
import dev.obscuria.elixirum.client.renderer.GlassCauldronRenderer;
import dev.obscuria.elixirum.client.renderer.PotionShelfRenderer;
import dev.obscuria.elixirum.client.renderer.ThrownElixirRenderer;
import dev.obscuria.elixirum.client.sound.CauldronSoundInstance;
import dev.obscuria.elixirum.common.alchemy.ExtractContents;
import dev.obscuria.elixirum.common.alchemy.elixir.ElixirContents;
import dev.obscuria.elixirum.common.alchemy.elixir.ElixirStyle;
import dev.obscuria.elixirum.common.block.entity.GlassCauldronEntity;
import dev.obscuria.elixirum.registry.ElixirumBlockEntityTypes;
import dev.obscuria.elixirum.registry.ElixirumEntityTypes;
import dev.obscuria.elixirum.registry.ElixirumItems;
import dev.obscuria.elixirum.registry.ElixirumParticleTypes;
import dev.obscuria.fragmentum.client.ClientRegistrar;
import dev.obscuria.fragmentum.client.FragmentumClientRegistry;
import org.jetbrains.annotations.ApiStatus;

public final class ElixirumClient
{
    public static final ClientRegistrar CLIENT_REGISTRAR = FragmentumClientRegistry.registrar(Elixirum.MODID);

    public static float getSeconds()
    {
        return ClientHooks.seconds;
    }

    public static void playBoilingSound(GlassCauldronEntity entity)
    {
        CauldronSoundInstance.play(entity);
    }

    @ApiStatus.Internal
    public static void init()
    {
        CLIENT_REGISTRAR.registerItemProperty(Elixirum.key("shape"), ElixirStyle::getShapePredicate);
        CLIENT_REGISTRAR.registerItemProperty(Elixirum.key("cap"), ElixirStyle::getCapPredicate);

        CLIENT_REGISTRAR.registerItemColor(ElixirContents::getOverlayColor, ElixirumItems.ELIXIR);
        CLIENT_REGISTRAR.registerItemColor(ElixirContents::getOverlayColor, ElixirumItems.SPLASH_ELIXIR);
        CLIENT_REGISTRAR.registerItemColor(ElixirContents::getOverlayColor, ElixirumItems.WITCH_TOTEM_OF_UNDYING);
        CLIENT_REGISTRAR.registerItemColor(ExtractContents::getOverlayColor, ElixirumItems.EXTRACT);

        CLIENT_REGISTRAR.registerEntityRenderer(ElixirumEntityTypes.THROWN_ELIXIR, ThrownElixirRenderer::new);
        CLIENT_REGISTRAR.registerBlockEntityRenderer(ElixirumBlockEntityTypes.GLASS_CAULDRON, GlassCauldronRenderer::new);
        CLIENT_REGISTRAR.registerBlockEntityRenderer(ElixirumBlockEntityTypes.POTION_SHELF, PotionShelfRenderer::new);

        CLIENT_REGISTRAR.registerModelLayer(ElixirumLayers.GLASS_CAULDRON, ModelGlassCauldron::createBodyLayer);
        CLIENT_REGISTRAR.registerModelLayer(ElixirumLayers.GLASS_CAULDRON_FLUID, ModelGlassCauldron::createFluidLayer);

        CLIENT_REGISTRAR.registerParticleRenderer(ElixirumParticleTypes.ELIXIR_BUBBLE, new ElixirBubbleParticle.Provider());
        CLIENT_REGISTRAR.registerTexturedParticleRenderer(ElixirumParticleTypes.ELIXIR_SPLASH, ElixirSplashParticle.Provider::new);

        ClientHooks.init();
    }
}
