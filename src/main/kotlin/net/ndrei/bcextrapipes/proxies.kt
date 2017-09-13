package net.ndrei.bcextrapipes

import net.minecraft.item.Item
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.ndrei.bcextrapipes.pipes.BCExtraPipesRegistry

@Suppress("unused")
open class CommonProxy(val side: Side) {
    constructor() : this(Side.SERVER)

    open fun preInit(ev: FMLPreInitializationEvent) {
        MinecraftForge.EVENT_BUS.register(this)
        BCExtraPipesRegistry.preInit()
    }

    open fun init(ev: FMLInitializationEvent) {
    }

    open fun postInit(ev: FMLPostInitializationEvent) {
        BCExtraPipesRegistry.postInit()
    }

    @SubscribeEvent
    fun registerItems(ev: RegistryEvent.Register<Item>) {
        BCExtraPipesRegistry.registerItems(ev.registry)
    }

    @SubscribeEvent
    fun registerModels(ev: ModelRegistryEvent) {
        BCExtraPipesRegistry.registerModels()
    }
}

@SideOnly(Side.CLIENT)
@Suppress("unused")
class ClientProxy : CommonProxy(Side.CLIENT)

@SideOnly(Side.SERVER)
@Suppress("unused")
class ServerProxy : CommonProxy(Side.SERVER)
