/*
 * Copyright (c) 2021 dzikoysk
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

package org.panda_lang.framework.architecture.dynamic.assigner;

import org.panda_lang.framework.architecture.statement.Variable;
import org.panda_lang.framework.interpreter.source.Location;
import org.panda_lang.framework.architecture.dynamic.accessor.Accessor;
import org.panda_lang.framework.architecture.statement.AbstractStatement;

public abstract class AbstractAssigner<T extends Variable> extends AbstractStatement implements Assigner<T> {

    protected final Accessor<T> accessor;

    protected AbstractAssigner(Location location, Accessor<T> accessor) {
        super(location);
        this.accessor = accessor;
    }

    @Override
    public Accessor<T> getAccessor() {
        return accessor;
    }

}