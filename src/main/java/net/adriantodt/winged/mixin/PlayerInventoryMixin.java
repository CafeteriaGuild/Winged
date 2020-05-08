package net.adriantodt.winged.mixin;

import net.adriantodt.winged.WingedPlayerInventory;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

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
    protected abstract boolean areItemsEqual(ItemStack stack1, ItemStack stack2);

    @Override
    public boolean hasAtLeast(ItemConvertible item, int count) {
        if (count == 0) return true;
        for (ItemStack itemStack : this.main) {
            if (itemStack.getItem() == item.asItem()) {
                count -= itemStack.getCount();
                if (count <= 0) return true;
            }
        }
        for (ItemStack itemStack : this.offHand) {
            if (itemStack.getItem() == item.asItem()) {
                count -= itemStack.getCount();
                if (count <= 0) return true;
            }
        }
        return count <= 0;
    }

    @Override
    public void takeFromInventory(ItemConvertible item, int count) {
        if (count == 0) return;
        for (int i = 0; i < this.main.size(); i++) {
            ItemStack itemStack = this.main.get(i);
            if (itemStack.getItem() == item.asItem()) {
                if (itemStack.getCount() <= count) {
                    count -= itemStack.getCount();
                    this.main.set(0, ItemStack.EMPTY);
                }
                itemStack.decrement(count);
            }

            if (count <= 0) {
                return;
            }
        }
        for (int i = 0; i < this.offHand.size(); i++) {
            ItemStack itemStack = this.offHand.get(i);
            if (itemStack.getItem() == item.asItem()) {
                if (itemStack.getCount() <= count) {
                    count -= itemStack.getCount();
                    this.offHand.set(0, ItemStack.EMPTY);
                }
                itemStack.decrement(count);
            }

            if (count <= 0) {
                return;
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
}
