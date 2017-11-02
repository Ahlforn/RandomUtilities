package net.ahlforn.randomutilities.proxy;

import net.ahlforn.randomutilities.ModBlocks;
import net.ahlforn.randomutilities.RandomUtilitiesMod;
import net.ahlforn.randomutilities.blocks.transformerblock.TransformerBlock;
import net.ahlforn.randomutilities.blocks.transformerblock.TransformerTileEntity;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.File;

@Mod.EventBusSubscriber
public class CommonProxy {
    public static Configuration config;

    public void preInit(FMLPreInitializationEvent e) {
        File directory = e.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), "randomutilities.cfg"));
        //Config.readConfig();
    }

    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(RandomUtilitiesMod.instance, new GuiProxy());
    }

    public void postInit(FMLPostInitializationEvent e) {
        if(config.hasChanged()) {
            config.save();
        }
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new TransformerBlock());

        GameRegistry.registerTileEntity(TransformerTileEntity.class, RandomUtilitiesMod.MODID + "_transformerblock");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(ModBlocks.transformerBlock).setRegistryName(ModBlocks.transformerBlock.getRegistryName()));
    }
}
