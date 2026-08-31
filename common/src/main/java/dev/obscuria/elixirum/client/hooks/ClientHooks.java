package dev.obscuria.elixirum.client.hooks;

import dev.obscuria.elixirum.client.ClientAlchemy;
import dev.obscuria.elixirum.client.ElixirumKeyMappings;
import dev.obscuria.elixirum.client.sound.CauldronSoundInstance;
import dev.obscuria.fragmentum.content.util.event.Event;
import net.minecraft.client.Minecraft;

public final class ClientHooks
{
    private static final long START_TIME = System.currentTimeMillis();
    public static float seconds;
    public static final Event<WorldRender> START_RENDER = new Event<>();
    public static final Event<ClientTick> START_CLIENT_TICK = new Event<>();


    public static void init()
    {
        START_RENDER.register(() ->
                seconds = (System.currentTimeMillis() - START_TIME) / 1000f);

        START_CLIENT_TICK.register(minecraft -> {
            CauldronSoundInstance.onClientTick();
            while (ElixirumKeyMappings.MENU.consumeClick())
                ElixirumKeyMappings.menuPressed(minecraft);
        });
    }

    @FunctionalInterface
    public interface ClientTick
    {
        void invoke(Minecraft minecraft);
    }

    @FunctionalInterface
    public interface WorldRender
    {
        void invoke();
    }

    public static void onDisconnect()
    {
        ClientAlchemy.clearCache();
    }
}
