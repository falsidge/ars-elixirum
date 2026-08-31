package dev.obscuria.elixirum.server.hooks;

import dev.obscuria.fragmentum.content.util.event.Event;
import net.minecraft.server.MinecraftServer;

public final class MinecraftServerHooks
{
    public static final Event<ServerEvent> SERVER_STARTED = new Event<>();
    public static final Event<ServerEvent> START_DATA_PACK_RELOAD = new Event<>();
    public static final Event<ServerEvent> AFTER_SAVE = new Event<>();
    public static final Event<ServerEvent> SERVER_STOPPED = new Event<>();

    @FunctionalInterface
    public interface ServerEvent
    {
        void invoke(MinecraftServer minecraft);
    }
}
