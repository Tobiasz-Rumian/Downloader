import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Scanner;

class File
{
    static private final int x=4;
    static private String settings[]=new String[x];
    static private int faze=1;

    static void setFaze(int i)
    {
        faze=i;
    }
    static boolean checkIfExist()
    {
        try
        {
            Scanner in = new Scanner(Paths.get("settings.txt"));
            for(int i=0;i<x;i++)
            {
                settings[i]=in.nextLine();
            }
            return true;
        }
        catch(IOException e)
        {
            return false;
        }

    }
    static String getSetting(int i)
    {
        return settings[i];
    }
    private static void setSetting(int i, String setting)
    {
        settings[i]=setting;
    }
    private static void saveSettings()
    {
        try
        {
            PrintWriter writer = new PrintWriter("settings.txt");
            for(int i=0;i<x;i++)
            {
                writer.println(settings[i]);
            }
            writer.close();
        }
        catch(IOException ignored){}
    }
    static void askForSettings(Gui Gui,Download download)
    {
        switch(faze)
        {
            case 1:
                if(Gui.getText().equals("JanPaweł2"))
                {
                    Gui.startEnabled(true);
                    Gui.selectEnabled(true);
                    Gui.acceptEnabled(false);
                }
                else Gui.setConsole("Błędne hasło!");
                Gui.acceptEnabled(true);
                break;
            case 2:
                File.setSetting(0,Gui.getText());
                Gui.setConsole("Podaj ścieżkę zapisu (np: D:\\Desktop\\folder\\)");
                Gui.setText("ścieżka zapisu");
                Gui.acceptEnabled(true);
                setFaze(3);
                break;
            case 3:
                File.setSetting(1,Gui.getText());
                Gui.setConsole("Podaj ścieżkę VLC (np: D:\\Program Files\\VideoLAN\\VLC\\VLC.exe)");
                Gui.setText("ścieżka VLC");
                Gui.acceptEnabled(true);
                setFaze(4);
                break;
            case 4:
                File.setSetting(2,Gui.getText());
                Gui.setConsole("Podaj ilość linijek które chcesz jednorazowo otworzyć (przy wolniejszych procesorach pojawia się desynchronizacja)");
                Gui.setText("ilość linijek");
                Gui.acceptEnabled(true);
                setFaze(5);
                break;
            case 5:
                Gui.acceptEnabled(false);
                File.setSetting(3,Gui.getText());
                Gui.setConsole("Wszystkie ustawienia podane. Życzę udanego pobierania ;)");
                File.saveSettings();
                Gui.newSettingsEnabled(true);
                Gui.startEnabled(true);
                Gui.selectEnabled(true);
                setFaze(6);
                break;
            case 6:
                Gui.setText("");
                break;
            case 7:
                String selectedPath = Gui.getText();
                Gui.setLines1(download.howManyLines(selectedPath));
                Gui.setSelectedLinks(download.downloadLinks(Gui.getLines1(),selectedPath));
                Gui.setLines(download.howManyLines(getSetting(0)));
                Gui.setLinks(download.downloadLinks(Gui.getLines(),getSetting(0)));
                download.downloadSelectedMovies();
                break;
        }
    }
}
//odczyt 0
//zapis 1
//VLC 2
//linijki 3