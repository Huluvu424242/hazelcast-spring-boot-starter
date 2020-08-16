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

import com.hazelcast.core.ITopic;
import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;
import com.github.funthomas424242.hazelcast.api.ClusteredTopic;
import com.github.funthomas424242.hazelcast.api.TopicMessage;
import com.github.funthomas424242.hazelcast.api.TopicMessageListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.experimental.FieldDefaults;

import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
final class HazelcastTopic<T> implements ClusteredTopic<T>, MessageListener<T> {
  ITopic<T> delegate;
  List<TopicMessageListener<T>> listeners;

  HazelcastTopic(final ITopic<T> delegate) {
    super();
    this.delegate = requireNonNull(delegate);
    this.listeners = new CopyOnWriteArrayList<>();
    delegate.addMessageListener(this);
  }

  @Override
  public void destroy() {
    delegate.destroy();
  }

  @Override
  public void register(final TopicMessageListener<T> listener) {
    listeners.add(listener);
  }

  @Override
  public void unregister(final TopicMessageListener<T> listener) {
    listeners.remove(listener);
  }

  @Override
  public void publish(final T message) {
    delegate.publish(message);
  }

  @Override
  public void onMessage(final Message<T> message) {
    final TopicMessage<T> msg = new TopicMessage<>(message.getMessageObject());
    listeners.forEach(l -> l.onMessage(msg));
  }
}
