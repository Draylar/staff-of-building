package draylar.staffofbuilding.registry;

import draylar.staffofbuilding.StaffOfBuilding;
import draylar.staffofbuilding.item.BuilderStaffItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final Item WOODEN_BUILDER_STAFF = register("wooden_builder_staff", new BuilderStaffItem(new Item.Settings().group(StaffOfBuilding.GROUP).maxDamage(100), 5, Items.OAK_PLANKS));
    public static final Item STONE_BUILDER_STAFF = register("stone_builder_staff", new BuilderStaffItem(new Item.Settings().group(StaffOfBuilding.GROUP).maxDamage(250), 13, Items.STONE)); // +8
    public static final Item IRON_BUILDER_STAFF = register("iron_builder_staff", new BuilderStaffItem(new Item.Settings().group(StaffOfBuilding.GROUP).maxDamage(500), 25, Items.IRON_INGOT)); // +12
    public static final Item GOLDEN_BUILDER_STAFF = register("golden_builder_staff", new BuilderStaffItem(new Item.Settings().group(StaffOfBuilding.GROUP).maxDamage(150), 41, Items.GOLD_INGOT)); // +16
    public static final Item DIAMOND_BUILDER_STAFF = register("diamond_builder_staff", new BuilderStaffItem(new Item.Settings().group(StaffOfBuilding.GROUP).maxDamage(1000), 41, Items.DIAMOND)); // +16
    public static final Item INFINITE_BUILDER_STAFF = register("infinite_builder_staff", new BuilderStaffItem(new Item.Settings().group(StaffOfBuilding.GROUP), 145, null)); // + 24 + 28 + 32

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
