package dev.unnm3d.comevent.dragon;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.inventivetalent.packetlistener.PacketListenerAPI;

import dev.unnm3d.comevent.Main;
import dev.unnm3d.comevent.gateway.NoGatewayBeam;
import net.minecraft.server.v1_12_R1.EntityEnderDragon;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_12_R1.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_12_R1.World;

public class EffettoDragone {

	private static NoExplosion ph;
	private EntityEnderDragon eed;
	public EffettoDragone(World w,double x,double y,double z) {
		eed=new EntityEnderDragon(w);
		eed.setPositionRotation(x, y, z, 0, 0);

	}
	
	public void sendToPlayer(final Player p) {
		PacketPlayOutSpawnEntityLiving pposel=new PacketPlayOutSpawnEntityLiving(eed);
		
		final PacketPlayOutEntityStatus ppoes=new PacketPlayOutEntityStatus(eed,(byte)3);
		((CraftPlayer)p).getHandle().playerConnection.sendPacket(pposel);
		Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {

			public void run() {
				((CraftPlayer)p).getHandle().playerConnection.sendPacket(ppoes);
				
			}
			
		}, 100);
		
	}
    /**
     * Registers listener for blocking animation packet from gateways (purple beam)
     * @deprecated
     */
    public static void register() {
    	ph=new NoExplosion(Main.getInstance());
		PacketListenerAPI.addPacketHandler(ph);
	}
    /**
     * Registers listener for blocking animation packet from gateways (purple beam)
     * @deprecated
     */
	public static void unregister() {
		PacketListenerAPI.removePacketHandler(ph);
	}
}
