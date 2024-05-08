package com.mrcrayfish.goblintraders.trades;

import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;

import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public class GoblinMerchantOffer extends MerchantOffer
{
    public GoblinMerchantOffer(MerchantOffer offer)
    {
        super(offer.getItemCostA(), offer.getItemCostB(), offer.getResult(), offer.getUses(), offer.getMaxUses(), offer.getDemand(), offer.getPriceMultiplier(), offer.getXp());
    }

    public GoblinMerchantOffer(ItemCost paymentStack, Optional<ItemCost> secondaryPaymentStack, ItemStack offerStack, int maxUses, int experience, float priceMultiplier)
    {
        super(paymentStack, secondaryPaymentStack, offerStack, maxUses, experience, priceMultiplier);
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

        DataComponentPredicate predicate = cost.components();

        // Special case when checking enchantments, allow over leveling instead of exact level
        ItemStack costCopy = cost.itemStack().copy();
        if(givenCopy.has(DataComponents.STORED_ENCHANTMENTS) && costCopy.has(DataComponents.STORED_ENCHANTMENTS))
        {
            ItemEnchantments givenEnchantments = givenCopy.get(DataComponents.STORED_ENCHANTMENTS);
            ItemEnchantments paymentEnchantments = costCopy.get(DataComponents.STORED_ENCHANTMENTS);
            boolean hasEnchants = paymentEnchantments.entrySet().stream().allMatch(entry -> {
                return givenEnchantments.getLevel(entry.getKey().value()) >= entry.getIntValue();
            });

            // Not perfect, but good enough to allow
            if(hasEnchants) {
                return true;
            }

            // Remove since we don't want to test enchantments again
            DataComponentPredicate.Builder builder = DataComponentPredicate.builder();
            predicate.asPatch().entrySet().forEach(entry -> {
                entry.getValue().ifPresent(o -> {
                    if(entry.getKey() != DataComponents.STORED_ENCHANTMENTS) {
                        //noinspection unchecked
                        builder.expect((DataComponentType<? super Object>) entry.getKey(), o);
                    }
                });
            });
            predicate = builder.build();
        }

        // Finally compare all the components
        return predicate.test(givenCopy);
    }
}
