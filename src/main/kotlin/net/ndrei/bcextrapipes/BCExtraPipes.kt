package net.ndrei.bcextrapipes

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.SidedProxy
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import org.apache.logging.log4j.Logger

@Suppress("unused")
@Mod(modid = MOD_ID, version = MOD_VERSION, name = MOD_NAME,
    acceptedMinecraftVersions = MOD_MC_VERSION,
    dependencies = MOD_DEPENDENCIES,
    certificateFingerprint = MOD_SIGN_FINGERPRINT,
    useMetadata = true, modLanguage = "kotlin", modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter")
object BCExtraPipes {
    @SidedProxy(clientSide = "net.ndrei.bcextrapipes.ClientProxy", serverSide = "net.ndrei.bcextrapipes.ServerProxy")
    lateinit var proxy: CommonProxy
    lateinit var logger: Logger

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        BCExtraPipes.logger = event.modLog
        BCExtraPipes.proxy.preInit(event)
    }

    @Mod.EventHandler
    fun init(e: FMLInitializationEvent) {
        BCExtraPipes.proxy.init(e)
    }

    @Mod.EventHandler
    fun postInit(e: FMLPostInitializationEvent) {
        BCExtraPipes.proxy.postInit(e)
    }
}
