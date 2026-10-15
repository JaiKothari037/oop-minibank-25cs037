import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface NotBlank
{
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength
{
    int value();
}

class SignupForm
{
    @NotBlank
    @MaxLength(10)
    String username;

    @NotBlank
    String email;

    @MaxLength(5)
    String code;

    public SignupForm(String username, String email, String code)
    {
        this.username = username;
        this.email = email;
        this.code = code;
    }
}

public class FormValidator
{
    public static List<String> validate(Object obj)
    {
        List<String> errors = new ArrayList<>();
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields)
        {
            field.setAccessible(true);
            try
            {
                Object value = field.get(obj);
                String strValue = (value == null) ? "" : value.toString();

                if (field.isAnnotationPresent(NotBlank.class))
                {
                    if (strValue.trim().isEmpty())
                    {
                        errors.add(field.getName() + " cannot be blank");
                    }
                }

                if (field.isAnnotationPresent(MaxLength.class))
                {
                    int max = field.getAnnotation(MaxLength.class).value();
                    if (strValue.length() > max)
                    {
                        errors.add(field.getName() + " exceeds max length of " + max);
                    }
                }
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }

        return errors;
    }

    public static void main(String[] args)
    {
        SignupForm form = new SignupForm("", "user@test.com", "123456");
        List<String> errors = validate(form);

        for (String err : errors)
        {
            System.out.println("error: " + err);
        }
    }
}