/**
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.neo4j.support.mapping;

import org.neo4j.graphdb.PropertyContainer;
import org.neo4j.helpers.collection.ClosableIterable;
import org.neo4j.helpers.collection.IterableWrapper;

/**
* @author mh
* @since 21.10.11
*/
public class EntityCreatingClosableIterable<T> extends IterableWrapper<T,PropertyContainer> implements ClosableIterable<T>{
    private final ClosableIterable<PropertyContainer> iterable;
    private final Class<T> entityClass;
    private final Neo4jEntityPersister entityPersister;

    public EntityCreatingClosableIterable(ClosableIterable<PropertyContainer> iterable, Class<T> entityClass, Neo4jEntityPersister entityPersister) {
        super(iterable);
        this.iterable = iterable;
        this.entityClass = entityClass;
        this.entityPersister = entityPersister;
    }

    @Override
    protected T underlyingObjectToObject(PropertyContainer state) {
        return entityPersister.createEntityFromState(state, entityClass);
    }

    @Override
    public void close() {
        this.iterable.close();
    }
}
