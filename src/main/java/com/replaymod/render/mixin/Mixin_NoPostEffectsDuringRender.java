package com.replaymod.render.mixin;

import com.replaymod.render.hooks.EntityRendererHandler;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Prevents post-processing effects from being (re-)loaded into the GameRenderer while a
 * video is being rendered. Such effects (vanilla spectator shaders, DragonMineZ'
 * transformation outline, ...) are sized for the window framebuffer and bind it mid-frame,
 * which corrupts video frames rendered at a different resolution into dedicated
 * framebuffers. EntityRendererHandler shuts down any already-active effect when the render
 * starts; this blocks e.g. per-tick re-load attempts for its duration.
 */
@Mixin(GameRenderer.class)
public abstract class Mixin_NoPostEffectsDuringRender {
    @Inject(method = "loadPostProcessor", at = @At("HEAD"), cancellable = true)
    private void replayModRender_noPostEffectsDuringRender(Identifier id, CallbackInfo ci) {
        if (((EntityRendererHandler.IEntityRenderer) (Object) this).replayModRender_getHandler() != null) {
            ci.cancel();
        }
    }
}
