package com.mrcrayfish.goblintraders.trades;

import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;

import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public class GoblinMerchantOffer extends MerchantOffer
{
    private final Entity trader;

    public GoblinMerchantOffer(Entity trader, ItemCost paymentStack, Optional<ItemCost> secondaryPaymentStack, ItemStack offerStack, int maxUses, int experience, float priceMultiplier)
    {
        super(paymentStack, secondaryPaymentStack, offerStack, maxUses, experience, priceMultiplier);
        this.trader = trader;
    }

    @Override
    public boolean satisfiedBy(ItemStack primary, ItemStack secondary)
    {
        return this.isMatching(primary, Optional.of(this.getItemCostA())) && primary.getCount() >= this.getCostA().getCount() && this.isMatching(secondary, this.getItemCostB()) && secondary.getCount() >= this.getCostB().getCount();
    }

    private boolean isMatching(ItemStack given, Optional<ItemCost> costOptional)
    {
        if(costOptional.isEmpty() && given.isEmpty())
            return true;

        if(costOptional.isEmpty())
            return true;

        ItemStack givenCopy = given.copy();
        if(givenCopy.getMaxDamage() > 0)
        {
            givenCopy.setDamageValue(givenCopy.getDamageValue());
        }

        // Check if the same item
        ItemCost cost = costOptional.get();
        if(!givenCopy.is(cost.item()))
            return false;

        // Special case when checking enchantments, allow over leveling instead of exact level
        ItemStack costCopy = cost.itemStack().copy();
        if(givenCopy.has(DataComponents.ENCHANTMENTS) && costCopy.has(DataComponents.ENCHANTMENTS))
        {
            ItemEnchantments givenEnchantments = givenCopy.getEnchantments();
            ItemEnchantments paymentEnchantments = costCopy.getEnchantments();
            boolean hasEnchants = paymentEnchantments.entrySet().stream().allMatch(entry -> {
                return givenEnchantments.getLevel(entry.getKey().value()) >= entry.getIntValue();
            });
            if(!hasEnchants) {
                return false;
            }
            // Remove since we don't want to test again
            givenCopy.remove(DataComponents.ENCHANTMENTS);
            costCopy.remove(DataComponents.ENCHANTMENTS);
        }

        // Remove item name since we don't care about it
        givenCopy.remove(DataComponents.ITEM_NAME);
        costCopy.remove(DataComponents.ITEM_NAME);

        // Finally compare all the components
        return DataComponentPredicate.allOf(costCopy.getComponents()).test(givenCopy.getComponents());
    }
}
