/**
 * 
 */
package cn.liutils.api.client;

import cn.liutils.api.client.register.IKeyProcess;
import cn.liutils.api.client.render.PlayerRenderHelper;
import cn.liutils.api.entity.EntityPlayerRenderHelper;
import cn.liutils.core.client.register.LIKeyProcess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * 客户端的各种注册辅助。
 * @author WeAthFolD
 *
 */
@SideOnly(Side.CLIENT)
public class LIClientRegistry {

	public static void addKey(String s, int key, boolean isRep, IKeyProcess process) {
		LIKeyProcess.addKey(s, key, isRep, process);
	}
	
	public static void addPlayerRenderingHelper(PlayerRenderHelper helper) {
		EntityPlayerRenderHelper.addRenderHelper(helper);
	}
	
	
}
