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

import com.hazelcast.core.MembershipAdapter;
import com.hazelcast.core.MembershipEvent;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
final class HazelcastQuorumListener extends MembershipAdapter implements HZQuorumListener {
  int quorum;
  AtomicBoolean isQuorum;

  HazelcastQuorumListener(final int quorum) {
    super();
    this.quorum = quorum;
    this.isQuorum = new AtomicBoolean(quorum == 1);
  }

  @Override
  public void memberAdded(final MembershipEvent e) {
    memberRemoved(e);
  }

  @Override
  public void memberRemoved(final MembershipEvent e) {
    isQuorum.set(e.getMembers().size() >= quorum);
  }

  @Override
  public boolean isQuorum() {
    return isQuorum.get();
  }
}
