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
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a workspace in which all file creates or attach to this workspace will be stored. Thanks to the
 * workspace concept, it's possible to create a private workspace during a session, for example, in which all
 * temporaries files will be saved.
 *
 * @author Tioben Neenot &lt;tioben.neenot@laposte.net&gt;
 */
public class Workspace
{

	/**
	 * Path associated with this workspace
	 */
	private final File root;

	/**
	 * List of files of the workspace
	 */
	private List<File> files;

	/**
	 * Builds an instance of the {@link Workspace} for the specified path.
	 *
	 * @param path
	 *          Path for the workspace.
	 */
	public Workspace(String path)
	{
		this.files = new ArrayList<>();
		this.root = new File(path);
	}

	/**
	 * Return the representation of the Workspace
	 *
	 * @return Representation of the Workspace
	 */
	@Override
	public String toString()
	{
		return this.root.toString();
	}

	/**
	 * Return the URI description of this workspace.
	 * 
	 * @return URI of this workspace.
	 */
	public URI toURI()
	{
		return root.toURI();
	}

	/**
	 * Delete the contents of the current Workspace
	 */
	public void delete()
	{
		if (!this.root.delete())
		{
			this.root.deleteOnExit();
		}
	}

	/**
	 * Initialize the workspace. The initialization process consist to create the workspace corresponding to its workspace
	 * name as defined by its constructor. If workspace has been initialized yet, an <code>IllegalAccessError</code> will
	 * occur.
	 */
	public void init()
	{
		if (!this.root.exists())
		{
			if (!this.root.mkdirs()) { throw new IllegalAccessError("File creation failed: " + this.root); }
		}
		else
		{
			throw new IllegalAccessError("Workspace exists yet: " + this.root);
		}
	}

	/**
	 * Adds a file into the workspace according to its path. If path is given as full absolute path, it will be always
	 * stored into the workspace name. The file adds into the workspace is not created by default.
	 *
	 * @param path
	 *          WorkspaceFile's path.
	 * @return The file into the workspace.
	 * @throws IOException
	 *           If file exists into the workspace yet.
	 */
	public File addFileByStringPath(String path) throws IOException
	{
		File _workspace_file = new WorkspaceFile(this, path);
		if (!this.files.contains(_workspace_file) && !_workspace_file.exists())
		{
			this.files.add(_workspace_file);
			return _workspace_file;
		}

		throw new IOException("File exists yet");
	}

	/**
	 * Adds a file according to its URI representation.
	 * 
	 * @param uri
	 *          The URI file
	 * @return The file added into the workspace. It's URI will differ from the original URI, according to the fact the
	 *         root URI will be removed from the original URI file.
	 * @throws IOException
	 *           If file exists into the workspace yet.
	 */
	public File addFileByURI(URI uri) throws IOException
	{
		File _workspace_file = new WorkspaceFile(this, uri);
		return addFileByStringPath(_workspace_file.toString());
	}

	/**
	 * Attach a file to the workspace. If file exists yet or had been created, an <code>IOException</code> will throw.
	 *
	 * @param file
	 *          File to attach to the workspace.
	 * @return The attached file.
	 * @throws IOException
	 *           If file exists into the workspace or had been created outside the workspace.
	 */
	public File attachFile(File file) throws IOException
	{
		if (!file.exists()) { return addFileByStringPath(file.getPath()); }

		throw new IOException("File exists or created yet");
	}

	/**
	 * Gets the file that is corresponding to the given path.
	 *
	 * @param path
	 *          WorkspaceFile's path
	 * @return WorkspaceFile that is corresponding to the given path, or <code>null</code> if not found.
	 */
	public File getFileByStringPath(String path)
	{
		int _idx = this.files.indexOf(new WorkspaceFile(this, path));
		return _idx < 0 ? null : this.files.get(_idx);
	}

	/**
	 * A class that's representing a controlled file that is locking into a specific workspace.
	 *
	 * @author Tioben Neenot
	 */
	private class WorkspaceFile extends File
	{

		/**
		 * Component serial ID
		 */
		private static final long serialVersionUID = -3621417823396419262L;

		/**
		 * Workspace associated for this file.
		 */
		private Workspace workspace;

		/**
		 * Creates a new <code>WorkspaceFile</code> instance by converting the given <code>pathname</code> string into an
		 * abstract pathname. The rules applied to this constructor are the same as {@link File}. Furthermore, according to
		 * {@link Workspace} concepts, if this abstract pathname contains the full pathname of the workspace, the pathname
		 * will be considered as relative inside the {@link Workspace} pathname.
		 *
		 * @param workspace
		 *          workspace links to this file
		 * @param pathname
		 *          A pathname string
		 * @throws NullPointerException
		 *           If the pathname or workspace argument is <code>null</code>
		 * @see java.io.File
		 */
		WorkspaceFile(Workspace workspace, String pathname)
		{
			super(workspace.toString(), pathname);
			this.workspace = workspace;
		}

		/**
		 * Creates a new File instance by converting the given file: URI into an abstract pathname. Furthermore, according
		 * to {@link Workspace} concepts, if this abstract pathname contains the full pathname of the workspace, the
		 * pathname will be considered as relative inside the {@link Workspace} pathname.
		 *
		 * @param uri
		 *          An absolute, hierarchical URI with a scheme equal to <code>"file"</code>, a non-empty path component,
		 *          and undefined authority, query, and fragment components
		 * @throws NullPointerException
		 *           If <code>URI</code> is <code>null</code>.
		 * @throws IllegalArgumentException
		 *           If the preconditions on the parameter do not hold
		 * @see java.io.File
		 */
		WorkspaceFile(Workspace workspace, URI uri)
		{
			this(workspace, uri.toString());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.io.File#toPath()
		 */
		@Override
		public Path toPath()
		{
			return new File(this.toString()).toPath();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.io.File#getParentFile()
		 */
		@Override
		public File getParentFile()
		{
			File _parent_file = super.getParentFile();

			return null == _parent_file ? null : new WorkspaceFile(this.workspace, _parent_file.getAbsolutePath());
		}

		/**
		 * Returns the pathname string of this abstract pathname's parent, or <code>null</code> if this pathname does not
		 * name a parent directory.
		 */
		@Override
		public String getParent()
		{
			// Limits to workspace root directory has upper level.
			String _parent = super.getParent();
			String _its_parent = _parent.replace(this.workspace.toString(), "");
			return "".equals(_its_parent) ? null : _its_parent;
		}

		/**
		 * Returns the description for this WorkspaceFile. This description doesn't show the root workspace into the full
		 * workspace.
		 *
		 * @return The description for this WorkspaceFile.
		 */
		@Override
		public String toString()
		{
			return super.toString().replace(String.format("%1$1s%2$1s", this.workspace.toString(), File.separator),
					File.separator);
		}

		/**
		 * Returns the URI of this WorkspaceFile.
		 * 
		 * @return URI description of this WorkspaceFile or <code>null</code> if the URI is invalid.
		 */
		@Override
		public URI toURI()
		{
			try
			{
				return new URI(super.toURI().toString().replace(this.workspace.toURI().toString(), ""));
			}
			catch (URISyntaxException e)
			{
				e.printStackTrace();
			}

			return null;
		}
	}
}
