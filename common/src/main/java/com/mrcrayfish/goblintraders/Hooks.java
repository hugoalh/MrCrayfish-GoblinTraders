package com.mrcrayfish.goblintraders;

import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.trading.ItemCost;

import java.util.function.Consumer;

/**
 * Author: MrCrayfish
 */
public class Hooks
{
    public static int getEnchantmentLevel(Enchantment enchantment)
    {
        return enchantment.getMaxLevel();
    }

    public static boolean costTest(ItemStack given, ItemCost cost)
    {
        if(given.is(Items.ENCHANTED_BOOK) && given.is(cost.item()))
        {
            ItemStack givenCopy = given.copy();
            ItemStack costCopy = cost.itemStack().copy();
            if(givenCopy.has(DataComponents.STORED_ENCHANTMENTS) && costCopy.has(DataComponents.STORED_ENCHANTMENTS))
            {
                // Only compare the cost enchantments
                ItemEnchantments givenEnchantments = givenCopy.get(DataComponents.STORED_ENCHANTMENTS);
                ItemEnchantments costEnchantments = costCopy.get(DataComponents.STORED_ENCHANTMENTS);
                if(givenEnchantments != null && costEnchantments != null)
                {
                    return costEnchantments.entrySet().stream().allMatch(entry -> {
                        return givenEnchantments.getLevel(entry.getKey().value()) >= entry.getIntValue();
                    });
                }
            }
        }
        return cost.test(given);
    }
}
