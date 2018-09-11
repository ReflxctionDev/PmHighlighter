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
package net.reflxction.pmhighlighter.updater;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import net.reflxction.pmhighlighter.PmHighlighter;
import net.reflxction.pmhighlighter.proxy.ClientProxy;
import net.reflxction.pmhighlighter.utils.SimpleSender;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Listener which sends the player a notification update
 */
public class NotificationSender {

    // Whether the notification was already sent or not
    private boolean sent;

    @SubscribeEvent
    public void onFMLNetworkClientConnectedToServer(ClientConnectedToServerEvent event) {
        if (!sent && PmHighlighter.getSettings().sendNotification()) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    if (ClientProxy.getChecker().isUpdateAvailable()) {
                        SimpleSender.send("&eAn update is available for PM Highlighter! To update, do &a/highlighter update&e.");
                        sent = true;
                    }
                }
            }, 2000);
        }
    }
}
