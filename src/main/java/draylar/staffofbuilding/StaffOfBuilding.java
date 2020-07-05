package draylar.staffofbuilding;

import draylar.staffofbuilding.config.StaffOfBuildingConfig;
import draylar.staffofbuilding.registry.ModItems;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class StaffOfBuilding implements ModInitializer {

    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(id("group"), () -> new ItemStack(ModItems.STONE_BUILDER_STAFF));
    public static final StaffOfBuildingConfig CONFIG = AutoConfig.register(StaffOfBuildingConfig.class, JanksonConfigSerializer::new).getConfig();

    @Override
    public void onInitialize() {
        ModItems.init();
    }

    public static Identifier id(String name) {
        return new Identifier("staffofbuilding", name);
    }
}
