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

package org.panda_lang.language.architecture.expression;

import org.panda_lang.language.architecture.type.signature.Signature;
import org.panda_lang.language.architecture.type.TypeContext;
import org.panda_lang.language.architecture.type.member.MemberFrame;
import org.panda_lang.language.interpreter.parser.Context;
import org.panda_lang.language.runtime.ProcessStack;
import org.panda_lang.utilities.commons.ObjectUtils;

public final class ThisExpression implements DynamicExpression {

    private final Signature signature;

    private ThisExpression(Signature signature) {
        this.signature = signature;
    }

    @Override
    public <T> T evaluate(ProcessStack stack, Object instance) {
        return ObjectUtils.cast(instance instanceof MemberFrame ? ((MemberFrame) instance).getInstance() : instance);
    }

    @Override
    public Signature getReturnType() {
        return signature;
    }

    public static Expression of(Signature signature) {
        return new PandaExpression(new ThisExpression(signature));
    }

    public static Expression of(Context<TypeContext> context) {
        return of(context.getSubject().getType().getSignature());
    }

    @SuppressWarnings("unchecked")
    public static Expression ofUnknownContext(Context<?> context) {
        if (context.getSubject() instanceof TypeContext) {
            return of((Context<TypeContext>) context);
        }

        throw new IllegalArgumentException("Context does not contain typed subject");
    }

}
