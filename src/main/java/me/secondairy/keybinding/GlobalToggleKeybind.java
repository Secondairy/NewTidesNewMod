package me.secondairy.keybinding;

import me.secondairy.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class GlobalToggleKeybind {
    private static KeyBinding keyBinding;

    public GlobalToggleKeybind() {
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.newtidesnewmod.global_toggle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_K,
                "category.newtidesnewmod.keybinds"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) {return;}
            while (keyBinding.wasPressed()) {
                ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
                config.global_toggle = !config.global_toggle;
                String color = "§c ";
                if (config.global_toggle) {
                    color = "§a ";
                }
                client.player.sendMessage(Text.literal("§6[§9NewTidesNewMod§6]§r Auto reply has been toggled to:" + color + config.global_toggle), false);
            }
        });
    }
}
