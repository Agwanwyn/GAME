package sounds;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javax.speech.SynthesizerModeDesc;
import javax.speech.Synthesizer;

public class FreeTTSSyllables {
    private static Voice voice;
    
    public static void init() {
        // Инициализация голоса (английский, но работает для простых слогов)
        VoiceManager voiceManager = VoiceManager.getInstance();
        voice = voiceManager.getVoice("kevin16");  // Или "alan" 
        voice.allocate();
        voice.setRate(0.9f);  // Медленнее для слогов
    }
    
    public static void speakTask(String syllable) {
        new Thread(() -> {
            try {
                // Для русских слогов используем фонетику или английские аналогии
                String text = mapSyllableToEnglish(syllable);
                voice.speak("Build syllable " + text + "!");
                voice.waitEngineDone();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
    
    private static String mapSyllableToEnglish(String syllable) {
        // Маппинг русских слогов на английскую фонетику
        return switch (syllable.toUpperCase()) {
            case "МА" -> "ma";
            case "БО" -> "bo";
            case "ТУ" -> "too";
            case "ПА" -> "pa";
            case "ДА" -> "da";
            case "ЛА" -> "la";
            default -> syllable; // Фallback
        };
    }
}