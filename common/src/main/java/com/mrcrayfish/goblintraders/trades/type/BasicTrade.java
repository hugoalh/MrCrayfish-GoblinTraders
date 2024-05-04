package com.mrcrayfish.goblintraders.trades.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrcrayfish.goblintraders.Constants;
import com.mrcrayfish.goblintraders.CustomCodecs;
import com.mrcrayfish.goblintraders.trades.GoblinTrade;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.trading.ItemCost;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public class BasicTrade implements ITradeType
{
    public static final ResourceLocation ID = new ResourceLocation(Constants.MOD_ID, "basic");
    public static final MapCodec<BasicTrade> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
        ItemStack.CODEC.fieldOf("offer_item")
            .forGetter(trade -> trade.offerStack),
        ItemStack.CODEC.fieldOf("payment_item")
            .forGetter(trade -> trade.primaryPayment),
        ItemStack.CODEC.optionalFieldOf("secondary_payment_item", ItemStack.EMPTY)
            .forGetter(trade -> trade.secondaryPayment),
        Codec.FLOAT.optionalFieldOf("price_multiplier", 0F)
            .forGetter(trade -> trade.priceMultiplier),
        Codec.INT.optionalFieldOf("max_trades", 12)
            .forGetter(trade -> trade.maxTrades),
        Codec.INT.optionalFieldOf("experience", 0)
            .forGetter(trade -> trade.experience),
        Codec.list(CustomCodecs.ENCHANTMENT_INSTANCE).optionalFieldOf("enchantments", List.of())
            .forGetter(trade -> trade.enchantments),
        Codec.list(MobEffectInstance.CODEC).optionalFieldOf("potion_effects", List.of())
            .forGetter(trade -> trade.mobEffects)
        ).apply(builder, BasicTrade::new)
    );

    private final ItemStack offerStack;
    private final ItemStack primaryPayment;
    private final ItemStack secondaryPayment;
    private final float priceMultiplier;
    private final int maxTrades;
    private final int experience;
    private final List<EnchantmentInstance> enchantments;
    private final List<MobEffectInstance> mobEffects;

    public BasicTrade(ItemStack offerStack, ItemStack primaryPayment, ItemStack secondaryPayment, float priceMultiplier, int maxTrades, int experience, List<EnchantmentInstance> enchantments, List<MobEffectInstance> mobEffects)
    {
        this.offerStack = offerStack;
        this.primaryPayment = primaryPayment;
        this.secondaryPayment = secondaryPayment;
        this.priceMultiplier = priceMultiplier;
        this.maxTrades = maxTrades;
        this.experience = experience;
        this.enchantments = enchantments;
        this.mobEffects = mobEffects;
    }

    @Override
    public ResourceLocation getTypeId()
    {
        return ID;
    }

    @Override
    public GoblinTrade createVillagerTrade()
    {
        ItemStack offerStack = this.offerStack.copy();
        if(!this.enchantments.isEmpty())
        {
            for(EnchantmentInstance data : this.enchantments)
            {
                offerStack.enchant(data.enchantment, data.level);
            }

            // TODO do we still need separate handling?
            /*if(offerStack.getItem() == Items.ENCHANTED_BOOK)
            {
                ItemEnchantments.Mutable enchants = new ItemEnchantments.Mutable(EnchantmentHelper.getEnchantmentsForCrafting(offerStack));
                this.enchantments.forEach(instance -> enchants.set(instance.enchantment, instance.level));
                offerStack.set(DataComponents.ENCHANTMENTS, enchants.toImmutable());
            }
            else
            {
                for(EnchantmentInstance data : this.enchantments)
                {
                    offerStack.enchant(data.enchantment, data.level);
                }
            }*/
        }
        if(!this.mobEffects.isEmpty())
        {
            // TODO add back this
            //PotionUtils.setCustomEffects(offerStack, this.mobEffects);
        }

        ItemCost primaryCost = this.fromStack(this.primaryPayment);
        Optional<ItemCost> secondaryCost = this.secondaryPayment.isEmpty() ? Optional.empty() : Optional.of(this.fromStack(this.secondaryPayment));
        return new GoblinTrade(offerStack, primaryCost, secondaryCost, this.maxTrades, this.experience, this.priceMultiplier);
    }

    private ItemCost fromStack(ItemStack stack)
    {
        return new ItemCost(stack.getItemHolder(), stack.getCount(), DataComponentPredicate.allOf(stack.getComponents()));
    }

    public static class Builder
    {
        private ItemStack offerStack;
        private ItemStack paymentStack;
        private ItemStack secondaryPaymentStack = ItemStack.EMPTY;
        private float priceMultiplier = 0.0F;
        private int maxTrades = 12;
        private int experience = 10;
        private List<EnchantmentInstance> enchantments = new ArrayList<>();
        private List<MobEffectInstance> modEffects = new ArrayList<>();

        private Builder() {}

        public static Builder create()
        {
            return new Builder();
        }

        public BasicTrade build()
        {
            return new BasicTrade(this.offerStack, this.paymentStack, this.secondaryPaymentStack, this.priceMultiplier, this.maxTrades, this.experience, this.enchantments, this.modEffects);
        }

        public Builder setOfferStack(ItemStack offerStack)
        {
            this.offerStack = offerStack;
            return this;
        }

        public Builder setPaymentStack(ItemStack paymentStack)
        {
            this.paymentStack = paymentStack;
            return this;
        }

        public Builder setSecondaryPaymentStack(ItemStack secondaryPaymentStack)
        {
            this.secondaryPaymentStack = secondaryPaymentStack;
            return this;
        }

        public Builder setPriceMultiplier(float priceMultiplier)
        {
            this.priceMultiplier = priceMultiplier;
            return this;
        }

        public Builder setMaxTrades(int maxTrades)
        {
            this.maxTrades = maxTrades;
            return this;
        }

        public Builder setExperience(int experience)
        {
            this.experience = experience;
            return this;
        }

        public Builder addEnchantment(EnchantmentInstance enchantment)
        {
            this.enchantments.add(enchantment);
            return this;
        }

        public Builder addPotionEffect(MobEffectInstance effect)
        {
            this.modEffects.add(effect);
            return this;
        }

        public <T> Builder addDataComponent(DataComponentType<? super T> type, T value)
        {
            this.offerStack.set(type, value);
            return this;
        }
    }
}
