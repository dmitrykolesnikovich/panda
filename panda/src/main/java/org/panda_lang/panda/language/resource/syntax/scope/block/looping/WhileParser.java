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

package org.panda_lang.panda.language.resource.syntax.scope.block.looping;

import org.panda_lang.language.architecture.expression.Expression;
import org.panda_lang.language.interpreter.parser.Context;
import org.panda_lang.language.interpreter.parser.PandaParserFailure;
import org.panda_lang.language.interpreter.parser.pool.Targets;
import org.panda_lang.language.resource.syntax.keyword.Keywords;
import org.panda_lang.panda.language.interpreter.parser.PandaSourceReader;
import org.panda_lang.panda.language.resource.syntax.scope.block.BlockParser;
import org.panda_lang.utilities.commons.ArrayUtils;
import org.panda_lang.language.interpreter.parser.Component;
import org.panda_lang.utilities.commons.function.Completable;
import org.panda_lang.utilities.commons.function.Option;

public final class WhileParser extends BlockParser<WhileBlock> {

    @Override
    public String name() {
        return "while";
    }

    @Override
    public Component<?>[] targets() {
        return ArrayUtils.of(Targets.SCOPE);
    }

    @Override
    public Option<Completable<WhileBlock>> parse(Context<?> context) {
        PandaSourceReader sourceReader = new PandaSourceReader(context.getStream());

        if (sourceReader.read(Keywords.WHILE).isEmpty()) {
            return Option.none();
        }

        Expression whileCondition = context.getExpressionParser().parse(context, context.getStream());

        if (!whileCondition.getKnownType().is("panda::Bool")) {
            throw new PandaParserFailure(context, context.getSource(), "Loop requires boolean as an argument");
        }

        WhileBlock whileBlock = new WhileBlock(context.getScope(), context.getSource().getLocation(), whileCondition);
        context.getScope().addStatement(whileBlock);
        SCOPE_PARSER.parse(context, whileBlock, sourceReader.readBody().get());

        return Option.ofCompleted(whileBlock);
    }

}
