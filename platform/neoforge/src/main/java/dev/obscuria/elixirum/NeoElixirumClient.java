package dev.obscuria.elixirum;

import dev.obscuria.elixirum.client.ElixirumKeyMappings;
import dev.obscuria.elixirum.client.hooks.ClientHooks;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

@Mod(
        value = Elixirum.MODID,
        dist = Dist.CLIENT)
public final class NeoElixirumClient {
    public NeoElixirumClient(IEventBus eventBus) {
        ElixirumClient.init();
    }

    public static final class Events {
        @SubscribeEvent
        private static void onClientTick(ClientTickEvent.Pre event) {
            ClientHooks.START_CLIENT_TICK.broadcast(listener -> {
                listener.invoke(Minecraft.getInstance());
            });
        }

        @SubscribeEvent
        private static void onRenderLevel(RenderLevelStageEvent event) {
            if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY) {
                ClientHooks.START_RENDER.broadcast(ClientHooks.WorldRender::invoke);
            }
        }

        @SubscribeEvent
        public static void registerBindings(RegisterKeyMappingsEvent event) {
            event.register(ElixirumKeyMappings.MENU);
        }
    }
}
