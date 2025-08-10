package com.pinzen.survivalist.survival_age;

import com.pinzen.survivalist.Helpers;
import com.pinzen.survivalist.Survivalist;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.RegistryObject;

public class Items {

    // Tools
    public static final RegistryObject<Item> FLINT_HATCHET = Survivalist.REGISTER_ITEMS.register(
            "flint_hatchet",
            () -> new AxeItem(
                    ToolMaterial.WOOD, 1, 1,
                    new Item.Properties().durability(10).setId(Helpers.resKeyItem("flint_hatchet"))
            )
    );
    public static final RegistryObject<Item> FLINT_AXE = Survivalist.REGISTER_ITEMS.register(
            "flint_axe",
            () -> new AxeItem(
                    ToolMaterial.WOOD, 1, 1,
                    new Item.Properties().durability(50).setId(Helpers.resKeyItem("flint_axe"))
            )
    );
    public static final RegistryObject<Item> FLINT_PICKAXE = Survivalist.REGISTER_ITEMS.register(
            "flint_pickaxe",
            () -> new Item(
                    new Item.Properties()
                            .pickaxe(ToolMaterial.WOOD, 1, 1)
                            .durability(50)
                            .setId(Helpers.resKeyItem("flint_pickaxe"))
            )
    );
    public static final RegistryObject<Item> FLINT_SHOVEL = Survivalist.REGISTER_ITEMS.register(
            "flint_shovel",
            () -> new ShovelItem(
                    ToolMaterial.WOOD, 1, 1,
                    new Item.Properties().durability(50).setId(Helpers.resKeyItem("flint_shovel"))
            )
    );

    // Regular Items
    public static final RegistryObject<Item> CHIPPED_FLINT = Survivalist.REGISTER_ITEMS.register(
            "chipped_flint",
            () -> new Item(new Item.Properties().setId(Helpers.resKeyItem("chipped_flint")))
    );
}
