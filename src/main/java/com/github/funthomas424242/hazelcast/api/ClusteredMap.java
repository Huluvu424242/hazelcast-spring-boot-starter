package com.github.funthomas424242.hazelcast.api;

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

import java.util.Map;
import java.util.Optional;

public interface ClusteredMap<K, V> extends DestroyableObject {

  Optional<V> get(final K key);

  void put(final K key, final V value);

  void remove(final K key);

  Map<K, V> copyOf();
}
