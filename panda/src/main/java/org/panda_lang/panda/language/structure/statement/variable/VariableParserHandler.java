/*
 * Copyright (c) 2015-2018 Dzikoysk
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

package org.panda_lang.panda.language.structure.statement.variable;

import org.panda_lang.panda.framework.language.interpreter.parser.pipeline.ParserHandler;
import org.panda_lang.panda.framework.language.interpreter.token.TokenizedSource;
import org.panda_lang.panda.framework.language.interpreter.token.extractor.Extractor;
import org.panda_lang.panda.framework.language.interpreter.token.reader.TokenReader;

import java.util.List;

public class VariableParserHandler implements ParserHandler {

    @Override
    public boolean handle(TokenReader reader) {
        Extractor extractor = VariableParser.PATTERN.extractor();
        List<TokenizedSource> hollows = extractor.extract(reader);
        return hollows != null && hollows.size() > 0;
    }

}
