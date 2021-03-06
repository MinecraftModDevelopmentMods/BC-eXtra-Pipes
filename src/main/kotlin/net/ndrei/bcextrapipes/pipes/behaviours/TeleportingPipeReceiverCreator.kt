package net.ndrei.bcextrapipes.pipes.behaviours

import buildcraft.api.transport.pipe.*
import net.minecraft.item.EnumDyeColor
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.math.BlockPos
import net.minecraftforge.common.DimensionManager
import java.util.*

object TeleportingPipeReceiverCreator : PipeDefinition.IPipeCreator, PipeDefinition.IPipeLoader {
    private val teleportingPipes = mutableMapOf<UUID, MutableList<Pair<Int, BlockPos>>>()

    private fun addTeleportingPipe(pipe: IPipe) {
        this.addTeleportingPipe(pipe.holder.owner.id, pipe.holder.pipeWorld.provider.dimension, pipe.holder.pipePos)
    }

    private fun addTeleportingPipe(profile: UUID, dimension: Int, pos: BlockPos) {
        val list = this.teleportingPipes.getOrPut(profile, { mutableListOf() })
        list.add(Pair(dimension, pos))
    }

    override fun createBehaviour(pipe: IPipe) =
        object : TeleportingPipeReceiverBehaviour(pipe) {
            private var ticked = false

            override fun onTick() {
                if (!this.ticked) {
                    if (!this.pipe.holder.pipeWorld.isRemote) {
                        // teleportingPipes.add(Pair(this.pipe.holder.pipeWorld.provider.dimension, this.pipe.holder.pipePos))
                        this@TeleportingPipeReceiverCreator.addTeleportingPipe(this.pipe)
                    }
                    this.ticked = true
                }

                super.onTick()
            }
        }

    override fun loadBehaviour(pipe: IPipe, nbt: NBTTagCompound) =
        object : TeleportingPipeReceiverBehaviour(pipe, nbt) {
            private var ticked = false

            override fun onTick() {
                if (!this.ticked) {
                    if (!this.pipe.holder.pipeWorld.isRemote) {
                        // teleportingPipes.add(Pair(this.pipe.holder.pipeWorld.provider.dimension, this.pipe.holder.pipePos))
                        this@TeleportingPipeReceiverCreator.addTeleportingPipe(this.pipe)
                    }
                    this.ticked = true
                }

                super.onTick()
            }
        }

    private fun <T> Iterable<T>.randomOrNull(): T? {
        val count = this.count()
        return if (count == 0) null
        else this.elementAtOrNull(Random().nextInt(this.count()))
    }

    fun findRandomPipe(/*filterWorld: World, filterPos: BlockPos, */profile: UUID, colour: EnumDyeColor, forItems: Boolean) =
        teleportingPipes.getOrDefault(profile, mutableListOf()).also {
            it.removeIf {
                !DimensionManager.isDimensionRegistered(it.first) || DimensionManager.getWorld(it.first).let { world ->
                    !world.isBlockLoaded(it.second) || ((world.getTileEntity(it.second) as? IPipeHolder) == null)
                }
            }
        }.mapNotNull {
            //            if ((it.first == filterWorld.provider.dimension) && (it.second == filterPos)) {
//                return@mapNotNull null
//            }

            val entity = DimensionManager.getWorld(it.first).getTileEntity(it.second) ?: return@mapNotNull null
            val holder = (entity as? IPipeHolder) ?: return@mapNotNull null
            if ((holder.pipe.colour == colour) && (holder.pipe.behaviour is TeleportingPipeReceiverBehaviour) && when (forItems) {
                true -> holder.pipe.flow is IFlowItems
                false -> holder.pipe.flow is IFlowFluid
            }) {
                return@mapNotNull holder
            }

            return@mapNotNull null
        }.randomOrNull()
}
