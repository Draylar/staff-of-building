package draylar.staffofbuilding.registry;

import draylar.staffofbuilding.StaffOfBuilding;
import draylar.staffofbuilding.item.BuilderStaffItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.registry.Registry;

import static draylar.staffofbuilding.StaffOfBuilding.CONFIG;

public class ModItems {

    public static final Item WOODEN_BUILDER_STAFF = register("wooden_builder_staff", new BuilderStaffItem(new Item.Settings().group(ItemGroup.TOOLS), CONFIG.woodenSize, ToolMaterials.WOOD));
    public static final Item STONE_BUILDER_STAFF = register("stone_builder_staff", new BuilderStaffItem(new Item.Settings().group(ItemGroup.TOOLS), CONFIG.stoneSize, ToolMaterials.STONE)); // +8
    public static final Item IRON_BUILDER_STAFF = register("iron_builder_staff", new BuilderStaffItem(new Item.Settings().group(ItemGroup.TOOLS), CONFIG.ironSize, ToolMaterials.IRON)); // +12
    public static final Item GOLDEN_BUILDER_STAFF = register("golden_builder_staff", new BuilderStaffItem(new Item.Settings().group(ItemGroup.TOOLS), CONFIG.goldenSize, ToolMaterials.WOOD)); // +16
    public static final Item DIAMOND_BUILDER_STAFF = register("diamond_builder_staff", new BuilderStaffItem(new Item.Settings().group(ItemGroup.TOOLS), CONFIG.diamondSize, ToolMaterials.DIAMOND)); // +16
    public static final Item NETHERITE_BUILDER_STAFF = register("netherite_builder_staff", new BuilderStaffItem(new Item.Settings().group(ItemGroup.TOOLS).fireproof(), CONFIG.netheriteSize, ToolMaterials.NETHERITE)); // +20
    public static final Item INFINITE_BUILDER_STAFF = register("infinite_builder_staff", new BuilderStaffItem(new Item.Settings().group(ItemGroup.TOOLS), CONFIG.infiniteSize, null).invincible()); // + 24 + 28 + 32

    private static <T extends Item> T register(String name, T item) {
        return Registry.register(Registry.ITEM, StaffOfBuilding.id(name), item);
    }

    public static void init() {
        // NO-OP static field initializer
    }

    private ModItems() {
        // NO-OP
    }
}
