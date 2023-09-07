package barrel.random_potion;

import barrel.random_potion.command.RandomPotionCommands;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.network.ServerPlayerEntity;
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

	public static final int DEFAULT_EFFECT_LENGTH = 30;

	public static final int DEFAULT_DIFFICULTY = 2;

	public static int delay = DEFAULT_DELAY;
	public static int effect_length = DEFAULT_EFFECT_LENGTH;
	public static int difficulty = DEFAULT_DIFFICULTY;
	public static boolean isRunning = false;
	public static int currTime = delay;

	@Override
	public void onInitialize() {
		long start = System.currentTimeMillis();

		RandomPotionCommands.registerCommands();

		// Register Game Loop
		ServerTickEvents.END_WORLD_TICK.register(world -> {
			if(isRunning) {
				List<ServerPlayerEntity> playersList = world.getPlayers();
				if(currTime <= 1) {
					currTime = delay;
					for(ServerPlayerEntity player : playersList) {
						player.addStatusEffect(new StatusEffectInstance(getRandomEffect(), effect_length * 20, (int) (Math.random() * difficulty)));
					}
				} else {
					currTime--;
				}
			}
		});

		LOGGER.info("Initialized " + MOD_ID + " in " + (start - System.currentTimeMillis()) + " milliseconds!");
	}

	public static StatusEffect getRandomEffect() {
		int randomId = Math.max(1, (int) (Math.random() * 34));

		return StatusEffect.byRawId(randomId);
	}
}