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

package org.panda_lang.panda.examples;

import org.junit.jupiter.api.Assertions;
import org.panda_lang.panda.Panda;
import org.panda_lang.panda.PandaFactory;
import org.panda_lang.panda.bootstrap.PandaApplicationBootstrap;
import org.panda_lang.panda.framework.PandaFrameworkLoggerUtils;
import org.panda_lang.panda.framework.design.architecture.Application;

import java.util.Optional;

public final class Launcher {

    public static void launch(String directory, String file) {
        Assertions.assertDoesNotThrow(() -> interpret(directory, file).launch());
    }

    public static Application interpret(String directory, String file) {
        PandaFrameworkLoggerUtils.printJVMUptime();

        PandaFactory factory = new PandaFactory();
        Panda panda = factory.createPanda();

        Optional<Application> application = new PandaApplicationBootstrap(panda)
                .workingDirectory("../examples/" + directory)
                .main(file)
                .createApplication();

        Assertions.assertTrue(application.isPresent());
        return application.get();
    }

}