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

package org.panda_lang.framework.language.architecture.module;

import org.panda_lang.framework.design.architecture.module.Imports;
import org.panda_lang.framework.design.architecture.prototype.PrototypeReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public final class PandaImports implements Imports {

    private final Map<String, Supplier<PrototypeReference>> importedReferences = new HashMap<>();

    @Override
    public boolean importReference(String name, Supplier<PrototypeReference> supplier) {
        if (importedReferences.containsKey(name)) {
            return false;
        }

        importedReferences.put(name, supplier);
        return true;
    }

    @Override
    public Optional<PrototypeReference> forName(String name) {
        return Optional.ofNullable(importedReferences.get(name)).map(Supplier::get);
    }

}