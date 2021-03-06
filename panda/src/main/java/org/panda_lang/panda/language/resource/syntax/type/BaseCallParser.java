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

package org.panda_lang.panda.language.resource.syntax.type;

import org.panda_lang.language.architecture.expression.Expression;
import org.panda_lang.language.architecture.statement.Scope;
import org.panda_lang.language.architecture.type.Type;
import org.panda_lang.language.architecture.type.TypeContext;
import org.panda_lang.language.architecture.type.member.constructor.BaseCall;
import org.panda_lang.language.architecture.type.member.constructor.ConstructorScope;
import org.panda_lang.language.architecture.type.member.constructor.TypeConstructor;
import org.panda_lang.language.interpreter.parser.Context;
import org.panda_lang.language.interpreter.parser.ContextParser;
import org.panda_lang.language.interpreter.parser.PandaParserFailure;
import org.panda_lang.language.interpreter.parser.pool.Targets;
import org.panda_lang.language.interpreter.token.Snippet;
import org.panda_lang.language.resource.syntax.keyword.Keywords;
import org.panda_lang.panda.language.interpreter.parser.PandaSourceReader;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.ArgumentsParser;
import org.panda_lang.utilities.commons.ArrayUtils;
import org.panda_lang.language.interpreter.parser.Component;
import org.panda_lang.utilities.commons.function.Completable;
import org.panda_lang.utilities.commons.function.Option;

import java.util.List;

public final class BaseCallParser implements ContextParser<TypeContext, BaseCall> {

    private static final ArgumentsParser ARGUMENTS_PARSER = new ArgumentsParser();

    @Override
    public String name() {
        return "base";
    }

    @Override
    public Component<?>[] targets() {
        return ArrayUtils.of(Targets.SCOPE);
    }

    @Override
    public Option<Completable<BaseCall>> parse(Context<? extends TypeContext> context) {
        PandaSourceReader sourceReader = new PandaSourceReader(context.getStream());

        if (sourceReader.read(Keywords.BASE).isEmpty()) {
            return Option.none();
        }

        Type type = context.getSubject().getType();
        Scope parent = context.getScope();

        if (!(parent instanceof ConstructorScope)) {
            throw new PandaParserFailure(context, context.getSource(), "Cannot use base constructor outside of the constructor");
        }

        if (!parent.getStatements().isEmpty()) {
            throw new PandaParserFailure(context, context.getSource(), "Base constructor has to be the first statement in the scope");
        }

        if (type.getSuperclass().isEmpty()) {
            throw new PandaParserFailure(context, context.getSource(), "Cannot use base call without superclass");
        }

        Option<Snippet> arguments = sourceReader.readArguments();

        if (arguments.isEmpty()) {
            throw new PandaParserFailure(context, context.getSource(), "Missing base arguments");
        }

        List<Expression> expressions = ARGUMENTS_PARSER.parse(context, arguments.get());
        Option<TypeConstructor> constructor = type.getSuperclass().get().fetchType().getConstructors().getConstructor(expressions);

        constructor.onEmpty(() -> {
            throw new PandaParserFailure(context, context.getSource(), "Base type does not contain constructor with the given parameters");
        });

        BaseCall baseCall = new BaseCall(context.toLocation(), constructor.get(), expressions);
        parent.addStatement(baseCall);

        return Option.ofCompleted(baseCall);
    }

}