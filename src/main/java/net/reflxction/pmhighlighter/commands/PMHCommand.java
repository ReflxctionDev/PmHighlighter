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
package net.reflxction.pmhighlighter.commands;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.reflxction.pmhighlighter.PmHighlighter;
import net.reflxction.pmhighlighter.proxy.ClientProxy;
import net.reflxction.pmhighlighter.utils.ChatColor;
import net.reflxction.pmhighlighter.utils.Multithreading;
import net.reflxction.pmhighlighter.utils.SimpleSender;

import java.util.Arrays;
import java.util.List;

/**
 * Class which handles command input for "/pmhighlighter"
 */
public class PMHCommand implements ICommand {

    /**
     * Gets the name of the command
     */
    @Override
    public String getCommandName() {
        return "pmhighlighter";
    }

    /**
     * Gets the usage string for the command.
     *
     * @param sender The command sender that executed the command
     */
    @Override
    public String getCommandUsage(ICommandSender sender) {
        return ChatColor.format("/pmhighlighter &c<&ctoggle / &cupdate / &ccheck / &ccolor / &csound>");
    }

    @Override
    public List<String> getCommandAliases() {
        return Arrays.asList("highlighter", "pmh");
    }

    /**
     * Callback when the command is invoked
     *
     * @param sender The command sender that executed the command
     * @param args   The arguments that were passed
     */
    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        switch (args.length) {
            case 0:
                SimpleSender.send("&cIncorrect command usage. Try " + getCommandUsage(sender));
                break;
            case 1:
                switch (args[0]) {
                    case "toggle":
                        PmHighlighter.getSettings().setEnabled(!PmHighlighter.getSettings().isEnabled());
                        SimpleSender.send(PmHighlighter.getSettings().isEnabled() ? "&aPM Highlighter has been enabled" : "&cPM Highlighter has been disabled");
                        break;
                    case "update":
                        if (ClientProxy.getChecker().isUpdateAvailable()) {
                            new Multithreading<>().schedule((foo) -> {
                                if (PmHighlighter.getUpdateManager().updateMod()) {
                                    SimpleSender.send("&aSuccessfully updated the mod! Restart your game to see changes.");
                                } else {
                                    SimpleSender.send("&cFailed to update mod! To avoid any issues, delete the mod jar and install it manually again.");
                                }
                            });
                        } else {
                            SimpleSender.send("&cNo updates found. You're up to date!");
                        }
                        break;
                    case "check":
                        PmHighlighter.getSettings().setSendNotification(!PmHighlighter.getSettings().sendNotification());
                        SimpleSender.send(PmHighlighter.getSettings().sendNotification() ? "&aYou will be notified on updates" : "&cYou will no longer be notified on updates");
                        break;
                    case "sound":
                        PmHighlighter.getSettings().setPlaySound(!PmHighlighter.getSettings().playSound());
                        SimpleSender.send(PmHighlighter.getSettings().playSound() ? "&aSounds enabled!" : "&cSounds disabled!");
                        break;
                    default:
                        SimpleSender.send("&cIncorrect command usage. Try " + getCommandUsage(sender));
                        break;
                }
                break;
            case 2:
                switch (args[0]) {
                    case "sound":
                        PmHighlighter.getSettings().setSoundName(args[1]);
                        SimpleSender.send("&aSound has been set to &e" + args[1]);
                        break;
                    case "color":
                        PmHighlighter.getSettings().setColorCode(args[1]);
                        SimpleSender.send("&aColor has been set to " + ChatColor.format(args[1] + "this"));
                        break;
                    default:
                        SimpleSender.send("&cIncorrect command usage. Try " + getCommandUsage(sender));
                        break;
                }
                break;
        }
    }

    /**
     * Returns true if the given command sender is allowed to use this command.
     *
     * @param sender The command sender that executed the command
     */
    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }


    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return Arrays.asList("toggle", "update", "check", "color", "sound");
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     *
     * @param args  The arguments that were passed
     * @param index Idk lul
     */
    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }

}
