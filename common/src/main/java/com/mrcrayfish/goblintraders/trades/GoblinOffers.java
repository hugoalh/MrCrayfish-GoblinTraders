package com.mrcrayfish.goblintraders.trades;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.trading.MerchantOffers;

/**
 * Author: MrCrayfish
 */
public class GoblinOffers extends MerchantOffers
{
    public GoblinOffers() {}

    public GoblinOffers(CompoundTag tag)
    {
        CODEC.decode(NbtOps.INSTANCE, tag).result().ifPresent(pair -> {
            this.addAll(pair.getFirst());
        });
    }
}
