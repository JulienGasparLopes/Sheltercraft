package com.pinzen.sheltercraft.item;

import com.pinzen.sheltercraft.Sheltercraft;
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
                    ModToolTiers.SHARPENED_FLINT_TIER, 1.5f, 	-2.2f,
                    new Item.Properties().setId(Helpers.resKeyItem("sharpened_flint_hatchet"))
            )
    );
    public static final RegistryObject<Item> SHARPENED_FLINT_AXE = REGISTER_ITEMS.register(
            "sharpened_flint_axe",
            () -> new AxeItem(
                    ModToolTiers.SHARPENED_FLINT_TIER, 5f, 	-2.2f,
                    new Item.Properties().setId(Helpers.resKeyItem("sharpened_flint_axe"))
            )
    );
    public static final RegistryObject<Item> SHARPENED_FLINT_PICKAXE = REGISTER_ITEMS.register(
            "sharpened_flint_pickaxe",
            () -> new Item(
                    new Item.Properties()
                            .pickaxe(ModToolTiers.SHARPENED_FLINT_TIER, 1.5f, -1.8f)
                            .setId(Helpers.resKeyItem("sharpened_flint_pickaxe"))
            )
    );
    public static final RegistryObject<Item> SHARPENED_FLINT_SHOVEL = REGISTER_ITEMS.register(
            "sharpened_flint_shovel",
            () -> new ShovelItem(
                    ModToolTiers.SHARPENED_FLINT_TIER, 2f, 	-2.0f,
                    new Item.Properties().setId(Helpers.resKeyItem("sharpened_flint_shovel"))
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

    public static void register(BusGroup busGroup) {
        REGISTER_ITEMS.register(busGroup);
    }
}
