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

package org.panda_lang.language.interpreter.parser.expression;

import org.panda_lang.language.architecture.expression.Expression;
import org.panda_lang.language.interpreter.parser.Contextual;
import org.panda_lang.language.interpreter.token.Snippetable;
import org.panda_lang.language.interpreter.token.SynchronizedSource;

import java.util.Stack;

/**
 * Context of expression parser
 */
public interface ExpressionContext<T> extends Contextual<T>, Snippetable {

    /**
     * Remove the latest expression from stack
     *
     * @return removed expression
     */
    Expression popExpression();

    /**
     * Check the latest expression on stack
     *
     * @return the latest expression
     */
    Expression peekExpression();

    /**
     * Check if context contains any expressions on stack
     *
     * @return true if stack contains some expressions
     */
    boolean hasResults();

    /**
     * Get synchronized source used by expression parser
     *
     * @return the synchronized source
     */
    SynchronizedSource getSynchronizedSource();

    /**
     * Get errors
     *
     * @return the stack containing latest errors
     */
    Stack<ExpressionResult> getErrors();

    /**
     * Get results
     *
     * @return the stack containing results
     */
    Stack<Expression> getResults();

    /**
     * Get current expression parser
     *
     * @return the expression parser instance
     */
    ExpressionParser getParser();

}
