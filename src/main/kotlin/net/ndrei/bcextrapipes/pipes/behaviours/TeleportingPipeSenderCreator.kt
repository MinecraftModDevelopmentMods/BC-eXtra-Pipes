package net.ndrei.bcextrapipes.pipes.behaviours

import buildcraft.api.transport.pipe.IPipe
import buildcraft.api.transport.pipe.PipeDefinition
import net.minecraft.nbt.NBTTagCompound

object TeleportingPipeSenderCreator : PipeDefinition.IPipeCreator, PipeDefinition.IPipeLoader {
    override fun createBehaviour(pipe: IPipe) =
        TeleportingPipeSenderBehaviour(pipe)

    override fun loadBehaviour(pipe: IPipe, nbt: NBTTagCompound) =
        TeleportingPipeSenderBehaviour(pipe, nbt)
}
