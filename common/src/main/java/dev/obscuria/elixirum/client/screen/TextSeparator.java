package dev.obscuria.elixirum.client.screen;

import com.google.common.collect.Lists;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

import java.util.ArrayList;
import java.util.List;

public class TextSeparator
{
    public static List<? extends Component> getSeparatedText(Component text, int maxWidth, Style style)
    {
        return getSeparatedText(text.getString(), maxWidth, style);
    }

    public static List<? extends Component> getSeparatedText(String text, int maxWidth, Style style)
    {
        ArrayList<String> words = Lists.newArrayList(text.split(" "));
        ArrayList<Component> lines = new ArrayList<>();
        while (!words.isEmpty())
        {
            MutableComponent component = Component.empty();
            int lineLength = words.getFirst().length();
            component.append(Component.literal(words.removeFirst()).setStyle(style));
            while (!words.isEmpty() && lineLength <= maxWidth)
            {
                lineLength += words.getFirst().length() + 1;
                component.append(" ");
                component.append(Component.literal(words.removeFirst()).setStyle(style));
            }
            lines.add(component);
        }
        return lines;
    }
}
