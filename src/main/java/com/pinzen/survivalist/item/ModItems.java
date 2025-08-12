package com.pinzen.survivalist.item;

import com.pinzen.survivalist.util.Helpers;
import com.pinzen.survivalist.Survivalist;
import com.pinzen.survivalist.item.custom.Sickle;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    // Tools
    public static final RegistryObject<Item> FLINT_SICKLE = Survivalist.REGISTER_ITEMS.register(
            "flint_sickle",
            () -> new Sickle(
                    ModToolTiers.FLINT_TIER,
                    new Item.Properties().setId(Helpers.resKeyItem("flint_sickle"))
            )
    );
    public static final RegistryObject<Item> FLINT_HATCHET = Survivalist.REGISTER_ITEMS.register(
            "flint_hatchet",
            () -> new AxeItem(
                    ModToolTiers.FLINT_TIER, 1f, 	-3.2f,
                    new Item.Properties().setId(Helpers.resKeyItem("flint_hatchet"))
            )
    );
    public static final RegistryObject<Item> FLINT_AXE = Survivalist.REGISTER_ITEMS.register(
            "flint_axe",
            () -> new AxeItem(
                    ModToolTiers.FLINT_TIER, 3f, 	-3.2f,
                    new Item.Properties().setId(Helpers.resKeyItem("flint_axe"))
            )
    );
    public static final RegistryObject<Item> FLINT_PICKAXE = Survivalist.REGISTER_ITEMS.register(
            "flint_pickaxe",
            () -> new Item(
                    new Item.Properties()
                            .pickaxe(ModToolTiers.FLINT_TIER, 1f, -2.8f)
                            .setId(Helpers.resKeyItem("flint_pickaxe"))
            )
    );
    public static final RegistryObject<Item> FLINT_SHOVEL = Survivalist.REGISTER_ITEMS.register(
            "flint_shovel",
            () -> new ShovelItem(
                    ModToolTiers.FLINT_TIER, 1.5f, 	-3.0f,
                    new Item.Properties().setId(Helpers.resKeyItem("flint_shovel"))
            )
    );

    // Regular Items
    public static final RegistryObject<Item> SHARPENED_FLINT = Survivalist.REGISTER_ITEMS.register(
            "sharpened_flint",
            () -> new Item(new Item.Properties().setId(Helpers.resKeyItem("sharpened_flint")))
    );
    public static final RegistryObject<Item> PLANT_FIBER = Survivalist.REGISTER_ITEMS.register(
            "plant_fiber",
            () -> new Item(new Item.Properties().setId(Helpers.resKeyItem("plant_fiber")))
    );
    public static final RegistryObject<Item> WICKER = Survivalist.REGISTER_ITEMS.register(
            "wicker",
            () -> new Item(new Item.Properties().setId(Helpers.resKeyItem("wicker")))
    );

}
