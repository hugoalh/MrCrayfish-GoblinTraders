package com.mrcrayfish.goblintraders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public final class CustomCodecs
{
    public static final Codec<EnchantmentInstance> ENCHANTMENT_INSTANCE = RecordCodecBuilder.create(builder ->
        builder.group(
            BuiltInRegistries.ENCHANTMENT.byNameCodec().fieldOf("enchantment")
                .forGetter(instance -> instance.enchantment),
            Codec.INT.optionalFieldOf("level", 1)
                .forGetter(instance -> instance.level)
        ).apply(builder, EnchantmentInstance::new)
    );
}
