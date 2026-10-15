import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Column
{
    String name();
}

class UserProfile
{
    @Column(name = "user_id")
    int id;

    @Column(name = "user_name")
    String name;

    @Column(name = "user_age")
    int age;
}

public class ColumnMapper
{
    public static <T> T populate(Class<T> clazz, String[] headers, String[] values) throws Exception
    {
        T instance = clazz.getDeclaredConstructor().newInstance();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields)
        {
            if (field.isAnnotationPresent(Column.class))
            {
                String colName = field.getAnnotation(Column.class).name();
                int index = -1;

                for (int i = 0; i < headers.length; i++)
                {
                    if (headers[i].equalsIgnoreCase(colName))
                    {
                        index = i;
                        break;
                    }
                }

                if (index != -1 && index < values.length)
                {
                    field.setAccessible(true);
                    String val = values[index];

                    if (field.getType() == int.class || field.getType() == Integer.class)
                    {
                        field.set(instance, Integer.parseInt(val));
                    }
                    else if (field.getType() == String.class)
                    {
                        field.set(instance, val);
                    }
                }
                else
                {
                    System.out.println("missing column for field: " + field.getName());
                }
            }
        }

        return instance;
    }

    public static void main(String[] args)
    {
        String[] headers = { "user_id", "user_name" };
        String[] values = { "101", "Alex" };

        try
        {
            UserProfile user = populate(UserProfile.class, headers, values);
            System.out.println("id: " + user.id + ", name: " + user.name + ", age: " + user.age);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}