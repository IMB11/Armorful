package mine.block.illagerslovearmor;

import mine.block.illagerslovearmor.loot_tables.ILATables;
import mine.block.illagerslovearmor.loot_tables.RaidWaveCondition;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class IllagersLoveArmor implements ModInitializer {
    public static final String MODID = "illagerslovearmor";
    @Override
    public void onInitialize() {
        ILATables.init();
        Registry.register(Registry.LOOT_CONDITION_TYPE, new Identifier(MODID, "wave"), RaidWaveCondition.WAVE);
    }
}
