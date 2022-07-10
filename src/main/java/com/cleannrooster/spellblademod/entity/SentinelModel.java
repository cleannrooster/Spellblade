package com.cleannrooster.spellblademod.entity;// Made with Blockbench 4.2.5
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class SentinelModel<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("spellblademod", "sentinel"), "main");
    private final ModelPart bone;
    private final ModelPart bone2;
    private final ModelPart bone3;
    private final ModelPart bone4;
    private final ModelPart bone5;

    public SentinelModel(ModelPart root) {
        this.bone = root.getChild("bone");
        this.bone2 = root.getChild("bone2");
        this.bone3 = root.getChild("bone3");
        this.bone4 = root.getChild("bone4");
        this.bone5 = root.getChild("bone5");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = bone.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(46, 20).addBox(5.0F, -7.0F, 4.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 60).addBox(5.0F, -4.0F, -4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(12, 20).addBox(5.0F, -6.0F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(46, 25).addBox(5.0F, -7.0F, -5.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(5.0F, -10.0F, -1.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 34).addBox(5.0F, -9.0F, -5.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(18, 34).addBox(5.0F, -9.0F, 2.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(40, 9).addBox(5.0F, -10.0F, -5.0F, 2.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(30, 29).addBox(5.0F, -12.0F, 4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 6).addBox(6.0F, -11.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 29).addBox(6.0F, -11.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 6).addBox(5.0F, -12.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(40, 15).addBox(5.0F, -12.0F, -5.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(46, 20).addBox(5.0F, -13.0F, -5.0F, 2.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(8, 16).addBox(5.0F, -14.0F, -5.0F, 2.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bone2 = partdefinition.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(8, 16).addBox(5.0F, -14.0F, -5.0F, 2.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(54, 31).addBox(5.0F, -13.0F, -5.0F, 2.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(54, 11).addBox(5.0F, -12.0F, -5.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 44).addBox(6.0F, -11.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 4).addBox(5.0F, -12.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(40, 9).addBox(5.0F, -10.0F, -1.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(54, 0).addBox(5.0F, -10.0F, -5.0F, 2.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(54, 42).addBox(5.0F, -9.0F, 2.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(24, 60).addBox(5.0F, -9.0F, -5.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(13, 22).addBox(5.0F, -6.0F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 52).addBox(5.0F, -7.0F, -5.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(62, 65).addBox(5.0F, -4.0F, -4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(28, 53).addBox(5.0F, -7.0F, 4.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(54, 3).addBox(5.0F, -12.0F, 4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 44).addBox(6.0F, -11.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition bone3 = partdefinition.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r2 = bone3.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(52, 64).addBox(5.0F, -4.0F, -4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(28, 48).addBox(5.0F, -7.0F, 4.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(12, 22).addBox(5.0F, -6.0F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(42, 49).addBox(5.0F, -7.0F, -5.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 23).addBox(5.0F, -10.0F, -1.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(18, 39).addBox(5.0F, -9.0F, -5.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(36, 40).addBox(5.0F, -9.0F, 2.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(28, 49).addBox(5.0F, -10.0F, -5.0F, 2.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(14, 53).addBox(5.0F, -12.0F, 4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 44).addBox(6.0F, -11.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(40, 20).addBox(6.0F, -11.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 0).addBox(5.0F, -12.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(54, 0).addBox(5.0F, -12.0F, -5.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(42, 50).addBox(5.0F, -13.0F, -5.0F, 2.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(8, 16).addBox(5.0F, -14.0F, -5.0F, 2.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition bone4 = partdefinition.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -16.0F, -5.0F, 10.0F, 13.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(54, 11).addBox(-3.0F, -1.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(30, 0).addBox(-4.0F, -2.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 23).addBox(-5.0F, -3.0F, -5.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r3 = bone4.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 47).addBox(5.0F, -7.0F, 4.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(42, 61).addBox(5.0F, -4.0F, -4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(13, 23).addBox(5.0F, -6.0F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(14, 48).addBox(5.0F, -7.0F, -5.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 23).addBox(5.0F, -10.0F, -1.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(36, 35).addBox(5.0F, -9.0F, -5.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 39).addBox(5.0F, -9.0F, 2.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 47).addBox(5.0F, -10.0F, -5.0F, 2.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(46, 31).addBox(5.0F, -12.0F, 4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(35, 31).addBox(6.0F, -11.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 29).addBox(5.0F, -12.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(52, 31).addBox(5.0F, -12.0F, -5.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 29).addBox(6.0F, -11.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 48).addBox(5.0F, -13.0F, -5.0F, 2.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(10, 17).addBox(5.0F, -14.0F, -5.0F, 2.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition bone5 = partdefinition.addOrReplaceChild("bone5", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone4.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone5.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}