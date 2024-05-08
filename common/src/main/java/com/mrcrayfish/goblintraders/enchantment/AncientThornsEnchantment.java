package com.mrcrayfish.goblintraders.enchantment;

import com.mrcrayfish.goblintraders.Config;
import com.mrcrayfish.goblintraders.core.ModEnchantments;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ThornsEnchantment;

/**
 * Author: MrCrayfish
 */
public class AncientThornsEnchantment extends ThornsEnchantment implements IAncientEnchantment
{
    private final Enchantment original;

    public AncientThornsEnchantment(EnchantmentDefinition definition, Enchantment original)
    {
        super(definition);
        this.original = original;
        ModEnchantments.ORIGINAL_TO_ANCIENT.put(this.original, this);
    }

    @Override
    protected boolean checkCompatibility(Enchantment enchantment)
    {
        return enchantment != this.original;
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
