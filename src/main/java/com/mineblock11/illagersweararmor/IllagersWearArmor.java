package com.mineblock11.illagersweararmor;

import com.mineblock11.illagersweararmor.loot_tables.ILATables;
import com.mineblock11.illagersweararmor.loot_tables.RaidWaveCondition;
import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class IllagersWearArmor implements ModInitializer {
    public static final String MODID = "illagersweararmor";
    @Override
    public void onInitialize() {
        ILATables.init();
        Registry.register(Registries.LOOT_CONDITION_TYPE, new Identifier(MODID, "wave"), RaidWaveCondition.WAVE);
    }
}
