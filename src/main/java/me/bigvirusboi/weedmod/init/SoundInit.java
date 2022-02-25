package me.bigvirusboi.weedmod.init;

import me.bigvirusboi.weedmod.WeedMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundInit {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, WeedMod.MOD_ID);

    public static final Lazy<SoundEvent> LAZY_EXHALE = Lazy.of(() -> new SoundEvent(WeedMod.getId("exhale")));

    public static final RegistryObject<SoundEvent> EXHALE = SOUNDS.register("exhale", LAZY_EXHALE);
}
