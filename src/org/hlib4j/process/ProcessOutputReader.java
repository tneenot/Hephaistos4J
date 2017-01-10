package org.hlib4j.process;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by beonnet on 10/01/17.
 */
public class ProcessOutputReader extends Thread
{
  private final InputStream inputStream;
  private final StringBuffer stringBuffer;

  public ProcessOutputReader(InputStream inputStream)
  {
    this.inputStream = inputStream;
    this.stringBuffer = new StringBuffer();
  }

  public String getOutputResult()
  {
    return stringBuffer.toString();
  }

  @Override
  public void run()
  {
    Scanner text_reader = new Scanner(this.inputStream);
    while (text_reader.hasNextLine())
    {
      synchronized (stringBuffer)
      {
        stringBuffer.append(text_reader.nextLine());
        stringBuffer.append("\n");
      }
    }

    text_reader.close();
  }
}
