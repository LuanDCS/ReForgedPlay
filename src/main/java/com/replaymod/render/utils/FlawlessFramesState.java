package com.replaymod.render.utils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * Estado do Flawless Frames SEM referenciar a interface de servico do Embeddium.
 *
 * <p>{@code FlawlessFramesService} so existe no Embeddium 0.3+; com o 0.2.x da
 * 1.20.2 no classpath, qualquer classe que a mencione leva NoClassDefFoundError
 * ao carregar — era exatamente o crash ao abrir a tela de render com shaders
 * instalados. Este holder e livre de dependencias: {@link FlawlessFrames} (o
 * impl do servico, carregado apenas pelo ServiceLoader do Embeddium 0.3+)
 * delega pra ca, e o resto do mod fala somente com esta classe.</p>
 */
public class FlawlessFramesState {
    private static final List<Consumer<Boolean>> CONSUMERS = new CopyOnWriteArrayList<>();
    private static boolean hasSodium;
    /** Ligado durante o render de vídeo — lido pelo Mixin_EmbeddiumFlawlessFrames (backport 0.2.x). */
    private static volatile boolean enabled;

    public static void addConsumer(Consumer<Boolean> consumer, String providerClassName) {
        CONSUMERS.add(consumer);

        if (providerClassName.contains(".embeddium.") || consumer.getClass().getName().contains(".embeddium.")) {
            hasSodium = true;
        }
    }

    public static void setEnabled(boolean newState) {
        enabled = newState;
        CONSUMERS.forEach(it -> it.accept(newState));
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static boolean hasSodium() {
        return hasSodium;
    }
}
