package com.baghoor.toolbird;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public class ToolbirdMod implements ModInitializer {

    public static final String MOD_ID = "toolbird";

    public static final BridgeItem BRIDGE_ITEM = registerItem("bridge_item",
        new BridgeItem(new Item.Properties().durability(10).setId(
            ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MOD_ID, "bridge_item")))));

    public static final BirdItem BIRD_ITEM = registerItem("bird_item",
        new BirdItem(new Item.Properties().setId(
            ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MOD_ID, "bird_item")))));

    private static <T extends Item> T registerItem(String name, T item) {
        Registry.register(BuiltInRegistries.ITEM,
            Identifier.fromNamespaceAndPath(MOD_ID, name), item);
        return item;
    }

    @Override
    public void onInitialize() {
        ToolLevelHandler.register();
    }
}