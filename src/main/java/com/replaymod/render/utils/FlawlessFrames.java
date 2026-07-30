package com.replaymod.render.utils;

import org.embeddedt.embeddium.api.service.FlawlessFramesService;

import java.util.function.Consumer;
import java.util.function.Function;

import static com.replaymod.core.ReplayMod.MOD_ID;

/**
 * Uses the "Flawless Frames" FREX feature which allows us to instruct third-party mods to sacrifice performance (even
 * beyond the point where it can no longer achieve interactive frame rates) in exchange for a noticeable boost to
 * quality.
 * In particular also to force-load all chunks with Canvas/Sodium/Bobby.
 *
 * See https://github.com/grondag/frex/pull/9
 *
 * <p>NUNCA toque nesta classe fora do ServiceLoader do Embeddium 0.3+: ela referencia a interface
 * {@code FlawlessFramesService}, que nao existe no Embeddium 0.2.x — todo o estado fica em
 * {@link FlawlessFramesState}, que e seguro em qualquer versao.</p>
 */
public class FlawlessFrames implements FlawlessFramesService {

    public FlawlessFrames() {}

    public void acceptController(Function<String, Consumer<Boolean>> provider) {
        Consumer<Boolean> consumer = provider.apply(MOD_ID);
        FlawlessFramesState.addConsumer(consumer, provider.getClass().getName());
    }

    public static void setEnabled(boolean enabled) {
        FlawlessFramesState.setEnabled(enabled);
    }

    public static boolean hasSodium() {
        return FlawlessFramesState.hasSodium();
    }
}
