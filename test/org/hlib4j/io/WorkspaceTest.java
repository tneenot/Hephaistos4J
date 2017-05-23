/*
 * Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 * This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.io;

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

  // Tmp directory where temp directories will be created for all these unit tests procedures.
  private static File tmpDir = null;
  /**
   * Workspace used for unit tests
   */
  private Workspace workspace = null;
  /**
   * File used during test
   */
  private File ftest = null;

  @BeforeClass
  public static void setUpClass()
  {
    String _tmp_dir = System.getenv("TEMP");

    tmpDir = null == _tmp_dir ? new File("/tmp") : new File(File.separator + "tmp");
  }

  @AfterClass
  public static void tearDownClass()
  {
    tmpDir = null;
  }

  @Before
  public void setUp()
  {
    // Create the workspace context
    workspace = new Workspace(tmpDir + File.separator + "FTest");
    try
    {
      workspace.mkdirs();
    } catch (IllegalAccessError e)
    {
      workspace = new Workspace(tmpDir + File.separator + "FTest2");
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
    if (workspace.exists() && !workspace.delete())
    {
      workspace.deleteOnExit();
    }

    workspace = null;
  }

  /**
   * Creates a file into the workspace.
   *
   * @throws java.io.IOException If file exists
   */
  @Test
  public void test_AddFileByStringPath_FileNotExists() throws IOException
  {
    File f = workspace.addFileByStringPath("foo");
    Assert.assertFalse(f.exists());
  }

  /**
   * Adds a file according to its URI representation
   *
   * @throws IOException If error during file adding or creation
   */
  @Test
  public void test_AddFileByURI_ValidFileURI_FileAccepted() throws IOException
  {
    File f = new File(File.separator + "foobar3");
    Assert.assertNotNull(workspace.addFileByURI(f.toURI()));
  }

  @Test
  public void test_ToURI_ControlURIFormatIfInWorkspace_ValidURI() throws IOException
  {
    File _ref = new File(File.separator + "foobar2");
    File _uri_file = workspace.addFileByURI(new File(File.separator + "foobar2").toURI());

    Assert.assertEquals(_ref.toURI(), _uri_file.toURI());
  }

  /**
   * Adds an existing file.
   *
   * @throws java.io.IOException If file exists
   */
  @Test(expected = IOException.class)
  public void test_AddFileByStringPath_ExistingFileYet_IOException() throws IOException
  {
    ftest = new File(workspace + File.separator + "foobar");
    ftest.createNewFile();

    workspace.addFileByStringPath("foobar");
  }

  /**
   * Test the parent path for a given file. If parent reaches the workspace path.
   *
   * @throws java.io.IOException If file exists
   */
  @Test
  public void test_GetParent_NoParent_GetNull() throws IOException
  {
    File f = workspace.addFileByStringPath("foo");
    Assert.assertNull(f.getParent());
  }

  @Test
  public void test_GetParentFile_NoParent_GetNull() throws IOException
  {
    File f = workspace.addFileByStringPath("foo");
    Assert.assertNull(f.getParentFile());
  }

  /**
   * Test the parent path for a file. Source file has several path levels.
   *
   * @throws java.io.IOException If file exists
   */
  @Test
  public void test_GetParentFile_ParentExistsForFirstLevel_NotNull() throws IOException
  {
    File f = workspace.addFileByStringPath("foo" + File.separator + " bar");
    Assert.assertNotNull(f.getParentFile());
  }

  @Test
  public void test_GetParentFile_ParentExistsForSecondLevel_NotNull() throws IOException
  {
    File f = workspace.addFileByStringPath("foo" + File.separator + " bar");
    Assert.assertNull(f.getParentFile().getParentFile());
  }

  /**
   * Attach a file into the workspace.
   *
   * @throws java.io.IOException If file already exists
   */
  @Test
  public void test_AttachFile_OnValidFile_FileAttached() throws IOException
  {
    Assert.assertNotNull(workspace.attachFile(new File("foo")));
  }

  /**
   * Attach an existent file into the workspace.
   *
   * @throws java.io.IOException If file exists
   */
  @Test(expected = IOException.class)
  public void test_AttachFile_FileExistsYet_IOException() throws IOException
  {
    workspace.attachFile(tmpDir);
  }

  /**
   * Gets a file that is not existing from the workspace
   */
  @Test
  public void test_GetFileByStringPath_FileNotExists_GetNull()
  {
    Assert.assertNull(workspace.getFileByStringPath("foo"));
  }

  /**
   * Initialized a workspace already initialized.
   */
  @Test
  public void test_Mkdirs_WorkspaceExistsYet_DirectoryNotCreated()
  {
    Assert.assertFalse(workspace.mkdirs());
  }

  /**
   * Test the string description of a file of the workspace.
   *
   * @throws java.io.IOException If file exists
   */
  @Test
  public void test_ToString_ControlDescription_ValidDescription() throws IOException
  {
    File f = workspace.addFileByStringPath("foo");

    Assert.assertEquals(File.separator + "foo", f.toString());
  }

  /**
   * Move a file inside the workspace.
   *
   * @throws java.io.IOException If file exists.
   */
  @Test
  public void test_RenameTo_ValidName_FileNameUpdated() throws IOException
  {
    File f = workspace.addFileByStringPath("foo");
    f.createNewFile();

    Assert.assertTrue(f.renameTo(workspace.addFileByStringPath("bar")));
  }

  /**
   * ToPath() description for a file added into a Workspace
   *
   * @throws IOException If error during file creation.
   */
  @Test
  public void test_ToPath_WhoseOneFileNotInWorkspace_NotSameFile() throws IOException
  {
    ftest = workspace.addFileByStringPath("foo3");
    File f = new File(workspace + File.separator + "foo3");

    Assert.assertNotEquals(f.toPath(), ftest.toPath());
  }

  /**
   * Test getRoot() value tru the toPath() method for a simple file added to workspace.
   *
   * @throws IOException If error during file creation.
   */
  @Test
  public void test_GetRoot_ControlDescription_ValidDescription() throws IOException
  {
    ftest = workspace.addFileByStringPath("foo4");

    Assert.assertEquals(File.separator, ftest.toPath().getRoot().toString());
  }

  /**
   * Test getParent() tru the toPath() method for a simple file added to workspace.
   *
   * @throws IOException If error during file creation.
   */
  @Test
  public void test_GetParent_ControlDescription_ValidDescription() throws IOException
  {
    ftest = workspace.addFileByStringPath("foo5");

    Assert.assertEquals(File.separator, ftest.toPath().getParent().toString());
  }

  /**
   * Call <code>createNewFile</code> for a <code>Workspace</code> and control that raises an exception.
   */
  @Test(expected = IOException.class)
  public void test_CreateNewFile_ForbiddenOperationSinceIsADirectoryType_IOException() throws IOException
  {
    workspace.createNewFile();
  }

  @Test
  public void test_Constructor_BuildInstanceWithParentAndChildStringParameters_NoError()
  {
    new Workspace(tmpDir + File.separator + "foo", "bar");
  }

  @Test
  public void test_Constructor_BuildInstanceWithParentFileAndChildStringParameters_NoError()
  {
    new Workspace(new File(tmpDir + File.separator + "foo"), "bar");
  }

  @Test
  public void test_Constructor_BuildInstanceFromURI_NoError()
  {
    new Workspace(new File(tmpDir + File.separator + "foo").toURI());
  }

  @Test
  public void test_ToURI_ReferenceInternalFileFromURI_SameValues() throws Exception
  {
    File add_file = workspace.addFileByURI(new File(File.separator + "foobar").toURI());
    add_file.createNewFile();
    File uri_file = workspace.getFileByURI(add_file.toURI());

    Assert.assertEquals(add_file.toURI(), uri_file.toURI());
  }

  @Test
  public void test_Mkdirs_ForEncapsulateWorkspaces_DirectoryCreated() throws IOException
  {
    File second_workspace = workspace.attachFile(new Workspace(File.separator + "W2"));
    Assert.assertTrue(second_workspace.mkdirs());
  }

  @SuppressWarnings("deprecation")
  @Test(expected = UnsupportedOperationException.class)
  public void test_ToURL_CallException_UnsupportedOperationException() throws IOException
  {
    File f = workspace.addFileByStringPath("foo4");
    f.toURL();
  }

  @Test
  public void test_Workspace_ControlIfExistsAfterDeletion_NotExists()
  {
    workspace.deleteOnExit();

    Assert.assertFalse(workspace.exists());
  }
}
