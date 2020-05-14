package net.adriantodt.winged;

import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;

public interface WingedPlayerInventory {
    int count(ItemConvertible item);

    void takeFromInventory(ItemConvertible item, int count);

    boolean takeOneAndReplace(ItemConvertible take, ItemConvertible replace);

    boolean findAndReplace(ItemStack find, ItemStack replace);

    void ensureOnlyActiveBooster(ItemStack ignore);
}
