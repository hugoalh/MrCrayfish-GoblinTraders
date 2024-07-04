package com.mrcrayfish.goblintraders.enchantment;

import com.mrcrayfish.goblintraders.Config;
import com.mrcrayfish.goblintraders.core.ModEnchantments;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public class AncientDamageEnchantment extends DamageEnchantment implements IAncientEnchantment
{
    private final Enchantment original;

    public AncientDamageEnchantment(EnchantmentDefinition definition, Optional<TagKey<EntityType<?>>> targets, Enchantment original)
    {
        super(definition, targets);
        this.original = original;
        ModEnchantments.ORIGINAL_TO_ANCIENT.put(this.original, this);
    }

    @Override
    public float getDamageBonus(int level, @Nullable EntityType<?> type)
    {
        return super.getDamageBonus(this.getAncientLevel(level), type);
    }

    @Override
    public Component getFullname(int level)
    {
        Component name = Component.translatable(this.original.getDescriptionId());
        return Component.translatable("enchantment.goblintraders.ancient", name)
            .withStyle(ChatFormatting.LIGHT_PURPLE);
    }

    @Override
    public boolean isTreasureOnly()
    {
        return Config.SERVER.ancientEnchantments.treasureOnly.get();
    }

    @Override
    public boolean isDiscoverable()
    {
        return !Config.SERVER.ancientEnchantments.goblinsOnly.get();
    }

    @Override
    public boolean isTradeable()
    {
        return !Config.SERVER.ancientEnchantments.goblinsOnly.get() && !Config.SERVER.ancientEnchantments.treasureOnly.get();
    }

    @Override
    public Enchantment getAncientEnchantment()
    {
        return this;
    }

    @Override
    public Enchantment getOriginal()
    {
        return this.original;
    }

    @Override
    public int getAncientLevel(int level)
    {
        return this.original.getMaxLevel() + level * Config.SERVER.ancientEnchantments.bonusLevels.get();
    }

    // For Forge (yes, the method is spelled wrong on purpose)
    //@Override
    public boolean canApplyAtEnchatingTable(ItemStack stack)
    {
        return this.isDiscoverable();
    }

    // For NeoForge
    //@Override
    public boolean canApplyAtEnchantingTable(ItemStack stack)
    {
        return this.canApplyAtEnchatingTable(stack);
    }
}
