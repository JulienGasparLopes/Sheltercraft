package com.pinzen.sheltercraft.screen;

import com.pinzen.sheltercraft.Sheltercraft;
import com.pinzen.sheltercraft.screen.custom.WickerBasketMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {

    public static final RegistryObject<MenuType<WickerBasketMenu>> WICKER_BASKET_MENU = Sheltercraft.REGISTER_MENUS.register(
            "wicker_basket_menu",
            () -> IForgeMenuType.create(WickerBasketMenu::new)
    );
}
