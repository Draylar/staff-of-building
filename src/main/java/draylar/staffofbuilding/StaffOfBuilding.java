package draylar.staffofbuilding;

import draylar.omegaconfig.OmegaConfig;
import draylar.staffofbuilding.config.StaffOfBuildingConfig;
import draylar.staffofbuilding.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

public class StaffOfBuilding implements ModInitializer {

    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(id("group"), () -> new ItemStack(ModItems.STONE_BUILDER_STAFF));
    public static final StaffOfBuildingConfig CONFIG = OmegaConfig.register(StaffOfBuildingConfig.class);
    public static final List<Block> RESET_LIST = Arrays.asList(Blocks.SNOW, Blocks.COMPOSTER, Blocks.CAULDRON, Blocks.CAKE, Blocks.BEEHIVE, Blocks.BREWING_STAND);

    @Override
    public void onInitialize() {
        ModItems.init();
    }

    public static Identifier id(String name) {
        return new Identifier("staffofbuilding", name);
    }
}
