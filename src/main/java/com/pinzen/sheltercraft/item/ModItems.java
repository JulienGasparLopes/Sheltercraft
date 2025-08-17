package com.pinzen.sheltercraft.item;

import com.pinzen.sheltercraft.Sheltercraft;
import com.pinzen.sheltercraft.item.custom.TrapperKnife;
import com.pinzen.sheltercraft.item.custom.Whetstone;
import com.pinzen.sheltercraft.util.Helpers;
import com.pinzen.sheltercraft.item.custom.Sickle;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> REGISTER_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Sheltercraft.MOD_ID);

    // Tools
    public static final RegistryObject<Item> FLINT_SICKLE = REGISTER_ITEMS.register(
            "flint_sickle",
            () -> new Sickle(
                    ModToolTiers.FLINT_TIER,
                    new Item.Properties().setId(Helpers.resKeyItem("flint_sickle"))
            )
    );
    public static final RegistryObject<Item> FLINT_HATCHET = REGISTER_ITEMS.register(
            "flint_hatchet",
            () -> new AxeItem(
                    ModToolTiers.FLINT_TIER, 1f, 	-3.2f,
                    new Item.Properties().setId(Helpers.resKeyItem("flint_hatchet"))
            )
    );
    public static final RegistryObject<Item> FLINT_AXE = REGISTER_ITEMS.register(
            "flint_axe",
            () -> new AxeItem(
                    ModToolTiers.FLINT_TIER, 3f, 	-3.2f,
                    new Item.Properties().setId(Helpers.resKeyItem("flint_axe"))
            )
    );
    public static final RegistryObject<Item> FLINT_PICKAXE = REGISTER_ITEMS.register(
            "flint_pickaxe",
            () -> new Item(
                    new Item.Properties()
                            .pickaxe(ModToolTiers.FLINT_TIER, 1f, -2.8f)
                            .setId(Helpers.resKeyItem("flint_pickaxe"))
            )
    );
    public static final RegistryObject<Item> FLINT_SHOVEL = REGISTER_ITEMS.register(
            "flint_shovel",
            () -> new ShovelItem(
                    ModToolTiers.FLINT_TIER, 1.5f, 	-3.0f,
                    new Item.Properties().setId(Helpers.resKeyItem("flint_shovel"))
            )
    );
    public static final RegistryObject<Item> FLINT_TRAPPER_KNIFE = REGISTER_ITEMS.register(
            "flint_trapper_knife",
            () -> new TrapperKnife(
                    ModToolTiers.FLINT_TIER,
                    new Item.Properties().setId(Helpers.resKeyItem("flint_trapper_knife"))
            )
    );

    public static final RegistryObject<Item> SHARPENED_FLINT_SICKLE = REGISTER_ITEMS.register(
            "sharpened_flint_sickle",
            () -> new Sickle(
                    ModToolTiers.SHARPENED_FLINT_TIER,
                    new Item.Properties().setId(Helpers.resKeyItem("sharpened_flint_sickle"))
            )
    );
    public static final RegistryObject<Item> SHARPENED_FLINT_HATCHET = REGISTER_ITEMS.register(
            "sharpened_flint_hatchet",
            () -> new AxeItem(
                    ModToolTiers.SHARPENED_FLINT_TIER, 1f, 	-3.2f,
                    new Item.Properties().setId(Helpers.resKeyItem("sharpened_flint_hatchet"))
            )
    );
    public static final RegistryObject<Item> SHARPENED_FLINT_AXE = REGISTER_ITEMS.register(
            "sharpened_flint_axe",
            () -> new AxeItem(
                    ModToolTiers.SHARPENED_FLINT_TIER, 3f, 	-3.2f,
                    new Item.Properties().setId(Helpers.resKeyItem("sharpened_flint_axe"))
            )
    );
    public static final RegistryObject<Item> SHARPENED_FLINT_PICKAXE = REGISTER_ITEMS.register(
            "sharpened_flint_pickaxe",
            () -> new Item(
                    new Item.Properties()
                            .pickaxe(ModToolTiers.SHARPENED_FLINT_TIER, 1f, -2.8f)
                            .setId(Helpers.resKeyItem("sharpened_flint_pickaxe"))
            )
    );
    public static final RegistryObject<Item> SHARPENED_FLINT_SHOVEL = REGISTER_ITEMS.register(
            "sharpened_flint_shovel",
            () -> new ShovelItem(
                    ModToolTiers.SHARPENED_FLINT_TIER, 1.5f, 	-3.0f,
                    new Item.Properties().setId(Helpers.resKeyItem("sharpened_flint_shovel"))
            )
    );
    public static final RegistryObject<Item> SHARPENED_FLINT_TRAPPER_KNIFE = REGISTER_ITEMS.register(
            "sharpened_flint_trapper_knife",
            () -> new TrapperKnife(
                    ModToolTiers.FLINT_TIER,
                    new Item.Properties().setId(Helpers.resKeyItem("sharpened_flint_trapper_knife"))
            )
    );

    // Regular Items
    public static final RegistryObject<Item> FLINT_FLAKE = REGISTER_ITEMS.register(
            "flint_flake",
            () -> new Item(new Item.Properties().setId(Helpers.resKeyItem("flint_flake")))
    );
    public static final RegistryObject<Item> SHARPENED_FLINT = REGISTER_ITEMS.register(
            "sharpened_flint",
            () -> new Item(new Item.Properties().setId(Helpers.resKeyItem("sharpened_flint")))
    );
    public static final RegistryObject<Item> WHETSTONE = REGISTER_ITEMS.register(
            "whetstone",
            Whetstone::new
    );
    public static final RegistryObject<Item> PLANT_FIBER = REGISTER_ITEMS.register(
            "plant_fiber",
            () -> new Item(new Item.Properties().setId(Helpers.resKeyItem("plant_fiber")))
    );
    public static final RegistryObject<Item> WICKER = REGISTER_ITEMS.register(
            "wicker",
            () -> new Item(new Item.Properties().setId(Helpers.resKeyItem("wicker")))
    );

    public static final RegistryObject<Item> ANIMAL_SKIN = REGISTER_ITEMS.register(
            "animal_skin",
            () -> new Item(new Item.Properties().setId(Helpers.resKeyItem("animal_skin")))
    );
    public static final RegistryObject<Item> RAWHIDE = REGISTER_ITEMS.register(
            "rawhide",
            () -> new Item(new Item.Properties().setId(Helpers.resKeyItem("rawhide")))
    );
    public static final RegistryObject<Item> CURED_HIDE = REGISTER_ITEMS.register(
            "cured_hide",
            () -> new Item(new Item.Properties().setId(Helpers.resKeyItem("cured_hide")))
    );
    public static final RegistryObject<Item> SOAKED_HIDE = REGISTER_ITEMS.register(
            "soaked_hide",
            () -> new Item(new Item.Properties().setId(Helpers.resKeyItem("soaked_hide")))
    );
    public static final RegistryObject<Item> TANNED_HIDE = REGISTER_ITEMS.register(
            "tanned_hide",
            () -> new Item(new Item.Properties().setId(Helpers.resKeyItem("tanned_hide")))
    );

    public static final RegistryObject<Item> SALT = REGISTER_ITEMS.register(
            "salt",
            () -> new Item(new Item.Properties().setId(Helpers.resKeyItem("salt")))
    );
    public static final RegistryObject<Item> WOOD_BARK = REGISTER_ITEMS.register(
            "wood_bark",
            () -> new Item(new Item.Properties().setId(Helpers.resKeyItem("wood_bark")))
    );

    public static void register(BusGroup busGroup) {
        REGISTER_ITEMS.register(busGroup);
    }
}
