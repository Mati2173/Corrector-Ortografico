package sources;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;


public class FileManager {
    private File file;
    private BufferedReader bufferedReader;

    public FileManager() {
        this.file = null;
        this.bufferedReader = null;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return this.file;
    }

    public void chooseFile(String description, String... extensions) {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        FileNameExtensionFilter filter = new FileNameExtensionFilter(description, extensions);
        fc.setFileFilter(filter);

        fc.setVisible(true);

        int result = fc.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION)
            this.file = fc.getSelectedFile();
        else
            this.file = null;
    }

    public void saveFile(File file, String text) throws IOException{
        FileWriter fw = new FileWriter(file.getAbsolutePath());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(text);
        bw.close();
    }

    public void saveFile(String text) throws IOException {
        saveFile(this.file, text);
    }

    public void openReader(File file) throws IOException {
        FileReader fr = new FileReader(file.getAbsolutePath());
        this.bufferedReader = new BufferedReader(fr);
    }

    public void openReader() throws IOException {
        openReader(this.file);
    }

    public String readFileLine() throws IOException {
        return this.bufferedReader.readLine();
    }

    public void closeReader() throws IOException {
        this.bufferedReader.close();
    }

    public boolean containsFile() {
        return this.file != null;
    }

    public String getFileAbsolutePath() {
        return this.file.getAbsolutePath();
    }

    public boolean fileExtensionContains(String s) {
        return this.file.getName().contains(s);
    }
}
