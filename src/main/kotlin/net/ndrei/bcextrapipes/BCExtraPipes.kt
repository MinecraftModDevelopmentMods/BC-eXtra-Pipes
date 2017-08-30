package net.ndrei.bcextrapipes

import buildcraft.api.transport.pipe.PipeApi
import buildcraft.api.transport.pipe.PipeDefinition
import buildcraft.lib.registry.RegistryHelper
import buildcraft.lib.registry.TagManager
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.SidedProxy
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.ndrei.bcextrapipes.pipes.behaviours.TeleportingPipeReceiverCreator
import net.ndrei.bcextrapipes.pipes.behaviours.TeleportingPipeSenderCreator
import org.apache.logging.log4j.Logger

@Suppress("unused")
@Mod(modid = MOD_ID, version = MOD_VERSION, name = MOD_NAME,
    acceptedMinecraftVersions = MOD_MC_VERSION,
    dependencies = MOD_DEPENDENCIES,
    useMetadata = true, modLanguage = "kotlin", modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter")
object BCExtraPipes {
    @SidedProxy(clientSide = "net.ndrei.bcextrapipes.ClientProxy", serverSide = "net.ndrei.bcextrapipes.ServerProxy")
    lateinit var proxy: CommonProxy
    lateinit var logger: Logger

//    val creativeTab = CreativeTabManager.createTab(MOD_NAME)

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        BCExtraPipes.logger = event.modLog
        // BCExtraPipes.proxy.preInit(event)

        TagManager.startBatch()
        TagManager.registerTag("item.pipe.bcextrapipes.teleport_sender_item").reg("teleport_sender_item").locale("PipeTeleportSenderItem")
        TagManager.registerTag("item.pipe.bcextrapipes.teleport_sender_fluid").reg("teleport_sender_fluid").locale("PipeTeleportSenderFluid")
        TagManager.registerTag("item.pipe.bcextrapipes.teleport_receiver_item").reg("teleport_receiver_item").locale("PipeTeleportReceiverItem")
        TagManager.registerTag("item.pipe.bcextrapipes.teleport_receiver_fluid").reg("teleport_reciever_fluid").locale("PipeTeleportReceiverFluid")
        TagManager.endBatch(TagManager.prependTags("${MOD_ID}:", TagManager.EnumTagType.REGISTRY_NAME, TagManager.EnumTagType.MODEL_LOCATION)
            .andThen(TagManager.setTab("buildcraft.pipes"))) // creativeTab.tabLabel)))

        RegistryHelper.useOtherModConfigFor(MOD_ID, "buildcrafttransport")

        val builderSenderItem = PipeDefinition.PipeDefinitionBuilder()
        builderSenderItem.flow(PipeApi.flowItems)
        builderSenderItem.identifier = ResourceLocation(MOD_ID, "teleport_sender_item")
        builderSenderItem.texPrefix("teleport_sender_item")
        builderSenderItem.logic(TeleportingPipeSenderCreator, TeleportingPipeSenderCreator)
        /*val itemSender =*/ PipeApi.pipeRegistry.registerPipeAndItem(builderSenderItem.define())
//        if (itemSender is Item) {
//            BCExtraPipes.creativeTab.setItem(itemSender)
//        }

        val builderSenderFluid = PipeDefinition.PipeDefinitionBuilder()
        builderSenderFluid.flow(PipeApi.flowFluids)
        builderSenderFluid.identifier = ResourceLocation(MOD_ID, "teleport_sender_fluid")
        builderSenderFluid.texPrefix("teleport_sender_fluid")
        builderSenderFluid.logic(TeleportingPipeSenderCreator, TeleportingPipeSenderCreator)
        /*val fluidSender =*/ PipeApi.pipeRegistry.registerPipeAndItem(builderSenderFluid.define())

        val builderReceiverItem = PipeDefinition.PipeDefinitionBuilder()
        builderReceiverItem.flow(PipeApi.flowItems)
        builderReceiverItem.identifier = ResourceLocation(MOD_ID, "teleport_receiver_item")
        builderReceiverItem.texPrefix("teleport_receiver_item")
        builderReceiverItem.logic(TeleportingPipeReceiverCreator, TeleportingPipeReceiverCreator)
        /*val itemReceiver =*/ PipeApi.pipeRegistry.registerPipeAndItem(builderReceiverItem.define())

        val builderReceiverFluid = PipeDefinition.PipeDefinitionBuilder()
        builderReceiverFluid.flow(PipeApi.flowFluids)
        builderReceiverFluid.identifier = ResourceLocation(MOD_ID, "teleport_receiver_fluid")
        builderReceiverFluid.texPrefix("teleport_receiver_fluid")
        builderReceiverFluid.logic(TeleportingPipeReceiverCreator, TeleportingPipeReceiverCreator)
        /*val fluidReceiver =*/ PipeApi.pipeRegistry.registerPipeAndItem(builderReceiverFluid.define())
    }

    @Mod.EventHandler
    fun init(e: FMLInitializationEvent) {
        // BCExtraPipes.proxy.init(e)
    }

    @Mod.EventHandler
    fun postInit(e: FMLPostInitializationEvent) {
        // BCExtraPipes.proxy.postInit(e)
    }
}
