package com.yiransheng.magmatnt;

import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class MagmaTNYItemBlock extends ItemBlock {

	public MagmaTNYItemBlock(int id) {
		super(id);
		setHasSubtypes(true);
		setItemName("Magma TNT");
	}
	
	@Override
	public int getMetadata (int damageValue) {
		return damageValue;
	}
	
	@Override
	public String getItemNameIS(ItemStack itemstack) {
		return itemstack.getItemDamage() == 15 ? "Magma TNT" : "Molten Stone";
	}

}
