package net.ahlforn.randomutilities.blocks.transformerblock;

import net.ahlforn.randomutilities.RandomUtilitiesMod;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TransformerBlock extends Block implements ITileEntityProvider {

    public TransformerBlock() {
        super(Material.ROCK);
        setUnlocalizedName(RandomUtilitiesMod.MODID + ".transformerblock");
        setRegistryName("transformerblock");
        setCreativeTab(CreativeTabs.INVENTORY);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TransformerTileEntity();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(world.isRemote) {
            TileEntity te = world.getTileEntity(pos);
            if(te instanceof TransformerTileEntity) player.openGui(RandomUtilitiesMod.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }

        return false;
    }
}
