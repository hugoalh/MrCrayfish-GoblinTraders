package com.mrcrayfish.goblintraders.inventory;

import com.mrcrayfish.goblintraders.client.ClientGoblinMerchant;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.trading.Merchant;

/**
 * Author: MrCrayfish
 */
public class GoblinMerchantMenu extends MerchantMenu
{
    public GoblinMerchantMenu(int windowId, Inventory playerInventory)
    {
        this(windowId, playerInventory, new ClientGoblinMerchant(playerInventory.player));
    }

    public GoblinMerchantMenu(int windowId, Inventory playerInventory, Merchant merchant)
    {
        super(windowId, playerInventory, merchant);
    }
}
