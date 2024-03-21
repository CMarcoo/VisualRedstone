/*
 *     VisualRedstone - PaperMC Plugin to Visualize your Redstone Power.
 *     Copyright © 2024  CMarco
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package top.cmarco.visualredstone.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import top.cmarco.visualredstone.VisualRedstone;
import top.cmarco.visualredstone.chat.ChatUtils;
import top.cmarco.visualredstone.manager.RedstoneDataManager;
import top.cmarco.visualredstone.task.RedstoneRenderingService;

import java.util.Objects;

public final class RedstoneCommand implements CommandExecutor {

    public RedstoneCommand(@NotNull final RedstoneRenderingService redstoneRenderingService) {
        this.redstoneRenderingService = Objects.requireNonNull(redstoneRenderingService, "The RedstoneRenderingService provided in the constructor for " + getClass().getName() + " was null!");
    }

    // Instance fields:
    private final RedstoneDataManager redstoneDataManager = new RedstoneDataManager();
    private final RedstoneRenderingService redstoneRenderingService;


    // Methods:
    @NotNull
    private static Component generateToggleMessage(final boolean toggleResult) {
        final String temp = VisualRedstone.LOGO + " &fYour Visual Redstone mode has been ";
        return ChatUtils.colour(temp + (toggleResult ? "&aenabled." : "&cdisabled."));
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender commandSender,
                             @NotNull final Command command,
                             @NotNull final String s,
                             @NotNull final String[] strings) {

        if (!(commandSender instanceof final Player player)) {
            commandSender.sendMessage(ChatUtils.colour(VisualRedstone.LOGO + " &4This command isn't available for console."));
            return false;
        }

        if (strings.length != 1 || !strings[0].equalsIgnoreCase("toggle")) {
            return false;
        }

        final boolean toggleResult = redstoneDataManager.togglePlayer(player);
        System.out.println(toggleResult);

        if (toggleResult) {
            redstoneRenderingService.startRendering(player);
        } else {
            redstoneRenderingService.stopRendering(player);
        }

        player.sendMessage(generateToggleMessage(toggleResult));

        return true;
    }

    /**
     * Clears all the data associated to this class instance.
     */
    public void clearData() {
        Objects.requireNonNull(redstoneDataManager, "Could not destroy RedstoneDataManager data because it was null!").clearData();
        Objects.requireNonNull(redstoneRenderingService, "Could not destroy RedstoneRenderingService because it was null!").clearData();
    }
}
