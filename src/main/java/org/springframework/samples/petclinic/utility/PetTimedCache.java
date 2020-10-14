package org.springframework.samples.petclinic.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetRepository;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
	import java.util.Date;
	import java.util.Map;
	import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Vivekananthan M
 */
@Component
public class PetTimedCache {

	private final static Logger log = LoggerFactory.getLogger(PetTimedCache.class);
	private final PetRepository repository;

	private Map<Integer, Long> timeMap = new ConcurrentHashMap<Integer, Long>();
	private Map<Integer, Pet> actualMap = new ConcurrentHashMap<Integer, Pet>();

	private long expiryInMillis = 10000;
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("hh:mm:ss:SSS");

	@Autowired
	public PetTimedCache(PetRepository repository) {
		this.repository = repository;
		initialize();
	}

	public PetTimedCache(long expiryInMillis, PetRepository repository) {
		this.expiryInMillis = expiryInMillis;
		this.repository = repository;
		initialize();
	}

	void initialize() {
		new CleanerThread().start();
	}

	private Pet put(Pet pet) {
		Integer key = pet.getId();
		Date date = new Date();
		timeMap.put(key, date.getTime());
		log.info("Inserting : " + SIMPLE_DATE_FORMAT.format(date) + " : " + key + " : " + pet);
		Pet returnVal = actualMap.put(key, pet);
		return returnVal;
	}

	public Pet get(Integer key) {
		if(actualMap.containsKey(key)) {
			log.info("cache hit");
			return actualMap.get(key);
		} else {
			log.info("cache miss");
			Pet pet = repository.findById(key);
			if(pet != null) {
				put(pet);
			}
			return pet;
		}
	}

	public void save(Pet pet) {
		repository.save(pet);
	}

	class CleanerThread extends Thread {
		@Override
		public void run() {
			log.info("Initiating Cleaner Thread...");
			while (true) {
				cleanMap();
				try {
					Thread.sleep(expiryInMillis / 2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		private void cleanMap() {
			long currentTime = new Date().getTime();
			for (Integer key : timeMap.keySet()) {
				if (currentTime > (timeMap.get(key) + expiryInMillis)) {
					Pet value = actualMap.remove(key);
					timeMap.remove(key);
					log.info("Removing : " + SIMPLE_DATE_FORMAT.format(new Date()) + " : " + key + " : " + value);
				}
			}
		}
	}
}
