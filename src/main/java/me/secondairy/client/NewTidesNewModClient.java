package me.secondairy.client;

import me.secondairy.config.ModConfig;
import me.secondairy.keybinding.GlobalToggleKeybind;
import me.secondairy.keybinding.JoinF8Keybind;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.WorldSavePath;
import org.lwjgl.glfw.GLFW;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NewTidesNewModClient implements ClientModInitializer {

    public boolean inFishtance;

    @Override
    public void onInitializeClient() {

        new GlobalToggleKeybind();
        new JoinF8Keybind();

        ClientReceiveMessageEvents.GAME.register((message, overlay) -> {

            if (!inFishtance) {return;}

            String raw_message = message.toString();

            if (MinecraftClient.getInstance().getNetworkHandler() == null) {return;}

            if (!raw_message.contains("empty[siblings=[literal{                                                                                }[style={color=white,strikethrough}]")) {return;}

            ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

            if (!config.global_toggle) {return;}

            Boolean info_detected = raw_message.contains("siblings=[empty[siblings=[literal{\uE2CB}[style={color=white,font=mcc:icon}]]], literal{] Info:}]]");
            Boolean ingo_detected = raw_message.contains("siblings=[empty[siblings=[literal{\uE2CB}[style={color=white,font=mcc:icon}]]], literal{] Ingo:}]]");
            Boolean tides_detected = raw_message.contains("literal{\uE0A5}") //glimmering
                    || raw_message.contains("literal{\uE0A6}") //greedy
                    || raw_message.contains("literal{\uE0A7}") //lucky
                    || raw_message.contains("literal{\uE0A8}") //strong
                    || raw_message.contains("literal{\uE0A9}"); //wise
            Boolean winds_detected = raw_message.contains("literal{\uE0AD}") //glimmering
                    || raw_message.contains("literal{\uE0AE}") //greedy
                    || raw_message.contains("literal{\uE0AF}") //lucky
                    || raw_message.contains("literal{\uE0B0}") //strong
                    || raw_message.contains("literal{\uE0B1}"); //wise

            if (config.enable_info && info_detected) {
                MinecraftClient.getInstance().getNetworkHandler().sendChatCommand("local " + config.info_response);
            }

            else if (config.enable_ingo && ingo_detected) {
                MinecraftClient.getInstance().getNetworkHandler().sendChatCommand("local " + config.ingo_response);
            }

            else if (config.enable_tides && tides_detected) {
                MinecraftClient.getInstance().getNetworkHandler().sendChatCommand("local " + config.tides_response);
            }

            else if (config.enable_winds && winds_detected) {
                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

                scheduler.schedule(() -> {
                    MinecraftClient.getInstance().getNetworkHandler().sendChatCommand("local " + config.winds_response);
                }, 300, TimeUnit.MILLISECONDS);
            }
        });

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (client.world == null) {return;}
            if (MinecraftClient.getInstance().getNetworkHandler() == null) {return;}

            String world_name = client.world.getRegistryKey().getValue().toString();

            boolean in_fishtance_8 = world_name.contains("6223c6ac-7550-446d-8512-01ef562ce305");
            inFishtance = in_fishtance_8;
            if (in_fishtance_8) {return;}

            ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
            if (!config.force_fishtance8) {return;}

            boolean other_fishtance = world_name.contains("a51591e2-e052-4c2a-9ca8-bc65d46721e6") //fishtance 9
                    || world_name.contains("388d6e1f-0d10-482b-80ba-1305c92118bb") //fishtance 10
                    || world_name.contains("c04058a2-15b0-4764-b598-2f512822bc11") //fishtance 11
                    || world_name.contains("a259713d-0806-43b3-a13b-af2b352e05b5"); //fishstance 12

            if (other_fishtance) {
                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                scheduler.schedule(() -> {
                    MinecraftClient.getInstance().getNetworkHandler().sendChatCommand("instance join 8");
                }, 300, TimeUnit.MILLISECONDS);
            }
        });
    }
}
