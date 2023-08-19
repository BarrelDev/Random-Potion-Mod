package barrel.random_potion.command;

import barrel.random_potion.RandomPotionMod;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import static net.minecraft.server.command.CommandManager.*;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;


public class RandomPotionCommands {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("start_random_potion")
                .executes(context -> startRandomPotion(context.getSource()))));

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("stop_random_potion")
                .executes(context -> stopRandomPotion(context.getSource()))));

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("set_delay")
                .then(argument("ticks", IntegerArgumentType.integer(0))
                .executes(context -> setDelay(context.getSource(), IntegerArgumentType.getInteger(context, "ticks"))))));

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
}
