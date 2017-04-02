/*
 * Copyright (c) 2015-2017 Dzikoysk
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

package org.panda_lang.panda.implementation.interpreter.source;

import org.panda_lang.panda.framework.interpreter.source.CodeSource;
import org.panda_lang.panda.framework.util.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class PandaCodeSource implements CodeSource {

    private final URL location;

    private PandaCodeSource(URL location) {
        this.location = location;
    }

    @Override
    public String getContent() {
        try (InputStream inputStream = this.location.openStream()) {
            return IOUtils.convertStreamToString(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public URL getLocation() {
        return this.location;
    }

    public static PandaCodeSource fromFile(File file) {
        try {
            return fromUrl(file.toURI().toURL());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static PandaCodeSource fromUrl(URL url) {
        return new PandaCodeSource(url);
    }

}
