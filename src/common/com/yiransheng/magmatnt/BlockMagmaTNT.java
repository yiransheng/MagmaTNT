package com.yiransheng.magmatnt;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import cpw.mods.fml.common.network.EntitySpawnPacket;
import net.minecraftforge.common.ForgeDirection;

import net.minecraft.src.Block;
import net.minecraft.src.BlockStationary;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.EntityTNTPrimed;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.Packet60Explosion;
import net.minecraft.src.Vec3;
import net.minecraft.src.World;

public class BlockMagmaTNT extends Block {

	public BlockMagmaTNT (int id, int texture, Material material) {
		super(id, texture, material);
		this.setLightValue(1.0F);
	}
	
	public void onBlockAdded(World par1World, int par2, int par3, int par4){
		par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, 10);
		
		if (!isBomb(par1World, par2, par3, par4)) return;

		if (par1World.isBlockIndirectlyGettingPowered(par2, par3, par4))
        {
            this.onBlockDestroyedByPlayer(par1World, par2, par3, par4, 1);
            par1World.setBlockWithNotify(par2, par3, par4, 0);
        }
	}
	
	@Override
	public int damageDropped (int metadata) {
		return metadata;
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs tab, List subItems) {
		for (int ix = 0; ix < 16; ix+=15) {
			subItems.add(new ItemStack(this, 1, ix));
		}
	}
	
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
		if (!isBomb(par1World, par2, par3, par4)) return;
		
        if (par5 > 0 && Block.blocksList[par5].canProvidePower() && par1World.isBlockIndirectlyGettingPowered(par2, par3, par4))
        {
            this.onBlockDestroyedByPlayer(par1World, par2, par3, par4, 1);
            par1World.setBlockWithNotify(par2, par3, par4, 0);
        }
    }
	
	public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5)
    {
		if (!isBomb(par1World, par2, par3, par4)) return;
		
        if (!par1World.isRemote)
        {
            if ((par5 & 1) == 1)
            {
            	double x = (double)((float)par2 + 0.5F);
            	double y = (double)((float)par3 + 0.5F);
            	double z = (double)((float)par4 + 0.5F);
            	EntityPrimedMagmaTNT var6 = new EntityPrimedMagmaTNT(par1World, x, y, z);
                par1World.spawnEntityInWorld(var6);
                par1World.playSoundAtEntity(var6, "random.fuse", 1.0F, 1.0F);
                
            }
        }
    }
	
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
		if (!isBomb(par1World, par2, par3, par4)) return super.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9);
		
		if (par5EntityPlayer.getCurrentEquippedItem() != null && par5EntityPlayer.getCurrentEquippedItem().itemID == Item.flintAndSteel.shiftedIndex)
        {
            this.onBlockDestroyedByPlayer(par1World, par2, par3, par4, 1);
            par1World.setBlockWithNotify(par2, par3, par4, 0);
            return true;
        }
        else
        {
            return super.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9);
        }
    }
	
	public int quantityDropped(Random par1Random)
    {
        return 1;
    }
	
	public void updateTick(World w, int par2, int par3, int par4, Random par5Random){
		
        if (!w.isRemote) {
		
			int metadata = w.getBlockMetadata(par2, par3, par4);
	        if (metadata == 15) return;
			
			if (metadata>4) {		
				if (metadata == 9) {
					w.setBlockAndMetadataWithNotify(par2, par3, par4, Block.lavaStill.blockID, 0);
					return;
				} else if (metadata == 12) {
					w.setBlockAndMetadataWithNotify(par2, par3, par4, Block.gravel.blockID, 0);
					w.playSoundEffect(par2, par3, par4, "random.fizz", 4.0F, (1.0F + (w.rand.nextFloat() - w.rand.nextFloat()) * 0.2F) * 0.7F);
					return;
				} 
				metadata ++;
				w.setBlockAndMetadata(par2, par3, par4, this.blockID, metadata);
				w.scheduleBlockUpdate(par2, par3, par4, this.blockID, 5);
				return;			
			}
			
			int x = par2;
			int y = par3-1;
			int z = par4;
			int count;
			count = this.shouldFill(w, x, y, z, false, 0);
			if (count<5) {
				this.shouldFill(w, par2, par3, par4, true, 1);
			}
			x = par2;
			y = par3 + 1;
			z = par4;
			count = this.shouldFill(w, x, y, z, false, 0);
			if (count<5) {
				this.shouldFill(w, par2, par3, par4, true, 2);
			}
			x = par2 -1;
			y = par3;
			z = par4;
			count = this.shouldFill(w, x, y, z, false, 0);
			if (count<5) {
				this.shouldFill(w, par2, par3, par4, true, 3);
			}
			x = par2 + 1;
			y = par3;
			z = par4;
			count = this.shouldFill(w, x, y, z, false, 0);
			if (count<5) {
				this.shouldFill(w, par2, par3, par4, true, 4);
			}
			x = par2;
			y = par3;
			z = par4 - 1;
			count = this.shouldFill(w, x, y, z, false, 0);
			if (count<5) {
				this.shouldFill(w, par2, par3, par4, true, 5);
			}
			x = par2;
			y = par3;
			z = par4 + 1;
			count = this.shouldFill(w, x, y, z, false, 0);
			if (count<5) {
				this.shouldFill(w, par2, par3, par4, true, 6);
			}	
			
			if (metadata<4) {
				metadata +=5;
			} else if(w.rand.nextFloat()<0.25F) {
				w.setBlockAndMetadataWithNotify(par2, par3, par4, Block.lavaStill.blockID, 0);
				return;
			} else {
				metadata = 10;
			}
			
			w.setBlockAndMetadata(par2, par3, par4, this.blockID, metadata);
			w.scheduleBlockUpdate(par2, par3, par4, this.blockID, 5);
        }
								
	}
	
	
	public int shouldFill(World w, int x, int y, int z, boolean fill, int side){
		int count = 0;
		int id = this.blockID;
		int metaData;
		if ((w.getBlockId(x, y, z) == id) || !fill) {
			metaData = w.getBlockMetadata(x, y, z);
		} else {
			return -1;
		}
			
		if (fill && metaData < 4) {
		    if (w.isAirBlock(x, y-1, z)) {
			    count ++;
			    if (side == 1 || side ==0) {
		    	    w.setBlockAndMetadataWithNotify(x, y-1, z, id, metaData+1);
			    }
		    }
		    if (w.isAirBlock(x, y+1, z)) {
			    count ++;
			    if (side == 2 || side ==0) {
		    	    w.setBlockAndMetadataWithNotify(x, y+1, z, id, metaData+1);
			    } 
			}
		    if (w.isAirBlock(x-1, y, z)) {
			    count ++;
			    if (side == 3 || side ==0) {
		    	    w.setBlockAndMetadataWithNotify(x-1, y, z, id, metaData+1);
			    }
			}
		    if (w.isAirBlock(x+1, y, z)) {
			    count ++;	
			    if (side == 4 || side ==0) {
		    	    w.setBlockAndMetadataWithNotify(x+1, y, z, id, metaData+1);
			    }
		    }
		    if (w.isAirBlock(x, y, z-1)) {
			    count ++;
			    if (side == 5 || side ==0) {
		    	    w.setBlockAndMetadataWithNotify(x, y, z-1, id, metaData+1);
			    }
		    }
		    if (w.isAirBlock(x, y, z+1)) {
			    count ++;	
			    if (side == 6 || side ==0) {
		    	    w.setBlockAndMetadataWithNotify(x, y, z+1, id, metaData+1);
			    }
		    }
		} else {
			if (w.isAirBlock(x, y-1, z)) {
			    count ++;			    
		    }
		    if (w.isAirBlock(x, y+1, z)) {
			    count ++;			    
		    }
		    if (w.isAirBlock(x-1, y, z)) {
			    count ++;			    
		    }
		    if (w.isAirBlock(x+1, y, z)) {
			    count ++;			    
		    }
		    if (w.isAirBlock(x, y, z-1)) {
			    count ++;			    
		    }
		    if (w.isAirBlock(x, y, z+1)) {
			    count ++;			    
		    }
		}
		
		return count;
		
	}
	
	public boolean isBomb(World w, int x, int y, int z){
		return w.getBlockMetadata(x,y,z) == 15; 
	} 
	
	@Override
	public int getBlockTextureFromSideAndMetadata (int side, int metadata) {
		if (metadata == 4) return 7;
		
		if (metadata != 15) {
			return metadata<=9 ? metadata % 5 + 3: metadata - 3;
		} else {
			switch(side) {
			case 0:
				return 2;
			case 1:
				return 1;
			default: 
				return 0;				
			}
		}
	}
	
	@Override
	public String getTextureFile () {
		return CommonProxy.BLOCK_PNG;
	}

}