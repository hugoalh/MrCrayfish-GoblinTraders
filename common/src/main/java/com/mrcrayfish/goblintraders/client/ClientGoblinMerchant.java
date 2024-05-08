package com.mrcrayfish.goblintraders.client;

import com.mrcrayfish.goblintraders.trades.GoblinMerchantOffer;
import net.minecraft.world.entity.npc.ClientSideMerchant;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.trading.MerchantOffers;

/**
 * Author: MrCrayfish
 */
public class ClientGoblinMerchant extends ClientSideMerchant
{
    public ClientGoblinMerchant(Player player)
    {
        super(player);
    }

    @Override
    public boolean canRestock()
    {
        return true;
    }

    @Override
    public void overrideOffers(MerchantOffers offers)
    {
        // We need to fix the offers since we have custom behaviour
        MerchantOffers newOffers = new MerchantOffers();
        offers.forEach(offer -> newOffers.add(new GoblinMerchantOffer(offer)));
        super.overrideOffers(newOffers);
    }
}
