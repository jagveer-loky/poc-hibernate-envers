package com.fiserv.luc.api.infrastructure.database.naming;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class PrefixPhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl {

    /**
     * TODO Make this an injectable application property
     */
    public static final String TABLE_NAME_PREFIX = "LUC_";

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        final Identifier newIdentifier;
        if (name.getText().toUpperCase().contains(TABLE_NAME_PREFIX))
            newIdentifier = new Identifier(name.getText().toUpperCase(), name.isQuoted());
        else
            newIdentifier = new Identifier(TABLE_NAME_PREFIX + name.getText().toUpperCase(), name.isQuoted());
        return newIdentifier;
    }
}