package org.springframework.samples.petclinic.utility;
import org.springframework.samples.petclinic.model.BaseEntity;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * This is a utility class to provide a sortable map
 * for more information see https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html
 */
public class IdentityMap<Entity extends BaseEntity> implements Collection<Entity> {

	private ArrayList<Entity> entities = new ArrayList<>();
	private Set<Integer> ids = new HashSet<>();


	@Override
	public int size() {
		return ids.size();
	}

	@Override
	public boolean isEmpty() {
		return ids.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		if(o instanceof BaseEntity) {
			return ids.contains(((BaseEntity) o).getId());
		} else {
			return false;
		}
	}

	@Override
	public Iterator<Entity> iterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T[] toArray(T[] ts) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(Entity entity) {
		if(!ids.contains(entity)) {
			ids.add(entity.getId());
			entities.add(entity);
		} else {
			entities.removeIf(entity::sameId);
			this.add(entity);
		}
		return true;
	}

	@Override
	public boolean remove(Object object) {
		if(object instanceof BaseEntity) {
			if(ids.remove(((BaseEntity) object).getId())) {
				return entities.removeIf(((BaseEntity) object)::sameId);
			}
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		for(Object object: collection) {
			if(!contains(object))
				return false;
		}
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends Entity> collection) {
		boolean collectionChaged = false;
		for(Entity entity: collection) {
			collectionChaged |= add(entity);
		}
		return collectionChaged;
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		boolean collectionChaged = false;
		for(Object object: collection) {
			collectionChaged |= remove(object);
		}
		return collectionChaged;
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		ids.clear();
		entities.clear();
	}

	public void sort(Comparator<Entity> comparator) {
		entities.sort(comparator);
	}
}
