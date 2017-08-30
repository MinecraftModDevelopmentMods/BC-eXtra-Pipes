package net.ndrei.bcextrapipes.pipes.behaviours

import buildcraft.api.transport.pipe.*
import net.minecraft.nbt.NBTTagCompound

class TeleportingPipeSenderBehaviour : PipeBehaviour {
    constructor(pipe: IPipe) : super(pipe)

    constructor(pipe: IPipe, nbt: NBTTagCompound) : super(pipe, nbt)

    companion object {
        @JvmStatic
        @PipeEventHandler
        @Suppress("unused")
        fun onReachCenter(ev: PipeEventItem.ReachCenter) {
            if (ev.holder.pipe.colour != null) {
                val target = TeleportingPipeReceiverCreator.findRandomPipe(ev.holder.pipe.colour, true) ?: return
                val targetFlow = (target.pipe.flow as? IFlowItems) ?: return

                val stack = ev.stack.copy()
                ev.stack.count = 0 // empty stack

                stack.tagCompound = (stack.tagCompound ?: NBTTagCompound())
                    .also { it.setBoolean("just_spawned", true) }
                targetFlow.insertItemsForce(stack, ev.from.opposite, ev.colour, 0.05)
            }
        }

        @JvmStatic
        @PipeEventHandler
        @Suppress("unused")
        fun onReachCenter(ev: PipeEventFluid.OnMoveToCentre) {
            if (ev.holder.pipe.colour != null) {
                // TODO: bribe BC guys to add better fluid support
//                val target = TeleportingPipeReceiverCreator.findRandomPipe(ev.holder.pipe.colour, false) ?: return
//                val targetFlow = (target.pipe.flow as? IFlowFluid) ?: return
//
//                val cap = target.pipeTile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null) ?: return
            }
        }
    }
}
