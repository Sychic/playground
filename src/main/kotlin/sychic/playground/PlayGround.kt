import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import sychic.playground.commands.ChromaCommand

@Mod(name = "PlayGround", modid = "playground", version = "1.0.0", modLanguageAdapter = "gg.essential.api.utils.KotlinAdapter")
object PlayGround {

    // variable to delay opening guis a tick
    var displayScreen: GuiScreen? = null

    @Mod.EventHandler
    fun onFMLInitialization(event: FMLInitializationEvent) {
        MinecraftForge.EVENT_BUS.register(this)
        ClientCommandHandler.instance.registerCommand(ChromaCommand)
    }

    @SubscribeEvent
    fun onTick(event: TickEvent.ClientTickEvent) {
        if (event.phase != TickEvent.Phase.START) return

        if (displayScreen != null) {
            Minecraft.getMinecraft().displayGuiScreen(displayScreen)
            displayScreen = null
        }
    }
}