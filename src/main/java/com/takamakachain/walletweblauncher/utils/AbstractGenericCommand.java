/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.takamakachain.walletweblauncher.utils;

import javax.management.NotificationBroadcasterSupport;

/**
 *
 * @author isacco
 */
public abstract class AbstractGenericCommand extends AbstractNotifyMessage implements ExecutableRemoteCommand {

    protected String args[];
    protected NotificationBroadcasterSupport nbs;
    protected long sequence;

    public AbstractGenericCommand(String[] args, NotificationBroadcasterSupport nbs, long sequence) {
        this.args = args;
        this.nbs = nbs;
        this.sequence = sequence;
    }

}
