package net.tejty.gamediscs.util.component;

import net.minecraft.component.ComponentType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.tejty.gamediscs.GameDiscsMod;

import java.util.function.UnaryOperator;

public class DataComponentTypeRegistry {
    public static final ComponentType<NbtCompound> NBT = register("nbt_compond",
            builder -> builder.codec(NbtCompound.CODEC));

    private static <T>ComponentType<T> register(String name,
                                                UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(GameDiscsMod.MOD_ID, name),
                builderOperator.apply(ComponentType.builder()).build());
    }

    public static void registerDataComponentType() {
        GameDiscsMod.LOGGER.info("Registering Data Component Type for " + GameDiscsMod.MOD_ID);
    }
}
