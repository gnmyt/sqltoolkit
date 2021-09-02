import de.gnmyt.sqltoolkit.drivers.MySQLConnection;
import de.gnmyt.sqltoolkit.queries.DeletionQuery;
import de.gnmyt.sqltoolkit.queries.InsertQuery;
import de.gnmyt.sqltoolkit.queries.SelectionQuery;
import de.gnmyt.sqltoolkit.querybuilder.QueryParameter;
import de.gnmyt.sqltoolkit.querybuilder.SQLQuery;

import java.util.HashMap;

public class QueryExample {

    public static void main(String[] args) {

        // First we need to connect to our database
        MySQLConnection connection = new MySQLConnection("localhost", "root", "password", "database");

        // We then can create a hashmap with all where-parameters
        HashMap<String, Object> whereList = new HashMap<>();

        // Now we can add some values to the 'where'-list
        whereList.put("username", "GNM");
        whereList.put("email", "germannewsmaker@gmail.com");

        // Now we can create an example 'select'-query
        SQLQuery query = connection.createQuery(SelectionQuery.class) // Here you can provide which query you want to create
                .addParameter(QueryParameter.TABLE_NAME, "example_table") // Now we can pass in the table name
                .addParameter(QueryParameter.WHERE_LIST, whereList) // And the list of 'where'-parameters
                .build(); // Then we build the query

        // You can then execute it
        connection.getResult(query);

        // Or print it
        System.out.println(query);

        // You can also create a 'delete'-query

        SQLQuery deleteQuery = connection.createQuery(DeletionQuery.class) // Now we try the deletion query
                .addParameter(QueryParameter.TABLE_NAME, "example_table") // Here you can pass in the table name
                .addParameter(QueryParameter.WHERE_LIST, whereList) // For the example we are using the same list as before
                .build(); // And now we build the query

        // You can also create for example a 'insert'-query

        // Because we want to insert values into a table we first need to create the list of values
        HashMap<String, Object> insertValues = new HashMap<>();

        // Now we can put add some values to the 'insert'-list
        insertValues.put("username", "GNM");
        insertValues.put("email", "germannewsmaker@gmail.com");
        insertValues.put("password", "example123!");

        // And then we can create the query
        SQLQuery insertQuery = connection.createQuery(InsertQuery.class) // Now we try the insert query
                .addParameter(QueryParameter.TABLE_NAME, "example_table") // Here you can pass in the table name
                .addParameter(QueryParameter.VALUE_LIST, insertValues) // And now we pass in the value list
                .build();
    }

}
