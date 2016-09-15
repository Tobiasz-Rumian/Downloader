import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
class Download
{
    private String tekst1=":sout=#file{dst=";
    private String tekst2;
    private String tekst3=tekst1+tekst2;
    private Gui Gui;
    private int howMany=0;
    private Runtime openingVLC = Runtime.getRuntime();
    private Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    private int afterWhat;
    private void applySettings()
    {
        tekst2=File.getSetting(1);
        this.afterWhat=Integer.valueOf(File.getSetting(3));
    }
    void start()
    {
        Gui.setConsole("Witaj w Downloader "+Gui.getVersion()+"!");

    }
    int howManyLines(String file)
    {
        int numbers = 0;
        try
        {
            Scanner sLines = new Scanner(Paths.get(file));
            while(sLines.hasNext())
            {
                numbers++;
                sLines.nextLine();
            }
            sLines.close();
            this.Gui.setConsole("Linijek jest: "+ numbers);
        }
        catch(IOException e)
        {
            this.Gui.setConsole("brak pliku!");
        }
        this.Gui.setConsole("Linijki policzone");
        return numbers;
    }
    String[] downloadLinks(int lines,String path)
    {
        String[] links = new String[lines];
        try
        {
            Scanner file = new Scanner(Paths.get(path));
            for(int i=0;i<lines;i++)
            {
                links[i]=file.nextLine();
            }
            file.close();
        }
        catch(IOException e)
        {
            this.Gui.setConsole("brak pliku!");
        }
        this.Gui.setConsole("Linki przypisane");
        return links;


    }
    private void downloadWait()//waiting for another package
    {
        if(this.howMany==this.afterWhat)
        {
            JOptionPane.showMessageDialog(Gui,"Kontynuować?");
        }
        howMany++;
    }
    private void sleep(int sleepingTime)
    {
        try
        {
            Thread.sleep(sleepingTime);
        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    private void robotActions(int whichOne)
    {
        switch(whichOne)
        {
            case 1:
                Robot robot;
                try {
                    robot = new Robot();
                    robot.setAutoDelay(100);
                    robot.keyPress(KeyEvent.VK_CONTROL);
                    robot.keyPress(KeyEvent.VK_N);
                    robot.keyRelease(KeyEvent.VK_N);
                    robot.keyRelease(KeyEvent.VK_CONTROL);
                } catch (AWTException ex)
                {
                    ex.printStackTrace();
                }
                break;
            case 2:
                try {
                    robot = new Robot();
                    robot.setAutoDelay(100);
                    robot.keyPress(KeyEvent.VK_CONTROL);
                    robot.keyPress(KeyEvent.VK_V);
                    robot.keyRelease(KeyEvent.VK_V);
                    robot.keyRelease(KeyEvent.VK_CONTROL);
                    robot.keyPress(KeyEvent.VK_RIGHT);
                    robot.keyRelease(KeyEvent.VK_RIGHT);
                    robot.keyRelease(KeyEvent.VK_BACK_SPACE);
                    robot.keyPress(KeyEvent.VK_ALT);
                    robot.keyPress(KeyEvent.VK_S);
                    robot.keyRelease(KeyEvent.VK_S);
                    robot.keyRelease(KeyEvent.VK_ALT);
                    robot.keyPress(KeyEvent.VK_ENTER);
                    robot.keyRelease(KeyEvent.VK_ENTER);
                    robot.keyPress(KeyEvent.VK_ENTER);
                    robot.keyRelease(KeyEvent.VK_ENTER);
                    robot.keyPress(KeyEvent.VK_ENTER);
                    robot.keyRelease(KeyEvent.VK_ENTER);
                    robot.keyPress(KeyEvent.VK_LEFT);
                    robot.keyRelease(KeyEvent.VK_LEFT);
                    robot.keyPress(KeyEvent.VK_LEFT);
                    robot.keyRelease(KeyEvent.VK_LEFT);
                    robot.keyPress(KeyEvent.VK_LEFT);
                    robot.keyRelease(KeyEvent.VK_LEFT);
                    robot.keyPress(KeyEvent.VK_LEFT);
                    robot.keyRelease(KeyEvent.VK_LEFT);
                } catch (AWTException ex)
                {
                    ex.printStackTrace();
                }
                break;
            case 3:
                try {
                    robot = new Robot();
                    robot.setAutoDelay(250);
                    robot.keyPress(KeyEvent.VK_CONTROL);
                    robot.keyPress(KeyEvent.VK_V);
                    robot.keyRelease(KeyEvent.VK_V);
                    robot.keyRelease(KeyEvent.VK_CONTROL);
                } catch (AWTException ex) {
                    ex.printStackTrace();
                }
                break;
        }
    }
    void downloadNormalMovies()
    {
        this.applySettings();
        for(int j=0;j<Gui.getLines();j++)
        {
            this.downloadMovies(j);
        }
        this.Gui.setConsole("Pobieranie zakończone. Życzę miłego dnia ;)");
    }
    private void downloadMovies(int i)
    {
        try
        {
            this.downloadWait();
            openingVLC.exec(File.getSetting(2));
            this.sleep(1000);
            this.robotActions(1);
            StringSelection selection = new StringSelection(Gui.getLinks(i));
            this.clipboard.setContents(selection, selection);
            this.robotActions(2);
            String tekst4 = ".mp4,no-overwrite} :sout-keep";
            selection = new StringSelection(tekst3+i+tekst4);
            clipboard.setContents(selection, selection);
            this.robotActions(3);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    void downloadSelectedMovies()
    {
        this.applySettings();
        for(int j=0;j<Gui.getLines();j++)
        {
            for (int h = 0; h < Gui.getLines1(); h++)
            {
                if (Gui.getSelectedLinks(h).equals(Integer.toString(j)))
                {
                    this.downloadMovies(j);
                }
            }
        }
        this.Gui.setConsole("Pobieranie zakończone. Życzę miłego dnia ;)");
    }
    Download(Gui Gui)
    {
        this.Gui = Gui;
    }
}

