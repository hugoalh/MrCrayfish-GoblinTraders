package com.mrcrayfish.goblintraders.trades.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrcrayfish.goblintraders.Constants;
import com.mrcrayfish.goblintraders.CustomCodecs;
import com.mrcrayfish.goblintraders.trades.GoblinTrade;
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
        ItemCost.CODEC.fieldOf("payment_item")
            .forGetter(trade -> trade.primaryPayment),
        ItemCost.CODEC.lenientOptionalFieldOf("secondary_payment_item")
            .forGetter(trade -> trade.secondaryPayment),
        Codec.FLOAT.optionalFieldOf("price_multiplier", 0F)
            .forGetter(trade -> trade.priceMultiplier),
        Codec.INT.optionalFieldOf("max_trades", 12)
            .forGetter(trade -> trade.maxTrades),
        Codec.INT.optionalFieldOf("experience", 0)
            .forGetter(trade -> trade.experience)
        ).apply(builder, BasicTrade::new)
    );

    private final ItemStack offerStack;
    private final ItemCost primaryPayment;
    private final Optional<ItemCost> secondaryPayment;
    private final float priceMultiplier;
    private final int maxTrades;
    private final int experience;

    public BasicTrade(ItemStack offerStack, ItemCost primaryPayment, Optional<ItemCost> secondaryPayment, float priceMultiplier, int maxTrades, int experience)
    {
        this.offerStack = offerStack;
        this.primaryPayment = primaryPayment;
        this.secondaryPayment = secondaryPayment;
        this.priceMultiplier = priceMultiplier;
        this.maxTrades = maxTrades;
        this.experience = experience;
    }

    @Override
    public ResourceLocation getTypeId()
    {
        return ID;
    }

    @Override
    public GoblinTrade createVillagerTrade()
    {
        return new GoblinTrade(this.offerStack.copy(), this.primaryPayment, this.secondaryPayment, this.maxTrades, this.experience, this.priceMultiplier);
    }

    public static class Builder
    {
        private ItemStack offerStack;
        private ItemCost paymentStack;
        private ItemCost secondaryPaymentStack;
        private float priceMultiplier = 0.0F;
        private int maxTrades = 12;
        private int experience = 10;

        private Builder() {}

        public static Builder create()
        {
            return new Builder();
        }

        public BasicTrade build()
        {
            return new BasicTrade(this.offerStack, this.paymentStack, Optional.ofNullable(this.secondaryPaymentStack), this.priceMultiplier, this.maxTrades, this.experience);
        }

        public Builder setOfferStack(ItemStack offerStack)
        {
            this.offerStack = offerStack;
            return this;
        }

        public Builder setPaymentStack(ItemCost paymentStack)
        {
            this.paymentStack = paymentStack;
            return this;
        }

        public Builder setSecondaryPaymentStack(ItemCost secondaryPaymentStack)
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
    }
}
