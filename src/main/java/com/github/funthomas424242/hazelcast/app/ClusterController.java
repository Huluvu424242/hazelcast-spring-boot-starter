//package com.github.funthomas424242.hazelcast.app;
//
///*-
// * #%L
// * Hazelcast Spring Boot Starter
// * %%
// * Copyright (C) 2019 - 2020 PIUG
// * %%
// * This program is free software: you can redistribute it and/or modify
// * it under the terms of the GNU Lesser General Public License as
// * published by the Free Software Foundation, either version 3 of the
// * License, or (at your option) any later version.
// *
// * This program is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU General Lesser Public License for more details.
// *
// * You should have received a copy of the GNU General Lesser Public
// * License along with this program.  If not, see
// * <http://www.gnu.org/licenses/lgpl-3.0.html>.
// * #L%
// */
//
//import com.github.funthomas424242.hazelcast.api.ClusteredMap;
//import com.github.funthomas424242.hazelcast.api.ClusteringService;
//import java.util.Optional;
//import lombok.AllArgsConstructor;
//import lombok.NonNull;
//import lombok.experimental.FieldDefaults;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import static lombok.AccessLevel.PACKAGE;
//import static lombok.AccessLevel.PRIVATE;
//
//@RestController
//@RequestMapping("/cluster")
//@AllArgsConstructor(access = PACKAGE)
//@FieldDefaults(level = PRIVATE, makeFinal = true)
//class ClusterController {
//  private static final String MAP_ID = "map";
//
//  @NonNull
//  ClusteringService clustering;
//
//  @GetMapping("/is-leader") boolean isLeader() {
//    return clustering.isLeader();
//  }
//
//  @GetMapping("/{key}")
//  Optional<String> get(@PathVariable("key") final String key) {
//    final ClusteredMap<String, String> map = clustering.getMap(MAP_ID);
//    return map.get(key);
//  }
//
//  @PutMapping("/{key}/{value}")
//  void get(@PathVariable("key") final String key, @PathVariable("value") final String value) {
//    final ClusteredMap<String, String> map = clustering.getMap("map");
//    map.put(key, value);
//  }
//
//  @DeleteMapping("/{key}")
//  void delete(@PathVariable("key") final String key) {
//    final ClusteredMap<String, String> map = clustering.getMap("map");
//    map.remove(key);
//  }
//
//}
