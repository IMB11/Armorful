package com.mineblock11.armorful.wolves;

import net.minecraft.item.ItemStack;

public interface WolfDataAccessor {
    ItemStack getWolfArmor();

    void setWolfArmor(ItemStack stack);
}