/**
 * Copyright (C) Lambda-Innovation, 2013-2014
 * This code is open-source. Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer. 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 */
package cn.liutils.api.block;

import cn.liutils.core.LIUtilsMod;
import cn.liutils.core.network.MsgTileDMulti;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * 主要用来在子方块太多的时候，托管metadata以绕过byte值存储的限制~
 * @author WeAthFolD
 */
public class TileDirectionedMulti extends TileEntity implements IMetadataProvider {

	//#boilerplate0
	private boolean synced = false;
	private int ticksUntilReq = 4;
	int metadata = -1;
	
	public void updateEntity() {
		if(metadata == -1) {
			metadata = this.getBlockMetadata();
		}
		if(worldObj.isRemote && !synced && ++ticksUntilReq == 5) {
			ticksUntilReq = 0;
			LIUtilsMod.netHandler.sendToServer(new MsgTileDMulti.Request(this));
		}
	}
	
	public void setMetadata(int meta) {
		synced = true;
		metadata = meta;
	}
	
    public void readFromNBT(NBTTagCompound nbt)
    {
    	metadata = nbt.getInteger("meta");
        super.readFromNBT(nbt);
    }

    public void writeToNBT(NBTTagCompound nbt)
    {
    	nbt.setInteger("meta", metadata);
        super.writeToNBT(nbt);
    }

	@Override
	public int getMetadata() {
		if(metadata == -1)
			metadata = this.getBlockMetadata();
		return metadata;
	}
	//#end boilerplate0

}