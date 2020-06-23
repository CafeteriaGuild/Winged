package net.adriantodt.winged.mixin;

import io.github.ladysnake.pal.AbilityTracker;
import io.github.ladysnake.pal.VanillaAbilities;
import net.adriantodt.winged.Winged;
import net.adriantodt.winged.WingedPlayerInventory;
import net.adriantodt.winged.item.ActiveBoosterItem;
import net.adriantodt.winged.item.HeartOfTheSkyItem;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin implements WingedPlayerInventory {
    @Shadow
    @Final
    public DefaultedList<ItemStack> main;

    @Shadow
    @Final
    public DefaultedList<ItemStack> offHand;

    @Shadow
    public abstract int getEmptySlot();

    @Shadow
    @Final
    public PlayerEntity player;

    @Override
    public int count(ItemConvertible item) {
        int count = 0;
        for (ItemStack itemStack : this.main) {
            if (itemStack.getItem() == item.asItem()) {
                count += itemStack.getCount();
            }
        }
        for (ItemStack itemStack : this.offHand) {
            if (itemStack.getItem() == item.asItem()) {
                count += itemStack.getCount();
            }
        }
        return count;
    }

    @Override
    public void takeFromInventory(ItemConvertible item, int count) {
        if (count == 0) return;
        for (int i = 0; i < this.main.size(); i++) {
            ItemStack itemStack = this.main.get(i);
            if (itemStack.getItem() == item.asItem()) {
                if (itemStack.getCount() <= count) {
                    count -= itemStack.getCount();
                    this.main.set(i, ItemStack.EMPTY);

                } else {
                    itemStack.decrement(count);
                    return;
                }
            }
        }
        for (int i = 0; i < this.offHand.size(); i++) {
            ItemStack itemStack = this.offHand.get(i);
            if (itemStack.getItem() == item.asItem()) {
                if (itemStack.getCount() <= count) {
                    count -= itemStack.getCount();
                    this.main.set(i, ItemStack.EMPTY);

                } else {
                    itemStack.decrement(count);
                    return;
                }
            }
        }
    }

    @Override
    public boolean findAndReplace(ItemStack find, ItemStack replace) {
        for (int i = 0; i < this.main.size(); i++) {
            ItemStack itemStack = this.main.get(i);
            if (Objects.equals(itemStack, find)) {
                this.main.set(i, replace);
                return true;
            }
        }
        for (int i = 0; i < this.offHand.size(); i++) {
            ItemStack itemStack = this.offHand.get(i);
            if (Objects.equals(itemStack, find)) {
                this.offHand.set(i, replace);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean takeOneAndReplace(ItemConvertible take, ItemConvertible replace) {
        for (int i = 0; i < this.main.size(); i++) {
            ItemStack itemStack = this.main.get(i);
            if (itemStack.getItem() == take.asItem()) {
                if (itemStack.getCount() == 1) {
                    this.main.set(i, new ItemStack(replace, 1));
                    return true;
                } else {
                    int emptySlot = this.getEmptySlot();
                    if (emptySlot != -1) {
                        itemStack.decrement(1);
                        this.main.set(emptySlot, new ItemStack(replace, 1));
                        return true;
                    }
                }
            }
        }
        for (int i = 0; i < this.offHand.size(); i++) {
            ItemStack itemStack = this.offHand.get(i);
            if (itemStack.getItem() == take.asItem()) {
                if (itemStack.getCount() == 1) {
                    this.offHand.set(i, new ItemStack(replace, 1));
                    return true;
                } else {
                    int emptySlot = this.getEmptySlot();
                    if (emptySlot != -1) {
                        itemStack.decrement(1);
                        this.offHand.set(emptySlot, new ItemStack(replace, 1));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void ensureOnlyActiveBooster(ItemStack ignore) {
        for (int i = 0; i < this.offHand.size(); i++) {
            ItemStack itemStack = this.offHand.get(i);
            Item item = itemStack.getItem();
            if (!Objects.equals(itemStack, ignore) && item instanceof ActiveBoosterItem) {
                this.offHand.set(i, ((ActiveBoosterItem) item).deactivateBooster(itemStack));
            }
        }
        for (int i = 0; i < this.main.size(); i++) {
            ItemStack itemStack = this.main.get(i);
            Item item = itemStack.getItem();
            if (!Objects.equals(itemStack, ignore) && item instanceof ActiveBoosterItem) {
                this.main.set(i, ((ActiveBoosterItem) item).deactivateBooster(itemStack));
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "updateItems")
    public void beforeUpdateItems(CallbackInfo info) {
        if (this.player.world.isClient) {
            return;
        }
        AbilityTracker allowFlyingTracker = VanillaAbilities.ALLOW_FLYING.getTracker(this.player);
        if (!allowFlyingTracker.isGrantedBy(Winged.INSTANCE.getHeartOfTheSkyAbilitySource())) {
            for (ItemStack itemStack : this.offHand) {
                Item item = itemStack.getItem();
                if (item instanceof HeartOfTheSkyItem) {
                    allowFlyingTracker.addSource(Winged.INSTANCE.getHeartOfTheSkyAbilitySource());
                    return;
                }
            }
            for (ItemStack itemStack : this.main) {
                Item item = itemStack.getItem();
                if (item instanceof HeartOfTheSkyItem) {
                    allowFlyingTracker.addSource(Winged.INSTANCE.getHeartOfTheSkyAbilitySource());
                    return;
                }
            }
        }
    }

    @Inject(at = @At("TAIL"), method = "updateItems")
    public void afterUpdateItems(CallbackInfo info) {
        if (this.player.world.isClient) {
            return;
        }
        AbilityTracker allowFlyingTracker = VanillaAbilities.ALLOW_FLYING.getTracker(this.player);
        if (allowFlyingTracker.isGrantedBy(Winged.INSTANCE.getHeartOfTheSkyAbilitySource())) {
            for (int i = 0; i < this.offHand.size(); i++) {
                ItemStack itemStack = this.offHand.get(i);
                Item item = itemStack.getItem();
                if (item instanceof HeartOfTheSkyItem) {
                    this.offHand.set(i, ((HeartOfTheSkyItem) item).damageOnce(this.player, itemStack));
                    return;
                }
            }
            for (int i = 0; i < this.main.size(); i++) {
                ItemStack itemStack = this.main.get(i);
                Item item = itemStack.getItem();
                if (item instanceof HeartOfTheSkyItem) {
                    this.main.set(i, ((HeartOfTheSkyItem) item).damageOnce(this.player, itemStack));
                    return;
                }
            }
            // Not found, oh well.
            boolean wasFlying = player.abilities.flying;
            allowFlyingTracker.removeSource(Winged.INSTANCE.getHeartOfTheSkyAbilitySource());
            if (!allowFlyingTracker.isEnabled() && wasFlying && !player.abilities.flying) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 200));
            }
        }
    }
}
