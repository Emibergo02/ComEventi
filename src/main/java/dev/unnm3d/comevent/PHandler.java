package dev.unnm3d.comevent;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.plugin.Plugin;
import org.inventivetalent.packetlistener.handler.PacketHandler;
import org.inventivetalent.packetlistener.handler.ReceivedPacket;
import org.inventivetalent.packetlistener.handler.SentPacket;

import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.EntityEnderDragon;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityStatus;

public class PHandler extends PacketHandler {

	private Plugin main;

	public PHandler(Main main) {
		super(main);
		this.main = main;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onReceive(ReceivedPacket arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSend(final SentPacket sent) {
		if (sent.getPacketName().equals("PacketPlayOutEntityStatus")) {

			final List<Entity> listaEntita = ((CraftWorld) sent.getPlayer().getWorld()).getHandle().entityList;
			if (Byte.compare((Byte) sent.getPacketValue(1), (byte) 3) != 0)
				for (final Entity e : listaEntita) {

					if (e instanceof EntityEnderDragon)
						//((EntityEnderDragon)e).setGoalTarget(entityliving);
						Bukkit.getScheduler().runTaskLater(main, new Runnable() {

							public void run() {

								((CraftPlayer) sent.getPlayer()).getHandle().playerConnection
										.sendPacket(new PacketPlayOutEntityStatus(e, (byte) 3));
								Bukkit.getConsoleSender().sendMessage(
										sent.getPacketValue(0) + "-" + sent.getPacketValue(1) + "-" + e.getClass());
							}

						}, 10);

//			Bukkit.getConsoleSender().sendMessage(sent.toString()+sent.getPacket().toString());
//			List<Entity> listaEntita=((CraftWorld)sent.getPlayer().getWorld()).getHandle().entityList;
//			Entity ed;
//			for(Entity e:listaEntita) {
//				if(e.getId()==id) {
//					if(e instanceof EntityEnderDragon) {
//						ed=e;
//					}
//				}
//			}
//			sent.setPacketValueSilent(1, (byte)3);

				}

		}

	}
}
