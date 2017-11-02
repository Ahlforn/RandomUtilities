package net.ahlforn.randomutilities.blocks.transformerblock;

import net.ahlforn.randomutilities.Config;

public class TransformerCalc {

    public enum EnergyTypes {
        RF, EU
    }

    public static int toEU(int value, EnergyTypes from) {
        switch(from) {
            case RF:
                return (int) value * (Config.baseRFValue / Config.baseEUValue);
        }

        return 0;
    }

    public static int toRF(int value, EnergyTypes from) {
        switch(from) {
            case EU:
                return (int) value * (Config.baseEUValue / Config.baseRFValue);
        }

        return 0;
    }

}
