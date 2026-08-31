package dev.obscuria.elixirum;

import dev.obscuria.elixirum.registry.ElixirumAttributes;
import dev.obscuria.elixirum.server.hooks.MinecraftServerHooks;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;

@Mod(Elixirum.MODID)
public class NeoElixirum {
    public NeoElixirum(IEventBus eventBus) {
        Elixirum.init();
        NeoForge.EVENT_BUS.register(Events.class);
        eventBus.addListener(NeoElixirum::onEntityAttributeModification);
    }

    public static void onEntityAttributeModification(EntityAttributeModificationEvent event) {
        for (EntityType<? extends LivingEntity> entityType : event.getTypes()) {
            event.add(entityType, ElixirumAttributes.POTION_MASTERY.holder());
            event.add(entityType, ElixirumAttributes.POTION_IMMUNITY.holder());
        }
    }

    public static final class Events {
        @SubscribeEvent
        private static void onServerStarted(ServerStartedEvent event) {
            MinecraftServerHooks.SERVER_STARTED.broadcast(listener -> {
                listener.invoke(event.getServer());
            });
        }

        @SubscribeEvent
        private static void onServerStopped(ServerStoppedEvent event) {
            MinecraftServerHooks.SERVER_STOPPED.broadcast(listener -> {
                listener.invoke(event.getServer());
            });
        }

        @SubscribeEvent
        public static void onSave(LevelEvent.Save event) {
            MinecraftServerHooks.AFTER_SAVE.broadcast(listener -> {
                listener.invoke(event.getLevel().getServer());
            });
        }

        @SubscribeEvent
        public static void onStartDatapackReload(OnDatapackSyncEvent event) {
            MinecraftServerHooks.START_DATA_PACK_RELOAD.broadcast(listener -> {
                if (event.getPlayer() != null) {
                    listener.invoke(event.getPlayer().getServer());
                }
            });
        }
    }
}