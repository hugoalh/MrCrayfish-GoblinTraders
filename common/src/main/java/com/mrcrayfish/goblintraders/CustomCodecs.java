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
    public static final Codec<ItemStack> ITEMSTACK = RecordCodecBuilder.create(builder ->
        builder.group(
            BuiltInRegistries.ITEM.holderByNameCodec().fieldOf("item")
                .forGetter(ItemStack::getItemHolder),
            Codec.INT.optionalFieldOf("count", 1)
                .forGetter(ItemStack::getCount),
            CompoundTag.CODEC.optionalFieldOf("nbt")
                .forGetter(stack -> Optional.ofNullable(stack.getTag()))
        ).apply(builder, ItemStack::new)
    );

    public static final Codec<EnchantmentInstance> ENCHANTMENT_INSTANCE = RecordCodecBuilder.create(builder ->
        builder.group(
            BuiltInRegistries.ENCHANTMENT.byNameCodec().fieldOf("enchantment")
                .forGetter(instance -> instance.enchantment),
            Codec.INT.optionalFieldOf("level", 1)
                .forGetter(instance -> instance.level)
        ).apply(builder, EnchantmentInstance::new)
    );

    public static final Codec<MobEffectInstance> MOD_EFFECT_INSTANCE = RecordCodecBuilder.create(builder ->
        builder.group(
            BuiltInRegistries.MOB_EFFECT.byNameCodec().fieldOf("id")
                .forGetter(MobEffectInstance::getEffect),
            Codec.INT.optionalFieldOf("duration", 1)
                .forGetter(MobEffectInstance::getDuration),
            Codec.INT.optionalFieldOf("amplifier", 1)
                .forGetter(MobEffectInstance::getAmplifier),
            Codec.BOOL.optionalFieldOf("show_particles", true)
                .forGetter(MobEffectInstance::isVisible)
        ).apply(builder, (effect, duration, amplifier, visible) -> {
            return new MobEffectInstance(effect, duration, amplifier, false, visible);
        })
    );
}
