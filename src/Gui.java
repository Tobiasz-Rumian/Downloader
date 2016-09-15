import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Gui extends JFrame
{
    private String version="1.4";
    private JButton start;
    private JButton accept;
    private JPanel mainWindow;
    private JTextArea console;
    private JTextField text;
    private JButton select;
    private JButton newSettings;
    private String[] links;
    private String[] selectedLinks;
    private int lines=0;
    private int lines1=0;
    String getVersion()
    {
        return version;
    }
    String getLinks(int i)
    {
        return links[i];
    }
    void setLinks(String[] links)
    {
        this.links=links;
    }

    void setSelectedLinks(String[] links)
    {
        this.selectedLinks=links;
    }
    String getSelectedLinks(int i)
    {
        return selectedLinks[i];
    }

    int getLines()
    {
        return lines;
    }
    int getLines1()
    {
        return lines1;
    }
    void setConsole(String text)
    {
        console.append(text+"\n");
    }
    void setLines1(int lines)
    {
        this.lines1=lines;
    }
    void setLines(int lines)
    {
        this.lines=lines;
    }
    String getText()
    {
        String text1=text.getText();
        text.setText("");
        return text1;
    }
    void setText(String a)
    {
        text.setText(a);
    }
    void startEnabled(boolean a)
    {
        start.setEnabled(a);
    }
    void selectEnabled(boolean a)
    {
        select.setEnabled(a);
    }
    void acceptEnabled(boolean a)
    {
        accept.setEnabled(a);
    }
    void newSettingsEnabled(boolean a)
    {
        newSettings.setEnabled(a);
    }
    Gui()
    {
        setContentPane(mainWindow);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        Download download = new Download(Gui.this);
        download.start();
        start.addActionListener(e ->
        {
            start.setEnabled(false);
            newSettings.setEnabled(false);
            if(File.checkIfExist())
            {
                lines = download.howManyLines(File.getSetting(0));
                links = download.downloadLinks(getLines(),File.getSetting(0));
                download.downloadNormalMovies();
            }
            else
            {
                File.setFaze(2);
                accept.setEnabled(true);
                setConsole("Podaj ścieżkę linków: (np: D:\\Desktop\\plik.txt)");
                text.setText("ścieżka linków");
            }
        });
        accept.addActionListener(e ->
        {
            accept.setEnabled(false);
            File.askForSettings(Gui.this,download);

        });
        select.addActionListener(e ->
        {
            select.setEnabled(false);
            acceptEnabled(true);
            if(File.checkIfExist())
            {
                File.setFaze(7);
                setConsole("Podaj ścieżkę wybranych numerów (każdy w kolejnej linijce)");
                text.setText("ścieżka wybranych numerów");
                acceptEnabled(true);
            }
            else
            {
                File.setFaze(2);
                setConsole("Podaj ścieżkę linków: (np: D:\\Desktop\\plik.txt)");
                text.setText("ścieżka linków");
            }
        });

        newSettings.addActionListener(e ->
        {
            newSettingsEnabled(false);
            startEnabled(false);
            selectEnabled(false);
            accept.setEnabled(true);
            File.setFaze(2);
            setConsole("Podaj ścieżkę dostępu: (np: D:\\Desktop\\plik.txt)");
            text.setText("ścieżka dostępu");

        });
        text.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                switch (e.getKeyCode()){
                    case KeyEvent.VK_ENTER :
                        accept.doClick();
                        break;
                }
            }
        });
    }

}
