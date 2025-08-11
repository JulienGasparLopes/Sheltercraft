package com.pinzen.survivalist.survival_age;

import com.pinzen.survivalist.Survivalist;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.RegistryObject;

public class Menus {

    public static final RegistryObject<MenuType<WickerBasketMenu>> WICKER_BASKET_MENU = Survivalist.REGISTER_MENUS.register(
            "wicker_basket_menu",
            () -> IForgeMenuType.create(WickerBasketMenu::new)
    );
}
