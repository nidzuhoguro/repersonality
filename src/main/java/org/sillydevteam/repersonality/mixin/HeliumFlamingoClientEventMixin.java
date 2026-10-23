package org.sillydevteam.repersonality.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import it.hurts.shatterbyte.reliquified_artifacts.items.charm.HeliumFlamingoItem;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Pose;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HeliumFlamingoItem.HeliumFlamingoClientEvent.class)
public class HeliumFlamingoClientEventMixin {

    @Unique
    private static Pose repersonality$lastForced;

    // Making the method return if forced pose was modified by another mod
    @WrapOperation(method = "onClientTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;setForcedPose(Lnet/minecraft/world/entity/Pose;)V"), require = 1)
    private static void repersonality$softSetForcedPose(LocalPlayer instance, Pose pose, Operation<Void> original) {
        if (instance == null) return;
        if (repersonality$lastForced != instance.getForcedPose()) return;
        repersonality$lastForced = pose;
        original.call(instance, pose);
    }
}
