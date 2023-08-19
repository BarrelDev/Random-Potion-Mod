package barrel.random_potion;

import barrel.random_potion.command.RandomPotionCommands;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import org.apache.logging.log4j.core.jmx.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class RandomPotionMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("random_potion");
	public static final String MOD_ID = "random_potion";

	public static final int DEFAULT_DELAY = 200;

	public static int delay = DEFAULT_DELAY;
	public static boolean isRunning = false;
	public static int currTime = delay;

	@Override
	public void onInitialize() {
		long start = System.currentTimeMillis();

		RandomPotionCommands.registerCommands();

		ServerTickEvents.END_WORLD_TICK.register(world -> {
			if(isRunning) {
				List<ServerPlayerEntity> playersList = world.getPlayers();
				if(currTime <= 1) {
					currTime = delay;
					for(ServerPlayerEntity player : playersList) {
						player.sendMessage(Text.literal("subtracting " + currTime));
						player.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, delay));
						player.sendMessage(Text.literal("effect sent"));
					}
				} else {
					currTime--;
					for(ServerPlayerEntity player : playersList)
						player.sendMessage(Text.literal("subtracting " + currTime));
				}
			}
		});

		LOGGER.info("Initialized " + MOD_ID + " in " + (start - System.currentTimeMillis()) + " milliseconds!");
	}
}