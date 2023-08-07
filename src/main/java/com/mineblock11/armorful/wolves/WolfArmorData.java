package com.mineblock11.armorful.wolves;

import com.mineblock11.armorful.Armorful;
import com.mineblock11.armorful.item.DyeableWolfArmorItem;
import com.mineblock11.armorful.item.WolfArmorDispenserBehavior;
import com.mineblock11.armorful.item.WolfArmorItem;
import com.mineblock11.armorful.util.ArmorfulUtil;
import draylar.staticcontent.api.ContentData;
import net.minecraft.block.DispenserBlock;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class WolfArmorData implements ContentData {

    private final String name;
    private final int bonus;
    private final boolean fireproof;
    private final boolean dyeable;

    public WolfArmorData(int bonus, String name, boolean fireproof, boolean dyeable) {
        this.bonus = bonus;
        this.name = name;
        this.fireproof = fireproof;
        this.dyeable = dyeable;
    }

    @Override
    public void register(Identifier identifier) {
        Item item;

        if (isFireproof()) {
            if (dyeable) {
                item = new DyeableWolfArmorItem(this, true);
            } else {
                item = new WolfArmorItem(this, true);
            }
        } else {
            if (dyeable) {
                item = new DyeableWolfArmorItem(this);
            } else {
                item = new WolfArmorItem(this);
            }
        }

        var registeredItem = Registry.register(Registries.ITEM, ArmorfulUtil.id(name + "_wolf_armor"), item);
        Armorful.WOLF_ARMOR_ITEMS.add(registeredItem);
        DispenserBlock.registerBehavior(item, new WolfArmorDispenserBehavior());
    }

    public int getBonus() {
        return bonus;
    }

    public boolean isFireproof() {
        return fireproof;
    }

    public String getName() {
        return name;
    }
}