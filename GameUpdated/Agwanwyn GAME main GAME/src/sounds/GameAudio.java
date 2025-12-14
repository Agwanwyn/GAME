package sounds;
import javax.sound.sampled.*;
import java.util.concurrent.Executors;

public class GameAudio {
    public static void success() {
        playToneAsync(800, 600);  // Радостный высокий тон
        playToneAsync(1000, 300); // Финальный аккорд
    }
    
    public static void error() {
        playToneAsync(300, 400);  // Грустный низкий
        playToneAsync(200, 300);
    }
    
    private static void playToneAsync(int freq, int durationMs) {
        Executors.newSingleThreadExecutor().execute(() -> playTone(freq, durationMs));
    }
    
    private static void playTone(int hz, int ms) {
        try {
            byte[] buffer = new byte[ms * 8];
            for (int i = 0; i < buffer.length; i++) {
                double angle = 2.0 * Math.PI * hz * i / 8000;
                buffer[i] = (byte) (Math.sin(angle) * 127);
            }
            
            AudioFormat format = new AudioFormat(8000, 8, 1, true, false);
            SourceDataLine line = AudioSystem.getSourceDataLine(format);
            line.open(format);
            line.start();
            line.write(buffer, 0, buffer.length);
            line.drain();
            line.close();
        } catch (LineUnavailableException ignored) {}
    }
}
