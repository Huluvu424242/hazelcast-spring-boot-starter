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

import com.google.common.collect.Iterables;
import com.hazelcast.core.Cluster;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IAtomicLong;
import com.hazelcast.core.ILock;
import com.hazelcast.core.IMap;
import com.hazelcast.core.IQueue;
import com.hazelcast.core.ITopic;
import com.hazelcast.core.Member;
import com.github.funthomas424242.hazelcast.api.ClusteredAtomicLong;
import com.github.funthomas424242.hazelcast.api.ClusteredLock;
import com.github.funthomas424242.hazelcast.api.ClusteredMap;
import com.github.funthomas424242.hazelcast.api.ClusteredQueue;
import com.github.funthomas424242.hazelcast.api.ClusteredTopic;
import com.github.funthomas424242.hazelcast.api.ClusteringService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access=PACKAGE)
@FieldDefaults(level=PRIVATE, makeFinal=true)
final class HazelcastClusteringService implements ClusteringService {
  @NonNull
  HazelcastInstance hz;
  @NonNull
  HZQuorumListener quorum;

  @Override
  public boolean isLeader() {
    final Cluster cluster = hz.getCluster();
    final Member leader = Iterables.getFirst(cluster.getMembers(), cluster.getLocalMember());
    return leader.localMember() && quorum.isQuorum();
  }

  @Override
  public <K, V> ClusteredMap<K, V> getMap(final String id) {
    final IMap<K, V> map = hz.getMap(id);
    return new HazelcastMap<>(map);
  }

  @Override
  public <T> ClusteredQueue<T> getQueue(final String id) {
    final IQueue<T> queue = hz.getQueue(id);
    return new HazelcastQueue<>(queue);
  }

  @Override
  public <T> ClusteredTopic<T> getReliableTopic(final String id) {
    final ITopic<T> topic = hz.getReliableTopic(id);
    return new HazelcastTopic<>(topic);
  }

  @Override
  public ClusteredAtomicLong getAtomicLong(final String id) {
    final IAtomicLong atomic = hz.getAtomicLong(id);
    return new HazelcastAtomicLong(atomic);
  }

  @Override
  public ClusteredLock getLock(final String id) {
    final ILock lock = hz.getLock(id);
    return new HazelcastLock(lock);
  }
}
