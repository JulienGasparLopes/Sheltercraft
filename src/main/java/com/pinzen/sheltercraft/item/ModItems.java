package com.pinzen.sheltercraft.item;

import com.pinzen.sheltercraft.Sheltercraft;
import com.pinzen.sheltercraft.util.Helpers;
import com.pinzen.sheltercraft.item.custom.Sickle;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    // Tools
    public static final RegistryObject<Item> FLINT_SICKLE = Sheltercraft.REGISTER_ITEMS.register(
            "flint_sickle",
            () -> new Sickle(
                    ModToolTiers.FLINT_TIER,
                    new Item.Properties().setId(Helpers.resKeyItem("flint_sickle"))
            )
    );
    public static final RegistryObject<Item> FLINT_HATCHET = Sheltercraft.REGISTER_ITEMS.register(
            "flint_hatchet",
            () -> new AxeItem(
                    ModToolTiers.FLINT_TIER, 1f, 	-3.2f,
                    new Item.Properties().setId(Helpers.resKeyItem("flint_hatchet"))
            )
    );
    public static final RegistryObject<Item> FLINT_AXE = Sheltercraft.REGISTER_ITEMS.register(
            "flint_axe",
            () -> new AxeItem(
                    ModToolTiers.FLINT_TIER, 3f, 	-3.2f,
                    new Item.Properties().setId(Helpers.resKeyItem("flint_axe"))
            )
    );
    public static final RegistryObject<Item> FLINT_PICKAXE = Sheltercraft.REGISTER_ITEMS.register(
            "flint_pickaxe",
            () -> new Item(
                    new Item.Properties()
                            .pickaxe(ModToolTiers.FLINT_TIER, 1f, -2.8f)
                            .setId(Helpers.resKeyItem("flint_pickaxe"))
            )
    );
    public static final RegistryObject<Item> FLINT_SHOVEL = Sheltercraft.REGISTER_ITEMS.register(
            "flint_shovel",
            () -> new ShovelItem(
                    ModToolTiers.FLINT_TIER, 1.5f, 	-3.0f,
                    new Item.Properties().setId(Helpers.resKeyItem("flint_shovel"))
            )
    );

    // Regular Items
    public static final RegistryObject<Item> SHARPENED_FLINT = Sheltercraft.REGISTER_ITEMS.register(
            "sharpened_flint",
            () -> new Item(new Item.Properties().setId(Helpers.resKeyItem("sharpened_flint")))
    );
    public static final RegistryObject<Item> PLANT_FIBER = Sheltercraft.REGISTER_ITEMS.register(
            "plant_fiber",
            () -> new Item(new Item.Properties().setId(Helpers.resKeyItem("plant_fiber")))
    );
    public static final RegistryObject<Item> WICKER = Sheltercraft.REGISTER_ITEMS.register(
            "wicker",
            () -> new Item(new Item.Properties().setId(Helpers.resKeyItem("wicker")))
    );

}
