class DatabaseConnection implements AutoCloseable
{
    private final String dbName;

    public DatabaseConnection(String dbName)
    {
        this.dbName = dbName;
        System.out.println("connected to " + dbName);
    }

    public void executeQuery()
    {
        System.out.println("executing database query");
        throw new IllegalStateException("query execution failed");
    }

    @Override
    public void close()
    {
        System.out.println("closed connection to " + dbName);
    }
}

public class resource_test
{
    public static void main(String[] args)
    {
        try (DatabaseConnection db = new DatabaseConnection("ProductionDB"))
        {
            db.executeQuery();
        }
        catch (Exception e)
        {
            System.out.println("caught: " + e.getMessage());
        }
    }
}