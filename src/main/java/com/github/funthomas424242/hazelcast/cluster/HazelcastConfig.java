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

import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;
import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.ListenerConfig;
import com.hazelcast.config.MulticastConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.config.TcpIpConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MembershipListener;
import com.octoperf.cluster.api.ClusteringService;
import java.net.InetAddress;
import java.net.UnknownHostException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConditionalOnProperty(name = "clustering.driver", havingValue = "com/github/funthomas424242/hazelcast/cluster")
class HazelcastConfig {
  private static final Splitter COMA = Splitter.on(',').trimResults();

  @Bean
  HazelcastQuorumListener membershipListener(@Value("${clustering.hazelcast.quorum:1}") final int quorum) {
    return new HazelcastQuorumListener(quorum);
  }

  @Bean
  Config config(
    final MembershipListener listener,
    @Value("${clustering.hazelcast.members:127.0.0.1}") final String members) throws UnknownHostException {
    final Config config = new Config();

    final NetworkConfig network = config.getNetworkConfig();
    
    final JoinConfig join = network.getJoin();
    final MulticastConfig multicast = join.getMulticastConfig();
    multicast.setEnabled(false);
    
    final TcpIpConfig tcpIp = join.getTcpIpConfig();

    tcpIp.setEnabled(true);
    for(final String member : COMA.splitToList(members)) {
      final InetAddress[] addresses = MoreObjects.firstNonNull(
        InetAddress.getAllByName(member),
        new InetAddress[0]);
      for (final InetAddress addr : addresses) {
        final String hostAddress = addr.getHostAddress();
        tcpIp.addMember(hostAddress);
        log.info("[Hazelcast] New Member: " + hostAddress);
      }
    }

    return config.addListenerConfig(new ListenerConfig(listener));
  }

  @Bean
  ClusteringService clusteringService(final HazelcastInstance hz, final HZQuorumListener listener) {
    return new HazelcastClusteringService(hz, listener);
  }

  @Bean
  HazelcastHealthIndicator clusterHealthIndicator(
    final ClusteringService cluster,
    final HZQuorumListener listener,
    final HazelcastInstance hz) {
    return new HazelcastHealthIndicator(cluster, listener, hz.getCluster());
  }
}
