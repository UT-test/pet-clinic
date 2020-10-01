package org.springframework.samples.petclinic.utility;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.model.BaseEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;
import java.util.stream.IntStream;

import static java.lang.Math.abs;
import static org.junit.jupiter.api.Assertions.*;

class IdentityMapTest {

	private IdentityMap<SampleEntity> identityMapUnderTest;
	private HashSet<SampleEntity> someSampleEntities;

	@BeforeEach
	public void setup() {
		identityMapUnderTest = new IdentityMap<>();
		someSampleEntities = new HashSet<>();
		someSampleEntities.add(new SampleEntity());
		someSampleEntities.add(new SampleEntity());
		someSampleEntities.add(new SampleEntity());
	}


    @Test
    void testSizeOfEmptyIdentityMapMustBeZero() {
		assertEquals(0, identityMapUnderTest.size());
    }

    @Test
    void testIsEmpty() {
		assertTrue(identityMapUnderTest.isEmpty());
	}

    @Test
    void testAddOneEntity() {
		SampleEntity entity = new SampleEntity();
		identityMapUnderTest.add(entity);
		assertTrue(identityMapUnderTest.contains(entity));
		assertEquals(1, identityMapUnderTest.size());
    }

	@Test
	void testAddSomeEntity() {
		identityMapUnderTest.addAll(someSampleEntities);
		someSampleEntities.forEach(sampleEntity -> {
			assertTrue(identityMapUnderTest.contains(sampleEntity));
		});
		assertEquals(someSampleEntities.size(), identityMapUnderTest.size());
	}

    @Test
    void testRemove() {
		identityMapUnderTest.addAll(someSampleEntities);
		someSampleEntities.forEach(sampleEntity -> {
			assertTrue(identityMapUnderTest.contains(sampleEntity));
			identityMapUnderTest.remove(sampleEntity);
			assertFalse(identityMapUnderTest.contains(sampleEntity));
		});
    }

    @Test
    void test_containsAll() {
		identityMapUnderTest.addAll(someSampleEntities);
		assertTrue(identityMapUnderTest.containsAll(someSampleEntities));
	}

    @Test
    void test_removeAll() {
		identityMapUnderTest.addAll(someSampleEntities);
		assertTrue(identityMapUnderTest.containsAll(someSampleEntities));
		identityMapUnderTest.removeAll(someSampleEntities);
		someSampleEntities.forEach(
			sampleEntity -> {
				assertFalse(identityMapUnderTest.contains(sampleEntity));
			}
		);
	}

    @Test
    void test_clear() {
		identityMapUnderTest.addAll(someSampleEntities);
		identityMapUnderTest.clear();
		assertTrue(identityMapUnderTest.isEmpty());
		assertEquals(0, identityMapUnderTest.size());
    }

	/**
	 * a simple entity just for testing {@link IdentityMap}
	 */
	private static class SampleEntity extends BaseEntity {
		private static final Random idGenerator = new Random();

		SampleEntity() {
			this.setId(idGenerator.nextInt());
		}

		SampleEntity(int id) {
			this.setId(id);
		}
	}


	ArrayList<String> list = new ArrayList<>();
	Random random = new Random();
	@Test
	public void test() {
		IntStream.range(0, 10000)
			.forEach(id -> {
				identityMapUnderTest.add(new SampleEntity(id));
			});
		IntStream.range(0, 100)
			.forEach(i -> {
				new Thread(this::operate).run();
			});
	}

	private void operate() {
		System.out.println("thread running");
		IntStream.range(0, 100)
			.forEach(id -> {
				int randomIndex = abs(random.nextInt()) % identityMapUnderTest.size();
				SampleEntity entity = new SampleEntity(randomIndex);
				identityMapUnderTest.add(entity);
				System.out.println("sorting");
				identityMapUnderTest.sort(new Comparator<SampleEntity>() {
					@Override
					public int compare(SampleEntity entity1, SampleEntity entity2) {
						return entity1.getId().compareTo(entity2.getId());
					}
				});
				identityMapUnderTest.remove(entity);
			});
	}
}
