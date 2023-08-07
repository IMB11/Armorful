package com.mineblock11.armorful.item;

import com.mineblock11.armorful.wolves.WolfArmorData;
import net.minecraft.item.DyeableItem;

public class DyeableWolfArmorItem extends WolfArmorItem implements DyeableItem {

    public DyeableWolfArmorItem(WolfArmorData data) {
        super(data);
    }

    public DyeableWolfArmorItem(WolfArmorData data, boolean fireproof) {
        super(data, fireproof);
    }
}
