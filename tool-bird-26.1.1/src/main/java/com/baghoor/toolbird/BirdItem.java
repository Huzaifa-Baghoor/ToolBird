package com.baghoor.toolbird;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class BirdItem extends Item {

    public BirdItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide()) {
            player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 60, 2));
            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 200, 0));
            player.addEffect(new MobEffectInstance(MobEffects.SPEED, 200, 1));
            player.getCooldowns().addCooldown(
                player.getItemInHand(hand), 300);
        }
        return InteractionResult.SUCCESS;
    }
}