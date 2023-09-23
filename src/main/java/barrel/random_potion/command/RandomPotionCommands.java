package barrel.random_potion.command;

import barrel.random_potion.RandomPotionMod;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import static net.minecraft.server.command.CommandManager.*;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;


public class RandomPotionCommands {
    public static void registerCommands() {
        // start_random_potion
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("start_random_potion")
                .executes(context -> startRandomPotion(context.getSource()))));

        // stop_random_potion
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("stop_random_potion")
                .executes(context -> stopRandomPotion(context.getSource()))));

        // set_delay [ticks]
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("set_delay")
                .then(argument("ticks", IntegerArgumentType.integer(0))
                .executes(context -> setDelay(context.getSource(), IntegerArgumentType.getInteger(context, "ticks"))))));

        // set_effect_length [seconds]
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("set_effect_length")
                .then(argument("seconds", IntegerArgumentType.integer(0))
                        .executes(context -> setEffectLength(context.getSource(), IntegerArgumentType.getInteger(context, "seconds"))))));

        // set_difficulty [level]
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("set_difficulty")
                .then(argument("level", IntegerArgumentType.integer(0))
                        .executes(context -> setDifficulty(context.getSource(), IntegerArgumentType.getInteger(context, "level"))))));

        // use_instant_damage
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("use_instant_damage")
                        .executes(context -> setUseInstantDamage(context.getSource()))));

        RandomPotionMod.LOGGER.info("Registered Commands for " + RandomPotionMod.MOD_ID);
    }

    public static int startRandomPotion(ServerCommandSource source) {
        source.sendMessage(Text.literal("Starting Random Potion"));

        RandomPotionMod.isRunning = true;

        return 1;
    }

    public static int stopRandomPotion(ServerCommandSource source) {
        source.sendMessage(Text.literal("Stopping Random Potion"));

        RandomPotionMod.isRunning = false;

        return 1;
    }

    public static int setDelay(ServerCommandSource source, int ticks) {
        RandomPotionMod.delay = ticks;
        RandomPotionMod.currTime = ticks;

        source.sendMessage(Text.literal("Set Random Potion Delay to " + ticks + " ticks"));
        return 1;
    }

    public static int setEffectLength(ServerCommandSource source, int seconds) {
        RandomPotionMod.effect_length = seconds;

        source.sendMessage(Text.literal("Set Random Potion Effect Length to " + seconds + " seconds"));
        return 1;
    }

    public static int setDifficulty(ServerCommandSource source, int level) {
        RandomPotionMod.difficulty = level;

        source.sendMessage(Text.literal("Set Random Potion Difficulty to " + level));
        return 1;
    }

    public static int setUseInstantDamage(ServerCommandSource source) {
        boolean use = !RandomPotionMod.useInstantDamage;
        RandomPotionMod.useInstantDamage = use;

        if(use)
            source.sendMessage(Text.literal("Enabled Instant Damage Effect"));
        else
            source.sendMessage(Text.literal("Disabled Instant Damage Effect"));
        return 1;
    }
}
