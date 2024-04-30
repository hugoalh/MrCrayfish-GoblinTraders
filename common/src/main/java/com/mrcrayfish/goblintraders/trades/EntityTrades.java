package com.mrcrayfish.goblintraders.trades;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mrcrayfish.goblintraders.trades.type.ITradeType;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.npc.VillagerTrades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public record EntityTrades(Map<TradeRarity, List<VillagerTrades.ItemListing>> map)
{
    public EntityTrades(Map<TradeRarity, List<VillagerTrades.ItemListing>> map)
    {
        this.map = ImmutableMap.copyOf(map);
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static class Builder
    {
        private final Map<TradeRarity, List<VillagerTrades.ItemListing>> tradeMap = Util.make(() -> {
            Map<TradeRarity, List<VillagerTrades.ItemListing>> map = new EnumMap<>(TradeRarity.class);
            Arrays.stream(TradeRarity.values()).forEach(rarity -> map.put(rarity, new ArrayList<>()));
            return map;
        });

        private Builder() {}

        public void deserialize(TradeRarity rarity, JsonObject object)
        {
            List<VillagerTrades.ItemListing> trades = this.tradeMap.get(rarity);
            if(GsonHelper.getAsBoolean(object, "replace", false))
            {
                trades.clear();
            }
            JsonArray tradeArray = GsonHelper.getAsJsonArray(object, "trades");
            for(JsonElement tradeElement : tradeArray)
            {
                JsonObject tradeObject = tradeElement.getAsJsonObject();
                String rawType = GsonHelper.getAsString(tradeObject, "type");
                ResourceLocation typeKey = Util.getOrThrow(ResourceLocation.read(rawType), JsonParseException::new);
                Codec<? extends ITradeType> codec = TradeManager.instance().getTradeCodec(typeKey);
                if(codec == null) throw new JsonParseException(String.format("Invalid trade type: %s", typeKey));
                ITradeType trade = Util.getOrThrow(codec.parse(JsonOps.INSTANCE, tradeObject), JsonParseException::new);
                trades.add(trade.createVillagerTrade());
            }
        }

        public EntityTrades build()
        {
            return new EntityTrades(this.tradeMap);
        }
    }
}
