package dev.unnm3d.comevent;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.EndGateway;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import de.slikey.effectlib.effect.AnimatedBallEffect;
import de.slikey.effectlib.effect.ArcEffect;
import de.slikey.effectlib.effect.AtomEffect;
import de.slikey.effectlib.effect.BigBangEffect;
import de.slikey.effectlib.effect.CloudEffect;
import de.slikey.effectlib.effect.CubeEffect;
import de.slikey.effectlib.effect.CylinderEffect;
import de.slikey.effectlib.effect.DiscoBallEffect;
import de.slikey.effectlib.effect.DnaEffect;
import de.slikey.effectlib.effect.DragonEffect;
import de.slikey.effectlib.effect.HillEffect;
import de.slikey.effectlib.effect.LineEffect;
import de.slikey.effectlib.effect.MusicEffect;
import de.slikey.effectlib.effect.TurnEffect;
import de.slikey.effectlib.effect.VortexEffect;
import de.slikey.effectlib.effect.WarpEffect;
import dev.unnm3d.comevent.dragon.EffettoDragone;
import dev.unnm3d.comevent.effectlib.ELibManager;
import dev.unnm3d.comevent.gateway.Gateway;
import dev.unnm3d.comevent.guardianlaser.RandomRay;
import dev.unnm3d.comevent.visual.VisualEffect;
import net.minecraft.server.v1_12_R1.PacketPlayOutResourcePackSend;

//import net.minecraft.server.v1_12_R1.Material;

public class Comandi implements CommandExecutor {

	private EnderDragon e;
	private ArrayList<Gateway> gats = new ArrayList<Gateway>();

	public boolean onCommand(final CommandSender arg0, Command arg1, String arg2, String[] arg3) {

		if (arg0 instanceof Player) {

			Player p = (Player) arg0;
//		p.getLocation().clone().add(0, 1, 0).getBlock().setType(Material.END_GATEWAY, false);
//		EndGateway eg=(EndGateway) p.getLocation().clone().add(0, 1, 0).getBlock();
			if (arg3[0].equals("set")) {
				Gateway gat = new Gateway(p.getEyeLocation());
				gats.add(gat);
			}
			if (arg3[0].equals("send")) {
				gats.get(0).addPlayer(p);
				gats.get(0).showTitle("Benvenuto", "su flexdojo", 10, 70, 200);
			}
			if (arg3[0].equals("pack")) {
				PacketPlayOutResourcePackSend res = new PacketPlayOutResourcePackSend(
						"https://www.dropbox.com/s/nt3i807gj97lthx/FDRS-1.2.6.zip?dl=1",
						"974f36f5ceb52f5765b4999a6ae0f8c23692f26d");
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(res);
			}
			if (arg3[0].equals("drago")) {
				// non va
				// EffettoDragone.register();
				new EffettoDragone(((CraftWorld) p.getWorld()).getHandle(), p.getLocation().getX(),
						p.getLocation().getY(), p.getLocation().getZ()).sendToPlayer(p);
			}
			if (arg3[0].equals("visual") && arg3.length > 1) {

				if (arg3[1].equalsIgnoreCase("remove")) {
					VisualEffect.removeEffect(p);
					return true;
				}
				VisualEffect ve = null;
				if(arg3[1].equalsIgnoreCase("creeper")) {
					ve = new VisualEffect(EntityType.CREEPER, p);
				}else if(arg3[1].equalsIgnoreCase("enderman")) {
					ve = new VisualEffect(EntityType.ENDERMAN, p);
				}else if(arg3[1].equalsIgnoreCase("spider")) {
					ve = new VisualEffect(EntityType.SPIDER, p);
				}
				if(ve!=null) {
				ve.spawn();
				}
			}
			if(arg3[0].equalsIgnoreCase("randomray")) {
				RandomRay disco=new RandomRay(p.getLocation(), 3, 10);
				disco.addTarget(p.getLocation().clone().add(5, 0, 2));
				disco.addTarget(p.getLocation().clone().add(2, 2, 2));
				disco.addTarget(p.getLocation().clone().add(6, 1, -1));
				disco.addTarget(p.getLocation().clone().add(-5, 5, -5));
				disco.addTarget(p.getLocation().clone().add(2, 3, -2));
				disco.addTarget(p.getLocation().clone().add(-2, 2, 2));
				disco.addTarget(p.getLocation().clone().add(6, -1, -1));
				disco.addTarget(p.getLocation().clone().add(-3, 5, -7));
				disco.start();
			}
			if(arg3[0].equalsIgnoreCase("prova")) {
//				WarpEffect de= new WarpEffect(ELibManager.getMan());
//				de.setEntity(p);
//				de.start();
				ArcEffect de= new ArcEffect(ELibManager.getMan());
				de.setEntity(p);
				de.setTargetLocation(p.getLocation().clone().add(0,4,10));
				de.start();
				CubeEffect ab=new CubeEffect(ELibManager.getMan());
				ab.particle=Particle.REDSTONE;
				
				ab.particleCount=3;
				ab.edgeLength=10;
				ab.particleData=Float.valueOf(arg3[1]);
				ab.period=2;
				ab.infinite();
				ab.enableRotation=true;
				ab.setEntity(p);
				ab.start();
			}

//		EnderDragon e=(EnderDragon) p.getWorld().spawnEntity(p.getLocation(), EntityType.ENDER_DRAGON);
//		e.setPhase(Phase.CHARGE_PLAYER);
//		EntityEnderDragon eee=((CraftEnderDragon)e).getHandle();
//		eee.setGoalTarget(((CraftPlayer)p).getHandle());

//		PacketPlayOutEntityDestroy packetdeath = new PacketPlayOutEntityDestroy(entity.getId());
//		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetdeath);

		}
		return true;
	}

}
