package net.ahlforn.randomutilities.proxy;

import net.ahlforn.randomutilities.blocks.transformerblock.TransformerContainer;
import net.ahlforn.randomutilities.blocks.transformerblock.TransformerGui;
import net.ahlforn.randomutilities.blocks.transformerblock.TransformerTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiProxy implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);

        if(te instanceof TransformerTileEntity) {
            return new TransformerContainer(player.inventory, (TransformerTileEntity) te);
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);

        if(te instanceof TransformerTileEntity) {
            return new TransformerGui(player.inventory, (TransformerTileEntity) te);
        }

        return null;
    }

}
