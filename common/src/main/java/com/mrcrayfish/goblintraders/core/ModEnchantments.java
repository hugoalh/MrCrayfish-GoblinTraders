package com.mrcrayfish.goblintraders.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.goblintraders.enchantment.AncientDamageEnchantment;
import com.mrcrayfish.goblintraders.enchantment.AncientEnchantment;
import com.mrcrayfish.goblintraders.enchantment.AncientFrostWalkerEnchantment;
import com.mrcrayfish.goblintraders.enchantment.AncientLootingEnchantment;
import com.mrcrayfish.goblintraders.enchantment.AncientPiercingEnchantment;
import com.mrcrayfish.goblintraders.enchantment.AncientProtectionEnchantment;
import com.mrcrayfish.goblintraders.enchantment.AncientThornsEnchantment;
import com.mrcrayfish.goblintraders.enchantment.AncientWaterWalkerEnchantment;
import com.mrcrayfish.goblintraders.enchantment.IAncientEnchantment;
import com.mrcrayfish.goblintraders.mixin.ItemEnchantmentsMixin;
import com.mrcrayfish.goblintraders.util.Utils;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceLinkedOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.Optional;

/**
 * Author: MrCrayfish
 */
@RegistryContainer
public class ModEnchantments
{
    public static final Map<Enchantment, IAncientEnchantment> ORIGINAL_TO_ANCIENT = new Reference2ReferenceLinkedOpenHashMap<>();
    public static final EquipmentSlot[] ARMOR_SLOTS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    /** Applied with {@link AncientDamageEnchantment#getDamageBonus(int, EntityType)} */
    public static final RegistryEntry<AncientDamageEnchantment> ANCIENT_SHARPNESS = RegistryEntry.enchantment(Utils.resource("ancient_sharpness"), () -> {
        return new AncientDamageEnchantment(Enchantment.definition(ItemTags.SHARP_WEAPON_ENCHANTABLE, ItemTags.SWORD_ENCHANTABLE, 1, 1, Enchantment.constantCost(32), Enchantment.constantCost(50), 50, EquipmentSlot.MAINHAND), Optional.empty(), Enchantments.SHARPNESS);
    });

    /** Applied with {@link AncientDamageEnchantment#getDamageBonus(int, EntityType)} */
    public static final RegistryEntry<AncientDamageEnchantment> ANCIENT_SMITE = RegistryEntry.enchantment(Utils.resource("ancient_smite"), () -> {
        return new AncientDamageEnchantment(Enchantment.definition(ItemTags.WEAPON_ENCHANTABLE, ItemTags.SWORD_ENCHANTABLE, 1, 1, Enchantment.constantCost(32), Enchantment.constantCost(50), 50, EquipmentSlot.MAINHAND), Optional.of(EntityTypeTags.SENSITIVE_TO_SMITE), Enchantments.SMITE);
    });

    /** Applied with {@link AncientDamageEnchantment#getDamageBonus(int, EntityType)} */
    public static final RegistryEntry<AncientDamageEnchantment> ANCIENT_BANE_OF_ARTHROPODS = RegistryEntry.enchantment(Utils.resource("ancient_bane_of_arthropods"), () -> {
        return new AncientDamageEnchantment(Enchantment.definition(ItemTags.WEAPON_ENCHANTABLE, ItemTags.SWORD_ENCHANTABLE, 1, 1, Enchantment.constantCost(32), Enchantment.constantCost(50), 50, EquipmentSlot.MAINHAND), Optional.of(EntityTypeTags.SENSITIVE_TO_BANE_OF_ARTHROPODS), Enchantments.BANE_OF_ARTHROPODS);
    });

    /** Applied with {@link ItemEnchantmentsMixin#goblinTraders$getAncientLevel(Enchantment, CallbackInfoReturnable)} */
    public static final RegistryEntry<AncientEnchantment> ANCIENT_LOOTING = RegistryEntry.enchantment(Utils.resource("ancient_looting"), () -> {
        return new AncientEnchantment(Enchantment.definition(ItemTags.SWORD_ENCHANTABLE, 1, 1, Enchantment.constantCost(32), Enchantment.constantCost(50), 50, EquipmentSlot.MAINHAND), Enchantments.LOOTING);
    });

    /** Applied with {@link ItemEnchantmentsMixin#goblinTraders$getAncientLevel(Enchantment, CallbackInfoReturnable)} */
    public static final RegistryEntry<AncientEnchantment> ANCIENT_KNOCKBACK = RegistryEntry.enchantment(Utils.resource("ancient_knockback"), () -> {
        return new AncientEnchantment(Enchantment.definition(ItemTags.SWORD_ENCHANTABLE, 1, 1, Enchantment.constantCost(32), Enchantment.constantCost(50), 50, EquipmentSlot.MAINHAND), Enchantments.KNOCKBACK);
    });

    /** Applied with {@link ItemEnchantmentsMixin#goblinTraders$getAncientLevel(Enchantment, CallbackInfoReturnable)} */
    public static final RegistryEntry<AncientEnchantment> ANCIENT_FIRE_ASPECT = RegistryEntry.enchantment(Utils.resource("ancient_fire_aspect"), () -> {
        return new AncientEnchantment(Enchantment.definition(ItemTags.FIRE_ASPECT_ENCHANTABLE, 1, 1, Enchantment.constantCost(32), Enchantment.constantCost(50), 50, EquipmentSlot.MAINHAND), Enchantments.FIRE_ASPECT);
    });

    /** Applied with {@link ItemEnchantmentsMixin#goblinTraders$getAncientLevel(Enchantment, CallbackInfoReturnable)} */
    public static final RegistryEntry<AncientEnchantment> ANCIENT_SWEEPING_EDGE = RegistryEntry.enchantment(Utils.resource("ancient_sweeping_edge"), () -> {
        return new AncientEnchantment(Enchantment.definition(ItemTags.SWORD_ENCHANTABLE, 1, 1, Enchantment.constantCost(32), Enchantment.constantCost(50), 50, EquipmentSlot.MAINHAND), Enchantments.SWEEPING_EDGE);
    });

    /** Applied with {@link ItemEnchantmentsMixin#goblinTraders$getAncientLevel(Enchantment, CallbackInfoReturnable)} */
    public static final RegistryEntry<AncientEnchantment> ANCIENT_UNBREAKING = RegistryEntry.enchantment(Utils.resource("ancient_unbreaking"), () -> {
        return new AncientEnchantment(Enchantment.definition(ItemTags.DURABILITY_ENCHANTABLE, 1, 1, Enchantment.constantCost(32), Enchantment.constantCost(50), 50, EquipmentSlot.MAINHAND), Enchantments.UNBREAKING);
    });

    /** Applied with {@link ItemEnchantmentsMixin#goblinTraders$getAncientLevel(Enchantment, CallbackInfoReturnable)} */
    public static final RegistryEntry<AncientEnchantment> ANCIENT_EFFICIENCY = RegistryEntry.enchantment(Utils.resource("ancient_efficiency"), () -> {
        return new AncientEnchantment(Enchantment.definition(ItemTags.MINING_ENCHANTABLE, 1, 1, Enchantment.constantCost(32), Enchantment.constantCost(50), 50, EquipmentSlot.MAINHAND), Enchantments.EFFICIENCY);
    });

    /** Applied with {@link ItemEnchantmentsMixin#goblinTraders$getAncientLevel(Enchantment, CallbackInfoReturnable)} */
    public static final RegistryEntry<AncientLootingEnchantment> ANCIENT_FORTUNE = RegistryEntry.enchantment(Utils.resource("ancient_fortune"), () -> {
        return new AncientLootingEnchantment(Enchantment.definition(ItemTags.MINING_LOOT_ENCHANTABLE, 1, 1, Enchantment.constantCost(32), Enchantment.constantCost(50), 50, EquipmentSlot.MAINHAND), Enchantments.FORTUNE);
    });

    /** Applied with {@link AncientProtectionEnchantment#getDamageProtection(int, DamageSource)} */
    public static final RegistryEntry<AncientProtectionEnchantment> ANCIENT_PROTECTION = RegistryEntry.enchantment(Utils.resource("ancient_protection"), () -> {
        return new AncientProtectionEnchantment(Enchantment.definition(ItemTags.ARMOR_ENCHANTABLE, 1, 1, Enchantment.constantCost(32), Enchantment.constantCost(50), 50, ARMOR_SLOTS), ProtectionEnchantment.Type.ALL, Enchantments.PROTECTION);
    });

    /** Applied with {@link AncientProtectionEnchantment#getDamageProtection(int, DamageSource)} */
    public static final RegistryEntry<AncientProtectionEnchantment> ANCIENT_PROJECTILE_PROTECTION = RegistryEntry.enchantment(Utils.resource("ancient_projectile_protection"), () -> {
        return new AncientProtectionEnchantment(Enchantment.definition(ItemTags.ARMOR_ENCHANTABLE, 1, 1, Enchantment.constantCost(32), Enchantment.constantCost(50), 50, ARMOR_SLOTS), ProtectionEnchantment.Type.PROJECTILE, Enchantments.PROJECTILE_PROTECTION);
    });

    /** Applied with {@link AncientProtectionEnchantment#getDamageProtection(int, DamageSource)} */
    public static final RegistryEntry<AncientProtectionEnchantment> ANCIENT_BLAST_PROTECTION = RegistryEntry.enchantment(Utils.resource("ancient_blast_protection"), () -> {
        return new AncientProtectionEnchantment(Enchantment.definition(ItemTags.ARMOR_ENCHANTABLE, 1, 1, Enchantment.constantCost(32), Enchantment.constantCost(50), 50, ARMOR_SLOTS), ProtectionEnchantment.Type.EXPLOSION, Enchantments.BLAST_PROTECTION);
    });

    /** Applied with {@link AncientProtectionEnchantment#getDamageProtection(int, DamageSource)} */
    public static final RegistryEntry<AncientProtectionEnchantment> ANCIENT_FIRE_PROTECTION = RegistryEntry.enchantment(Utils.resource("ancient_fire_protection"), () -> {
        return new AncientProtectionEnchantment(Enchantment.definition(ItemTags.ARMOR_ENCHANTABLE, 1, 1, Enchantment.constantCost(32), Enchantment.constantCost(50), 50, ARMOR_SLOTS), ProtectionEnchantment.Type.FIRE, Enchantments.FIRE_PROTECTION);
    });

    /** Applied with {@link AncientThornsEnchantment#doPostHurt(LivingEntity, Entity, int)} */
    public static final RegistryEntry<AncientThornsEnchantment> ANCIENT_THORNS = RegistryEntry.enchantment(Utils.resource("ancient_thorns"), () -> {
        return new AncientThornsEnchantment(Enchantment.definition(ItemTags.ARMOR_ENCHANTABLE, ItemTags.CHEST_ARMOR_ENCHANTABLE, 1, 1, Enchantment.constantCost(32), Enchantment.constantCost(50), 50, ARMOR_SLOTS), Enchantments.THORNS);
    });

    /** Applied with {@link ItemEnchantmentsMixin#goblinTraders$getAncientLevel(Enchantment, CallbackInfoReturnable)} */
    public static final RegistryEntry<AncientEnchantment> ANCIENT_RESPIRATION = RegistryEntry.enchantment(Utils.resource("ancient_respiration"), () -> {
        return new AncientEnchantment(Enchantment.definition(ItemTags.HEAD_ARMOR_ENCHANTABLE, 1, 1, Enchantment.constantCost(32), Enchantment.constantCost(50), 50, ARMOR_SLOTS), Enchantments.RESPIRATION);
    });

    /** Applied with {@link ItemEnchantmentsMixin#goblinTraders$getAncientLevel(Enchantment, CallbackInfoReturnable)} */
    public static final RegistryEntry<AncientEnchantment> ANCIENT_SWIFT_SNEAK = RegistryEntry.enchantment(Utils.resource("ancient_swift_sneak"), () -> {
        return new AncientEnchantment(Enchantment.definition(ItemTags.LEG_ARMOR_ENCHANTABLE, 1, 1, Enchantment.constantCost(32), Enchantment.constantCost(50), 50, EquipmentSlot.LEGS), Enchantments.SWIFT_SNEAK);
    });

    /** Applied with {@link AncientProtectionEnchantment#getDamageProtection(int, DamageSource)} */
    public static final RegistryEntry<AncientProtectionEnchantment> ANCIENT_FEATHER_FALLING = RegistryEntry.enchantment(Utils.resource("ancient_feather_falling"), () -> {
        return new AncientProtectionEnchantment(Enchantment.definition(ItemTags.FOOT_ARMOR_ENCHANTABLE, 1, 1, Enchantment.constantCost(32), Enchantment.constantCost(50), 50, ARMOR_SLOTS), ProtectionEnchantment.Type.FALL, Enchantments.FEATHER_FALLING);
    });

    /** Applied with {@link ItemEnchantmentsMixin#goblinTraders$getAncientLevel(Enchantment, CallbackInfoReturnable)} */
    public static final RegistryEntry<AncientWaterWalkerEnchantment> ANCIENT_DEPTH_STRIDER = RegistryEntry.enchantment(Utils.resource("ancient_depth_strider"), () -> {
        return new AncientWaterWalkerEnchantment(Enchantment.definition(ItemTags.FOOT_ARMOR_ENCHANTABLE, 1, 1, Enchantment.constantCost(32), Enchantment.constantCost(50), 50, ARMOR_SLOTS), Enchantments.DEPTH_STRIDER);
    });

    /** Applied with {@link AncientFrostWalkerEnchantment#onEntityMoved(LivingEntity, Level, BlockPos, int)}  */
    public static final RegistryEntry<AncientFrostWalkerEnchantment> ANCIENT_FROST_WALKER = RegistryEntry.enchantment(Utils.resource("ancient_frost_walker"), () -> {
        return new AncientFrostWalkerEnchantment(Enchantment.definition(ItemTags.FOOT_ARMOR_ENCHANTABLE, 1, 1, Enchantment.constantCost(32), Enchantment.constantCost(50), 50, EquipmentSlot.FEET), Enchantments.FROST_WALKER);
    });

    /** Applied with {@link ItemEnchantmentsMixin#goblinTraders$getAncientLevel(Enchantment, CallbackInfoReturnable)} */
    public static final RegistryEntry<AncientEnchantment> ANCIENT_SOUL_SPEED = RegistryEntry.enchantment(Utils.resource("ancient_soul_speed"), () -> {
        return new AncientEnchantment(Enchantment.definition(ItemTags.FOOT_ARMOR_ENCHANTABLE, 1, 1, Enchantment.constantCost(32), Enchantment.constantCost(50), 50, EquipmentSlot.FEET), Enchantments.SOUL_SPEED);
    });

    /** Applied with {@link ItemEnchantmentsMixin#goblinTraders$getAncientLevel(Enchantment, CallbackInfoReturnable)} */
    public static final RegistryEntry<AncientEnchantment> ANCIENT_LURE = RegistryEntry.enchantment(Utils.resource("ancient_lure"), () -> {
        return new AncientEnchantment(Enchantment.definition(ItemTags.FISHING_ENCHANTABLE, 1, 1, Enchantment.constantCost(32), Enchantment.constantCost(50), 50, EquipmentSlot.MAINHAND), Enchantments.LURE);
    });

    /** Applied with {@link ItemEnchantmentsMixin#goblinTraders$getAncientLevel(Enchantment, CallbackInfoReturnable)} */
    public static final RegistryEntry<AncientEnchantment> ANCIENT_LUCK_OF_THE_SEA = RegistryEntry.enchantment(Utils.resource("ancient_luck_of_the_sea"), () -> {
        return new AncientEnchantment(Enchantment.definition(ItemTags.FISHING_ENCHANTABLE, 1, 1, Enchantment.constantCost(32), Enchantment.constantCost(50), 50, EquipmentSlot.MAINHAND), Enchantments.LUCK_OF_THE_SEA);
    });

    /** Applied with {@link ItemEnchantmentsMixin#goblinTraders$getAncientLevel(Enchantment, CallbackInfoReturnable)} */
    public static final RegistryEntry<AncientEnchantment> ANCIENT_POWER = RegistryEntry.enchantment(Utils.resource("ancient_power"), () -> {
        return new AncientEnchantment(Enchantment.definition(ItemTags.BOW_ENCHANTABLE, 1, 1, Enchantment.constantCost(32), Enchantment.constantCost(50), 50, EquipmentSlot.MAINHAND), Enchantments.POWER);
    });

    /** Applied with {@link ItemEnchantmentsMixin#goblinTraders$getAncientLevel(Enchantment, CallbackInfoReturnable)} */
    public static final RegistryEntry<AncientEnchantment> ANCIENT_PUNCH = RegistryEntry.enchantment(Utils.resource("ancient_punch"), () -> {
        return new AncientEnchantment(Enchantment.definition(ItemTags.BOW_ENCHANTABLE, 1, 1, Enchantment.constantCost(32), Enchantment.constantCost(50), 50, EquipmentSlot.MAINHAND), Enchantments.PUNCH);
    });

    /** Applied with {@link ItemEnchantmentsMixin#goblinTraders$getAncientLevel(Enchantment, CallbackInfoReturnable)} */
    public static final RegistryEntry<AncientEnchantment> ANCIENT_LOYALTY = RegistryEntry.enchantment(Utils.resource("ancient_loyalty"), () -> {
        return new AncientEnchantment(Enchantment.definition(ItemTags.TRIDENT_ENCHANTABLE, 1, 1, Enchantment.constantCost(32), Enchantment.constantCost(50), 50, EquipmentSlot.MAINHAND), Enchantments.LOYALTY);
    });

    /** Applied with {@link ItemEnchantmentsMixin#goblinTraders$getAncientLevel(Enchantment, CallbackInfoReturnable)} */
    public static final RegistryEntry<AncientEnchantment> ANCIENT_RIPTIDE = RegistryEntry.enchantment(Utils.resource("ancient_riptide"), () -> {
        return new AncientEnchantment(Enchantment.definition(ItemTags.TRIDENT_ENCHANTABLE, 1, 1, Enchantment.constantCost(32), Enchantment.constantCost(50), 50, EquipmentSlot.MAINHAND), Enchantments.RIPTIDE);
    });

    /** Applied with {@link ItemEnchantmentsMixin#goblinTraders$getAncientLevel(Enchantment, CallbackInfoReturnable)} */
    public static final RegistryEntry<AncientDamageEnchantment> ANCIENT_IMPALING = RegistryEntry.enchantment(Utils.resource("ancient_impaling"), () -> {
        return new AncientDamageEnchantment(Enchantment.definition(ItemTags.TRIDENT_ENCHANTABLE, 1, 1, Enchantment.constantCost(32), Enchantment.constantCost(50), 50, EquipmentSlot.MAINHAND), Optional.of(EntityTypeTags.SENSITIVE_TO_IMPALING), Enchantments.IMPALING);
    });

    /** Applied with {@link ItemEnchantmentsMixin#goblinTraders$getAncientLevel(Enchantment, CallbackInfoReturnable)} */
    public static final RegistryEntry<AncientEnchantment> ANCIENT_QUICK_CHARGE = RegistryEntry.enchantment(Utils.resource("ancient_quick_charge"), () -> {
        return new AncientEnchantment(Enchantment.definition(ItemTags.CROSSBOW_ENCHANTABLE, 1, 1, Enchantment.constantCost(32), Enchantment.constantCost(50), 50, EquipmentSlot.MAINHAND), Enchantments.QUICK_CHARGE);
    });

    /** Applied with {@link ItemEnchantmentsMixin#goblinTraders$getAncientLevel(Enchantment, CallbackInfoReturnable)} */
    public static final RegistryEntry<AncientPiercingEnchantment> ANCIENT_PIERCING = RegistryEntry.enchantment(Utils.resource("ancient_piercing"), () -> {
        return new AncientPiercingEnchantment(Enchantment.definition(ItemTags.CROSSBOW_ENCHANTABLE, 1, 1, Enchantment.constantCost(32), Enchantment.constantCost(50), 50, EquipmentSlot.MAINHAND), Enchantments.PIERCING);
    });
}
