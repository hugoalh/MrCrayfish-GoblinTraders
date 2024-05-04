package com.mrcrayfish.goblintraders.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

/**
 * Author: MrCrayfish
 */
public class PlatformLootTableProvider
{
    public static void addProviders(FabricDataGenerator.Pack pack)
    {
        pack.addProvider(Entity::new);
    }

    public static class Entity extends SimpleFabricLootTableProvider
    {
        public Entity(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture)
        {
            super(output, registriesFuture, LootContextParamSets.ENTITY);
        }

        @Override
        public void generate(HolderLookup.Provider provider, BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer)
        {
            GoblinLootTableProvider.accept(new PlatformLootBuilder.Entity(consumer));
        }
    }
}
