package com.mrcrayfish.goblintraders.mixin;

import com.mrcrayfish.goblintraders.enchantment.IAncientEnchantment;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * Fixes combining tools in an anvil reducing enchantment level to it's max level when the
 * level of the enchantment is higher than it's max level. For example, combining level five
 * efficiency pickaxe with a level six efficiency pickaxe (which is higher than the max) will
 * keep the enchantment at level six instead of changing to it's max level of five.
 *
 * Author: MrCrayfish
 */
@Mixin(AnvilMenu.class)
public class RepairContainerMixin
{
    @Inject(method = "createResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getEnchantmentsForCrafting(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/item/enchantment/ItemEnchantments;", shift = At.Shift.BY, by = 2, ordinal = 1), locals = LocalCapture.CAPTURE_FAILHARD)
    private void goblinTradersBeforeCanApply(CallbackInfo ci, ItemStack leftOriginal, int enchantCost, long repairCost, int renameCost, ItemStack leftCopy, ItemStack rightOriginal, ItemEnchantments.Mutable mutable, boolean enchantingItem, ItemEnchantments mergingEnchantments)
    {
        // Ancient enchantments will merge over the non-ancient versions
        mergingEnchantments.entrySet().forEach(entry ->
        {
            if(entry.getKey().value() instanceof IAncientEnchantment ancientEnchantment)
            {
                Enchantment original = ancientEnchantment.getOriginal();
                Enchantment ancient = ancientEnchantment.getAncientEnchantment();
                if(mutable.getLevel(original) <= original.getMaxLevel())
                {
                    mutable.removeIf(holder -> holder.value().equals(original));
                    mutable.set(ancient, ancient.getMaxLevel());
                }
            }
        });
    }
}
