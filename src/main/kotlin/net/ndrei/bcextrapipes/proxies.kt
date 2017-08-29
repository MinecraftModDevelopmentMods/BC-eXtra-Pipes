package net.ndrei.bcextrapipes

import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@Suppress("unused")
open class CommonProxy(val side: Side) {
    constructor() : this(Side.SERVER)
}

@SideOnly(Side.CLIENT)
@Suppress("unused")
class ClientProxy : CommonProxy(Side.CLIENT)

@SideOnly(Side.SERVER)
@Suppress("unused")
class ServerProxy : CommonProxy(Side.SERVER)
