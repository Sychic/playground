package sychic.playground

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.Screen
import net.minecraft.server.command.CommandManager
import sychic.playground.gui.BlurScreen
import sychic.playground.gui.ChromaScreen
import sychic.playground.gui.ShaderTextScreen

//@Mod(name = "PlayGround", modid = "playground", version = "1.0.0", modLanguageAdapter = "gg.essential.api.utils.KotlinAdapter")
object PlayGround : ClientModInitializer {

    // variable to delay opening guis a tick
    var displayScreen: Screen? = null

    override fun onInitializeClient() {
        println("aaea")
//        MinecraftForge.EVENT_BUS.register(this)

        ClientTickEvents.END_CLIENT_TICK.register(::onTick)
        CommandRegistrationCallback.EVENT.register { dispatcher, registryAccess, environment ->
            dispatcher.register(CommandManager.literal("chroma").executes {
                displayScreen = ChromaScreen()
                1
            })
            dispatcher.register(CommandManager.literal("text").executes {
                displayScreen = ShaderTextScreen()
                1
            })
            dispatcher.register(CommandManager.literal("blur").executes {
                displayScreen = BlurScreen()
                1
            })
        }
    }

    fun onTick(client: MinecraftClient) {

        if (displayScreen != null) {
            client.setScreen(displayScreen)
            displayScreen = null
        }
    }
}