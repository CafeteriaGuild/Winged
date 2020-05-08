package net.adriantodt.winged;

import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;

public interface WingedPlayerInventory {
    boolean hasAtLeast(ItemConvertible item, int count);

    void takeFromInventory(ItemConvertible item, int count);

    boolean takeOneAndReplace(ItemConvertible take, ItemConvertible replace);

    boolean findAndReplace(ItemStack find, ItemStack replace);
}
