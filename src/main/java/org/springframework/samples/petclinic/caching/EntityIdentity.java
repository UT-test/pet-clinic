package org.springframework.samples.petclinic.caching;

import java.util.Objects;

/**
 * An entity can be retrieved uniquely EntityIdentity.
 */
public class EntityIdentity {

    private final Class clazz;
    private final Object id;

    public EntityIdentity(Class clazz, Object id) {
        this.clazz = clazz;
        this.id = id;
    }
}
