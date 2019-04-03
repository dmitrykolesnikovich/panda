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

package org.panda_lang.panda.framework.language.resource.parsers.expression.xxx;

import org.jetbrains.annotations.Nullable;
import org.panda_lang.panda.framework.design.architecture.prototype.ClassPrototypeReference;
import org.panda_lang.panda.framework.design.interpreter.parser.ParserData;
import org.panda_lang.panda.framework.design.interpreter.parser.component.UniversalComponents;
import org.panda_lang.panda.framework.design.interpreter.pattern.PandaTokenPattern;
import org.panda_lang.panda.framework.design.interpreter.pattern.token.TokenPattern;
import org.panda_lang.panda.framework.design.interpreter.pattern.token.extractor.ExtractorResult;
import org.panda_lang.panda.framework.design.interpreter.token.snippet.Snippet;
import org.panda_lang.panda.framework.design.interpreter.token.stream.SourceStream;
import org.panda_lang.panda.framework.design.resource.parsers.expression.xxx.ExpressionParserOld;
import org.panda_lang.panda.framework.design.resource.parsers.expression.xxx.ExpressionSubparser;
import org.panda_lang.panda.framework.design.runtime.expression.Expression;
import org.panda_lang.panda.framework.language.architecture.prototype.array.ArrayClassPrototype;
import org.panda_lang.panda.framework.language.architecture.prototype.array.ArrayClassPrototypeUtils;
import org.panda_lang.panda.framework.language.interpreter.token.stream.PandaSourceStream;
import org.panda_lang.panda.framework.language.resource.PandaTypes;
import org.panda_lang.panda.framework.language.resource.parsers.expression.xxx.callbacks.ArrayInstanceExpression;
import org.panda_lang.panda.framework.language.resource.syntax.keyword.Keywords;
import org.panda_lang.panda.framework.language.runtime.expression.PandaExpression;

import java.util.Optional;

public class ArrayInstanceExpressionSubparser implements ExpressionSubparser {

    private TokenPattern pattern;

    @Override
    public void initialize(ParserData data) {
        this.pattern = PandaTokenPattern.builder()
                .compile("new <type:reader type> `[ <*capacity> `]")
                .build(data);
    }

    @Override
    public @Nullable Snippet read(ExpressionParserOld parent, Snippet source) {
        if (!source.getFirst().contentEquals(Keywords.NEW)) {
            return null;
        }

        SourceStream stream = new PandaSourceStream(source);
        ExtractorResult result = pattern.extract(stream);

        if (!result.isMatched()) {
            return null;
        }

        return source.subSource(0, source.size() - stream.getUnreadLength());
    }

    @Override
    public @Nullable Expression parse(ExpressionParserOld parent, ParserData data, Snippet source) {
        ExtractorResult result = pattern.extract(source);

        if (!result.isMatched()) {
            return null;
        }

        String type = result.getWildcard("type").asString();
        Optional<ClassPrototypeReference> reference = ArrayClassPrototypeUtils.obtain(data.getComponent(UniversalComponents.MODULE_LOADER), type + "[]");

        if (!reference.isPresent()) {
            return null;
        }

        Snippet capacity = result.getWildcard("*capacity");
        Expression capacityExpression = parent.parse(data, capacity);

        if (!PandaTypes.INT.isAssignableFrom(capacityExpression.getReturnType())) {
            return null;
        }

        return new PandaExpression(new ArrayInstanceExpression((ArrayClassPrototype) reference.get().fetch(), capacityExpression));
    }

    @Override
    public int getMinimumLength() {
        return 5;
    }

    @Override
    public double getPriority() {
        return DefaultSubparsers.Priorities.Dynamic.CONSTRUCTOR_CALL;
    }

    @Override
    public String getName() {
        return DefaultSubparsers.Names.ARRAY_INSTANCE;
    }



}
