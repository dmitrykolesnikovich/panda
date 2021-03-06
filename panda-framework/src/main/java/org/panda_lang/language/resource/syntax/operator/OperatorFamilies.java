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

package org.panda_lang.language.resource.syntax.operator;

import org.panda_lang.utilities.commons.StringUtils;

/**
 * Families used by {@link org.panda_lang.language.resource.syntax.operator.Operators}
 */
public final class OperatorFamilies {

    /**
     * Family used by math operators
     */
    public static final String MATH = "math";

    /**
     * Family used by bitwise operators
     */
    public static final String BITWISE = "bitwise";

    /**
     * Family used by logical operators
     */
    public static final String LOGICAL = "logical";

    /**
     * Family used by crease operators
     */
    public static final String CREASE = "crease";

    /**
     * Family used by assignation operators
     */
    public static final String ASSIGNATION = "assignation";

    /**
     * Family used by other operators
     */
    public static final String UNDEFINED = StringUtils.EMPTY;

}
