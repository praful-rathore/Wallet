package com.praful.projects.utils;

import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 * @author Prafulla Rathore
 */
public class StringUniqueIdentityGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor,
        Object object) throws HibernateException {
        return UniqueIdentityGenerator.generateUniqueIdentity();
    }
}