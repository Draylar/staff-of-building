package draylar.staffofbuilding.mixin;

import draylar.staffofbuilding.api.SelectionCalculator;
import draylar.staffofbuilding.item.BuilderStaffItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {

    @Shadow @Final private MinecraftClient client;
    @Shadow private ClientWorld world;
    @Shadow private static void drawShapeOutline(MatrixStack matrixStack, VertexConsumer vertexConsumer, VoxelShape voxelShape, double d, double e, double f, float g, float h, float i, float j) { }
    @Unique private boolean sob_renderedHighlight = false;

    @Inject(
            method = "render",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/hit/HitResult;getType()Lnet/minecraft/util/hit/HitResult$Type;"),
            locals = LocalCapture.CAPTURE_FAILHARD)
    private void renderWandHighlight(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, CallbackInfo ci, Profiler profiler, Vec3d vec3d, double d, double e, double f, Matrix4f matrix4f2, boolean bl, Frustum frustum2, boolean bl3, VertexConsumerProvider.Immediate immediate) {
        profiler.swap("wand_outline");
        sob_renderedHighlight = false;

        if(client.player != null) {
            if(client.player.getEquippedStack(EquipmentSlot.MAINHAND).getItem() instanceof BuilderStaffItem) {
                HitResult hitResult = this.client.crosshairTarget;

                if (renderBlockOutline && hitResult != null && hitResult.getType() == HitResult.Type.BLOCK) {
                    BlockPos lookingAtPos = ((BlockHitResult) hitResult).getBlockPos();
                    BlockState lookingAtState = this.world.getBlockState(lookingAtPos);
                    Block lookingAtBlock = lookingAtState.getBlock();
                    Item item = lookingAtBlock.asItem();
                    VoxelShape shape = VoxelShapes.empty();
                    ClientPlayerEntity player = client.player;
                    BuilderStaffItem staffItem = (BuilderStaffItem) client.player.getEquippedStack(EquipmentSlot.MAINHAND).getItem();

                    // check to make sure the block we're placing off has an item
                    if (item != Items.AIR) {
                        // get amount of required item in player inventory
                        int count = player.inventory.count(item);

                        // run placement logic if they have at least 1 of the item (or if they are in creative)
                        if (count > 0 || player.isCreative()) {
                            // get number of blocks to place (min between max size and the count of items in inventory)
                            int maxChecks = Math.min(staffItem.getMaxSize(), player.isCreative() ? staffItem.getMaxSize() : count);

                            // get initial positions within our selection
                            List<BlockPos> validPositions = SelectionCalculator.calculateSelection(world, lookingAtPos, ((BlockHitResult) hitResult).getSide(), maxChecks);

                            // add all positions to the overall shape
                            for (BlockPos newPosition : validPositions) {
                                if (this.world.getWorldBorder().contains(newPosition)) {
                                    BlockPos testPos = lookingAtPos.subtract(newPosition);
                                    shape = VoxelShapes.union(shape, lookingAtState.getOutlineShape(this.world, lookingAtPos, ShapeContext.of(camera.getFocusedEntity())).offset(-testPos.getX(), -testPos.getY(), -testPos.getZ()));
                                }
                            }

                            // render shape
                            VertexConsumer linesBuffer = immediate.getBuffer(RenderLayer.getLines());
                            drawShapeOutline(matrices, linesBuffer, shape, (double) lookingAtPos.getX() - d, (double) lookingAtPos.getY() - e, (double) lookingAtPos.getZ() - f, 0.0F, 0.0F, 0.0F, 0.4F);
                            sob_renderedHighlight = true;
                        }
                    }
                }
            }
        }

        profiler.pop();
    }

    @Inject(
            method = "drawBlockOutline",
            at = @At("HEAD"),
            cancellable = true)
    private void preDrawBlockOutline(MatrixStack matrixStack, VertexConsumer vertexConsumer, Entity entity, double d, double e, double f, BlockPos blockPos, BlockState blockState, CallbackInfo ci) {
        if(sob_renderedHighlight) {
            ci.cancel();
        }
    }
}
