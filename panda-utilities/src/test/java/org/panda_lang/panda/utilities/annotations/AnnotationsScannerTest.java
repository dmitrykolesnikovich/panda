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

package org.panda_lang.panda.utilities.annotations;

import org.junit.jupiter.api.Test;

import java.util.Set;

public class AnnotationsScannerTest {

    @Test
    void testScanner() {
        AnnotationsScanner scanner = AnnotationsScanner.builder()
                .includeClassLoaders(this.getClass().getClassLoader())
                .build();

        Set<Class<?>> tests = scanner.createWorker()
                .addOfflineFilter(className -> className.endsWith("AnnotationsScannerTest"))
                .build()
                .scan();

        System.out.println(tests);
    }

}