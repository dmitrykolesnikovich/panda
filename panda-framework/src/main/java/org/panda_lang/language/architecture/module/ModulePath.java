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

package org.panda_lang.language.architecture.module;

import org.panda_lang.language.architecture.Script;
import org.panda_lang.language.interpreter.source.SourceService;
import org.panda_lang.utilities.commons.function.Completable;
import org.panda_lang.utilities.commons.function.Option;

import java.util.function.Function;

/**
 * ModulePath is collection of all available modules
 */
public interface ModulePath extends ModuleContainer {

    /**
     * Include initializer for the specified module
     *
     * @param name name of module
     * @param loader module initializer
     */
    Completable<Option<Script>> include(String name, Function<SourceService, Completable<Script>> loader);

}
