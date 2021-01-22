/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.takamakachain.walletweblauncher;

import com.takamakachain.walletweblauncher.utils.CommandTakamakaWebApp;
import java.io.IOException;
import javax.management.NotificationBroadcasterSupport;

/**
 *
 * @author isacco
 */
public class Launch {
    public static void main(String[] args) throws IOException {
        CommandTakamakaWebApp ctu = new CommandTakamakaWebApp(args, new NotificationBroadcasterSupport(), 0);
        ctu.execute();
    }
}
