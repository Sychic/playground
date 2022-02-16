package sychic.playground.commands

import PlayGround
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import sychic.playground.gui.ChromaScreen

object ChromaCommand : CommandBase() {
    override fun getRequiredPermissionLevel(): Int = 0

    override fun getCommandName(): String  = "chroma"

    override fun getCommandUsage(sender: ICommandSender?): String  = "/chroma"

    override fun processCommand(sender: ICommandSender?, args: Array<out String>?) {
        PlayGround.displayScreen = ChromaScreen()
    }
}