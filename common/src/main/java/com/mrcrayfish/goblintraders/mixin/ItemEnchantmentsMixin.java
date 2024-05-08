package com.mrcrayfish.goblintraders.mixin;

import com.mrcrayfish.goblintraders.core.ModEnchantments;
import com.mrcrayfish.goblintraders.enchantment.IAncientEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Author: MrCrayfish
 */
@Mixin(ItemEnchantments.class)
public class ItemEnchantmentsMixin
{
    // We'll have to see if this behaviour has any issues with other mods.
    @SuppressWarnings("DataFlowIssue")
    @Inject(method = "getLevel", at = @At(value = "HEAD"), cancellable = true)
    private void goblinTraders$getAncientLevel(Enchantment enchantment, CallbackInfoReturnable<Integer> cir)
    {
        IAncientEnchantment ancientEnchantment = ModEnchantments.ORIGINAL_TO_ANCIENT.get(enchantment);
        if(ancientEnchantment != null)
        {
            // Possible recursion but ancient enchantments don't link to ancient enchantments, so all good
            ItemEnchantments enchantments = (ItemEnchantments) (Object) this;
            int level = enchantments.getLevel(ancientEnchantment.getAncientEnchantment());
            if(level > 0)
            {
                cir.setReturnValue(ancientEnchantment.getAncientLevel(level));
            }
        }
    }
}
