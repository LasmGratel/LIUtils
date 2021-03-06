package cn.weaponmod.api.weapon;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cn.liutils.api.entity.EntityBullet;
import cn.weaponmod.api.WMInformation;
import cn.weaponmod.api.WeaponHelper;
import cn.weaponmod.api.action.Action;
import cn.weaponmod.api.action.ActionAutomaticShoot;
import cn.weaponmod.api.action.ActionJam;
import cn.weaponmod.api.action.ActionReload;
import cn.weaponmod.api.action.ActionShoot;
import cn.weaponmod.api.action.ActionUplift;
import cn.weaponmod.api.information.InfUtils;
import cn.weaponmod.api.information.InfWeapon;
import cn.weaponmod.core.event.ItemControlHandler;
import cn.weaponmod.core.proxy.WMClientProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Typical weapon class support shooting, reloading and simple reload&muzzleflash animations.
 * extend it to make your own custom^^
 * @author WeAthFolD
 */
public class WeaponGeneric extends WeaponGenericBase {

	public WeaponGeneric(Item ammo) {
		super(ammo);
		setMaxStackSize(1);
	}
	
	protected String CHANNEL_SHOOT = "shoot";
	
	/**
	 * Actions corresponding for their name.
	 * The time of execution is scheduled by Item class. Just make the action do the job.
	 */
	public Action
		actionShoot = new ActionShoot(5, ""),
		actionUplift = new ActionUplift(),
		actionReload = new ActionReload(20, "", ""),
		actionJam = new ActionJam(10, "");

	// -------------------Attributes-------------------
	
	public boolean useAutomaticShoot() {
		return true;
	}
	
	//------------------Utilities---------------------
	/**
	 * 用来决定当前武器是否可以射击【弹药充足】。
	 */
	public boolean canShoot(EntityPlayer player, ItemStack is) {
		if(getMaxDamage() == 0)
			return WeaponHelper.hasAmmo(this, player) || player.capabilities.isCreativeMode;
		return this.getAmmo(is) > 0 || player.capabilities.isCreativeMode;
	}
	
	//-------------------Functions--------------
	
	@Override
	public void onItemClick(World world, EntityPlayer player, ItemStack stack, int keyid) {
		InfWeapon inf = loadInformation(player);
		switch(keyid) {
		case 0: //LMOUSE
			if(!canShoot(player, stack)) {
				inf.executeAction(actionJam);
			} else {
				inf.executeAction(actionShoot);
			}
			break;
		case 2: //Reload
			inf.executeAction(actionReload);
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemRelease(World world, EntityPlayer pl, ItemStack stack,
			int keyid) {
		InfWeapon inf = loadInformation(pl);
		switch(keyid) {
		case 0: //LMOUSE
			inf.removeAction(actionShoot.name);
			inf.removeAction(actionJam.name);
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemUsingTick(World world, EntityPlayer player,
			ItemStack stack, int keyid, int ticks) {
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer, int par4) { }
	
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 0;
	}
	
	protected NBTTagCompound loadNBT(ItemStack s) {
		if (s.stackTagCompound == null)
			s.stackTagCompound = new NBTTagCompound();
		return s.stackTagCompound;
	}
}
