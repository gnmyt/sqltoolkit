package de.gnmyt.SQLToolkit.api;

import java.sql.SQLException;

/********************************
 * @author Mathias Wagner
 * Created 25.12.2020
 *******************************/

@FunctionalInterface
public interface SQLConsumer<T> {
    void accept(T t) throws SQLException;
}
