package com.mrcrayfish.goblintraders.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.goblintraders.Constants;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;

/**
 * Author: MrCrayfish
 */
@RegistryContainer
public class ModStats
{
    public static final RegistryEntry<ResourceLocation> TRADE_WITH_GOBLIN = RegistryEntry.customStat(new ResourceLocation(Constants.MOD_ID, "trade_with_goblin"), StatFormatter.DEFAULT);
}
