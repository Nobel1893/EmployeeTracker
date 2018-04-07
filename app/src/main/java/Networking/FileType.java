package Networking;

import android.net.Uri;

/**
 * Created by Mohamed Nabil Mohamed (Nobel) on 11/1/2017.
 * byte code SA
 * m.nabil.fci2015@gmail.com
 */

public class FileType {
    Uri fileUri;
    String filename;
    String MimiType;
    byte[] content;
    String extention;

    public String getExtention() {
        return extention;
    }

    public void setExtention(String extention) {
        this.extention = extention;
    }

    public Uri getFileUri() {
        return fileUri;
    }

    public void setFileUri(Uri fileUri) {
        this.fileUri = fileUri;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMimiType() {
        return MimiType;
    }

    public void setMimiType(String mimiType) {
        MimiType = mimiType;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
