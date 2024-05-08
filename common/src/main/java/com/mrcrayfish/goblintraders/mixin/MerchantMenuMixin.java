package com.mrcrayfish.goblintraders.mixin;

import com.mrcrayfish.goblintraders.Hooks;
import com.mrcrayfish.goblintraders.client.ClientGoblinMerchant;
import com.mrcrayfish.goblintraders.core.ModMenuTypes;
import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import com.mrcrayfish.goblintraders.inventory.GoblinMerchantMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.MerchantContainer;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.Merchant;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Author: MrCrayfish
 */
@Mixin(MerchantMenu.class)
public class MerchantMenuMixin
{
    @Shadow
    @Final
    private Merchant trader;

    @Shadow
    @Final
    private MerchantContainer tradeContainer;

    @SuppressWarnings("DataFlowIssue")
    @Inject(method = "moveFromInventoryToPaymentSlot", at = @At(value = "HEAD"), cancellable = true)
    private void goblinTraders$moveItem(int tradeIndex, ItemCost cost, CallbackInfo ci)
    {
        if(!(this.trader instanceof AbstractGoblinEntity) && !(this.trader instanceof ClientGoblinMerchant))
            return;

        MerchantMenu menu = (MerchantMenu) (Object) this;
        for(int i = 3; i < menu.slots.size(); ++i)
        {
            ItemStack stack = menu.slots.get(i).getItem();
            if(stack.isEmpty() || !Hooks.costTest(stack, cost))
                continue;

            ItemStack tradeStack = this.tradeContainer.getItem(tradeIndex);
            if(!tradeStack.isEmpty() && !ItemStack.isSameItemSameComponents(stack, tradeStack))
                continue;

            int maxStack = stack.getMaxStackSize();
            int shrinkCount = Math.min(maxStack - tradeStack.getCount(), stack.getCount());
            ItemStack copy = stack.copyWithCount(tradeStack.getCount() + shrinkCount);
            stack.shrink(shrinkCount);
            this.tradeContainer.setItem(tradeIndex, copy);
            if(copy.getCount() >= maxStack)
                break;
        }

        ci.cancel();
    }
}
