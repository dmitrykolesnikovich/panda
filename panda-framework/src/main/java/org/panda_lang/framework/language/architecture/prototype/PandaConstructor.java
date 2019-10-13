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

package org.panda_lang.framework.language.architecture.prototype;

import org.panda_lang.framework.design.architecture.dynamic.Frame;
import org.panda_lang.framework.design.architecture.parameter.Parameter;
import org.panda_lang.framework.design.architecture.prototype.Reference;
import org.panda_lang.framework.design.architecture.prototype.PrototypeConstructor;
import org.panda_lang.framework.design.interpreter.source.SourceLocation;
import org.panda_lang.framework.design.runtime.ProcessStack;
import org.panda_lang.framework.language.architecture.statement.AbstractPropertyFramedScope;
import org.panda_lang.framework.language.architecture.statement.PandaPropertyFrame;

import java.lang.reflect.Constructor;
import java.util.List;

public class PandaConstructor extends PandaExecutableProperty implements PrototypeConstructor {

    private PandaConstructor(PandaConstructorBuilder builder) {
        super(builder);
    }

    @Override
    public String toString() {
        return getName();
    }

    public static PandaConstructorBuilder builder() {
        return new PandaConstructorBuilder();
    }

    public static class PandaConstructorBuilder extends PandaParametrizedExecutableBuilder<PandaConstructorBuilder> {

        private PandaConstructorBuilder() { }

        public PandaConstructorBuilder constructor(Reference reference, Constructor<?> constructor) {
            return type(reference).name("constructor " + reference.getName());
        }

        public PandaConstructor build() {
            return new PandaConstructor(this);
        }

    }

    public static class PandaConstructorScope extends AbstractPropertyFramedScope {

        public PandaConstructorScope(SourceLocation location, List<Parameter> parameters) {
            super(location, parameters);
        }

        @Override
        public ConstructorFrame revive(ProcessStack stack, Object instance) {
            return new ConstructorFrame(this, (Frame) instance);
        }

        public List<Parameter> getParameters() {
            return parameters;
        }

    }

    public static class ConstructorFrame extends PandaPropertyFrame<PandaConstructorScope> {

        public ConstructorFrame(PandaConstructorScope scope, Frame instance) {
            super(scope, instance);
        }

    }

}
