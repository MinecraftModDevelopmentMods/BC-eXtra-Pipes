package net.ndrei.bcextrapipes.pipes

import buildcraft.api.transport.pipe.PipeApi
import buildcraft.api.transport.pipe.PipeDefinition
import buildcraft.lib.registry.RegistryConfig
import buildcraft.lib.registry.TagManager
import buildcraft.transport.item.ItemPipeHolder
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraftforge.registries.IForgeRegistry
import net.ndrei.bcextrapipes.MOD_ID
import net.ndrei.bcextrapipes.pipes.behaviours.TeleportingPipeReceiverCreator
import net.ndrei.bcextrapipes.pipes.behaviours.TeleportingPipeSenderCreator

object BCExtraPipesRegistry {
//    private lateinit var itemSender : IItemPipe
//    private lateinit var fluidSender : IItemPipe
//    private lateinit var itemReceiver : IItemPipe
//    private lateinit var fluidReceiver : IItemPipe

    fun preInit() {
        TagManager.startBatch()
        TagManager.registerTag("item.pipe.bcextrapipes.teleport_sender_item").reg("teleport_sender_item").locale("PipeTeleportSenderItem")
        TagManager.registerTag("item.pipe.bcextrapipes.teleport_sender_fluid").reg("teleport_sender_fluid").locale("PipeTeleportSenderFluid")
        TagManager.registerTag("item.pipe.bcextrapipes.teleport_receiver_item").reg("teleport_receiver_item").locale("PipeTeleportReceiverItem")
        TagManager.registerTag("item.pipe.bcextrapipes.teleport_receiver_fluid").reg("teleport_reciever_fluid").locale("PipeTeleportReceiverFluid")
        TagManager.endBatch(TagManager.prependTags("${MOD_ID}:", TagManager.EnumTagType.REGISTRY_NAME, TagManager.EnumTagType.MODEL_LOCATION)
            .andThen(TagManager.setTab("buildcraft.pipes")))

        RegistryConfig.useOtherModConfigFor(MOD_ID, "buildcrafttransport")

        val builderSenderItem = PipeDefinition.PipeDefinitionBuilder()
        builderSenderItem.flow(PipeApi.flowItems)
        builderSenderItem.identifier = ResourceLocation(MOD_ID, "teleport_sender_item")
        builderSenderItem.texPrefix("teleport_sender_item")
        builderSenderItem.logic(TeleportingPipeSenderCreator, TeleportingPipeSenderCreator)
        PipeApi.pipeRegistry.registerPipe(builderSenderItem.define().also { this.pipeDefinitions.add(it) })

        val builderSenderFluid = PipeDefinition.PipeDefinitionBuilder()
        builderSenderFluid.flow(PipeApi.flowFluids)
        builderSenderFluid.identifier = ResourceLocation(MOD_ID, "teleport_sender_fluid")
        builderSenderFluid.texPrefix("teleport_sender_fluid")
        builderSenderFluid.logic(TeleportingPipeSenderCreator, TeleportingPipeSenderCreator)
        PipeApi.pipeRegistry.registerPipe(builderSenderFluid.define().also { this.pipeDefinitions.add(it) })

        val builderReceiverItem = PipeDefinition.PipeDefinitionBuilder()
        builderReceiverItem.flow(PipeApi.flowItems)
        builderReceiverItem.identifier = ResourceLocation(MOD_ID, "teleport_receiver_item")
        builderReceiverItem.texPrefix("teleport_receiver_item")
        builderReceiverItem.logic(TeleportingPipeReceiverCreator, TeleportingPipeReceiverCreator)
        PipeApi.pipeRegistry.registerPipe(builderReceiverItem.define().also { this.pipeDefinitions.add(it) })

        val builderReceiverFluid = PipeDefinition.PipeDefinitionBuilder()
        builderReceiverFluid.flow(PipeApi.flowFluids)
        builderReceiverFluid.identifier = ResourceLocation(MOD_ID, "teleport_receiver_fluid")
        builderReceiverFluid.texPrefix("teleport_receiver_fluid")
        builderReceiverFluid.logic(TeleportingPipeReceiverCreator, TeleportingPipeReceiverCreator)
        PipeApi.pipeRegistry.registerPipe(builderReceiverFluid.define().also { this.pipeDefinitions.add(it) })
    }

    private val pipeDefinitions = mutableListOf<PipeDefinition>() // filled in preInit
    private val pipeItems = mutableListOf<ItemPipeHolder>()

    fun registerItems(registry: IForgeRegistry<Item>) {
        this.pipeDefinitions.mapTo(this.pipeItems) {
            ItemPipeHolder.createAndTag(it).registerWithPipeApi().also {
                registry.register(it)
            }
        }
    }

    fun registerModels() {
        this.pipeItems.forEach { it.registerVariants() }
        // RegistryHelper.registerVariants(*this.pipeItems.toTypedArray())
    }

    fun postInit() {
//        this.fluidSender.also {
//            if (it is Item) {
//                val pipe = Item.getByNameOrId("buildcrafttransport:pipe_diamond_fluid") ?: return
//                GameRegistry.addRecipe(ShapedOreRecipe(it,
//                    "ppp",
//                    "rdr",
//                    'p', "enderpearl",
//                    'r', "dustRedstone",
//                    'd', pipe
//                ))
//            }
//        }
//        this.fluidReceiver.also {
//            if (it is Item) {
//                val pipe = Item.getByNameOrId("buildcrafttransport:pipe_diamond_fluid") ?: return
//                GameRegistry.addRecipe(ShapedOreRecipe(it,
//                    "php",
//                    "rdr",
//                    'p', "enderpearl",
//                    'h', Blocks.HOPPER,
//                    'r', "dustRedstone",
//                    'd', pipe
//                ))
//            }
//        }
    }
}
