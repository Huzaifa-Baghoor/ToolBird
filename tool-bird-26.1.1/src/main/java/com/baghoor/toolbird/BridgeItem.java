package com.baghoor.toolbird;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class BridgeItem extends Item {

    public BridgeItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        Player player = context.getPlayer();
        if (player == null) return InteractionResult.PASS;

        BlockPos origin = context.getClickedPos();
        Direction facing = player.getDirection();

        for (int i = 1; i <= 5; i++) {
            BlockPos bridgePos = origin.relative(facing, i);
            if (world.getBlockState(bridgePos).isAir()) {
                world.setBlockAndUpdate(bridgePos, Blocks.OAK_PLANKS.defaultBlockState());
            }
        }

        if (!player.isCreative()) {
            context.getItemInHand().hurtAndBreak(1, player,
                player.getUsedItemHand());
        }

        return InteractionResult.SUCCESS;
    }
}