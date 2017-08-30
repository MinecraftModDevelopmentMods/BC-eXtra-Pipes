package net.ndrei.bcextrapipes.pipes.behaviours

import buildcraft.api.transport.pipe.IPipe
import buildcraft.api.transport.pipe.PipeBehaviour
import buildcraft.api.transport.pipe.PipeEventHandler
import buildcraft.api.transport.pipe.PipeEventItem
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing

open class TeleportingPipeReceiverBehaviour : PipeBehaviour {
    constructor(pipe: IPipe) : super(pipe)

    constructor(pipe: IPipe, nbt: NBTTagCompound) : super(pipe, nbt)

    override fun canConnect(face: EnumFacing, other: PipeBehaviour) = (other !is TeleportingPipeReceiverBehaviour)

    override fun canConnect(face: EnumFacing, oTile: TileEntity) = false

    @Suppress("unused")
    @PipeEventHandler
    fun tryBounce(tryBounce: PipeEventItem.TryBounce) {
        tryBounce.canBounce = this.pipe.isConnected(tryBounce.from)
    }
}
