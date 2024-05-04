package com.mrcrayfish.goblintraders.item;

import com.mojang.serialization.MapCodec;
import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.component.CustomData;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class GoblinSpawnEggItem extends SpawnEggItem
{
    private static final MapCodec<EntityType<?>> ENTITY_TYPE_FIELD_CODEC = BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("id");
    private final Supplier<EntityType<? extends AbstractGoblinEntity>> type;

    @SuppressWarnings("DataFlowIssue")
    public GoblinSpawnEggItem(Supplier<EntityType<? extends AbstractGoblinEntity>> type, int primaryColour, int secondaryColour, Properties properties)
    {
        super(null, primaryColour, secondaryColour, properties);
        this.type = type;
    }

    @Override
    public EntityType<?> getType(ItemStack stack)
    {
        CustomData data = stack.getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY);
        return !data.isEmpty() ? data.read(ENTITY_TYPE_FIELD_CODEC).result().orElse(this.type.get()) : this.type.get();
    }

    @Override
    public FeatureFlagSet requiredFeatures()
    {
        return this.type.get().requiredFeatures();
    }
}
