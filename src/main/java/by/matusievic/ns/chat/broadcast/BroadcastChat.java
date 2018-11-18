package by.matusievic.ns.chat.broadcast;

import by.matusievic.ns.chat.broadcast.config.Config;
import by.matusievic.ns.chat.broadcast.util.PacketListener;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class BroadcastChat {
    private boolean interrupted;

    public void run() throws Exception {
        DatagramSocket socket = new DatagramSocket(Config.port, address(Config.source));
        PacketListener listener = new PacketListener(socket);
        listener.start();
        Scanner s = new Scanner(System.in);

        while (!interrupted && s.hasNextLine()) {
            String message = s.nextLine();
            byte[] data = message.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, address(Config.target), Config.port);
            socket.send(packet);
        }
    }

    private InetAddress address(String address) {
        try {
            return InetAddress.getByName(address);
        } catch (Exception e) {
            return null;
        }
    }
}
