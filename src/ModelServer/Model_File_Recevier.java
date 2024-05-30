/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author FPTSHOP
 */
// quản lý việc nhận các gói dữ liệu của tệp , ghi chúng vào một tệp trên đĩa 
public class Model_File_Recevier {
    private Model_Send_Message message ;
    private File file ; 
//     cho phép đọc và ghi vào tệp 
    private RandomAccessFile accFile ;

    public Model_Send_Message getMessage() {
        return message;
    }

    public File getFile() {
        return file;
    }

    public RandomAccessFile getAccFile() {
        return accFile;
    }

    public void setMessage(Model_Send_Message message) {
        this.message = message;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setAccFile(RandomAccessFile accFile) {
        this.accFile = accFile; 
    }

    public Model_File_Recevier(Model_Send_Message message, File file) throws IOException  {
        this.message = message;
        this.file = file;
        this.accFile = new RandomAccessFile(file, "rw");  // đọc và ghi 
    }

    public Model_File_Recevier() {
    }
//    ghi dữ liệu vào cuối tệp 
    public synchronized long writeFile(byte[] data) throws IOException {
        accFile.seek(accFile.length());
        accFile.write(data);
        return accFile.length() ;
    }
    public void close () throws IOException {
        accFile.close();
    }
}
