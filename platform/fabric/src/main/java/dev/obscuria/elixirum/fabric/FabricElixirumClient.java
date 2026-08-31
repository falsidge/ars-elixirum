package dev.obscuria.elixirum.fabric;

import dev.obscuria.elixirum.ElixirumClient;
import dev.obscuria.elixirum.client.ElixirumKeyMappings;
import dev.obscuria.elixirum.client.hooks.ClientHooks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;

public final class FabricElixirumClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WorldRenderEvents.START.register(context -> {
            ClientHooks.START_RENDER.broadcast(ClientHooks.WorldRender::invoke);
        });
        ClientTickEvents.START_CLIENT_TICK.register(minecraft -> {
            ClientHooks.START_CLIENT_TICK.broadcast(listener -> {
                listener.invoke(minecraft);
            });
        });
        KeyBindingHelper.registerKeyBinding(ElixirumKeyMappings.MENU);
        ElixirumClient.init();
    }
}
