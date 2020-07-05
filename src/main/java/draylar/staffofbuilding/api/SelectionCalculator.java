package draylar.staffofbuilding.api;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class SelectionCalculator {

    public static List<BlockPos> calculateSelection(World world, BlockPos originPos, Direction direction, int maxChecks) {
        BlockPos offsetPos = originPos.offset(direction);
        BlockState originState = world.getBlockState(originPos);

        // stored values, start checks at 1 for the origin position
        List<BlockPos> selectedPositions = new ArrayList<>();
        int checks = 1;

        // get start neighbors
        List<BlockPos> storedNeighbors = new ArrayList<>();
        storedNeighbors.add(offsetPos);

        while(checks < maxChecks && !storedNeighbors.isEmpty()) {
            // add new neighbors to stored list
            selectedPositions.addAll(storedNeighbors);
            List<BlockPos> newNeighbors = new ArrayList<>();

            // get new set of new neighbors from the current new neighbors
            for (BlockPos neighbor : storedNeighbors) {
                List<BlockPos> facingNeighbors = getValidNeighbors(world, neighbor, direction, originState);

                // add all facing neighbors that aren't already in the pool
                for (BlockPos facingNeighbor : facingNeighbors) {
                    if(checks < maxChecks) {
                        if (!selectedPositions.contains(facingNeighbor) && !storedNeighbors.contains(facingNeighbor) && !newNeighbors.contains(facingNeighbor)) {
                            newNeighbors.add(facingNeighbor);
                            checks++;
                        }
                    }
                }
            }

            // clear new neighbors, set new ones
            storedNeighbors.clear();
            storedNeighbors.addAll(newNeighbors);
        }

        // add leftover stored neighbors
        selectedPositions.addAll(storedNeighbors);

        return selectedPositions;
    }

    private static List<BlockPos> getValidNeighbors(World world, BlockPos startPos, Direction facingDirection, BlockState originState) {
        List<BlockPos> foundNeighbors = new ArrayList<>();

        // check all side direction positions for the current facing direction to get neighbors
        for(Vec3i checkDirection : getPotentialNeighbors(facingDirection)) {
            BlockPos offsetPos = startPos.add(checkDirection);
            BlockState innerState = world.getBlockState(offsetPos.offset(facingDirection.getOpposite()));
            BlockState newState = world.getBlockState(offsetPos);

            // ensure inner state of neighbor position is the same as the original state the player is looking at
            if(innerState.equals(originState) && newState.isAir()) {
                foundNeighbors.add(offsetPos);
            }
        }

        return foundNeighbors;
    }

    // todo: cache these?
    // grabs a list of facing neighbors
    private static List<Vec3i> getPotentialNeighbors(Direction direction) {
        ArrayList<Vec3i> directions = new ArrayList<>();

        if(direction.getAxis() == Direction.Axis.Y) {
            directions.add(Direction.NORTH.getVector());
            directions.add(Direction.EAST.getVector());
            directions.add(Direction.SOUTH.getVector());
            directions.add(Direction.WEST.getVector());
            directions.add(Direction.WEST.getVector().offset(Direction.NORTH, 1));
            directions.add(Direction.NORTH.getVector().offset(Direction.EAST, 1));
            directions.add(Direction.EAST.getVector().offset(Direction.SOUTH, 1));
            directions.add(Direction.SOUTH.getVector().offset(Direction.WEST, 1));
        } else {
            directions.add(direction.rotateYClockwise().getVector());
            directions.add(direction.rotateYCounterclockwise().getVector());
            directions.add(Direction.UP.getVector());
            directions.add(Direction.DOWN.getVector());
        }

        return directions;
    }
}
