//#if MC>=12002
package com.replaymod.render.mixin;

import com.replaymod.render.utils.FlawlessFramesState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * Backport do Flawless Frames pro Embeddium 0.2.x (o único da 1.20.2).
 *
 * <p>No Embeddium 0.3+ o proprio mod consome a {@code FlawlessFramesService} e passa o flag como o
 * parametro {@code updateChunksImmediately} do {@code setupTerrain} — que ja existe no 0.2.x (o
 * plumbing de update sincrono e herdado do Sodium 0.5.x). Aqui forçamos esse parametro durante o
 * render de video, obtendo o mesmo efeito: cada frame só desenha depois de TODOS os rebuilds de
 * chunk concluidos, eliminando pop-in no video.</p>
 */
@Pseudo
@Mixin(targets = "me.jellysquid.mods.sodium.client.render.SodiumWorldRenderer", remap = false)
public class Mixin_EmbeddiumFlawlessFrames {

    @ModifyVariable(method = "setupTerrain", at = @At("HEAD"), argsOnly = true, ordinal = 1, remap = false)
    private boolean replaymod$forceImmediateChunkUpdates(boolean updateChunksImmediately) {
        return updateChunksImmediately || FlawlessFramesState.isEnabled();
    }
}
//#endif
