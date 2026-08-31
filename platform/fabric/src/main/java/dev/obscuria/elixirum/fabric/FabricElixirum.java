package dev.obscuria.elixirum.fabric;

import dev.obscuria.elixirum.Elixirum;
import dev.obscuria.elixirum.server.hooks.MinecraftServerHooks;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public final class FabricElixirum implements ModInitializer {
    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
            MinecraftServerHooks.SERVER_STOPPED.broadcast(listener -> {
                listener.invoke(server);
            });
        });

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            MinecraftServerHooks.SERVER_STARTED.broadcast(listener -> {
                listener.invoke(server);
            });
        });
        ServerLifecycleEvents.AFTER_SAVE.register((server, flush, force) -> {
            MinecraftServerHooks.AFTER_SAVE.broadcast(listener -> {
                listener.invoke(server);
            });
        });
        ServerLifecycleEvents.START_DATA_PACK_RELOAD.register((server, resourceManager) -> {
            MinecraftServerHooks.START_DATA_PACK_RELOAD.broadcast(listener -> {
                listener.invoke(server);
            });
        });
        Elixirum.init();
    }
}
