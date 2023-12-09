package my.cobeine.sqlava.connection.database.query.impl;

import my.cobeine.sqlava.connection.database.query.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public class SelectQuery implements Query {
    private final String table;
    private final List<String> columns = new ArrayList<>();
    private final List<String> wheres = new ArrayList<>();
    private String orderBy;
    private boolean orderByAscending = false;
    private int limitOffset = 0;
    private int limitRowCount = 0;

    public SelectQuery(String table) {
        this.table = table;
    }


    public SelectQuery column(String column) {
        columns.add(column);
        return this;
    }
    public SelectQuery column(String... column) {
        columns.addAll(Arrays.asList(column));
        return this;
    }

    public SelectQuery where(String expression) {
        wheres.add(expression + "=?");
        return this;
    }
    public SelectQuery where(String... expression) {
        for (String s : expression) {
            wheres.add(s + "=?");
        }
        return this;
    }
    public SelectQuery where(String expression,Object value) {
        wheres.add(expression + "=" + (value instanceof String ? "'" + value +"'" : value));
        return this;
    }
    public SelectQuery and(String expression) {
        where(expression);
        return this;
    }
    public SelectQuery and(String expression,Object value) {
        where(expression,value);
        return this;
    }

    public SelectQuery orderBy(String column, boolean ascending) {
        this.orderBy = column;
        this.orderByAscending = ascending;
        return this;
    }


    public SelectQuery limit(int offset, int rowCount) {
        this.limitOffset = offset;
        this.limitRowCount = rowCount;
        return this;
    }

    public SelectQuery limit(int rowCount) {
        this.limitOffset = 0;
        this.limitRowCount = rowCount;
        return this;
    }

    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();

        if (columns.isEmpty()) builder.append("SELECT *").append(" FROM ").append(table);

        else builder.append("SELECT ").append(separate(columns)).append(" FROM ").append(table);

        if (wheres.size() > 0)
            builder.append(" WHERE ").append(separate(wheres, " AND "));

        if (orderBy != null)
            builder.append(" ORDER BY ").append(orderBy).append(orderByAscending ? " ASC" : " DESC");

        if (limitRowCount > 0)
            builder.append(" LIMIT ").append(limitOffset).append(",").append(limitRowCount);


        return builder.toString();
    }

}
