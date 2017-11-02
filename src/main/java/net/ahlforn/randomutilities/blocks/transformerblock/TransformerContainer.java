package net.ahlforn.randomutilities.blocks.transformerblock;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class TransformerContainer extends Container {
    private TransformerTileEntity te;

    public TransformerContainer(IInventory playerInv, TransformerTileEntity te) {
        // Slots for the main inventory
        for(int row = 0; row < 3; row++) {
            for(int col = 0; col < 9; col++) {
                int x = 10 + col * 18;
                int y = row * 18 + 70;
                this.addSlotToContainer(new Slot(playerInv, col + row * 9 + 10, x, y));
            }
        }

        // Slots for the hotbar
        for(int row = 0; row < 9; row++) {
            int x = 10 + row * 18;
            int y = 58 + 70;
            this.addSlotToContainer(new Slot(playerInv, row, x, y));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.te.isUseableByPlayer(player);
    }
}
