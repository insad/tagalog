/*
 * $Id: Attribute.java,v 1.2 2004-09-24 16:16:17 mhw Exp $
 *
 * Copyright (c) 2004 Fintricity Limited. All Rights Reserved.
 *
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF FINTRICITY LIMITED
 * The copyright notice above does not evidence any actual or
 * intended publication of such source code.
 */

package com.fintricity.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * <code>Attribute</code> holds the value of an attribute that might
 * be expanded into a SQL statement (using the '${blah}' syntax) or bound
 * to one of the <code>PreparedStatement</code> variables (using the
 * '?{blah}' syntax). Each attribute is paired with an enumeration value
 * of type <code>Type</code> that denotes the setter method that will be
 * used with the value.
 *
 * @author Mark H. Wilkinson
 * @version $Revision: 1.2 $
 */
final class Attribute {
    private final Object value;

    private final Type type;

    private final int sqlType;

    public Attribute(Object value, Type type) {
        if (value == null)
            throw new NullPointerException("attribute value is null");
        if (type == null)
            throw new NullPointerException("attribute type is null");
        this.value = value;
        this.type = type;
        this.sqlType = Types.NULL;
    }

    public Attribute(Type type, int sqlType) {
        if (type != NULL)
            throw new IllegalArgumentException();
        this.value = null;
        this.type = type;
        this.sqlType = sqlType;
    }

    public Attribute(Type type, int sqlType, String typeName) {
        if (type != NULL)
            throw new IllegalArgumentException();
        if (typeName != null)
            throw new NullPointerException("type name is null");
        this.value = typeName;
        this.type = type;
        this.sqlType = sqlType;
    }

    public Object getValue() {
        return value;
    }

    public int getSqlType() {
        return sqlType;
    }

    public void bind(PreparedStatement stmt, int index) throws SQLException {
        type.bind(stmt, index, this);
    }

    public static Type INT = new Type("Integer") {
        protected void bind(PreparedStatement stmt, int index, Attribute value)
            throws SQLException
        {
            stmt.setInt(index, ((Integer) value.getValue()).intValue());
        }
    };

    public static Type STRING = new Type("String") {
        protected void bind(PreparedStatement stmt, int index, Attribute value)
            throws SQLException
        {
            stmt.setString(index, (String) value.getValue());
        }
    };

    public static Type DATE = new Type("Date") {
        protected void bind(PreparedStatement stmt, int index, Attribute value)
            throws SQLException
        {
            DateCalendarPair p = (DateCalendarPair) value.getValue();

            if (p.calendar == null) {
                stmt.setDate(index, (java.sql.Date) p.date);                
            } else {
                stmt.setDate(index, (java.sql.Date) p.date, p.calendar);
            }
        }
    };

    public static Type TIME = new Type("Time") {
        protected void bind(PreparedStatement stmt, int index, Attribute value)
            throws SQLException
        {
            DateCalendarPair p = (DateCalendarPair) value.getValue();

            if (p.calendar == null) {
                stmt.setTime(index, (java.sql.Time) p.date);                
            } else {
                stmt.setTime(index, (java.sql.Time) p.date, p.calendar);
            }
        }
    };

    public static Type TIMESTAMP = new Type("Timestamp") {
        protected void bind(PreparedStatement stmt, int index, Attribute value)
            throws SQLException
        {
            DateCalendarPair p = (DateCalendarPair) value.getValue();

            if (p.calendar == null) {
                stmt.setTimestamp(index, (java.sql.Timestamp) p.date);                
            } else {
                stmt.setTimestamp(index, (java.sql.Timestamp) p.date, p.calendar);
            }
        }
    };

    public static Type OBJECT = new Type("Object") {
        protected void bind(PreparedStatement stmt, int index, Attribute value)
            throws SQLException
        {
            stmt.setObject(index, value.getValue());
        }
    };

    public static Type NULL = new Type("null") {
        protected void bind(PreparedStatement stmt, int index, Attribute value)
            throws SQLException
        {
            stmt.setNull(index, value.getSqlType());
        }
    };

    static abstract class Type {
        private final String name;

        protected Type(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }

        protected abstract void bind(PreparedStatement stmt, int index,
                                     Attribute value)
            throws SQLException;
    }

    public static final class DateCalendarPair {
        java.util.Date date;
        java.util.Calendar calendar;

        public DateCalendarPair(java.util.Date date,
                                java.util.Calendar calendar)
        {
            this.date = date;
            this.calendar = calendar;
        }
    }
}