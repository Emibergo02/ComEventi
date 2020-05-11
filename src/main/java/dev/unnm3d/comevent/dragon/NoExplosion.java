package dev.unnm3d.comevent.dragon;

import org.bukkit.plugin.Plugin;
import org.inventivetalent.packetlistener.handler.PacketHandler;
import org.inventivetalent.packetlistener.handler.ReceivedPacket;
import org.inventivetalent.packetlistener.handler.SentPacket;

import net.minecraft.server.v1_12_R1.EnumParticle;

public class NoExplosion extends PacketHandler{
	
	public NoExplosion(Plugin p) {
		super(p);
	}

	@Override
	public void onReceive(ReceivedPacket arg0) {

		
	}

	@Override
	public void onSend(SentPacket arg0) {
		if(arg0.getPacketName().equals("PacketPlayOutWorldParticles")) {
			if(arg0.getPacketValue(0).equals(EnumParticle.EXPLOSION_HUGE)||arg0.getPacketValue(0).equals(EnumParticle.EXPLOSION_NORMAL)) {
				arg0.setCancelled(true);
			}
		}
		
	}
}
