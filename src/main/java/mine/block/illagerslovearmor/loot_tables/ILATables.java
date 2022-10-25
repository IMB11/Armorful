package mine.block.illagerslovearmor.loot_tables;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import mine.block.illagerslovearmor.IllagersLoveArmor;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class ILATables {
    public static final Identifier ILLAGER_HELMET = new Identifier(IllagersLoveArmor.MODID,
            "entities/illager_helmet");
    public static final Identifier ILLAGER_CHEST = new Identifier(IllagersLoveArmor.MODID,
            "entities/illager_chestplate");
    public static final Identifier ILLAGER_LEGGINGS = new Identifier(IllagersLoveArmor.MODID,
            "entities/illager_legs");
    public static final Identifier ILLAGER_FEET = new Identifier(IllagersLoveArmor.MODID,
            "entities/illager_feet");

    public static final BiMap<Identifier, LootContextType> REGISTRY = HashBiMap.create();
    public static final LootContextType SLOT = register("slot", (table) -> {
        table.require(LootContextParameters.THIS_ENTITY);
    });

    public static LootContextType register(String p_81429_, Consumer<LootContextType.Builder> p_81430_) {
        LootContextType.Builder LootContextType$builder = new LootContextType.Builder();
        p_81430_.accept(LootContextType$builder);
        LootContextType LootContextType = LootContextType$builder.build();
        Identifier Identifier = new Identifier(IllagersLoveArmor.MODID + p_81429_);
        LootContextType LootContextType1 = REGISTRY.put(Identifier, LootContextType);
        if (LootContextType1 != null) {
            throw new IllegalStateException("Loot table parameter set " + Identifier + " is already registered");
        } else {
            return LootContextType;
        }
    }

    public static void init() {
    }
}
