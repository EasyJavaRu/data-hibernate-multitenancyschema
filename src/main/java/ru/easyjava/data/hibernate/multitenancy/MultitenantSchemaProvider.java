package ru.easyjava.data.hibernate.multitenancy;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

/**
 * Schema selecting multitenant provider.
 * <p>
 * Works with PgSQL database and HikariCP pool.
 */
public class MultitenantSchemaProvider implements MultiTenantConnectionProvider {
    /**
     * As on the top we are Serializable, we have to put that.
     */
    private static final long serialVersionUID = 42L;

    /**
     * Connection pool.
     */
    private HikariDataSource connectionProvider;

    /**
     * Here we instantiate database connection pool.
     */
    public MultitenantSchemaProvider() {
        HikariConfig parameters = new HikariConfig();

        parameters.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
        parameters.setUsername("test");
        parameters.setPassword("test");
        parameters.setMaximumPoolSize(2);
        parameters.addDataSourceProperty("databaseName", "test");
        parameters.addDataSourceProperty("serverName", "127.0.0.1");

        connectionProvider = new HikariDataSource(parameters);
    }

    /**
     * Switches search path on specified connection.
     *
     * @param c      connection to operate on
     * @param schema tenant id
     * @throws SQLException is thrown when not able
     *                      to return connection to the pool.
     */
    private void setSchemaTo(final Connection c, final String schema) throws SQLException {
        try {
            c.createStatement().execute("SET SCHEMA '" + schema.toLowerCase(Locale.ENGLISH) + "'");
        } catch (SQLException e) {
            connectionProvider.evictConnection(c);
            throw new HibernateException("Error while switching schema", e);
        }
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return connectionProvider.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connectionProvider.evictConnection(connection);
    }

    @Override
    public Connection getConnection(String s) throws SQLException {
        Connection c = getAnyConnection();
        setSchemaTo(c, s);
        return c;
    }

    @Override
    public void releaseConnection(String s, Connection connection) throws SQLException {
        setSchemaTo(connection, "public");
        releaseAnyConnection(connection);
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    /* Spi related mandatory methods */
    @Override
    public boolean isUnwrappableAs(Class aClass) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> aClass) {
        return null;
    }
}
