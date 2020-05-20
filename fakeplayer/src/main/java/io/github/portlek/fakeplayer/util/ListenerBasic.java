package io.github.portlek.fakeplayer.util;

import java.util.function.Consumer;
import java.util.function.Predicate;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public final class ListenerBasic<T extends Event> {

    @NotNull
    private final Class<T> tClass;

    @NotNull
    private final Predicate<T> predicate;

    @NotNull
    private final Consumer<T> consumer;

    @NotNull
    private final EventPriority eventPriority;

    public ListenerBasic(@NotNull final Class<T> tClass, @NotNull final Predicate<T> predicate, @NotNull final Consumer<T> consumer) {
        this(tClass, predicate, consumer, EventPriority.NORMAL);
    }

    public ListenerBasic(@NotNull final Class<T> tClass, @NotNull final Predicate<T> predicate, @NotNull final Consumer<T> consumer, @NotNull final EventPriority eventPriority) {
        this.tClass = tClass;
        this.predicate = predicate;
        this.consumer = consumer;
        this.eventPriority = eventPriority;
    }

    public ListenerBasic(@NotNull final Class<T> tClass, @NotNull final EventPriority eventPriority,
                         @NotNull final Consumer<T> consumer) {
        this(tClass, t -> true, consumer, eventPriority);
    }

    public ListenerBasic(@NotNull final Class<T> tClass, @NotNull final Consumer<T> consumer) {
        this(tClass, t -> true, consumer, EventPriority.NORMAL);
    }

    @SuppressWarnings("unchecked")
    public void register(@NotNull final Plugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvent(
            this.tClass,
            new Listener() {
            },
            this.eventPriority,
            (listener, event) -> {
                if (event.getClass().equals(this.tClass) && this.predicate.test((T) event)) {
                    this.consumer.accept((T) event);
                }
            },
            plugin
        );
    }

}
