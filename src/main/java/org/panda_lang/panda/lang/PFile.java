package org.panda_lang.panda.lang;

import org.panda_lang.panda.core.scheme.ConstructorScheme;
import org.panda_lang.panda.core.scheme.MethodScheme;
import org.panda_lang.panda.core.scheme.ObjectScheme;
import org.panda_lang.panda.core.syntax.Constructor;
import org.panda_lang.panda.core.syntax.Executable;
import org.panda_lang.panda.core.syntax.Parameter;
import org.panda_lang.panda.util.IOUtils;

import java.io.File;

public class PFile extends PObject {

    static {
        // Register object
        ObjectScheme os = new ObjectScheme(PFile.class, "File");
        // Constructor
        os.registerConstructor(new ConstructorScheme(new Constructor<PFile>() {
            @Override
            public PFile run(Parameter... parameters) {
                return new PFile(parameters[0].getValue().toString());
            }
        }));
        // Method: create
        os.registerMethod(new MethodScheme("create", new Executable() {
            @Override
            public PObject run(Parameter instance, Parameter... parameters) {
                PFile f = instance.getValue(PFile.class);
                File file = f.getFile();
                if (!file.exists()) {

                }
                return null;
            }
        }));
        // Method: isDirectory
        os.registerMethod(new MethodScheme("isDirectory", new Executable() {
            @Override
            public PObject run(Parameter instance, Parameter... parameters) {
                PFile f = instance.getValue(PFile.class);
                return new PBoolean(f.getFile().isDirectory());
            }
        }));
        // Method: getContent
        os.registerMethod(new MethodScheme("getContent", new Executable() {
            @Override
            public PObject run(Parameter instance, Parameter... parameters) {
                PFile f = instance.getValue(PFile.class);
                return f.getContent();
            }
        }));
    }

    private final File file;

    public PFile(String s) {
        this.file = new File(s);
    }

    public File getFile() {
        return file;
    }

    public PString getContent() {
        return new PString(IOUtils.getContent(file));
    }

    @Override
    public String getType() {
        return "File";
    }

    @Override
    public String toString() {
        return file.getName();
    }

}