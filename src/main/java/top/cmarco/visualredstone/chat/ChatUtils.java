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

package top.cmarco.visualredstone.chat;

import com.google.common.base.Preconditions;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class ChatUtils {

    private static final Map<Integer, String> REDSTONE_POWER_HEX_COLOUR_MAP = new HashMap<>(0x10);
    private static final Map<Integer, Component> CACHED_REDSTONE_TEXT_COMPONENTS = new HashMap<>(0x10);

    static {
        REDSTONE_POWER_HEX_COLOUR_MAP.put(0xF, "#940303");
        REDSTONE_POWER_HEX_COLOUR_MAP.put(0xE, "#FF1A00");
        REDSTONE_POWER_HEX_COLOUR_MAP.put(0xD, "#FF3400");
        REDSTONE_POWER_HEX_COLOUR_MAP.put(0xC, "#FF4E00");
        REDSTONE_POWER_HEX_COLOUR_MAP.put(0xB, "#FF6800");
        REDSTONE_POWER_HEX_COLOUR_MAP.put(0xA, "#FF8100");
        REDSTONE_POWER_HEX_COLOUR_MAP.put(0x9, "#FF9B00");
        REDSTONE_POWER_HEX_COLOUR_MAP.put(0x8, "#FFB500");
        REDSTONE_POWER_HEX_COLOUR_MAP.put(0x7, "#FFCF00");
        REDSTONE_POWER_HEX_COLOUR_MAP.put(0x6, "#FFE900");
        REDSTONE_POWER_HEX_COLOUR_MAP.put(0x5, "#FFFF00");
        REDSTONE_POWER_HEX_COLOUR_MAP.put(0x4, "#E9FF00");
        REDSTONE_POWER_HEX_COLOUR_MAP.put(0x3, "#CFFF00");
        REDSTONE_POWER_HEX_COLOUR_MAP.put(0x2, "#B5FF00");
        REDSTONE_POWER_HEX_COLOUR_MAP.put(0x1, "#9BFF00");
        REDSTONE_POWER_HEX_COLOUR_MAP.put(0x0, "#81FF00");

        REDSTONE_POWER_HEX_COLOUR_MAP.forEach((k, v) -> CACHED_REDSTONE_TEXT_COMPONENTS.put(k, PlainTextComponentSerializer
                .builder()
                .build()
                .deserialize(Integer.toString(k))
                .color(TextColor.fromHexString(REDSTONE_POWER_HEX_COLOUR_MAP.get(k)))));
    }

    /**
     * Get the coloured component for a redstone power level.
     *
     * @param redstonePower The power level of a redstone, from 0 to 15.
     * @return The coloured component.
     */
    @NotNull
    public static Component getRedstoneHoverComponent(@Range(from=0, to=15) final int redstonePower) {
        Preconditions.checkArgument(0 <= redstonePower && redstonePower <= 15, "Illegal Redstone Power!");
        return CACHED_REDSTONE_TEXT_COMPONENTS.get(redstonePower);
    }

    private ChatUtils() {
        throw new RuntimeException(getClass().getName() + " constructor cannot be used!");
    }

    @NotNull
    public static TextComponent colour(@NotNull final String text) {
        return LegacyComponentSerializer
                .legacyAmpersand()
                .deserialize(Objects.requireNonNull(text, "Provided null string for ChatUtils#colour."));
    }
}
