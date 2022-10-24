package com.cleannrooster.spellblademod;

import com.besaba.revonline.pastebinapi.Pastebin;
import com.besaba.revonline.pastebinapi.impl.factory.PastebinFactory;
import com.cleannrooster.spellblademod.blocks.ModTileEntity;
import com.cleannrooster.spellblademod.enchants.GreaterWardingEnchant;
import com.cleannrooster.spellblademod.enchants.SpellProxy;
import com.cleannrooster.spellblademod.enchants.WardTempered;
import com.cleannrooster.spellblademod.enchants.WardingEnchant;
import com.cleannrooster.spellblademod.entity.ModEntities;
import com.cleannrooster.spellblademod.items.ModItems;
import com.cleannrooster.spellblademod.items.Spell;
import com.cleannrooster.spellblademod.manasystem.manatick;
import com.cleannrooster.spellblademod.patreon.KeyHandler;
import com.cleannrooster.spellblademod.setup.Config;
import com.cleannrooster.spellblademod.setup.ModSetup;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.logging.LogUtils;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.stream.Collectors;

import static com.cleannrooster.spellblademod.StatusEffectsModded.registerStatusEffects;
import static com.cleannrooster.spellblademod.items.ModItems.*;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("spellblademod")
public class SpellbladeMod
{
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public static Enchantment wardTempered = new WardTempered(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND).setRegistryName("wardtempered");
    public static Enchantment warding = new WardingEnchant(Enchantment.Rarity.UNCOMMON,EnchantmentCategory.ARMOR,ARMOR_SLOTS).setRegistryName("lesserwarding");
    public static Enchantment greaterwarding = new GreaterWardingEnchant(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.ARMOR,ARMOR_SLOTS).setRegistryName("greaterwarding");
    public static Enchantment spellproxy = new SpellProxy(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND).setRegistryName("spellproxy");
    public static String UUIDS;

    public SpellbladeMod()
    {

        MinecraftForge.EVENT_BUS.register(this);
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        registerStatusEffects();
        ModBlocks.register(eventBus);
        ModTileEntity.register(eventBus);
        //ENCHANTMENTS.register(eventBus);

        ForgeRegistries.ENCHANTMENTS.register(wardTempered);
        ForgeRegistries.ENCHANTMENTS.register(warding);
        ForgeRegistries.ENCHANTMENTS.register(greaterwarding);
        ForgeRegistries.ENCHANTMENTS.register(spellproxy);
        //Instantiating the URL class
        URL url = null;
        try {
            url = new URL("https://pastebin.com/raw/"+"6ZNv6DDb");

        //Retrieving the contents of the specified page
        Scanner sc = new Scanner(url.openStream());
        //Instantiating the StringBuffer class to hold the result
        StringBuffer sb = new StringBuffer();
        while(sc.hasNext()) {
            sb.append(sc.next());
            //System.out.println(sc.next());
        }
        //Retrieving the String from the String Buffer object
         UUIDS = sb.toString();
        //Removing the HTML tags
            UUIDS = UUIDS.replaceAll("<[^>]*>", "");

        } catch (IOException e) {
            e.printStackTrace();
        }
        ModItems.register(eventBus);
        ModEntities.ENTITIES.register(eventBus);

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        ModSetup.setup();
        Config.register();
        DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, "spellblademod");
        ATTRIBUTES.register("generic.ward", () -> manatick.WARD);
        ATTRIBUTES.register("generic.baseward", () -> manatick.BASEWARD);
        ATTRIBUTES.register("smote", () -> manatick.SMOTE);
        ATTRIBUTES.register(eventBus);
        // Register the setup method for modloading
        IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();
        modbus.addListener(ModSetup::init);


        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modbus.addListener(com.cleannrooster.spellblademod.manasystem.client.ClientSetup::init));


    }

    private void setup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {

            BrewingRecipeRegistry.addRecipe(new FlaskBrewingRecipe(Items.GLASS_BOTTLE,
                    Items.GLOW_BERRIES, (Spell) FLUXITEM.get()));
            BrewingRecipeRegistry.addRecipe(new FlaskBrewingRecipe(FLUXITEM.get(),
                    Items.FIRE_CHARGE, (Spell) VOLATILE.get()));
            BrewingRecipeRegistry.addRecipe(new FlaskBrewingRecipe(FLUXITEM.get(),
                    Items.MAGMA_CREAM, (Spell) BOUNCINGITEM.get()));
            BrewingRecipeRegistry.addRecipe(new FlaskBrewingRecipe(FLUXITEM.get(),
                    Items.FERMENTED_SPIDER_EYE, (Spell) SPARKITEM.get()));
            BrewingRecipeRegistry.addRecipe(new FlaskBrewingRecipe(FLUXITEM.get(),
                    Items.PRISMARINE, (Spell) SPLITTING_TRIDENT.get()));
            BrewingRecipeRegistry.addRecipe(new FlaskBrewingRecipe(FLUXITEM.get(),
                    Items.ENDER_PEARL, (Spell) REVERBERATING_RAY.get()));
            BrewingRecipeRegistry.addRecipe(new FlaskBrewingRecipe(FLUXITEM.get(),
                    Items.AMETHYST_SHARD, (Spell) BLADEFLURRY.get()));
            BrewingRecipeRegistry.addRecipe(new FlaskBrewingRecipe(FLUXITEM.get(),
                    Items.PRISMARINE_CRYSTALS, (Spell) LIGHTNING_WHIRL.get()));
            BrewingRecipeRegistry.addRecipe(new FlaskBrewingRecipe(FLUXITEM.get(),
                    Items.ENDER_EYE, (Spell) ENDERSEYE.get()));
            BrewingRecipeRegistry.addRecipe(new FlaskBrewingRecipe(FLUXITEM.get(),
                    Items.POINTED_DRIPSTONE, (Spell) IMPALE.get()));
            BrewingRecipeRegistry.addRecipe(new FlaskBrewingRecipe(FLUXITEM.get(),
                    Items.PACKED_ICE, (Spell) WINTERBURIAL.get()));
            BrewingRecipeRegistry.addRecipe(new FlaskBrewingRecipe(FLUXITEM.get(),
                    Items.GLOWSTONE_DUST, (Spell) ESSENCEBOLT.get()));

        });
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // Some example code to dispatch IMC to another mod
        InterModComms.sendTo("twohand", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // Some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.messageSupplier().get()).
                collect(Collectors.toList()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }


    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent)
        {
            // Register a new block here
            LOGGER.info("HELLO from Register Block");
        }


    }

}
