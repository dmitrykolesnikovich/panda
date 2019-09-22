/*
 * Copyright (c) 2015-2019 Dzikoysk
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

package org.panda_lang.framework.language.architecture.parameter;

import org.panda_lang.framework.design.architecture.prototype.PrototypeReference;
import org.panda_lang.framework.design.architecture.parameter.Parameter;
import org.panda_lang.framework.language.architecture.statement.PandaVariable;

public class PandaParameter extends PandaVariable implements Parameter {

    private final boolean varargs;

    public PandaParameter(int parameterIndex, PrototypeReference reference, String name, boolean varargs, boolean nillable) {
        super(parameterIndex, reference, name, false, nillable);
        this.varargs = varargs;
    }

    @Override
    public boolean isVarargs() {
        return varargs;
    }

}