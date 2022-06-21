package com.cleannrooster.spellblademod;

import com.cleannrooster.spellblademod.blocks.WardIronBlock;
import com.cleannrooster.spellblademod.blocks.WardingTotemBlock;
import com.cleannrooster.spellblademod.blocks.Wardlight;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS
            = DeferredRegister.create(ForgeRegistries.BLOCKS, "spellblademod");
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        return toReturn;
    }

    public static final RegistryObject<Block> WARDING_TOTEM_BLOCK = registerBlock("warding_totem_block",
            () -> new WardingTotemBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2F,3).requiresCorrectToolForDrops().sound(SoundType.WOOD)));
    public static final RegistryObject<Block> WARDLIGHT_BLOCK = registerBlock("wardlight",
            () -> new Wardlight(BlockBehaviour.Properties.of(Material.AIR).strength(-1.0F, 3600000.8F).noDrops().noOcclusion().lightLevel(LightBlock.LIGHT_EMISSION)));
    public static final RegistryObject<Block> WARD_IRON_BLOCK = registerBlock("ward_iron_block",
            () -> new WardIronBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)));

}
