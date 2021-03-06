package org.panda_lang.panda.examples.lang

import groovy.transform.CompileStatic
import org.junit.jupiter.api.Test

@CompileStatic
class BasicTypesTest extends LangTestSpecification {

    @Test
    void 'should compile and execute basic types' () {
        launch 'basic_types.panda'
    }

}
