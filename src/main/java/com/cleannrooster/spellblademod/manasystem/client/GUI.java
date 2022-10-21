package com.cleannrooster.spellblademod.manasystem.client;

import com.cleannrooster.spellblademod.manasystem.manatick;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraftforge.client.gui.ForgeIngameGui;

public class GUI extends ForgeIngameGui {
    public int health = 0;
    public GUI(Minecraft p_93005_) {
        super(p_93005_);
    }

    public int wardheight = 39;
    public int healthheight = 39;

    public int tickCount;

    public int healthBlinkTime;


    private LivingEntity getPlayerVehicleWithHealth() {
        Player player = this.minecraft.player;
        if (player != null) {
            Entity entity = player.getVehicle();
            if (entity == null) {
                return null;
            }

            if (entity instanceof LivingEntity) {
                return (LivingEntity)entity;
            }
        }

        return null;
    }

    private int getVisibleVehicleHeartRows(int p_93013_) {
        return (int)Math.ceil((double)p_93013_ / 10.0D);
    }
    private int getVehicleMaxHearts(LivingEntity p_93023_) {
        if (p_93023_ != null && p_93023_.showVehicleHealth()) {
            float f = p_93023_.getMaxHealth();
            int i = (int)(f + 0.5F) / 2;
            if (i > 30) {
                i = 30;
            }

            return i;
        } else {
            return 0;
        }
    }
    public void renderWard(int width, int height, PoseStack pStack)
    {
        bind(new ResourceLocation("spellblademod", "textures/gui/icons.png"));
        minecraft.getProfiler().push("health");
        RenderSystem.enableBlend();

        Player player = (Player)this.minecraft.player;
        int health = (int) Math.min(Math.round(player.getAttributeValue(manatick.WARD)/40),20);
        boolean highlight = healthBlinkTime > (long)tickCount && (healthBlinkTime - (long)tickCount) / 3L %2L == 1L;




        AttributeInstance attrMaxHealth = player.getAttribute(Attributes.MAX_HEALTH);
        float healthMax = (float) Math.max(attrMaxHealth.getValue(), Math.max(player.getHealth(),health));
        int absorb = 0;
        int healthRows = Mth.ceil((health) / 2.0F / 10.0F);
        int rowHeight = 10;

        this.random.setSeed((long)(tickCount * 312871));

        int left = width / 2 - 91;
        int top = height - left_height;

        left_height += (healthRows * rowHeight);

        int regen = -1;
        if (player.hasEffect(MobEffects.REGENERATION))
        {
            regen = this.tickCount % Mth.ceil(healthMax + 5.0F);
        }

        this.renderWardHearts(pStack, player, left, top, rowHeight, -1, health, health, health, 0, false);

        RenderSystem.disableBlend();
        minecraft.getProfiler().pop();
    }
   /* public void renderHealth(int width, int height, PoseStack pStack,Gui gui)
    {
        bind(GUI_ICONS_LOCATION);
        minecraft.getProfiler().push("health");
        RenderSystem.enableBlend();

        Player player = (Player)this.minecraft.getCameraEntity();
        int health = Mth.ceil(player.getHealth());
        boolean highlight = healthBlinkTime > (long)tickCount && (healthBlinkTime - (long)tickCount) / 3L %2L == 1L;

        if (health < this.lastHealth && player.invulnerableTime > 0)
        {
            this.lastHealthTime = Util.getMillis();
            this.healthBlinkTime = (long)(this.tickCount + 20);
        }
        else if (health > this.lastHealth && player.invulnerableTime > 0)
        {
            this.lastHealthTime = Util.getMillis();
            this.healthBlinkTime = (long)(this.tickCount + 10);
        }

        if (Util.getMillis() - this.lastHealthTime > 1000L)
        {
            this.lastHealth = health;
            this.displayHealth = health;
            this.lastHealthTime = Util.getMillis();
        }

        this.lastHealth = health;
        int healthLast = this.displayHealth;

        AttributeInstance attrMaxHealth = player.getAttribute(Attributes.MAX_HEALTH);
        float healthMax = Math.max((float)attrMaxHealth.getValue(), Math.max(healthLast, health));
        int absorb = Mth.ceil(player.getAbsorptionAmount());

        int healthRows = Mth.ceil((healthMax + absorb) / 2.0F / 10.0F);
        int rowHeight = Math.max(10 - (healthRows - 2), 3);

        this.random.setSeed((long)(tickCount * 312871));

        int left = width / 2 - 91;
        int top = height - left_height;
        healthheight += rowHeight*healthRows;
        if (rowHeight != 10) healthheight += 10 - rowHeight;

        int regen = -1;
        if (player.hasEffect(MobEffects.REGENERATION))
        {
            regen = this.tickCount % Mth.ceil(healthMax + 5.0F);
        }

        this.renderHearts(pStack, player, left, top, rowHeight, regen, healthMax, health, healthLast, absorb, highlight);

        RenderSystem.disableBlend();
        minecraft.getProfiler().pop();
    }*/
    protected void renderWardHearts(PoseStack p_168689_, Player p_168690_, int p_168691_, int p_168692_, int p_168693_, int p_168694_, float p_168695_, int p_168696_, int p_168697_, int p_168698_, boolean p_168699_) {
        HeartType gui$hearttype = HeartType.ABSORBING;
        int i = 9 * (p_168690_.level.getLevelData().isHardcore() ? 5 : 0);
        int j = Mth.ceil((double)p_168695_ / 2.0D);
        int k = Mth.ceil((double)p_168698_ / 2.0D);
        int l = j * 2;

        for(int i1 = j + k - 1; i1 >= 0; --i1) {
            int j1 = i1 / 10;
            int k1 = i1 % 10;
            int l1 = p_168691_ + k1 * 8;
            int i2 = p_168692_ - j1 * p_168693_;

            if (i1 < j && i1 == p_168694_) {
                i2 -= 2;
            }
            int j2 = i1 * 2;
            boolean flag = i1 >= j;
            this.renderHeart(p_168689_, HeartType.CONTAINER, l1, i2, i, p_168699_, false);

            if (flag) {
                    int k2 = j2 - l;
                    if (k2 < p_168698_) {
                        boolean flag1 = k2 + 1 == p_168698_;
                        this.renderHeart(p_168689_, HeartType.ABSORBING, l1, i2, i, false, flag1);
                    }
                }

                if (p_168699_ && j2 < p_168697_) {
                    boolean flag2 = j2 + 1 == p_168697_;
                    this.renderHeart(p_168689_, HeartType.ABSORBING, l1, i2, i, true, flag2);
                }

                if (j2 < p_168696_) {
                    boolean flag3 = j2 + 1 == p_168696_;
                    this.renderHeart(p_168689_, HeartType.ABSORBING, l1, i2, i, false, flag3);
                }

        }

    }
    public static void blit(PoseStack p_93201_, int p_93202_, int p_93203_, int p_93204_, int p_93205_, int p_93206_, TextureAtlasSprite p_93207_) {
        innerBlit(p_93201_.last().pose(), p_93202_, p_93202_ + p_93205_, p_93203_, p_93203_ + p_93206_, p_93204_, p_93207_.getU0(), p_93207_.getU1(), p_93207_.getV0(), p_93207_.getV1());
    }

    public void blit(PoseStack p_93229_, int p_93230_, int p_93231_, int p_93232_, int p_93233_, int p_93234_, int p_93235_) {
        blit(p_93229_, p_93230_, p_93231_, this.getBlitOffset(), (float)p_93232_, (float)p_93233_, p_93234_, p_93235_, 256, 256);
    }



    public static void blit(PoseStack p_93144_, int p_93145_, int p_93146_, int p_93147_, float p_93148_, float p_93149_, int p_93150_, int p_93151_, int p_93152_, int p_93153_) {
        innerBlit(p_93144_, p_93145_, p_93145_ + p_93150_, p_93146_, p_93146_ + p_93151_, p_93147_, p_93150_, p_93151_, p_93148_, p_93149_, p_93152_, p_93153_);
    }

    public static void blit(PoseStack p_93161_, int p_93162_, int p_93163_, int p_93164_, int p_93165_, float p_93166_, float p_93167_, int p_93168_, int p_93169_, int p_93170_, int p_93171_) {
        innerBlit(p_93161_, p_93162_, p_93162_ + p_93164_, p_93163_, p_93163_ + p_93165_, 0, p_93168_, p_93169_, p_93166_, p_93167_, p_93170_, p_93171_);
    }

    public static void blit(PoseStack p_93134_, int p_93135_, int p_93136_, float p_93137_, float p_93138_, int p_93139_, int p_93140_, int p_93141_, int p_93142_) {
        blit(p_93134_, p_93135_, p_93136_, p_93139_, p_93140_, p_93137_, p_93138_, p_93139_, p_93140_, p_93141_, p_93142_);
    }

    private static void innerBlit(PoseStack p_93188_, int p_93189_, int p_93190_, int p_93191_, int p_93192_, int p_93193_, int p_93194_, int p_93195_, float p_93196_, float p_93197_, int p_93198_, int p_93199_) {
        innerBlit(p_93188_.last().pose(), p_93189_, p_93190_, p_93191_, p_93192_, p_93193_, (p_93196_ + 0.0F) / (float)p_93198_, (p_93196_ + (float)p_93194_) / (float)p_93198_, (p_93197_ + 0.0F) / (float)p_93199_, (p_93197_ + (float)p_93195_) / (float)p_93199_);
    }

    private static void innerBlit(Matrix4f p_93113_, int p_93114_, int p_93115_, int p_93116_, int p_93117_, int p_93118_, float p_93119_, float p_93120_, float p_93121_, float p_93122_) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(p_93113_, (float)p_93114_, (float)p_93117_, (float)p_93118_).uv(p_93119_, p_93122_).endVertex();
        bufferbuilder.vertex(p_93113_, (float)p_93115_, (float)p_93117_, (float)p_93118_).uv(p_93120_, p_93122_).endVertex();
        bufferbuilder.vertex(p_93113_, (float)p_93115_, (float)p_93116_, (float)p_93118_).uv(p_93120_, p_93121_).endVertex();
        bufferbuilder.vertex(p_93113_, (float)p_93114_, (float)p_93116_, (float)p_93118_).uv(p_93119_, p_93121_).endVertex();
        bufferbuilder.end();
        BufferUploader.end(bufferbuilder);
    }
    protected void renderWard(PoseStack p_168689_, Player p_168690_, int p_168691_, int p_168692_, int p_168693_, int p_168694_, float p_168695_, int p_168696_, int p_168697_, int p_168698_, boolean p_168699_) {
        HeartType gui$hearttype = HeartType.forPlayer(p_168690_);
        int i = 9 * (p_168690_.level.getLevelData().isHardcore() ? 5 : 0);
        int j = Mth.ceil((double)p_168695_ / 2.0D);
        int k = Mth.ceil((double)p_168698_ / 2.0D);
        int l = j * 2;

        for(int i1 = j + k - 1; i1 >= 0; --i1) {
            int j1 = i1 / 10;
            int k1 = i1 % 10;
            int l1 = p_168691_ + k1 * 8;
            int i2 = p_168692_ - j1 * p_168693_;
            /*if (p_168696_ + p_168698_ <= 4) {
                i2 += this.random.nextInt(2);
            }*/

            if (i1 < j && i1 == p_168694_) {
                i2 -= 2;
            }

            this.renderHeart(p_168689_, HeartType.CONTAINER, l1, i2, i, p_168699_, false);
            int j2 = i1 * 2;
            boolean flag = i1 >= j;

                if (flag) {
                    int k2 = j2 - l;
                    if (k2 < p_168698_) {
                        boolean flag1 = k2 + 1 == p_168698_;
                        this.renderHeart(p_168689_, HeartType.ABSORBING, l1, i2, i, false, flag1);
                    }
                }

                if (p_168699_ && j2 < p_168697_) {
                    boolean flag2 = j2 + 1 == p_168697_;
                    this.renderHeart(p_168689_, HeartType.ABSORBING, l1, i2, i, true, flag2);
                }

                if (j2 < p_168696_) {
                    boolean flag3 = j2 + 1 == p_168696_;
                    this.renderHeart(p_168689_, HeartType.ABSORBING, l1, i2, i, false, flag3);
                }


        }

    }
    private void renderHeart(PoseStack p_168701_, HeartType p_168702_, int p_168703_, int p_168704_, int p_168705_, boolean p_168706_, boolean p_168707_) {
        this.blit(p_168701_, p_168703_, p_168704_, p_168702_.getX(p_168707_, p_168706_), p_168705_, 9, 9);
    }

    private static void bind(ResourceLocation res)
    {
        RenderSystem.setShaderTexture(0, res);
    }


    protected int screenWidth = minecraft.getWindow().getGuiScaledWidth();
    protected  int screenHeight = minecraft.getWindow().getGuiScaledWidth();;


    static enum HeartType {
        CONTAINER(0, false),
        NORMAL(2, true),
        POISIONED(4, true),
        WITHERED(6, true),
        ABSORBING(8, false),
        FROZEN(9, false);

        private final int index;
        private final boolean canBlink;

        private HeartType(int p_168729_, boolean p_168730_) {
            this.index = p_168729_;
            this.canBlink = p_168730_;
        }

        public int getX(boolean p_168735_, boolean p_168736_) {
            int i;
            if (this == CONTAINER) {
                i = p_168736_ ? 1 : 0;
            } else {
                int j = p_168735_ ? 1 : 0;
                int k = this.canBlink && p_168736_ ? 2 : 0;
                i = j + k;
            }

            return 16 + (this.index * 2 + i) * 9;
        }

        static HeartType forPlayer(Player p_168733_) {
            HeartType gui$hearttype;
            if (p_168733_.hasEffect(MobEffects.POISON)) {
                gui$hearttype = POISIONED;
            } else if (p_168733_.hasEffect(MobEffects.WITHER)) {
                gui$hearttype = WITHERED;
            } else if (p_168733_.isFullyFrozen()) {
                gui$hearttype = FROZEN;
            } else {
                gui$hearttype = NORMAL;
            }

            return gui$hearttype;
        }
    }

    }

