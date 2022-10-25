package mine.block.illagerslovearmor.mixin;

import com.google.common.collect.Maps;
import mine.block.illagerslovearmor.loot_tables.ILATables;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;
import java.util.Map;

@Mixin(IllagerEntity.class)
public abstract class AbstractIllagerMixin extends RaiderEntity {
    private static final Map<EquipmentSlot, Identifier> EQUIPMENT_SLOT_ITEMS = Util.make(Maps.newHashMap(),
            (slotItems) -> {
                slotItems.put(EquipmentSlot.HEAD, ILATables.ILLAGER_HELMET);
                slotItems.put(EquipmentSlot.CHEST, ILATables.ILLAGER_CHEST);
                slotItems.put(EquipmentSlot.LEGS, ILATables.ILLAGER_LEGGINGS);
                slotItems.put(EquipmentSlot.FEET, ILATables.ILLAGER_FEET);
            });

    protected AbstractIllagerMixin(EntityType<? extends RaiderEntity> p_37839_, World p_37840_) {
        super(p_37839_, p_37840_);
    }

    @Override
    public EntityData initialize(ServerWorldAccess p_34297_, LocalDifficulty p_34298_,
            SpawnReason p_34299_, @Nullable EntityData p_34300_, @Nullable NbtCompound p_34301_) {
        if (this.getRaid() != null && p_34299_ == SpawnReason.EVENT) {
            this.giveArmorOnRaids();
        } else {
            this.giveArmorNaturally(p_34298_);
        }
        return super.initialize(p_34297_, p_34298_, p_34299_, p_34300_, p_34301_);
    }

    private static float getWaveArmorChances(int waves) {
        return switch (waves) {
            case 0 -> 0.30f;
            case 1 -> 0.32f;
            case 2 -> 0.34f;
            case 3 -> 0.38f;
            case 4 -> 0.40f;
            case 5 -> 0.42f;
            case 6 -> 0.44f;
            case 7 -> 0.48f;
            default -> 0;
        };
    }

    public void giveArmorOnRaids() {
        float difficultyChance = this.world.getDifficulty() == Difficulty.HARD ? 0.1F : 0.25F;
        int illagerWaves = this.getRaid().getGroupsSpawned();
        float waveChances = getWaveArmorChances(illagerWaves);
        if (this.getRandom().nextFloat() < waveChances) {
            boolean flag = true;
            for (EquipmentSlot equipmentslottype : EquipmentSlot.values()) {
                if (equipmentslottype.getType() == EquipmentSlot.Type.ARMOR) {
                    if (!flag && this.random.nextFloat() < difficultyChance) {
                        break;
                    }
                    flag = false;
                    for (ItemStack stack : this.getItemsFromLootTable(equipmentslottype)) {
                        this.equipStack(equipmentslottype, stack);
                    }
                }
            }
        }
    }

    public List<ItemStack> getItemsFromLootTable(EquipmentSlot slot) {
        if (EQUIPMENT_SLOT_ITEMS.containsKey(slot)) {
            LootTable loot = this.world.getServer().getLootManager().getTable(EQUIPMENT_SLOT_ITEMS.get(slot));
            LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerWorld) this.world))
                    .parameter(LootContextParameters.THIS_ENTITY, this).random(this.getRandom());
            return loot.generateLoot(lootcontext$builder.build(ILATables.SLOT));
        }
        return null;
    }

    protected void giveArmorNaturally(LocalDifficulty p_21383_) {
        if (this.random.nextFloat() < 0.45F * p_21383_.getClampedLocalDifficulty()) {
            int i = this.random.nextInt(2);
            float f = this.world.getDifficulty() == Difficulty.HARD ? 0.1F : 0.25F;
            if (this.random.nextFloat() < 0.095F) {
                ++i;
            }

            if (this.random.nextFloat() < 0.095F) {
                ++i;
            }

            if (this.random.nextFloat() < 0.095F) {
                ++i;
            }

            boolean flag = true;

            for (EquipmentSlot equipmentslot : EquipmentSlot.values()) {
                if (equipmentslot.getType() == EquipmentSlot.Type.ARMOR) {
                    ItemStack itemstack = this.getEquippedStack(equipmentslot);
                    if (!flag && this.random.nextFloat() < f) {
                        break;
                    }

                    flag = false;
                    if (itemstack.isEmpty()) {
                        Item item = getEquipmentForSlot(equipmentslot, i);
                        if (item != null) {
                            this.equipStack(equipmentslot, new ItemStack(item));
                        }
                    }
                }
            }
        }

    }
}
