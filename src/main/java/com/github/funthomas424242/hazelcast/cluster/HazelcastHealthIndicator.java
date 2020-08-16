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

import com.hazelcast.core.Cluster;
import com.github.funthomas424242.hazelcast.api.ClusteringService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.boot.actuate.health.Status.OUT_OF_SERVICE;
import static org.springframework.boot.actuate.health.Status.UP;

@AllArgsConstructor(access= PACKAGE)
@FieldDefaults(level=PRIVATE, makeFinal=true)
final class HazelcastHealthIndicator extends AbstractHealthIndicator {
  @NonNull
  ClusteringService cluster;
  @NonNull
  HZQuorumListener quorum;
  @NonNull
  Cluster hz;

  @Override
  protected void doHealthCheck(final Health.Builder builder) {
    builder.withDetail("isLeader", cluster.isLeader());
    builder.withDetail("isQuorum", quorum.isQuorum());
    builder.withDetail("members", hz.getMembers().toString());
    builder.status(quorum.isQuorum() ? UP : OUT_OF_SERVICE);
  }
}
