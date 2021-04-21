package draylar.staffofbuilding.registry;

import draylar.staffofbuilding.StaffOfBuilding;
import draylar.staffofbuilding.item.BuilderStaffItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;

import static draylar.staffofbuilding.StaffOfBuilding.CONFIG;

public class ModItems {

    public static final Item WOODEN_BUILDER_STAFF = register("wooden_builder_staff", new BuilderStaffItem(new Item.Settings().group(StaffOfBuilding.GROUP).maxDamage(100), CONFIG.woodenSize, Items.OAK_PLANKS));
    public static final Item STONE_BUILDER_STAFF = register("stone_builder_staff", new BuilderStaffItem(new Item.Settings().group(StaffOfBuilding.GROUP).maxDamage(250), CONFIG.stoneSize, Items.STONE)); // +8
    public static final Item IRON_BUILDER_STAFF = register("iron_builder_staff", new BuilderStaffItem(new Item.Settings().group(StaffOfBuilding.GROUP).maxDamage(500), CONFIG.ironSize, Items.IRON_INGOT)); // +12
    public static final Item GOLDEN_BUILDER_STAFF = register("golden_builder_staff", new BuilderStaffItem(new Item.Settings().group(StaffOfBuilding.GROUP).maxDamage(150), CONFIG.goldenSize, Items.GOLD_INGOT)); // +16
    public static final Item DIAMOND_BUILDER_STAFF = register("diamond_builder_staff", new BuilderStaffItem(new Item.Settings().group(StaffOfBuilding.GROUP).maxDamage(1000), CONFIG.diamondSize, Items.DIAMOND)); // +16
    public static final Item NETHERITE_BUILDER_STAFF = register("netherite_builder_staff", new BuilderStaffItem(new Item.Settings().group(StaffOfBuilding.GROUP).maxDamage(1250).fireproof(), CONFIG.netheriteSize, Items.NETHERITE_INGOT)); // +20
    public static final Item INFINITE_BUILDER_STAFF = register("infinite_builder_staff", new BuilderStaffItem(new Item.Settings().group(StaffOfBuilding.GROUP), CONFIG.infiniteSize, null)); // + 24 + 28 + 32

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
