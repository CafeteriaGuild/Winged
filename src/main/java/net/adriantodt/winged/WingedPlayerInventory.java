package net.adriantodt.winged;

import net.minecraft.item.ItemConvertible;

public interface WingedPlayerInventory {
    boolean hasAtLeast(ItemConvertible item, int count);

    void takeFromInventory(ItemConvertible item, int count);

    boolean takeOneAndReplace(ItemConvertible take, ItemConvertible replace);
}
