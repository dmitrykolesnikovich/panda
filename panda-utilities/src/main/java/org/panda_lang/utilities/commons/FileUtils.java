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

package org.panda_lang.utilities.commons;

import org.panda_lang.utilities.commons.collection.Node;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public final class FileUtils {

    private FileUtils() { }

    /**
     * Check if a file is in the specified array
     *
     * @param files    files
     * @param fileName name of file
     * @return true if file is in specified array
     */
    public static boolean contains(File[] files, String fileName) {
        return Arrays.stream(files).anyMatch(file -> file.getName().equals(fileName));
    }

    /**
     * Count files in the specified directory
     *
     * @param directory root directory
     * @return amount of files
     */
    public static int getAmountOfFiles(File directory) {
        return directory.isDirectory() ? Objects.requireNonNull(directory.listFiles()).length : 0;
    }

    /**
     * @return content of the specified file
     */
    public static String getContentOfFile(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
    }

    /**
     * @return content of file divided by lines
     */
    public static String[] getContentAsLines(File file) throws IOException {
        List<String> list = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        return list.toArray(StringUtils.EMPTY_ARRAY);
    }

    /**
     * @return collection of file with the specified extension
     */
    public static Collection<File> findFilesByExtension(String directory, String extension) {
        return findFilesByExtension(new File(directory), extension);
    }

    /**
     * @return collection of file with the specified extension
     */
    public static Collection<File> findFilesByExtension(File directory, String extension) {
        Collection<File> files = new ArrayList<>();
        findFilesByExtension(directory, extension, files);
        return files;
    }

    private static void findFilesByExtension(File directory, String extension, Collection<File> files) {
        if (!directory.isDirectory()) {
            return;
        }

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isDirectory()) {
                findFilesByExtension(file, extension, files);
                continue;
            }

            if (!file.getName().endsWith(extension)) {
                continue;
            }

            files.add(file);
        }
    }

    /**
     * Collect all files (including files from subdirectories) from the given directory
     *
     * @param directory root
     * @return tree node of collected files
     */
    public static Node<File> collectFiles(File directory) {
        Node<File> tree = new Node<>(directory);

        if (!directory.isDirectory()) {
            return tree;
        }

        File[] files = directory.listFiles();

        if (files == null) {
            return tree;
        }

        for (File file : files) {
            tree.add(file.isDirectory() ? collectFiles(file) : new Node<>(file));
        }

        return tree;
    }

    /**
     * Override content of the specified file
     */
    public static void overrideFile(File file, String content) throws IOException {
        Files.write(file.toPath(), content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    /**
     * Delete file or directory with content
     *
     * @param file file/directory
     * @return true if succeeded
     */
    public static boolean delete(File file) {
        if (!file.exists()) {
            return true;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();

            if (files != null) {
                for (File c : files) {
                    delete(c);
                }
            }
        }

        return file.delete();
    }

    /**
     * Get vararg paths as array of {@link File}
     *
     * @param paths array of paths
     * @return array of files
     */
    public static File[] toFiles(String... paths) {
        return Arrays.stream(paths)
                .map(File::new)
                .toArray(File[]::new);
    }

    /**
     * Get file name without extension
     *
     * @return file name without extension
     */
    public static String getName(File file) {
        String name = file.getName();
        int pos = name.lastIndexOf(".");
        return pos == -1 ? name : name.substring(0, pos);
    }

    /**
     * Get file name of URL
     *
     * @param url the url to check
     * @return the name of file
     */
    public static String getName(URL url) {
        String path = url.toString();
        return path.substring(path.lastIndexOf('/') + 1);
    }

    /**
     * Get file name of path
     *
     * @param path the path to check
     * @return the name of file
     */
    public static String getName(String path) {
        return path.substring(path.lastIndexOf('/') + 1);
    }

}
