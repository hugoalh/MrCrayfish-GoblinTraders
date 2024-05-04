package com.mrcrayfish.goblintraders.mixin;

import com.mrcrayfish.goblintraders.Hooks;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.Map;

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
    @Unique
    private int goblinTraders$maxLevel;

    @SuppressWarnings("unchecked")
    @Inject(method = "createResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/Enchantment;canEnchant(Lnet/minecraft/world/item/ItemStack;)Z"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void goblinTradersBeforeCanApply(CallbackInfo ci, ItemStack leftOriginal, int enchantCost, long repairCost, int renameCost, ItemStack leftCopy, ItemStack rightOriginal, ItemEnchantments.Mutable leftEnchantments, boolean enchantingItem, ItemEnchantments rightEnchantments, boolean combinedEnchants, boolean invalidRepair, Iterator<Object2IntMap.Entry<Holder<Enchantment>>> var12, Object2IntMap.Entry<Holder<Enchantment>> entry, Holder<Enchantment> holder, Enchantment enchantment, int leftEnchantmentLevel, int combinedEnchantmentLevel)
    //private void goblinTradersBeforeCanApply(CallbackInfo ci, ItemStack leftOriginal, int enchantCost, long repairCost, int renameCost, ItemStack leftCopy, ItemStack rightOriginal, Map leftEnchantments, boolean enchantingItem, Map rightEnchantments, boolean combinedEnchants, boolean invalidRepair, Iterator var12, Enchantment enchantment, int leftEnchantmentLevel, int combinedEnchantmentLevel)
    {
        int maxLevel = Hooks.getEnchantmentLevel(enchantment);
        int leftLevel = leftEnchantments.getLevel(enchantment);
        int rightLevel = rightEnchantments.getLevel(enchantment);
        this.goblinTraders$maxLevel = Math.max(rightLevel, leftLevel);
        if(leftLevel == rightLevel && leftLevel < maxLevel)
        {
            this.goblinTraders$maxLevel = rightLevel + 1;
        }
    }

    @ModifyArg(method = "createResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/ItemEnchantments$Mutable;set(Lnet/minecraft/world/item/enchantment/Enchantment;I)V"), index = 1)
    private int goblinTradersAfterSetMaxLevel(int o)
    {
        return this.goblinTraders$maxLevel;
    }
}
