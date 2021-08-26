package de.gnmyt.sqltoolkit.querybuilder;

public class StatementBuilder {

    private final StringBuilder query = new StringBuilder();

    /**
     * Basic constructor of the {@link StatementBuilder} with a prefilled text
     *
     * @param text The text you want to add
     */
    public StatementBuilder(String text) {
        append(text);
    }

    /**
     * Basic constructor of the {@link StatementBuilder}
     */
    public StatementBuilder() {

    }

    /**
     * Adds a text to the query with spaces
     *
     * @param text The text you want to add
     * @return this class
     */
    public StatementBuilder append(String text) {
        if (text.isEmpty()) return this;

        if (!query.toString().isEmpty()) query.append(" ");
        query.append(text);
        return this;
    }

    /**
     * Adds a text to the query without spaces
     *
     * @param text The text you want to add
     * @return this class
     */
    public StatementBuilder appendDefault(String text) {
        if (text.isEmpty()) return this;

        query.append(text);

        return this;
    }

    /**
     * Builds the query string
     *
     * @return the built string
     */
    public String build() {
        return query.toString();
    }

    @Override
    public String toString() {
        return build();
    }
}
