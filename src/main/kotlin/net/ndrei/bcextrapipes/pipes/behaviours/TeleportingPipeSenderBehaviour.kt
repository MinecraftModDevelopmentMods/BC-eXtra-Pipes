package net.ndrei.bcextrapipes.pipes.behaviours

import buildcraft.api.transport.pipe.*
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing

class TeleportingPipeSenderBehaviour : PipeBehaviour {
    constructor(pipe: IPipe) : super(pipe)

    constructor(pipe: IPipe, nbt: NBTTagCompound) : super(pipe, nbt)

    companion object {
        @JvmStatic
        @PipeEventHandler
        @Suppress("unused")
        fun onReachCenter(ev: PipeEventItem.ReachCenter) {
            if (ev.holder.pipe.colour != null) {
                val target = TeleportingPipeReceiverCreator.findRandomPipe(ev.holder.owner.id, ev.holder.pipe.colour, true) ?: return
                val targetFlow = (target.pipe.flow as? IFlowItems) ?: return

                val stack = ev.stack.copy()
                ev.stack.count = 0 // empty stack

                targetFlow.insertItemsForce(stack, ev.from.opposite, ev.colour, 0.05)
            }
        }

//        @JvmStatic
//        @PipeEventHandler
//        @Suppress("unused")
//        fun onReachCenter(ev: PipeEventFluid.OnMoveToCentre) {
//            if (ev.holder.pipe.colour != null) {
//                val target = TeleportingPipeReceiverCreator.findRandomPipe(ev.holder.owner.id, ev.holder.pipe.colour, false) ?: return
//                val targetFlow = (target.pipe.flow as? IFlowFluid) ?: return
//
//                val totalFluid = ev.fluidEnteringCentre.sum()
//                val fluid = ev.fluid.copy()
//                fluid.amount = totalFluid
//                var inserted = targetFlow.insertFluidsForce(fluid, EnumFacing.DOWN, false)
//
//                ev.fluidEnteringCentre.forEachIndexed { index, _ ->
//                    val transferred = Math.min(ev.fluidEnteringCentre[index], inserted)
//                    if (transferred > 0) {
//                        ev.fluidEnteringCentre[index] -= transferred
//                        inserted -= transferred
//                    }
//                }
//            }
//        }

        @JvmStatic
        @PipeEventHandler
        @Suppress("unused")
        fun checkSides(ev: PipeEventFluid.SideCheck) {
            ev.disallowAll() // Don't let fluids escape from the pipe, or the center of the pipe.
        }
    }


    override fun onTick() {
        super.onTick()

        if (!this.pipe.holder.pipeWorld.isRemote && (this.pipe.colour != null) && (this.pipe.holder != null) && (this.pipe.holder.owner != null)) {
            val target = TeleportingPipeReceiverCreator.findRandomPipe(this.pipe.holder.owner.id, this.pipe.colour, false) ?: return
            val targetFlow = (target.pipe.flow as? IFlowFluid) ?: return

            val flow = this.pipe.flow as? IFlowFluid
            if (flow != null) {
                val extracted = flow.extractFluidsForce(1, 80, null, true)
                if ((extracted != null) && (extracted.amount > 0)) {
                    val inserted = targetFlow.insertFluidsForce(extracted, EnumFacing.DOWN, false)
                    if (inserted > 0) {
                        flow.extractFluidsForce(inserted, inserted, null, false)
                    }
                }
            }
        }
    }
}
