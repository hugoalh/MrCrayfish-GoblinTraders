package com.mrcrayfish.goblintraders.trades.type;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mrcrayfish.goblintraders.trades.TradeManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerTrades;

/**
 * Author: MrCrayfish
 */
public interface ITradeType
{
    Codec<ITradeType> CODEC = ResourceLocation.CODEC.dispatch(ITradeType::getTypeId, id -> TradeManager.instance().getTradeCodec(id));

    ResourceLocation getTypeId();

    VillagerTrades.ItemListing createVillagerTrade();
}
