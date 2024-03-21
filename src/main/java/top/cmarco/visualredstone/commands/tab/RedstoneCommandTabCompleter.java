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

package top.cmarco.visualredstone.commands.tab;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Class handling tab completion for the redstone command.
 */
public final class RedstoneCommandTabCompleter implements TabCompleter {

    // Static fields:
    private static final List<String> FIRST_ARGS = List.of("toggle");

    @Override
    public @Nullable List<String> onTabComplete(@NotNull final CommandSender commandSender,
                                                @NotNull final Command command,
                                                @NotNull final String s,
                                                @NotNull final String[] strings) {

        if (strings.length == 1) {
            return FIRST_ARGS;
        }

        return null;
    }
}
