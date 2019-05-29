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

package org.panda_lang.panda.framework.design.architecture;

import org.panda_lang.panda.framework.PandaFramework;
import org.panda_lang.panda.framework.design.architecture.statement.Statement;
import org.panda_lang.panda.framework.design.runtime.ExecutableProcess;
import org.panda_lang.panda.framework.language.architecture.dynamic.block.main.MainScope;
import org.panda_lang.panda.framework.language.runtime.PandaExecutableProcess;
import org.panda_lang.panda.utilities.commons.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PandaApplication implements Application {

    protected final List<Script> scripts;
    protected String workingDirectory;

    public PandaApplication() {
        this.scripts = new ArrayList<>();
    }

    @Override
    public void launch(String... args) {
        List<MainScope> mains = scripts.stream()
                .map(script -> script.select(MainScope.class))
                .flatMap(List::stream)
                .collect(Collectors.toList());

        if (mains.isEmpty()) {
            List<Statement> statements = scripts.stream()
                    .flatMap(script -> script.getStatements().stream())
                    .collect(Collectors.toList());

            PandaFramework.getLogger().debug("Size: " + statements.size());
            statements.forEach(statement -> PandaFramework.getLogger().debug("statement: " + statement.toString()));

            throw new RuntimeException("Main statement not found");
        }

        if (mains.size() > 1) {
            throw new RuntimeException("Duplicated main statement");
        }

        PandaFramework.getLogger().debug("[PandaApp] Launching application...");
        ExecutableProcess process = new PandaExecutableProcess(this, mains.get(0), args);

        long initTime = System.nanoTime();
        process.execute();

        long uptime = System.nanoTime() - initTime;
        PandaFramework.getLogger().debug("[PandaApp] Done (" + TimeUtils.toMilliseconds(uptime) + ")");
    }

    public void addScript(Script script) {
        scripts.add(script);
    }

    public void setWorkingDirectory(String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    @Override
    public List<? extends Script> getScripts() {
        return scripts;
    }

    @Override
    public String getWorkingDirectory() {
        return workingDirectory;
    }


}
