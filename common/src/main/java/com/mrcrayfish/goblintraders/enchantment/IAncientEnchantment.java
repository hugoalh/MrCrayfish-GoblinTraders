package com.mrcrayfish.goblintraders.enchantment;

import net.minecraft.world.item.enchantment.Enchantment;

/**
 * Author: MrCrayfish
 */
public interface IAncientEnchantment
{
    Enchantment getAncientEnchantment();

    Enchantment getOriginal();

    int getAncientLevel(int level);
}
