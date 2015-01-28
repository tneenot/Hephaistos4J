package org.hlib4j.io;
/*
*  Hephaistos 4 Java library: a library with facilities to get more concise code.
*  
*  Copyright (C) 2015 Tioben Neenot
*  
*  This program is free software; you can redistribute it and/or modify it under
*  the terms of the GNU General Public License as published by the Free Software
*  Foundation; either version 2 of the License, or (at your option) any later
*  version.
*  
*  This program is distributed in the hope that it will be useful, but WITHOUT
*  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
*  FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
*  details.
*  
*  You should have received a copy of the GNU General Public License along with
*  this program; if not, write to the Free Software Foundation, Inc., 51
*  Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
*  
*/

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;

/**
 * This class represents a workspace in which all file creates or attach to this workspace will be stored. Thanks to the
 * workspace concept, it's possible to create a private workspace during a session, for example, in which all
 * temporaries files will be saved.
 *
 * @author Tioben Neenot &lt;tioben.neenot@laposte.net&gt;
 */
public class Workspace extends File {
    /**
     * Builds an instance of the <code>Workspace</code> for the specified path.
     *
     * @param path Path for the workspace.
     */
    public Workspace(String path) {
        super(path);
    }

    /**
     * Builds an instance of <code>Workspace</code> from a parent pathname string and a child pathname child.
     *
     * @param parent The parent pathname string
     * @param child  The child pathname string
     * @see java.io.File
     */
    public Workspace(String parent, String child) {
        super(parent, child);
    }

    /**
     * Builds an instance of <code>Workspace</code> with a {@link java.io.File} instance by converting the given file: URI into an abstract pathname.
     *
     * @param uri The URI file.
     * @see java.io.File
     */
    public Workspace(URI uri) {
        super(uri);
    }

    /**
     * Builds an instance of <code>Workspace</code> from a parent file abstract pathname and a child pathname string.
     *
     * @param parent The parent abstract pathname
     * @param child  The child pathname string
     * @see java.io.File
     */
    public Workspace(File parent, String child) {
        super(parent, child);
    }

    /**
     * This method is overriding from the <code>File</code> parent class and always return an exception. It's impossible for a Workspace to be only a file. Only directory creating is available by calling {@link #mkdirs()}
     *
     * @return Nothing
     * @throws IOException Always throw an IOException, since a Workspace is only a directory and not a file.
     */
    @Override
    public boolean createNewFile() throws IOException {
        throw new IOException("Workspace can't be created as a file, but only as directory");
    }

    /**
     * Adds a file into the workspace according to its path. If path is given as full absolute path, it will be always
     * stored into the workspace name. The file adds into the workspace is not created by default.
     *
     * @param path WorkspaceUnitFile's path.
     * @return The file into the workspace.
     * @throws IOException If file exists into the workspace yet.
     */
    public File addFileByStringPath(String path) throws IOException {
        File _inner_file = new File(getAbsolutePath(), path);
        if(!_inner_file.exists())
        {
            return new WorkspaceUnitFile(this, path);
        }

        throw new IOException("File exists yet");
    }

    /**
     * Adds a file according to its URI representation.
     *
     * @param uri The URI file
     * @return The file added into the workspace. It's URI will differ from the original URI, according to the fact the
     * root URI will be removed from the original URI file.
     * @throws IOException If file exists into the workspace yet.
     */
    public File addFileByURI(URI uri) throws IOException {
        File _workspace_file = new WorkspaceUnitFile(this, uri);
        return addFileByStringPath(_workspace_file.toString());
    }

    /**
     * Attach a file to the workspace. If file exists yet or had been created, an <code>IOException</code> will throw.
     *
     * @param file File to attach to the workspace.
     * @return The attached file.
     * @throws IOException If file exists into the workspace or had been created outside the workspace.
     */
    public File attachFile(File file) throws IOException {
        if (!file.exists()) {
            return addFileByStringPath(file.getPath());
        }

        throw new IOException("File exists or created yet");
    }

    /**
     * Gets the file that is corresponding to the given path.
     *
     * @param path WorkspaceUnitFile's path
     * @return WorkspaceUnitFile that is corresponding to the given path, or <code>null</code> if not found.
     */
    public File getFileByStringPath(String path) {
        File _inner_file = new File(getAbsolutePath(), path);
        return _inner_file.exists() ? new File(path) : null;
    }

    /**
     * A class that's representing a controlled file that is locking into a specific workspace.
     *
     * @author Tioben Neenot
     */
    private class WorkspaceUnitFile extends File {

        /**
         * Component serial ID
         */
        private static final long serialVersionUID = -3621417823396419262L;

        /**
         * Workspace associated for this file.
         */
        private final Workspace workspace;

        /**
         * Creates a new <code>WorkspaceUnitFile</code> instance by converting the given <code>pathname</code> string into an
         * abstract pathname. The rules applied to this constructor are the same as {@link File}. Furthermore, according to
         * {@link Workspace} concepts, if this abstract pathname contains the full pathname of the workspace, the pathname
         * will be considered as relative inside the {@link Workspace} pathname.
         *
         * @param workspace workspace links to this file
         * @param pathname  A pathname string
         * @throws NullPointerException If the pathname or workspace argument is <code>null</code>
         * @see java.io.File
         */
        WorkspaceUnitFile(Workspace workspace, String pathname) {
            super(workspace.toString(), pathname);
            this.workspace = workspace;
        }

        /**
         * Creates a new File instance by converting the given file: URI into an abstract pathname. Furthermore, according
         * to {@link Workspace} concepts, if this abstract pathname contains the full pathname of the workspace, the
         * pathname will be considered as relative inside the {@link Workspace} pathname.
         *
         * @param uri An absolute, hierarchical URI with a scheme equal to <code>"file"</code>, a non-empty path component,
         *            and undefined authority, query, and fragment components
         * @throws NullPointerException     If <code>URI</code> is <code>null</code>.
         * @throws IllegalArgumentException If the preconditions on the parameter do not hold
         * @see java.io.File
         */
        WorkspaceUnitFile(Workspace workspace, URI uri) {
            this(workspace, uri.toString());
        }

        /*
         * (non-Javadoc)
         *
         * @see java.io.File#toPath()
         */
        @Override
        public Path toPath() {
            return new File(this.toString()).toPath();
        }

        /*
         * (non-Javadoc)
         *
         * @see java.io.File#getParentFile()
         */
        @Override
        public File getParentFile() {
            File _parent_file = super.getParentFile();

            return null == _parent_file ? null : new WorkspaceUnitFile(this.workspace, _parent_file.getAbsolutePath());
        }

        /**
         * Returns the pathname string of this abstract pathname parent, or <code>null</code> if this pathname does not
         * name a parent directory.
         */
        @Override
        public String getParent() {
            // Limits to workspace root directory has upper level.
            String _parent = super.getParent();
            String _its_parent = _parent.replace(this.workspace.toString(), "");
            return "".equals(_its_parent) ? null : _its_parent;
        }

        /**
         * Returns the description for this WorkspaceUnitFile. This description doesn't show the root workspace into the full
         * workspace.
         *
         * @return The description for this WorkspaceUnitFile.
         */
        @Override
        public String toString() {
            return super.toString().replace(String.format("%1$1s%2$1s", this.workspace.toString(), File.separator),
                    File.separator);
        }

        /**
         * Returns the URI of this WorkspaceUnitFile.
         *
         * @return URI description of this WorkspaceUnitFile or <code>null</code> if the URI is invalid.
         */
        @Override
        public URI toURI() {
            try {
                return new URI(super.toURI().toString().replace(this.workspace.toURI().toString(), ""));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
