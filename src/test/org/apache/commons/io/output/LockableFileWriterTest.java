/*
 * Copyright 2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.apache.commons.io.output;


import java.io.IOException;

import junit.framework.TestCase;

/**
 * Tests that files really lock, although no writing is done as 
 * the locking is tested only on construction. 
 *
 * @author Henri Yandell (bayard at apache dot org)
 * @version $Revision: 1.1 $ $Date: 2004/02/23 05:49:52 $
 */

public class LockableFileWriterTest extends TestCase {

    public LockableFileWriterTest(String name) {
        super(name);
    }

    public void testFileLocked() throws IOException {
        LockableFileWriter lfw = new LockableFileWriter("testlockfile");
        try {
            LockableFileWriter lfw2 = new LockableFileWriter("testlockfile");
            fail("Somehow able to open a locked file. ");
        } catch(IOException ioe) {
            String msg = ioe.getMessage();
            assertTrue( "Exception message does not start correctly. ", 
                        msg.startsWith("Can't write file, lock ") );
        }
        lfw.close();
    }

    public void testFileNotLocked() throws IOException {
        LockableFileWriter lfw = new LockableFileWriter("testnotlockfile");
        lfw.close();
        try {
            LockableFileWriter lfw2 = new LockableFileWriter("testnotlockfile");
            lfw2.close();
        } catch(IOException ioe) {
            String msg = ioe.getMessage();
            if( msg.startsWith("Can't write file, lock ") ) {
                fail("Somehow unable to open a unlocked file. ");
            }
        }
    }

}