package com.yiransheng.magmatnt;


import java.util.Iterator;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.EntityTNTPrimed;
import net.minecraft.src.Explosion;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet60Explosion;
import net.minecraft.src.Vec3;
import net.minecraft.src.World;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;


import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class EntityPrimedMagmaTNT extends EntityTNTPrimed
{
    /** How long the fuse is */
    public int fuse;

    public EntityPrimedMagmaTNT(World par1World)
    {
        super(par1World);
        this.fuse = 0;
        this.preventEntitySpawning = true;
        this.setSize(0.98F, 0.98F);
        this.yOffset = this.height / 2.0F;
    }

    public EntityPrimedMagmaTNT(World par1World, double par2, double par4, double par6)
    {
        this(par1World);
        this.setPosition(par2, par4, par6);
        float var8 = (float)(Math.random() * Math.PI * 2.0D);
        this.motionX = (double)(-((float)Math.sin((double)var8)) * 0.04F);
        this.motionY = 0.040000000298023224D;
        this.motionZ = (double)(-((float)Math.cos((double)var8)) * 0.04F);
        this.fuse = 90;
        this.prevPosX = par2;
        this.prevPosY = par4;
        this.prevPosZ = par6;
    }
    
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.03999999910593033D;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863D;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= 0.9800000190734863D;

        if (this.onGround)
        {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
            this.motionY *= -0.5D;
        }

        if (this.fuse-- <= 0)
        {
            this.setDead();

            if (!this.worldObj.isRemote)
            {
                this.doExplode();
            }
        }
        else
        {
            this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5D, this.posZ, this.motionX, -this.motionY, this.motionZ);
        }
        
    }


    private void doExplode()
    {
    	float var1 = 3.0F;
        this.createExplosion((Entity)null, this.posX, this.posY, this.posZ, var1, false, true);
    }
    
    public magmaExplosion createExplosion(Entity par1Entity, double par2, double par4, double par6, float par8, boolean par9, boolean par10)
    {
    	magmaExplosion var11 = new magmaExplosion(this.worldObj, par1Entity, par2, par4, par6, par8);
        var11.isFlaming = par9;
        var11.field_82755_b = par10;
        var11.doExplosionA();
        var11.doExplosionB(false);

        if (!par10)
        {
            var11.field_77281_g.clear();
        }

        Iterator var12 = this.worldObj.playerEntities.iterator();

        while (var12.hasNext())
        {
            EntityPlayer var13 = (EntityPlayer)var12.next();

            if (var13.getDistanceSq(par2, par4, par6) < 4096.0D)
            {
                ((EntityPlayerMP)var13).playerNetServerHandler.sendPacketToPlayer(new Packet60Explosion(par2, par4, par6, par8, var11.field_77281_g, (Vec3)var11.func_77277_b().get(var13)));
            }
        }

        return var11;
    }

}
