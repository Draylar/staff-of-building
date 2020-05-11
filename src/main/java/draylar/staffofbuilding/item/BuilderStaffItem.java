package draylar.staffofbuilding.item;

import draylar.staffofbuilding.api.SelectionCalculator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.List;

public class BuilderStaffItem extends Item {

    private final int size;
    private final Item repairIngredient;

    public BuilderStaffItem(Settings settings, int size, Item repairIngredient) {
        super(settings);
        this.size = size;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.getItem().equals(repairIngredient);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        Direction side = context.getSide();
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        PlayerEntity player = context.getPlayer();
        Block block = state.getBlock();
        Item item = block.asItem();

        // check to make sure the block we're placing off has an item
        if(player != null && item != Items.AIR && context.getHand() == Hand.MAIN_HAND) {
            // get amount of required item in player inventory
            int count = player.inventory.count(item);

            // run placement logic if they have at least 1 of the item (or if they are a creative user)
            if(count > 0 || player.isCreative()) {
                if(!world.isClient) {
                    // get number of blocks to place (min between max size and the count of items in inventory)
                    int maxChecks = Math.min(size, player.isCreative() ? size : count);
                    List<BlockPos> positions = SelectionCalculator.calculateSelection(world, pos, side, maxChecks);

                    // place blocks
                    positions.forEach(position -> {
                        world.setBlockState(position, state);
                    });

                    // take items from survival inventory
                    if(!player.isCreative()) {
                        player.inventory.clearItem(stack -> stack.getItem().equals(item), positions.size());
                    }

                    // damage item
                    if(context.getStack().isDamageable()) {
                        context.getStack().damage(positions.size(), player, (livingEntity) -> {
                            livingEntity.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
                        });
                    }
                }

                player.playSound(state.getSoundGroup().getPlaceSound(), state.getSoundGroup().getVolume(), state.getSoundGroup().getPitch());
                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.FAIL;
    }

    public int getMaxSize() {
        return size;
    }
}
