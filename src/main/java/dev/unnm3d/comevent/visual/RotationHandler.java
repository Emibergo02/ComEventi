package dev.unnm3d.comevent.visual;

import org.bukkit.plugin.Plugin;
import org.inventivetalent.packetlistener.handler.PacketHandler;
import org.inventivetalent.packetlistener.handler.ReceivedPacket;
import org.inventivetalent.packetlistener.handler.SentPacket;


public class RotationHandler extends PacketHandler{

	public RotationHandler(Plugin pl) {
		super(pl);
	}
	
	@Override
	public void onReceive(ReceivedPacket arg0) {
		if(arg0.getPacketName().equals("PacketPlayInPositionLook")) {
			
		}
				
		
	}

	@Override
	public void onSend(SentPacket arg0) {
		// TODO Auto-generated method stub
		
	}

}
