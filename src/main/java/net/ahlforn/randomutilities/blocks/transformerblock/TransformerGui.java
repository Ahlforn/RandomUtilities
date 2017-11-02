package net.ahlforn.randomutilities.blocks.transformerblock;

import net.ahlforn.randomutilities.RandomUtilitiesMod;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TransformerGui extends GuiContainer {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(RandomUtilitiesMod.MODID, "textures/gui/transformer/transformer-container.png");
    private static final int WIDTH = 180;
    private static final int HEIGHT = 152;

    public TransformerGui(IInventory playerInv, TransformerTileEntity te) {
        super(new TransformerContainer(playerInv, te));

        xSize = WIDTH;
        ySize = HEIGHT;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialkTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(BACKGROUND);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        super.renderHoveredToolTip(mouseX, mouseY);
    }
}
