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

package org.panda_lang.panda.framework.language.interpreter.lexer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.panda_lang.panda.Panda;
import org.panda_lang.panda.PandaFactory;
import org.panda_lang.panda.framework.design.interpreter.source.Source;
import org.panda_lang.panda.framework.design.interpreter.token.snippet.Snippet;
import org.panda_lang.panda.framework.language.interpreter.source.PandaSource;

import java.io.File;

class FileBasedLexerTest {

    private static final File SOURCE_FILE = new File("examples/hello_world.panda");
    private static final Source SOURCE = new PandaSource(FileBasedLexerTest.class, "a('z').b.c('y').d('x');");

    @Test
    public void testLexer() {
        PandaFactory pandaFactory = new PandaFactory();
        Panda panda = pandaFactory.createPanda();

        Snippet snippet = PandaLexer.of(panda.getPandaLanguage().getSyntax())
                .build()
                .convert(SOURCE);

        Assertions.assertEquals(17, snippet.size());
        Assertions.assertEquals("a", snippet.getFirst().getValue());
        Assertions.assertEquals(";", snippet.getLast().getValue());
    }

}
