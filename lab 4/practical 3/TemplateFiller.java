import java.util.regex.Matcher;
import java.util.regex.Pattern;

class TemplateFiller
{
    public static String fillTemplate(String template, String[] names, String[] values)
    {
        Pattern pattern = Pattern.compile("\\{(\\w+)\\}");
        Matcher matcher = pattern.matcher(template);

        StringBuilder sb = new StringBuilder();

        while(matcher.find())
        {
            String key = matcher.group(1);
            String replacement = "[?]";

            for (int i = 0; i < names.length; i++) {
                if (names[i].equals(key)) {
                    replacement = values[i];
                    break;
                }
            }

            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }

        matcher.appendTail(sb);
        return sb.toString();
    }
}