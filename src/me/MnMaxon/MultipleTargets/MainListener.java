package me.MnMaxon.MultipleTargets;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class MainListener implements Listener {
	public static ArrayList<Entity> hit = new ArrayList<Entity>();

	@EventHandler(priority = EventPriority.HIGHEST)
	public void clickAir(PlayerInteractEvent e) {
		Player p = (Player) e.getPlayer();
		if (p.getItemInHand().getType().equals(Material.DIAMOND_SPADE) && e.getAction().equals(Action.LEFT_CLICK_AIR)) {
			int durability = 0;
			int reach = 4;
			for (Entity ent : p.getNearbyEntities(reach, reach, reach))
				if (ent instanceof Creature) {
					Creature mob = (Creature) ent;
					double distance = p.getLocation().distance(mob.getLocation());
					if (distance <= reach) {
						YamlConfiguration cfg = Main.setupConfig();
						if (cfg.getDouble("Range") >= 180) {
							cfg.set("Range", 179);
							Config.Save(cfg, Main.dataFolder + "/Config.yml");
						}

						Location loc1 = p.getLocation();
						Location loc2 = ent.getLocation();
						Vector vector = loc2.toVector().subtract(loc1.toVector());
						Vector from = new Vector(loc1.getX(), loc1.getY(), loc1.getZ());
						Vector to = new Vector(loc2.getX(), loc2.getY(), loc2.getZ());

						vector = to.subtract(from);
						Location loc = p.getLocation();
						double angle = loc.setDirection(vector).getYaw();

						double terminal = p.getLocation().getYaw();
						if (angle < 0)
							angle = 360 + angle;
						if (terminal < 0)
							terminal = 360 + terminal;
						double Max = terminal + (cfg.getDouble("Range") / 2);
						if (Max >= 360)
							Max = 360 - Max;
						double Min = terminal - (cfg.getDouble("Range") / 2);
						if (Min < 0)
							Min = 360 + Min;
						if (Max < Min) {
							if (angle <= Max || angle >= Min) {
								hit.add(mob);
								mob.damage(4, p);

								Location first_location = p.getLocation();
								Location second_location = mob.getLocation();
								vector = second_location.toVector().subtract(first_location.toVector());
								from = new Vector(first_location.getX(), first_location.getY(), first_location.getZ());
								to = new Vector(second_location.getX(), second_location.getY(), second_location.getZ());
								vector = to.subtract(from);
								vector.setX(vector.getX() * .1);
								vector.setZ(vector.getZ() * .1);
								vector.setY(.5);
								mob.setVelocity(vector);

								durability++;
								e.setCancelled(true);
							}
						} else {
							if (angle <= Max && angle >= Min) {
								hit.add(mob);
								mob.damage(4, p);

								Location first_location = p.getLocation();
								Location second_location = mob.getLocation();
								vector = second_location.toVector().subtract(first_location.toVector());
								from = new Vector(first_location.getX(), first_location.getY(), first_location.getZ());
								to = new Vector(second_location.getX(), second_location.getY(), second_location.getZ());
								vector = to.subtract(from);
								vector.setX(vector.getX() * .1);
								vector.setZ(vector.getZ() * .1);
								vector.setY(.5);
								mob.setVelocity(vector);

								durability++;
								e.setCancelled(true);
							}
						}
					}
				}
			int origin = p.getItemInHand().getDurability();
			if (origin == 0)
				origin = 60;
			p.getItemInHand().setDurability((short) (origin - (durability * 2)));
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void clickMob(EntityDamageByEntityEvent e) {
		if (hit.contains(e.getEntity())) {
			hit.remove(e.getEntity());
			return;
		}
		if (e.getDamager() instanceof Player) {
			int durability = 0;
			Player p = (Player) e.getDamager();
			if (p.getItemInHand().getType().equals(Material.DIAMOND_SPADE)) {
				int reach = 4;
				for (Entity ent : p.getNearbyEntities(reach, reach, reach))
					if (ent instanceof Creature) {
						Creature mob = (Creature) ent;
						double distance = p.getLocation().distance(mob.getLocation());
						if (distance <= reach) {
							YamlConfiguration cfg = Main.setupConfig();
							if (cfg.getDouble("Range") >= 180) {
								cfg.set("Range", 179);
								Config.Save(cfg, Main.dataFolder + "/Config.yml");
							}

							Location loc1 = p.getLocation();
							Location loc2 = ent.getLocation();
							Vector vector = loc2.toVector().subtract(loc1.toVector());
							Vector from = new Vector(loc1.getX(), loc1.getY(), loc1.getZ());
							Vector to = new Vector(loc2.getX(), loc2.getY(), loc2.getZ());

							vector = to.subtract(from);
							Location loc = p.getLocation();
							double angle = loc.setDirection(vector).getYaw();

							double terminal = p.getLocation().getYaw();
							if (angle < 0)
								angle = 360 + angle;
							if (terminal < 0)
								terminal = 360 + terminal;
							double Max = terminal + (cfg.getDouble("Range") / 2);
							if (Max >= 360)
								Max = 360 - Max;
							double Min = terminal - (cfg.getDouble("Range") / 2);
							if (Min < 0)
								Min = 360 + Min;
							if (Max < Min) {
								if (angle <= Max || angle >= Min) {
									hit.add(mob);
									mob.damage(4, p);

									Location first_location = p.getLocation();
									Location second_location = mob.getLocation();
									vector = second_location.toVector().subtract(first_location.toVector());
									from = new Vector(first_location.getX(), first_location.getY(),
											first_location.getZ());
									to = new Vector(second_location.getX(), second_location.getY(),
											second_location.getZ());
									vector = to.subtract(from);
									vector.setX(vector.getX() * .1);
									vector.setZ(vector.getZ() * .1);
									vector.setY(.5);
									mob.setVelocity(vector);

									durability++;
									e.setCancelled(true);
								}
							} else {
								if (angle <= Max && angle >= Min) {
									hit.add(mob);
									mob.damage(4, p);

									Location first_location = p.getLocation();
									Location second_location = mob.getLocation();
									vector = second_location.toVector().subtract(first_location.toVector());
									from = new Vector(first_location.getX(), first_location.getY(),
											first_location.getZ());
									to = new Vector(second_location.getX(), second_location.getY(),
											second_location.getZ());
									vector = to.subtract(from);
									vector.setX(vector.getX() * .1);
									vector.setZ(vector.getZ() * .1);
									vector.setY(.5);
									mob.setVelocity(vector);

									durability++;
									e.setCancelled(true);
								}
							}
						}
					}
				int origin = p.getItemInHand().getDurability();
				if (origin == 0)
					origin = 60;
				p.getItemInHand().setDurability((short) (origin - (durability * 2)));
			}
		}
	}
}
