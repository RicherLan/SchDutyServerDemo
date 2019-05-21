package  netty.protocol.request.otherRequest;

import static  netty.protocol.command.Command.FreshNotification_REQUEST;

import java.util.Vector;

import netty.protocol.Packet;

public class FreshNotificationRequestPacket extends Packet {

    Vector<String> phs;
    public FreshNotificationRequestPacket() {
    	
    }
    @Override
    public int getCommand() {

        return FreshNotification_REQUEST;
    }

    public Vector<String> getPhs() {
        return phs;
    }

    public void setPhs(Vector<String> phs) {
        this.phs = phs;
    }
}
