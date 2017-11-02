package net.ahlforn.randomutilities.blocks.transformerblock;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.info.Info;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Optional;

import javax.annotation.Nullable;

@Optional.Interface(iface = "ic2.api.energy.tile.IEnergySink", modid = "ic2", striprefs = true)
@Optional.Interface(iface = "ic2.api.energy.tile.IEnergySource", modid = "ic2", striprefs = true)
public class TransformerTileEntity extends TileEntity implements ITickable, IEnergySink, IEnergySource {

    private final int CAPACITY = 100000;
    private EnergyStorage energyStorage;
    private int ic2Tier = 1;
    private boolean ic2OnNet = false;

    static Capability<IEnergyStorage> ENERGY = null;

    public TransformerTileEntity() {
        energyStorage = new EnergyStorage(CAPACITY);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityEnergy.ENERGY ? CapabilityEnergy.ENERGY.cast(this.energyStorage) : super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.ic2Tier = compound.getInteger("ic2Tier");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("ic2Tier", this.ic2Tier);
        return super.writeToNBT(compound);
    }

    private void onLoaded() {
        super.onLoad();
        if(this.ic2OnNet || FMLCommonHandler.instance().getEffectiveSide().isClient() || !Info.isIc2Available()) return;
        MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
        this.ic2OnNet = true;
    }

    @Override
    public void onChunkUnload() {
        super.onChunkUnload();
        if(this.ic2OnNet && Info.isIc2Available()) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
            this.ic2OnNet = false;
        }
    }

    @Override
    public void invalidate() {
        super.invalidate();
        onChunkUnload();
    }

    @Override
    public void update() {
        if(this.getWorld().isRemote) return;
        if(!ic2OnNet) onLoaded();
    }

    @Optional.Method(modid = "ic2")
    @Override
    public int getSinkTier() {
        return ic2Tier;
    }

    @Optional.Method(modid = "ic2")
    @Override
    public double getDemandedEnergy() {
        double d = TransformerCalc.toEU(energyStorage.getMaxEnergyStored() - energyStorage.getEnergyStored(), TransformerCalc.EnergyTypes.RF);
        return (d > 0) ? Math.min(d, EnergyNet.instance.getPowerFromTier(this.ic2Tier)) : 0;
    }

    @Optional.Method(modid = "ic2")
    @Override
    public boolean acceptsEnergyFrom(IEnergyEmitter emitter, EnumFacing facing) {
        return true;
    }

    @Optional.Method(modid = "ic2")
    @Override
    public double injectEnergy(EnumFacing facing, double value, double tier) {
        return value - TransformerCalc.toEU(energyStorage.receiveEnergy(TransformerCalc.toRF((int) value, TransformerCalc.EnergyTypes.EU), false), TransformerCalc.EnergyTypes.RF);
    }

    @Optional.Method(modid = "ic2")
    @Override
    public double getOfferedEnergy() {
        return Math.min(TransformerCalc.toEU(energyStorage.getEnergyStored(), TransformerCalc.EnergyTypes.RF), EnergyNet.instance.getPowerFromTier(this.ic2Tier));
    }

    @Optional.Method(modid = "ic2")
    @Override
    public void drawEnergy(double v) {
        energyStorage.extractEnergy(TransformerCalc.toRF((int) v, TransformerCalc.EnergyTypes.EU), false);
    }

    @Optional.Method(modid = "ic2")
    @Override
    public int getSourceTier() {
        return ic2Tier;
    }

    @Optional.Method(modid = "ic2")
    @Override
    public boolean emitsEnergyTo(IEnergyAcceptor acceptor, EnumFacing facing) {
        return true;
    }

    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

}