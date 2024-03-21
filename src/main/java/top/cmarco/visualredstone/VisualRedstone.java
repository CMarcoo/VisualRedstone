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

package top.cmarco.visualredstone;

import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import top.cmarco.visualredstone.commands.RedstoneCommand;
import top.cmarco.visualredstone.commands.tab.RedstoneCommandTabCompleter;
import top.cmarco.visualredstone.listener.PlayerLeaveListener;
import top.cmarco.visualredstone.task.RedstoneRenderingService;

import java.util.Objects;

/**
 * The main class of this plugin.
 */
public final class VisualRedstone extends JavaPlugin {

    // Static fields:
    public static final String LOGO = "&7⊳&cVisualRedstone&7⊲";

    // Instance fields:
    private RedstoneCommand redstoneCommand = null;
    private RedstoneRenderingService redstoneRenderingService = null;

    @Override
    public void onEnable() {
        setupRenderService();
        setupCommands();
        setupListeners();
    }

    @Override
    public void onDisable() {
        destroyData();
    }

    // Methods:

    private void setupListeners() {
        Listener listener = new PlayerLeaveListener(Objects.requireNonNull(redstoneRenderingService, "The rendering service cannot be null!"));
        getServer().getPluginManager().registerEvents(listener, this);
    }

    /**
     * Starts the service necessary to provide renderings.
     */
    private void setupRenderService() {
        redstoneRenderingService = new RedstoneRenderingService(this);
    }

    /**
     * Setups all the commands necessary for this plugin to function.
     */
    private void setupCommands() {
        redstoneCommand = new RedstoneCommand(Objects.requireNonNull(redstoneRenderingService, "The rendering service cannot be null!"));

        final PluginCommand redstonePluginCommand = Objects.requireNonNull(getCommand("redstone"), "Could not find a `redstone` command in the plugin.yml!");
        redstonePluginCommand.setExecutor(redstoneCommand);
        redstonePluginCommand.setTabCompleter(new RedstoneCommandTabCompleter());
    }

    /**
     * Destroys all data associated with this plugin.
     */
    private void destroyData() {
        if (redstoneCommand != null) {
            redstoneCommand.clearData();
            redstoneCommand = null;
        }

        redstoneRenderingService = null;
    }

}
