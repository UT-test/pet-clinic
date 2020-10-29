package org.springframework.samples.petclinic.caching;

import java.util.Optional;

/**
 * Caches entities to retrieve entities faster.
 */
public interface Cache {

    /**
     * Retrieves entity based on its class and id.
     * @param clazz Class of an entity
     * @param id Identity of an entity
     * @param <T> Type of that entity
     * @return Retrieved entity
     */
    <T> T retrieve(Class<T> clazz, Object id);

    /**
     * Remove the data for the given entity from the cache.
     */
    void evict(Class<?> clazz, Object id);
}
