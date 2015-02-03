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

import org.junit.*;

import java.io.File;
import java.io.IOException;

/**
 * Unit tests for {@link org.hlib4j.io.Workspace} class.
 *
 * @author Tioben Neenot
 */
public class WorkspaceTest
{

	/**
	 * Workspace used for unit tests
	 */
	private Workspace workspace = null;

	/**
	 * File used during test
	 */
	private File ftest = null;


	@Before
	public void setUp()
	{
		// Create the workspace context
		workspace = new Workspace(File.separator + "tmp" + File.separator + "FTest");
		try
		{
		workspace.mkdirs();
		}catch(IllegalAccessError e)
		{
			workspace = new Workspace(File.separator + "tmp" + File.separator + "FTest2");
			workspace.mkdirs();
		}

		ftest = null;
	}

	@After
	public void tearDown()
	{
		if (null != ftest)
		{
			if (!ftest.delete())
			{
				ftest.deleteOnExit();
			}
		}

		// Destroy the workspace context
		if(!workspace.delete())
		{
			workspace.deleteOnExit();
		}

		workspace = null;
	}

	/**
	 * Creates a file into the workspace.
	 *
	 * @throws java.io.IOException
	 *           If file exists
	 */
	@Test
	public void testAddFile() throws IOException
	{
		File f = workspace.addFileByStringPath("foo");
		Assert.assertFalse(f.exists());
	}

	/**
	 * Adds a file according to its URI representation
	 * 
	 * @throws IOException
	 *           If error during file adding or creation
	 */
	@Test
	public void testAddUriFile() throws IOException
	{
		File f = new File(File.separator + "foobar3");
		Assert.assertNotNull(workspace.addFileByURI(f.toURI()));
	}

	@Test
	public void testAddUriAndCompareToString() throws IOException
	{
		File ref = new File(File.separator + "foobar2");
		File add_file = workspace.addFileByURI(new File(File.separator + "foobar2").toURI());

		Assert.assertEquals(ref.toURI(), add_file.toURI());
	}

	/**
	 * Adds an existing file.
	 *
	 * @throws java.io.IOException
	 *           If file exists
	 */
	@Test(expected = IOException.class)
	public void testAddExistingFile() throws IOException
	{		
		ftest = new File(workspace + File.separator + "foobar");
		ftest.createNewFile();

		workspace.addFileByStringPath("foobar");
	}

	/**
	 * Test the parent path for a given file. If parent reaches the workspace path.
	 *
	 * @throws java.io.IOException
	 *           If file exists
	 */
	@Test
	public void testAddFileGetParent() throws IOException
	{
		File f = workspace.addFileByStringPath("foo");
		Assert.assertNull(f.getParent());
		Assert.assertNull(f.getParentFile());
	}

	/**
	 * Test the parent path for a given file. Source file has several path levels.
	 *
	 * @throws java.io.IOException
	 *           If file exists
	 */
	@Test
	public void testGetParentFile() throws IOException
	{
		File f = workspace.addFileByStringPath("foo" + File.separator + " bar");
		File f2 = f.getParentFile();
		Assert.assertNotNull(f2);
		Assert.assertNull(f2.getParentFile());
	}

	/**
	 * Attach a file into the workspace.
	 *
	 * @throws java.io.IOException
	 *           If file already exists
	 */
	@Test
	public void testAttachFile() throws IOException
	{
		File f = workspace.attachFile(new File("foo"));
		Assert.assertFalse(f.exists());
	}

	/**
	 * Attach an existent file into the workspace.
	 *
	 * @throws java.io.IOException
	 *           If file exists
	 */
	@Test(expected = IOException.class)
	public void testAttachFileError() throws IOException
	{
		File f = new File(File.separator + "tmp");
		workspace.attachFile(f);
	}

	/**
	 * Gets a file that is not exist from the workspace
	 */
	@Test
	public void testGetFile()
	{
		Assert.assertNull(workspace.getFileByStringPath("foo"));
	}

	/**
	 * Initialized a workspace already initialized.
	 */
	@Test
	public void testInitializedWorkspace()
	{
		Assert.assertFalse(workspace.mkdirs());
	}

	/**
	 * Test the string description of a file of the workspace.
	 *
	 * @throws java.io.IOException
	 *           If file exists
	 */
	@Test
	public void testToStringFile() throws IOException
	{
		File f = workspace.addFileByStringPath("foo");

		Assert.assertEquals(File.separator + "foo", f.toString());
	}

	/**
	 * Move a file inside the workspace.
	 *
	 * @throws java.io.IOException
	 *           If file exists<br>
	 *           <b>Note</b>: this test is as ignore mode due to java bug. While a file had been created with a
	 *           createNewFile, it's staying in blocking mode onto the operating system. Waits an change of OpenJDK to
	 *           resolve this problem.
	 */
	@Test
	@Ignore
	public void testRenameTo() throws IOException
	{
		File f = workspace.addFileByStringPath("foo");
		f.createNewFile();

		File f2 = workspace.addFileByStringPath("foo2");
		f2.createNewFile();

		Assert.assertTrue(f.renameTo(workspace.addFileByStringPath("bar")));
		Assert.assertTrue(f2.renameTo(workspace.addFileByStringPath(File.separator + "bar2")));

		Assert.assertTrue(new File(workspace + File.separator + "bar").exists());
		Assert.assertTrue(new File(workspace + File.separator + "bar2").exists());
	}

	/**
	 * ToPath() description for a file added into a Workspace
	 * 
	 * @throws IOException
	 *           If error during file creation.
	 */
	@Test
	public void testToPath() throws IOException
	{
		ftest = workspace.addFileByStringPath("foo3");
		File f = new File(workspace + File.separator + "foo3");
		
		Assert.assertNotEquals(f.toPath(), ftest.toPath());
	}
	
	/**
	 * Test getRoot() value thru the toPath() method for a simple file added to workspace.
	 * @throws IOException If error during file creation.
	 */
	@Test
	public void testToPath_getRoot() throws IOException
	{
		ftest = workspace.addFileByStringPath("foo4");
		
		Assert.assertEquals(File.separator, ftest.toPath().getRoot().toString());
	}
	
	/**
	 * Test getParent() thru the toPath() method for a simple file added to workspace.
	 * @throws IOException If error during file creation.
	 */
	@Test
	public void testToPath_getParentFile() throws IOException
	{
		ftest = workspace.addFileByStringPath("foo5");
		
		Assert.assertEquals(File.separator, ftest.toPath().getParent().toString());
	}

	/**
	 * Call <code>createNewFile</code> for a <code>Workspace</code> and control that raises an exception.
	 */
	@Test(expected = IOException.class )
	public void testWorkspace_createNewFile_Failed() throws IOException {
		workspace.createNewFile();
	}

	@Test
	public void Workspace_BuildInstanceWithParentAndChildString()
	{
		new Workspace(File.separator + "tmp" + File.separator +"foo","bar");
	}

	@Test
	public void Workspace_BuildInstanceWithParentFileAndChildString()
	{
		new Workspace(new File(File.separator + "tmp" + File.separator + "foo"), "bar");
	}

	@Test
	public void Workspace_BuildInstanceWithURI()
	{
		new Workspace(new File(File.separator + "tmp" + File.separator + "foo").toURI());
	}

    @Test
    public void testGetFileByURI() throws Exception {
        File add_file = workspace.addFileByURI(new File(File.separator + "foobar").toURI());
        add_file.createNewFile();
        File uri_file = workspace.getFileByURI(add_file.toURI());

        Assert.assertEquals(add_file.toURI(), uri_file.toURI());
    }
}
