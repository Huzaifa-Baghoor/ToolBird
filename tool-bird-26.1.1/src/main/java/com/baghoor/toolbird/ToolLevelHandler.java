package com.baghoor.toolbird;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.enchantment.ItemEnchantments;

public class ToolLevelHandler {
    public static void register() {
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, entity) -> {
            if (!(world instanceof ServerLevel serverLevel)) return;

            var stack = player.getMainHandItem();
            if (stack.isEmpty()) return;

            var nbt = stack.getOrDefault(DataComponents.CUSTOM_DATA,
                CustomData.EMPTY).copyTag();
            int uses = nbt.getInt("toolbird_uses").orElse(0) + 1;
            nbt.putInt("toolbird_uses", uses);
            stack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));

            var enchantmentRegistry = serverLevel.registryAccess()
                .lookupOrThrow(net.minecraft.core.registries.Registries.ENCHANTMENT);

            int level = 0;
            if (uses >= 100) level = 3;
            else if (uses >= 50) level = 2;
            else if (uses >= 20) level = 1;

            if (level > 0) {
                var efficiencyHolder = enchantmentRegistry.getOrThrow(Enchantments.EFFICIENCY);
                var existing = stack.getOrDefault(DataComponents.ENCHANTMENTS,
                    ItemEnchantments.EMPTY);
                if (existing.getLevel(efficiencyHolder) < level) {
                    var mutable = new ItemEnchantments.Mutable(existing);
                    mutable.set(efficiencyHolder, level);
                    stack.set(DataComponents.ENCHANTMENTS, mutable.toImmutable());
                }
            }
        });
    }
}