package io.github.bizcub.dryGhast.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
//~ if >=1.21.11 'HappyGhast' -> 'happyghast.HappyGhast'
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.DriedGhastBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HappyGhast.class)
public abstract class HappyGhastMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void dryOutOverCampfire(CallbackInfo ci) {
        HappyGhast ghast = (HappyGhast) (Object) this;
        Level level = ghast.level();
        if (level.isClientSide() || !ghast.isAlive()) return;
        if (ghast.tickCount % 20 != 0) return;
        if (!CampfireBlock.isSmokeyPos(level, ghast.blockPosition())) return;

        ghast.ejectPassengers();
        ItemStack harness = ghast.getItemBySlot(EquipmentSlot.BODY);
        if (!harness.isEmpty()) {
            ghast.spawnAtLocation((ServerLevel) level, harness.copy());
            harness.setCount(0);
        }

        BlockPos pos = ghast.blockPosition();
        ghast.discard();
        if (level.getBlockState(pos).canBeReplaced()) {
            level.setBlock(pos, Blocks.DRIED_GHAST.defaultBlockState()
                    .setValue(DriedGhastBlock.FACING, Direction.fromYRot(ghast.getYRot())), 3);
        } else {
            ghast.spawnAtLocation((ServerLevel) level, new ItemStack(Items.DRIED_GHAST));
        }
        level.playSound(null, pos, SoundEvents.DRIED_GHAST_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
    }
}
