package com.mineblock11.armorful.item;

import com.mineblock11.armorful.util.ArmorfulUtil;
import com.mineblock11.armorful.wolves.WolfArmorData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;

public class WolfArmorItem extends Item {

    private final int bonusProtection;
    private final Identifier entityTexture;
    private final WolfArmorData data;

    public WolfArmorItem(WolfArmorData data) {
        super(new Item.Settings().maxCount(1));
        this.bonusProtection = data.getBonus();
        this.data = data;
        this.entityTexture = ArmorfulUtil.id("textures/entity/wolf/armor/wolf_armor_" + data.getName() + ".png");
    }

    public WolfArmorItem(WolfArmorData data, boolean isFireproof) {
        super(new Item.Settings().maxCount(1).fireproof());
        this.bonusProtection = data.getBonus();
        this.data = data;
        this.entityTexture = ArmorfulUtil.id("textures/entity/wolf/armor/wolf_armor_" + data.getName() + ".png");
    }

    @Environment(EnvType.CLIENT)
    public Identifier getEntityTexture() {
        return this.entityTexture;
    }

    public int getBonus(ItemStack stack) {
        int protectionLevel = EnchantmentHelper.getLevel(Enchantments.PROTECTION, stack) * 2;
        return this.bonusProtection + protectionLevel;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        tooltip.add(Text.empty());
        tooltip.add(Text.translatable("armorful.tooltip.when_equipped").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("armorful.tooltip.bonus", getBonus(stack)).formatted(Formatting.BLUE));

        if (stack.hasEnchantments()) {
            tooltip.add(Text.empty());
        }
    }
}