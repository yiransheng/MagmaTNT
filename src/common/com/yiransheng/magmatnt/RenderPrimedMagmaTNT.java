package com.yiransheng.magmatnt;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityTNTPrimed;
import net.minecraft.src.Render;
import net.minecraft.src.RenderBlocks;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderPrimedMagmaTNT extends Render
{
    private RenderBlocks blockRenderer = new RenderBlocks();

    public void EntityPrimedMagmaTNT()
    {
        this.shadowSize = 0.5F;
    }

    private void renderPrimedTNT(EntityTNTPrimed par1EntityTNTPrimed, double par2, double par4, double par6, float par8, float par9)
    {
    	GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        float var10;

        if ((float)par1EntityTNTPrimed.fuse - par9 + 1.0F < 10.0F)
        {
            var10 = 1.0F - ((float)par1EntityTNTPrimed.fuse - par9 + 1.0F) / 10.0F;

            if (var10 < 0.0F)
            {
                var10 = 0.0F;
            }

            if (var10 > 1.0F)
            {
                var10 = 1.0F;
            }

            var10 *= var10;
            var10 *= var10;
            float var11 = 1.0F + var10 * 0.3F;
            GL11.glScalef(var11, var11, var11);
        }

        var10 = (1.0F - ((float)par1EntityTNTPrimed.fuse - par9 + 1.0F) / 100.0F) * 0.8F;
        this.loadTexture(CommonProxy.BLOCK_PNG);
        this.blockRenderer.renderBlockAsItem(MagmaTNT.magmaTNT, 0, par1EntityTNTPrimed.getBrightness(par9)/2);

        if (par1EntityTNTPrimed.fuse / 5 % 3 == 0)
        {
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_DST_ALPHA);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, var10);
            this.blockRenderer.renderBlockAsItem(MagmaTNT.magmaTNT, 0, par1EntityTNTPrimed.getBrightness(par9));
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }

        GL11.glPopMatrix();
    }

    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderPrimedTNT((EntityTNTPrimed)par1Entity, par2, par4, par6, par8, par9);
    }
}
