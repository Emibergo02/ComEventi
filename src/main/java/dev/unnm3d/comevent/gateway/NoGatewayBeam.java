package dev.unnm3d.comevent.gateway;

import org.bukkit.plugin.Plugin;
import org.inventivetalent.packetlistener.handler.PacketHandler;
import org.inventivetalent.packetlistener.handler.ReceivedPacket;
import org.inventivetalent.packetlistener.handler.SentPacket;

import net.minecraft.server.v1_12_R1.BlockEndGateway;

public class NoGatewayBeam extends PacketHandler{
	
	public NoGatewayBeam(Plugin p) {
		super(p);
	}
	
	@Override
    public void onSend(SentPacket packet) {
        if (packet.getPacketName().equals("PacketPlayOutBlockAction")) {
            if(packet.getPacketValue(3) instanceof BlockEndGateway) {
            	packet.setCancelled(true);
            }
        }
    }
    @Override
    public void onReceive(ReceivedPacket packet) {
    }
}
