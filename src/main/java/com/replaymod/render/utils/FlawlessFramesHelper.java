package com.replaymod.render.utils;

public class FlawlessFramesHelper {

    public static boolean hasEmbeddium() {
        // NUNCA delegar pra FlawlessFrames: carregar aquela classe com Embeddium 0.2.x
        // (sem a FlawlessFramesService API) estoura NoClassDefFoundError.
        return FlawlessFramesState.hasSodium();
    }

    public static void setEnabled(boolean enabled) {
        FlawlessFramesState.setEnabled(enabled);
    }

}