package by.matusievic.ns.chat.multicast;

import by.matusievic.ns.chat.multicast.config.Config;
import by.matusievic.ns.chat.multicast.util.PacketListener;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class MulticastChat {
    private MulticastSocket socket;
    private boolean interrupted;

    public void run() throws Exception {
        socket = new MulticastSocket(Config.port);
        PacketListener listener = new PacketListener(socket);
        listener.start();
        Scanner s = new Scanner(System.in);

        while (!interrupted && s.hasNextLine()) {
            String message = s.nextLine();
            process(message);
        }
    }

    private InetAddress address() {
        try {
            return InetAddress.getByName(Config.address);
        } catch (Exception e) {
            return null;
        }
    }

    private void process(String message) throws IOException {
        InetAddress address = address();

        String command = message.split(" ")[0];
        if ("JOIN".equals(command.toUpperCase())) {
            socket.joinGroup(address);
            return;
        } else if ("LEAVE".equals(command.toUpperCase())) {
            socket.leaveGroup(address);
            return;
        }

        byte[] data = message.getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, address, Config.port);
        socket.send(packet);
    }
}
