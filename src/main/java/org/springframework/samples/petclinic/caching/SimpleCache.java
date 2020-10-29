package org.springframework.samples.petclinic.caching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

@Component
public class SimpleCache implements Cache {

    private final EntityManager entityManager;

    private final Map<EntityIdentity, Object> cache;

    @Autowired
    public SimpleCache(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.cache = new HashMap<>();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T retrieve(Class<T> clazz, Object id) {
        EntityIdentity entityIdentity = new EntityIdentity(clazz, id);
        if (cache.containsKey(entityIdentity))
            return (T) cache.get(entityIdentity);
        T retrieved = entityManager.find(clazz, id);
        cache.put(entityIdentity, retrieved);
        return retrieved;
    }

    @Override
    public void evict(Class<?> clazz, Object id) {
        EntityIdentity entityIdentity = new EntityIdentity(clazz, id);
        cache.remove(entityIdentity);
    }

}
