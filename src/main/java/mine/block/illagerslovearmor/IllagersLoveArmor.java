package mine.block.illagerslovearmor;

import mine.block.illagerslovearmor.loot_tables.ILATables;
import mine.block.illagerslovearmor.loot_tables.RaidWaveCondition;
import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class IllagersLoveArmor implements ModInitializer {
    public static final String MODID = "illagerslovearmor";
    @Override
    public void onInitialize() {
        ILATables.init();
        Registry.register(Registries.LOOT_CONDITION_TYPE, new Identifier(MODID, "wave"), RaidWaveCondition.WAVE);
    }
}
