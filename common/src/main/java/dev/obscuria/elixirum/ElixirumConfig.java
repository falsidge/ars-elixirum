package dev.obscuria.elixirum;

import dev.obscuria.fragmentum.config.ConfigBuilder;
import dev.obscuria.fragmentum.config.ConfigValue;

public final class ElixirumConfig
{
    public static final ConfigValue<Boolean> testBoolean;
    public static final ConfigValue<Integer> testInteger;

    static
    {
        final var builder = new ConfigBuilder("obscuria/elixirum.toml");

        builder.push("TestSection");
        testBoolean = builder.define("testBoolean", true);
        testInteger = builder.defineInt("testInteger", 5, 0, 10);
        builder.pop();

        builder.buildClient(Elixirum.MODID);
    }
}
