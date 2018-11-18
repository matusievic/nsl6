package by.matusievic.ns.chat.broadcast.util;

import by.matusievic.ns.chat.broadcast.config.Config;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class PacketListener extends Thread {
    private DatagramSocket socket;

    public PacketListener(DatagramSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            DatagramPacket packet = packet();
            process(packet);
        }
    }

    private DatagramPacket packet() {
        byte[] data = new byte[Config.size];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        try {
            socket.receive(packet);
        } catch (IOException ignored) {}
        return packet;
    }

    private void process(DatagramPacket packet) {
        System.out.println(packet.getAddress() + " > " + new String(packet.getData()).split("\n")[0]);
    }
}
