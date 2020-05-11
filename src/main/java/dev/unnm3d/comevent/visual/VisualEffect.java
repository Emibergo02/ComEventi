package dev.unnm3d.comevent.visual;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.inventivetalent.packetlistener.PacketListenerAPI;
import org.inventivetalent.packetlistener.handler.PacketHandler;
import org.inventivetalent.packetlistener.handler.ReceivedPacket;
import org.inventivetalent.packetlistener.handler.SentPacket;

import dev.unnm3d.comevent.Main;
import dev.unnm3d.comevent.dragon.NoExplosion;
import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.EntityCreeper;
import net.minecraft.server.v1_12_R1.EntityEnderman;
import net.minecraft.server.v1_12_R1.EntityLiving;
import net.minecraft.server.v1_12_R1.EntitySpider;
import net.minecraft.server.v1_12_R1.MobEffect;
import net.minecraft.server.v1_12_R1.MobEffectList;
import net.minecraft.server.v1_12_R1.PacketPlayInFlying.PacketPlayInPositionLook;
import net.minecraft.server.v1_12_R1.PacketPlayOutCamera;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityEffect;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_12_R1.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_12_R1.World;

public class VisualEffect {

	private PacketHandler ph;
	private EntityType visualtype;
	private Player visualizer;
	private Entity support;
	private static ArrayList<VisualEffect> active = new ArrayList<VisualEffect>();

	public VisualEffect(EntityType visualtype) {
		this.visualtype = visualtype;
		register();
		active.add(this);
	}

	public VisualEffect(EntityType visualtype, Player p) {
		this.visualtype = visualtype;
		this.visualizer = p;
		register();
		active.add(this);
	}

	/**
	 * Fa partire la visuale
	 */
	public void spawn() {
		World w = ((CraftWorld) visualizer.getWorld()).getHandle();

		// Scelgo entità
		if (visualtype.equals(EntityType.CREEPER)) {
			EntityCreeper ec = new EntityCreeper(w);
			support = ec;
		} else if (visualtype.equals(EntityType.ENDERMAN)) {
			EntityEnderman ee = new EntityEnderman(w);
			ee.setNoAI(true);
			support = ee;
		} else if (visualtype.equals(EntityType.SPIDER)) {
			support = new EntitySpider(w);
		}
		// Muovo dove c'è il player
		support.setPositionRotation(visualizer.getLocation().getX(), visualizer.getLocation().getY(),
				visualizer.getLocation().getZ(), visualizer.getLocation().getYaw(),
				visualizer.getLocation().getPitch());
		// Non crea problemi
		support.setInvisible(true);
		support.setNoGravity(true);
		support.setSilent(true);
		support.setInvulnerable(true);
		// Pacchetti da mandare
		PacketPlayOutSpawnEntityLiving pSpawn = new PacketPlayOutSpawnEntityLiving((EntityLiving) support);
		PacketPlayOutCamera pCamera = new PacketPlayOutCamera(support);

		((CraftPlayer) visualizer).getHandle().playerConnection.sendPacket(pSpawn);
		((CraftPlayer) visualizer).getHandle().playerConnection.sendPacket(pCamera);

	}

	/**
	 * This makes enderman visible if in the player visual
	 * 
	 * @param tp location to teleport
	 * @deprecated
	 */
	public void move(Location tp) {
		support.teleportTo(tp, false);

		PacketPlayOutEntityTeleport pRotation = new PacketPlayOutEntityTeleport(support);
		((CraftPlayer) visualizer).getHandle().playerConnection.sendPacket(pRotation);
		PacketPlayOutEntityEffect pEffect = new PacketPlayOutEntityEffect(support.getId(),
				new MobEffect(MobEffectList.getByName("INVISIBILITY")));
		((CraftPlayer) visualizer).getHandle().playerConnection.sendPacket(pEffect);

	}

	public void register() {
		ph = new PacketHandler(Main.getInstance()) {

			@Override
			public void onSend(SentPacket arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onReceive(final ReceivedPacket arg0) {
//				if (arg0.getPacketName().equals("PacketPlayInPositionLook")) {
//					Bukkit.getConsoleSender().sendMessage("beh io vado");
//					if (arg0.getPlayername().equals(visualizer.getName())) {
//						Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
//
//							public void run() {
//								float yaw = (Float) arg0.getPacketValue(0);
//								float pitch = (Float) arg0.getPacketValue(1);
//
//								
//							}
//							
//						});
//
//					}
//				}

			}
		};
		PacketListenerAPI.addPacketHandler(ph);
	}

	public void unregister() {
		PacketListenerAPI.removePacketHandler(ph);
	}

	public void stop() {
		PacketPlayOutCamera pCamera = new PacketPlayOutCamera((Entity) ((CraftPlayer) visualizer).getHandle());
		((CraftPlayer) visualizer).getHandle().playerConnection.sendPacket(pCamera);
		PacketPlayOutEntityDestroy pDestroy = new PacketPlayOutEntityDestroy(support.getId());
		((CraftPlayer) visualizer).getHandle().playerConnection.sendPacket(pDestroy);
		unregister();
	}

	public static void removeEffect(Player player) {
		Iterator<VisualEffect> temp = active.iterator();
		while (temp.hasNext()) {
			VisualEffect aa = temp.next();
			if (aa.getVisualizer().equals(player)) {
				aa.stop();
				temp.remove();
			}
		}
	}

	public Player getVisualizer() {
		return visualizer;
	}

	public void setVisualizer(Player visualizer) {
		this.visualizer = visualizer;
	}

	public EntityType getVisualtype() {
		return visualtype;
	}

}
