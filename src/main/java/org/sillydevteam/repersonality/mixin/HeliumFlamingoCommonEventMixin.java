package org.sillydevteam.repersonality.mixin;

import artifacts.registry.ModItems;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import it.hurts.shatterbyte.reliquified_artifacts.items.charm.HeliumFlamingoItem;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HeliumFlamingoItem.CommonEvent.class)
public class HeliumFlamingoCommonEventMixin {

    @Unique
    private static Pose repersonality$lastForced;

    @WrapOperation(method = "onSwimAir", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;setForcedPose(Lnet/minecraft/world/entity/Pose;)V"), require = 1)
    private static void repersonality$softSetForcedPose(Player instance, Pose pose, Operation<Void> original) {
        if (instance == null) return;
        ItemStack stack = EntityUtils.findEquippedCurio(instance, ModItems.HELIUM_FLAMINGO.value());
        if (repersonality$lastForced != instance.getForcedPose() && !(stack.getItem() instanceof HeliumFlamingoItem item && item.isHovering(instance, stack)) ) return;
        repersonality$lastForced = pose;
        original.call(instance, pose);
    }
}
