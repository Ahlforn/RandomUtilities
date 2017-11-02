package net.ahlforn.randomutilities;

import net.ahlforn.randomutilities.blocks.transformerblock.TransformerBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {
    @GameRegistry.ObjectHolder("randomutilities:transformerblock")
    public static TransformerBlock transformerBlock;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        transformerBlock.initModel();
    }

    @SideOnly(Side.CLIENT)
    public static void initItemModels() {

    }
}
