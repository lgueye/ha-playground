package io.agileinfra.platform.cache.client;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ILock;
import io.agileinfra.platform.dto.IdReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;

/**
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
@Slf4j
public class PlatformCacheClient {
	private final HazelcastInstance hazelcastInstance;

	public ILock getLock(String requestId) {
		return hazelcastInstance.getLock(requestId);
	}

	public void unlock(ILock lock) {
		try {
			if (lock.isLockedByCurrentThread()) {
				lock.unlock();
			}
		} catch (Exception e) {
			log.warn("Tried to unlock lock but was probably not the owner");
		}
	}

	public <T> Optional<T> getOne(String collection, String id) {
		return Optional.ofNullable((T) getMap(collection).get(id));
	}

	public void remove(String collection, String id) {
		hazelcastInstance.getMap(collection).remove(id);
	}

	public <T extends IdReader> void save(String collection, T object) {
		getMap(collection).put(object.getId(), object);
	}

	public <T> Map<String, T> getMap(String collection) {
		return hazelcastInstance.getMap(collection);
	}
}
