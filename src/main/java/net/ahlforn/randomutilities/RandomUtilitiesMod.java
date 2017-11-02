package net.ahlforn.randomutilities;

import net.ahlforn.randomutilities.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.Logger;

@Mod(modid = RandomUtilitiesMod.MODID, name = RandomUtilitiesMod.MODNAME, version = RandomUtilitiesMod.VERSION)
public class RandomUtilitiesMod
{
    public static final String MODID = "randomutilities";
    public static final String MODNAME = "Random Utilities";
    public static final String VERSION = "1.0";

    @SidedProxy(clientSide = "net.ahlforn.randomutilities.proxy.ClientProxy",
            serverSide = "net.ahlforn.randomutilities.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static RandomUtilitiesMod instance;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        logger = e.getModLog();
        proxy.preInit(e);
    }
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent e)
    {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent e) {
        // TODO: server start code.
    }
}
