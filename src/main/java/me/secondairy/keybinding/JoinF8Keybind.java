package me.secondairy.keybinding;

import me.secondairy.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class JoinF8Keybind {
    private static KeyBinding keyBinding;

    public JoinF8Keybind() {
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.newtidesnewmod.join_fishtance_8",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_J,
                "category.newtidesnewmod.keybinds"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (MinecraftClient.getInstance().getNetworkHandler() == null) {return;}
            while (keyBinding.wasPressed()) {
                MinecraftClient.getInstance().getNetworkHandler().sendChatCommand("instance join 8");
            }
        });
    }
}
