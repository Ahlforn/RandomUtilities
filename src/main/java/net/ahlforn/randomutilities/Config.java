package net.ahlforn.randomutilities;

import net.ahlforn.randomutilities.proxy.CommonProxy;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

public class Config {
    private static final String CATEGORY_GENERAL = "general";
    private static final String CATEGORY_TRANSFORMER = "transformer";

    public static int baseRFValue = 1000;
    public static int baseEUValue = 250;

    public static void readConfig() {
        Configuration cfg = CommonProxy.config;
        try{
            cfg.load();
            initGeneralConfig(cfg);
            initTransformerConfig(cfg);
        } catch(Exception e) {
            RandomUtilitiesMod.logger.log(Level.ERROR, "Problem loading config file!", e);
        } finally {
            if(cfg.hasChanged()) {
                cfg.save();
            }
        }
    }

    private static void initGeneralConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General Configuration");
    }

    private static void initTransformerConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_TRANSFORMER, "Transformer Configuration");
        baseRFValue = cfg.getInt("baseRFValue", CATEGORY_TRANSFORMER, baseRFValue, 0, 10000, "Base value of RF power.");
        baseEUValue = cfg.getInt("baseEUValue", CATEGORY_TRANSFORMER, baseEUValue, 0, 10000, "Base value of EU power.");
    }
}
