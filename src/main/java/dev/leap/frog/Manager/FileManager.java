package dev.leap.frog.Manager;


import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {

    private String directoryleapfrog = "Leapfrog/";
    private String directoryModule = "/Module/";
    private String directoryGUI = "/GUI/";
    private String directoryFriends = "/Friends/";
    private String directoryBinds = "/Binds/";

    private String doubledirModule = directoryleapfrog + directoryModule;
    private String doubledirGUI = directoryleapfrog + directoryGUI;
    private String doubledirBinds = directoryleapfrog + directoryBinds;
    private String doubledFriends = directoryleapfrog + directoryFriends;

    private String friendsFile = "friends.json";
    private String bindsFile = "binds.txt";

    private Path pathleapfrog = Paths.get(directoryleapfrog);
    private Path pathModule = Paths.get(directoryleapfrog + directoryModule);
    private Path pathGUI = Paths.get(directoryleapfrog + directoryGUI);
    private Path pathFriends = Paths.get(directoryleapfrog + directoryFriends);
    private Path pathBinds = Paths.get(directoryleapfrog + directoryBinds);

}
