/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.takamakachain.walletweblauncher.utils;

import java.util.Date;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

/**
 *
 * @author giovanni
 */
public abstract class AbstractNotifyMessage {

    private NotificationBroadcasterSupport nbs;
    private long sequence;
    private String commandNameInternal;

    public void notifyMessage(String message) {
        nbs.sendNotification(
                new Notification(
                        "com.takamaka.mx",
                        "Node",
                        sequence,
                        new Date().getTime(),
                        commandNameInternal + ":> " + message));
    }

    protected void initNotify(NotificationBroadcasterSupport nbs, long sequence, String commandName) {
        this.nbs = nbs;
        this.sequence = sequence;
        this.commandNameInternal = commandName;
    }

}
