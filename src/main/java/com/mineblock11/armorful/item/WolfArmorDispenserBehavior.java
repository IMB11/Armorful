package com.mineblock11.armorful.item;

import com.mineblock11.armorful.util.ArmorfulUtil;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.util.Iterator;
import java.util.List;

public class WolfArmorDispenserBehavior extends FallibleItemDispenserBehavior {

    @Override
    public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        BlockPos blockPos = pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));

        List<WolfEntity> wolves = pointer
                .getWorld()
                .getEntitiesByClass(WolfEntity.class, new Box(blockPos), wolf -> wolf.isAlive() && ArmorfulUtil.getWolfData(wolf).getWolfArmor().isEmpty());

        Iterator<WolfEntity> iterator = wolves.iterator();
        WolfEntity wolf;
        do {
            if (!iterator.hasNext()) {
                return super.dispenseSilently(pointer, stack);
            }

            wolf = iterator.next();
        } while (!(stack.getItem() instanceof WolfArmorItem) || !wolf.isTamed());

        ArmorfulUtil.getWolfData(wolf).setWolfArmor(stack.split(1));
        this.setSuccess(true);
        return stack;
    }
}