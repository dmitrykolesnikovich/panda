/*
 * Copyright (c) 2015-2017 Dzikoysk
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

package org.panda_lang.panda.language.structure.prototype.structure.constructor;

import org.panda_lang.panda.framework.interpreter.lexer.token.TokenType;
import org.panda_lang.panda.framework.interpreter.lexer.token.TokenizedSource;
import org.panda_lang.panda.framework.interpreter.parser.ParserInfo;
import org.panda_lang.panda.framework.interpreter.parser.UnifiedParser;
import org.panda_lang.panda.framework.interpreter.parser.generation.ParserGeneration;
import org.panda_lang.panda.framework.interpreter.parser.generation.ParserGenerationCallback;
import org.panda_lang.panda.framework.interpreter.parser.generation.ParserGenerationLayer;
import org.panda_lang.panda.framework.interpreter.parser.generation.ParserGenerationType;
import org.panda_lang.panda.framework.interpreter.parser.generation.util.LocalCallback;
import org.panda_lang.panda.implementation.interpreter.lexer.token.pattern.TokenHollowRedactor;
import org.panda_lang.panda.implementation.interpreter.lexer.token.pattern.TokenPattern;
import org.panda_lang.panda.implementation.interpreter.lexer.token.pattern.TokenPatternHollows;
import org.panda_lang.panda.implementation.interpreter.lexer.token.pattern.TokenPatternUtils;
import org.panda_lang.panda.implementation.interpreter.parser.defaults.ScopeParser;
import org.panda_lang.panda.implementation.interpreter.parser.linker.PandaScopeLinker;
import org.panda_lang.panda.implementation.interpreter.parser.linker.ScopeLinker;
import org.panda_lang.panda.implementation.interpreter.parser.pipeline.DefaultPipelines;
import org.panda_lang.panda.implementation.interpreter.parser.pipeline.registry.ParserRegistration;
import org.panda_lang.panda.implementation.interpreter.parser.util.Components;
import org.panda_lang.panda.language.structure.prototype.ClassPrototype;
import org.panda_lang.panda.language.structure.prototype.ClassScope;
import org.panda_lang.panda.language.structure.prototype.structure.constructor.variant.PandaConstructor;
import org.panda_lang.panda.language.structure.prototype.structure.parameter.Parameter;
import org.panda_lang.panda.language.structure.prototype.structure.parameter.ParameterParser;
import org.panda_lang.panda.language.structure.prototype.structure.parameter.ParameterUtils;

import java.util.List;

@ParserRegistration(target = DefaultPipelines.PROTOTYPE, parserClass = ConstructorParser.class, handlerClass = ConstructorParserHandler.class)
public class ConstructorParser implements UnifiedParser {

    protected static final TokenPattern PATTERN = TokenPattern.builder()
            .unit(TokenType.KEYWORD, "constructor")
            .unit(TokenType.SEPARATOR, "(")
            .hollow()
            .unit(TokenType.SEPARATOR, ")")
            .unit(TokenType.SEPARATOR, "{")
            .hollow()
            .unit(TokenType.SEPARATOR, "}")
            .build();

    @Override
    public void parse(ParserInfo parserInfo) {
        ParserGeneration generation = parserInfo.getComponent(Components.GENERATION);

        generation.getLayer(ParserGenerationType.HIGHER)
                .delegateImmediately(new ConstructorExtractorCallback(), parserInfo.fork());
    }

    @LocalCallback
    private static class ConstructorExtractorCallback implements ParserGenerationCallback {

        @Override
        public void call(ParserInfo delegatedInfo, ParserGenerationLayer nextLayer) {
            TokenPatternHollows hollows = TokenPatternUtils.extract(PATTERN, delegatedInfo);
            TokenHollowRedactor redactor = new TokenHollowRedactor(hollows);

            redactor.map("parameters", "constructor-body");
            delegatedInfo.setComponent("redactor", redactor);

            TokenizedSource parametersSource = redactor.get("parameters");
            ParameterParser parameterParser = new ParameterParser();
            List<Parameter> parameters = parameterParser.parse(delegatedInfo, parametersSource);

            ConstructorScope constructorScope = new ConstructorScope(parameters);
            ParameterUtils.addAll(constructorScope.getVariables(), parameters, 0);
            delegatedInfo.setComponent("constructor-scope", constructorScope);

            ClassPrototype prototype = delegatedInfo.getComponent("class-prototype");
            ClassScope classScope = delegatedInfo.getComponent("class-scope");

            Constructor constructor = new PandaConstructor(prototype, classScope, constructorScope);
            delegatedInfo.setComponent("constructor", constructor);
            prototype.getConstructors().add(constructor);

            nextLayer.delegate(new ConstructorBodyCallback(), delegatedInfo);
        }

    }

    @LocalCallback
    private static class ConstructorBodyCallback implements ParserGenerationCallback {

        @Override
        public void call(ParserInfo delegatedInfo, ParserGenerationLayer nextLayer) {
            ClassScope classScope = delegatedInfo.getComponent("class-scope");

            ConstructorScope constructorScope = delegatedInfo.getComponent("constructor-scope");
            delegatedInfo.setComponent("scope", constructorScope);

            ScopeLinker linker = new PandaScopeLinker(classScope);
            linker.pushScope(constructorScope);
            delegatedInfo.setComponent(Components.LINKER, linker);

            TokenHollowRedactor redactor = delegatedInfo.getComponent("redactor");
            TokenizedSource body = redactor.get("constructor-body");

            ScopeParser scopeParser = new ScopeParser(constructorScope);
            scopeParser.parse(delegatedInfo, body);
        }

    }

}
