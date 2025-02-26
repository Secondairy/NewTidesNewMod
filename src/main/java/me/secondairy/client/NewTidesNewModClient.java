package me.secondairy.client;

import me.secondairy.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.MinecraftClient;

public class NewTidesNewModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ClientReceiveMessageEvents.GAME.register((message, overlay) -> {

            String raw_message = message.toString();

            if (MinecraftClient.getInstance().getNetworkHandler() == null) {return;}

            if (!raw_message.contains("empty[siblings=[literal{                                                                                }[style={color=white,strikethrough}]")) {return;}

            ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

            if (!config.global_toggle) {return;}

            Boolean info_detected = raw_message.contains("siblings=[empty[siblings=[literal{\uE2CB}[style={color=white,font=mcc:icon}]]], literal{] Info:}]]");
            Boolean ingo_detected = raw_message.contains("siblings=[empty[siblings=[literal{\uE2CB}[style={color=white,font=mcc:icon}]]], literal{] Ingo:}]]");
            Boolean tides_detected = raw_message.contains("literal{\uE0A5}")
                    || raw_message.contains("literal{\uE0A6}")
                    || raw_message.contains("literal{\uE0A7}")
                    || raw_message.contains("literal{\uE0A8}");
            Boolean winds_detected = raw_message.contains("literal{\uE0AD}")
                    || raw_message.contains("literal{\uE0AE}")
                    || raw_message.contains("literal{\uE0AF}")
                    || raw_message.contains("literal{\uE0B0}");

            if (config.enable_info && info_detected) {
                MinecraftClient.getInstance().getNetworkHandler().sendChatMessage(config.info_response);
            }

            else if (config.enable_ingo && ingo_detected) {
                MinecraftClient.getInstance().getNetworkHandler().sendChatMessage(config.ingo_response);
            }

            else if (config.enable_tides && tides_detected) {
                MinecraftClient.getInstance().getNetworkHandler().sendChatMessage(config.tides_response);
            }

            else if (config.enable_winds && winds_detected) {
                MinecraftClient.getInstance().getNetworkHandler().sendChatMessage(config.winds_response);
            }
        });
    }
}
