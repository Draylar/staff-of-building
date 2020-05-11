package draylar.staffofbuilding;

import draylar.staffofbuilding.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class StaffOfBuilding implements ModInitializer {

    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(id("group"), () -> new ItemStack(ModItems.STONE_BUILDER_STAFF));

    @Override
    public void onInitialize() {
        ModItems.init();
    }

    public static Identifier id(String name) {
        return new Identifier("staffofbuilding", name);
    }
}
