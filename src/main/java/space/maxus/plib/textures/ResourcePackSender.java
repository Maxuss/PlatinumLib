package space.maxus.plib.textures;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import space.maxus.plib.PlatinumLib;
import space.maxus.plib.utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public record ResourcePackSender(File resources) implements Runnable {

    @Override
    public void run() {
        var client = new FTPClient();

        var cfg = PlatinumLib.INSTANCE.getConfig();
        var port = cfg.getInt("ftp.port");
        var remote = cfg.getString("ftp.remote");
        var pwd = cfg.getString("ftp.password");
        var username = cfg.getString("ftp.username");

        try {
            client.connect(remote, port);
            if (!FTPReply.isPositiveCompletion(client.getReplyCode())) {
                PlatinumLib.logger().severe("Could not connect to remote FTP server! Will not be able to load resource pack!");
                return;
            }
            client.login(username, pwd);
            client.setFileType(FTP.BINARY_FILE_TYPE);
            client.enterLocalPassiveMode();

            var input = new FileInputStream(resources);
            // assuming that the server accepts ext4 path, and not ntfs
            // anyways, who tf would use windows as their server
            client.storeFile("/resources/PlatinumResources.zip", input);

            client.logout();
            client.disconnect();
        } catch (IOException e) {
            Utils.logError(e);
        }
    }
}
