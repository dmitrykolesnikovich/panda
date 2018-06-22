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

package org.panda_lang.panda.utilities.commons.objects;

import org.junit.jupiter.api.*;

public class StringUtilsTest {

    @Test
    public void testIsEmpty() {
        Assertions.assertAll(
                () -> Assertions.assertTrue(StringUtils.isEmpty(null)),
                () -> Assertions.assertTrue(StringUtils.isEmpty("")),
                () -> Assertions.assertTrue(StringUtils.isEmpty("  ")),
                () -> Assertions.assertFalse(StringUtils.isEmpty(" test "))
        );
    }

    @Test
    public void testReplace() {
        Assertions.assertEquals("Test", StringUtils.replace("TESTS", "ESTS", "est"));
    }

    @Test
    public void testCapitalize() {
        Assertions.assertEquals("Test", StringUtils.capitalize("test"));
    }

    @Test
    public void testTrimEnd() {
        Assertions.assertAll(
                // Without changes
                () -> Assertions.assertEquals("test", StringUtils.trimEnd("test")),
                () -> Assertions.assertEquals("  test", StringUtils.trimEnd("  test")),
                () -> Assertions.assertEquals("", StringUtils.trimEnd("")),

                // With changes
                () -> Assertions.assertEquals("", StringUtils.trimEnd("  ")),
                () -> Assertions.assertEquals("test", StringUtils.trimEnd("test  "))
        );
    }

    @Test
    public void testExtractParagraph() {
        Assertions.assertEquals("  ", StringUtils.extractParagraph("  test"));
    }

    @Test
    public void testCountOccurrences() {
        Assertions.assertEquals(2, StringUtils.countOccurrences(" test test ", "test"));
    }

}