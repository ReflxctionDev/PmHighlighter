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
package net.reflxction.pmhighlighter.settings;

import net.reflxction.pmhighlighter.PmHighlighter;

/**
 * Class which contains and manages all the mod settings (while saving it to config etc)
 */
public class Settings {

    // Whether the mod is enabled or not
    private boolean enabled;

    // Whether the mod should send a notification to the player if an update is available
    private boolean sendUpdateNotifications;

    // Whether it should play a sound on a PM
    private boolean playSound;

    // The sound name to be played
    private String soundName;

    // The color code to color
    private String colorCode;

    // Assign all variables
    public Settings() {
        enabled = PmHighlighter.getConfig().get("Settings", "Enabled", true).getBoolean();
        sendUpdateNotifications = PmHighlighter.getConfig().get("Settings", "SendUpdates", true).getBoolean();
        playSound = PmHighlighter.getConfig().get("Settings", "PlaySound", true).getBoolean();
        colorCode = PmHighlighter.getConfig().get("Settings", "Color", "&d").getString();
        soundName = PmHighlighter.getConfig().get("Settings", "Sound", "note.pling").getString();
    }

    /**
     * Whether the mod is enabled or not
     *
     * @return True if the mod is enabled, false if not
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets whether the mod is enabled or not
     *
     * @param enabled Boolean to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        PmHighlighter.getConfig().get("Settings", "Enabled", true).set(enabled);
        PmHighlighter.getConfig().save();
    }

    /**
     * The color code of the "To" and "From" messages
     *
     * @return The color code
     */
    public String getColorCode() {
        return colorCode;
    }

    /**
     * Sets the color code of "To" and "From"
     *
     * @param colorCode New value to set
     */
    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
        PmHighlighter.getConfig().get("Settings", "Color", "&d").set(colorCode);
        PmHighlighter.getConfig().save();
    }

    /**
     * The name of sound to play
     *
     * @return Sound name
     */
    public String getSoundName() {
        return soundName;
    }

    /**
     * Sets the sound to play when a pm is received
     *
     * @param soundName New value to set
     */
    public void setSoundName(String soundName) {
        this.soundName = soundName;
        PmHighlighter.getConfig().get("Settings", "Sound", "note.pling").set(soundName);
        PmHighlighter.getConfig().save();
    }

    /**
     * Whether the mod should play a sound on a PM or not
     *
     * @return Whether the mod should play a sound on a PM or not
     */
    public boolean playSound() {
        return playSound;
    }

    /**
     * Sets whether the mod should play a sound on a PM or not
     *
     * @param playSound New value to set
     */
    public void setPlaySound(boolean playSound) {
        this.playSound = playSound;
        PmHighlighter.getConfig().get("Settings", "PlaySound", true).set(playSound);
        PmHighlighter.getConfig().save();
    }

    /**
     * Whether the mod should send notifications if an update is available
     *
     * @return ^
     */
    public boolean sendNotification() {
        return sendUpdateNotifications;
    }

    /**
     * Sets whether the mod should notifications if an update is available
     *
     * @param flag Boolean to set
     */
    public void setSendNotification(boolean flag) {
        this.sendUpdateNotifications = flag;
        PmHighlighter.getConfig().get("Settings", "SendUpdates", true).set(flag);
        PmHighlighter.getConfig().save();
    }
}
