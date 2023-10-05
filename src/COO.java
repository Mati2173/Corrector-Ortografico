import GUI.tab.DictionaryTab;
import GUI.tab.TextTab;
import sources.Dictionary;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class COO extends JFrame {
    private Dictionary dictionary, ignoredWords;
    private DictionaryTab dictionaryTab;
    private TextTab textTab;

    public COO() {
        super();
        this.dictionary = new Dictionary();
        this.ignoredWords = new Dictionary();
        this.dictionaryTab = new DictionaryTab(this.dictionary, this.ignoredWords);
        this.textTab = new TextTab(this.dictionary, this.ignoredWords);
        this.textTab.setDictPane(this.dictionaryTab);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Diccionario", this.dictionaryTab);
        tabbedPane.add("Texto", this.textTab);

        addWindowListener(new EventosVentana());
        add(tabbedPane);
        setTitle("Corrector Ortográfico");
        setIconImage(new ImageIcon("sources/Icono.png").getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);
    }

    private class EventosVentana extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            dictionaryTab.saveDictionary();
        }
    }

    public static void main(String[] args) {
        COO c = new COO();
        c.setVisible(true);
    }
}