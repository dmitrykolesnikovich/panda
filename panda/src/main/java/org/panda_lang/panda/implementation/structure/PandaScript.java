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

package org.panda_lang.panda.implementation.structure;

import org.panda_lang.framework.structure.Script;
import org.panda_lang.framework.structure.Statement;
import org.panda_lang.framework.structure.Wrapper;

import java.util.ArrayList;
import java.util.List;

public class PandaScript implements Script {

    private final String scriptName;
    private final List<Statement> statements;
    private final List<Wrapper> wrappers;

    public PandaScript(String scriptName) {
        this.scriptName = scriptName;
        this.statements = new ArrayList<>();
        this.wrappers = new ArrayList<>();
    }

    @Override
    public Wrapper select(Class<? extends Wrapper> clazz, String wrapperName) {
        for (Wrapper wrapper : wrappers) {
            if (!clazz.isInstance(wrapper)) {
                continue;
            }

            return wrapper;
        }

        return null;
    }

    public void addStatement(Statement statement) {
        this.statements.add(statement);

        if (statement instanceof Wrapper) {
            this.wrappers.add((Wrapper) statement);
        }
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public String getScriptName() {
        return scriptName;
    }

}
