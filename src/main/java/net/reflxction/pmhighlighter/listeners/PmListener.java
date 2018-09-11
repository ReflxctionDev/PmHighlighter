/*
 * * Copyright 2018 github.com/ReflxctionDev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.reflxction.pmhighlighter.listeners;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.reflxction.pmhighlighter.PmHighlighter;
import net.reflxction.pmhighlighter.utils.ChatColor;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class PmListener {

    private static final Pattern TO_PATTERN = Pattern.compile("\\bTo\\b");
    private static final Pattern FROM_PATTERN = Pattern.compile("\\bFrom\\b");

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent event) {
        if (PmHighlighter.getSettings().isEnabled()) {
            String color = PmHighlighter.getSettings().getColorCode();
            String text = event.message.getUnformattedText();
            if (check(text)) {
                if (text.startsWith("To ")) {
                    String bufferedMessage = new ChatBuffer(text).apply();
                    String normalMessage = text.split(":")[1].trim();
                    event.setCanceled(true);
                    String newText = StringUtils.replaceOnce(event.message.getFormattedText(), "To", ChatColor.format(color + "To"))
                            .replace(normalMessage, bufferedMessage);

                    ChatComponentText chatComponent = new ChatComponentText(newText);
                    chatComponent.setChatStyle(event.message.getChatStyle());
                    Minecraft.getMinecraft().thePlayer.addChatMessage(chatComponent);
                    playSound();
                } else if (text.startsWith("From ")) {
                    event.setCanceled(true);
                    String bufferedMessage = new ChatBuffer(text).apply();
                    String normalMessage = text.split(":")[1].trim();
                    event.setCanceled(true);
                    String newText = StringUtils.replaceOnce(event.message.getFormattedText(), "From", ChatColor.format(color + "From"))
                            .replace(normalMessage, bufferedMessage);

                    ChatComponentText chatComponent = new ChatComponentText(newText);
                    chatComponent.setChatStyle(event.message.getChatStyle());
                    Minecraft.getMinecraft().thePlayer.addChatMessage(chatComponent);
                }
            }
        }
    }

    /**
     * Does some checks using regex whether it starts with From and To
     *
     * @param text Text to check for
     * @return True if regex is matched, false if otherwise
     */
    private boolean check(String text) {
        if (!text.startsWith("To") && !text.startsWith("From")) return false;
        if (text.startsWith("To")) return TO_PATTERN.matcher(text).find();
        return text.startsWith("From") && FROM_PATTERN.matcher(text).find();
    }

    /**
     * Handles playing the sound
     */
    private void playSound() {
        if (!PmHighlighter.getSettings().playSound()) return;
        SoundHandler soundHandler = Minecraft.getMinecraft().getSoundHandler();
        if (soundHandler == null || Minecraft.getMinecraft().theWorld == null) return;
        final EntityPlayerSP p = Minecraft.getMinecraft().thePlayer;
        soundHandler.playSound(PositionedSoundRecord.create(
                new ResourceLocation(PmHighlighter.getSettings().getSoundName()), (float) p.posX, (float) p.posY, (float) p.posZ));
    }

    /**
     * A simple class to fix some chat issues
     */
    private class ChatBuffer {

        // Text to buff
        private String text;

        private ChatBuffer(String text) {
            this.text = text;
        }

        /**
         * Applies fixes to text
         */
        private String apply() {
            String[] words = text.split(":")[1].trim().split("\\s");
            StringBuilder builder = new StringBuilder();
            for (String word : words) {
                builder.append(ChatColor.GRAY).append(word).append(" ");
            }
            return builder.toString();
        }
    }

}
