package com.github.funthomas424242.hazelcast.cluster;

/*-
 * #%L
 * Hazelcast Spring Boot Starter
 * %%
 * Copyright (C) 2019 - 2020 PIUG
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import com.hazelcast.core.IMap;
import com.octoperf.cluster.api.ClusteredMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class HazelcastMap<K, V> implements ClusteredMap<K, V> {
  @NonNull
  IMap<K, V> map;

  @Override
  public Optional<V> get(final K key) {
    return ofNullable(map.get(key));
  }

  @Override
  public void put(final K key, final V value) {
    map.set(key, value);
  }

  @Override
  public void remove(final K key) {
    map.delete(key);
  }

  @Override
  public Map<K, V> copyOf() {
    return new HashMap<>(map);
  }

  @Override
  public void destroy() {
    map.destroy();
  }
}