package com.yiransheng.magmatnt.client;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.src.RenderTNTPrimed;
import net.minecraftforge.client.MinecraftForgeClient;
import com.yiransheng.magmatnt.CommonProxy;
import com.yiransheng.magmatnt.EntityPrimedMagmaTNT;
import com.yiransheng.magmatnt.RenderPrimedMagmaTNT;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerRenderInformation() {
		MinecraftForgeClient.preloadTexture(BLOCK_PNG);
		RenderingRegistry.registerEntityRenderingHandler(EntityPrimedMagmaTNT.class, new RenderPrimedMagmaTNT());
	}
	
}