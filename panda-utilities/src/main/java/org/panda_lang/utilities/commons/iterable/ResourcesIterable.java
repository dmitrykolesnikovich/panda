/*
 * Copyright (c) 2020 Dzikoysk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.panda_lang.utilities.commons.iterable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.IntFunction;

public final class ResourcesIterable<T> implements Iterable<T> {

    private final Iterable<? extends T>[] iterables;

    @SafeVarargs
    public ResourcesIterable(Iterable<? extends T>... iterables) {
        if (iterables.length == 0) {
            throw new IllegalArgumentException("ResourcesIterable requires at least one resource");
        }

        this.iterables = iterables;
    }

    @Override
    public Iterator<T> iterator() {
        return new ResourceIterator();
    }

    final class ResourceIterator implements Iterator<T> {

        private final Iterator<? extends T>[] iterators = Arrays.stream(iterables)
                .map(Iterable::iterator)
                .toArray((IntFunction<Iterator<? extends T>[]>) Iterator[]::new);

        private int selected;

        public ResourceIterator() {
            this.selectNext();
        }

        @Override
        public boolean hasNext() {
            return iterators[selected].hasNext();
        }

        @Override
        public T next() {
            T value = iterators[selected].next();

            if (!iterators[selected].hasNext()) {
                selectNext();
            }

            return value;
        }

        private void selectNext() {
            for (int i = selected; i < iterators.length; i++) {
                Iterator<? extends T> iterator = iterators[i];

                if (iterator.hasNext()) {
                    selected = i;
                    break;
                }
            }
        }

    }

}
