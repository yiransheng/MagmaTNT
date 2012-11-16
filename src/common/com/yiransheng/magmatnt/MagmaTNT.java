package com.yiransheng.magmatnt;

import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.RenderEntity;
import net.minecraft.src.RenderTNTPrimed;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraftforge.common.Configuration;

@Mod(modid="MagmaTNT", name="MagmaTNT", version="1.0.0")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class MagmaTNT {	
	
	@Instance("MagmaTNT")
	public static MagmaTNT instance;
	
	@SidedProxy(clientSide="com.yiransheng.magmatnt.client.ClientProxy",
			serverSide="com.yiransheng.magmatnt.CommonProxy")
	public static CommonProxy proxy;
	
	public static Block magmaTNT;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		try {
		    config.load();
		    int blockID = config.getBlock("MagmaTNT", 523).getInt();
		    magmaTNT = new BlockMagmaTNT(blockID, 0, Material.ground)
                .setBlockName("magmaTNT").setCreativeTab(CreativeTabs.tabRedstone);
		} finally {
			config.save();
		}
		
		
	}
	
	@Init
	public void load(FMLInitializationEvent event) {
		
		proxy.registerRenderInformation();
		
		ItemStack gravelStack = new ItemStack(Block.gravel);
		ItemStack gunpowderStack = new ItemStack(Item.gunpowder);
		ItemStack magmaTNTStack = new ItemStack(magmaTNT, 1, 15);
		ItemStack moltenStone = new ItemStack(magmaTNT, 1, 0);
		
		GameRegistry.registerBlock(magmaTNT, MagmaTNYItemBlock.class);	
		
		GameRegistry.addRecipe(new ItemStack(magmaTNT, 1, 15), "xyx", "yxy", "xyx", 
				'x', gunpowderStack, 
				'y', gravelStack);
		LanguageRegistry.addName(magmaTNTStack, "Magma TNT");
		LanguageRegistry.addName(moltenStone, "Molten Stone");
		
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent event) {
		// Stub Method
	}
	
}