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

package org.panda_lang.panda.framework.language.resource.parsers.container.assignation;

import org.panda_lang.panda.framework.design.architecture.statement.Scope;
import org.panda_lang.panda.framework.design.interpreter.parser.component.Component;
import org.panda_lang.panda.framework.design.runtime.expression.Expression;

public class AssignationComponents {

    public static final String SCOPE_LABEL = "assignation-scope";
    public static final Component<Scope> SCOPE = Component.of(SCOPE_LABEL, Scope.class);

    public static final String EXPRESSION_LABEL = "assignation-expression";
    public static final Component<Expression> EXPRESSION = Component.of(EXPRESSION_LABEL, Expression.class);

}