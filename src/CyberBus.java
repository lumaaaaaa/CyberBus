import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
public class CyberBus {
    static String[] options = {"Another 30 Seconds", "Terminate Cyberbus"};
    static int initial = 0;
    static boolean noRPT = true;
    public static void main(String[] args) throws IOException, AWTException, InterruptedException {
        JOptionPane.showMessageDialog(null, "Cyberbus started successfully! \n\nLaunch Kega Fusion, map left to Q and the virtual A button to the E key.\nAfter done, press OK.\nThe bot will automatically start driving when the game is placed in fullscreen by pressing ESC in Kega Fusion.\nPut the game in fullscreen when you're actually on the road, the menus won't do, and you'll just get random keypresses.\n \n Have fun!", "Cyberbus", JOptionPane.INFORMATION_MESSAGE);
        int counter = 1;
        int stillHereCount = 0;
        bot(counter, stillHereCount);
    }
    public static void bot(int x, int stillHereCount) throws AWTException, IOException, InterruptedException {
        while(x>-1) {
            int delFile = x - 1;
            File f = new File("tmpbusbot" + delFile + ".jpg");
            if (f.delete()) {
            }
            Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize(); //gets resolution of screen, allows program to verify width of screen
            Rectangle screenRectangle = new Rectangle(resolution);
            Robot robot = new Robot();
            BufferedImage image = robot.createScreenCapture(screenRectangle);
            File file = new File("tmpbusbot" + x + ".jpg");
            ImageIO.write(image, "jpg", file);
            System.out.println("Temporary screenshot has been saved as file: " + file.getPath() + ". Older images have been wiped.");
            BufferedImage rescheck = ImageIO.read(new File("tmpbusbot" + x + ".jpg")); //checks image dimensions to verify eligibility
            int width = rescheck.getWidth();
            if (width == 640 && initial == 0) {
                System.out.println("Autopilot engaged...");
                stillHereCount = 0;
                initial = 1;
            }
            if (width != 640) {
                initial = 0;
            }
            if(initial == 0) {
                stillHereCount++;
            }
            if(stillHereCount == 30) {
                int questionable = JOptionPane.showOptionDialog(null,
                        "Hello, you haven't been in fullscreen mode for 30 seconds now. Would you like to close the program?",
                        "Are you there?",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        options,
                        null);
                if(questionable == 0) {
                    stillHereCount = 0;
                }
                else{
                    System.exit(0);
                }
            }
            if (initial == 1) {
                robot.keyPress(KeyEvent.VK_E);
            }
            int rgb = image.getRGB(556, 341);
            if (rgb != -10264221 && initial == 1) {
                for (int c = 0; c < 200; c++) {
                    robot.keyPress(KeyEvent.VK_Q);
                    Thread.sleep(1);
                    robot.keyRelease(KeyEvent.VK_Q);
                }
            }
            x++;
            Thread.sleep(500);
        }
    }
}