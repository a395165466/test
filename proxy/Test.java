
/**
 * Created by zhangguoqing.zgq on 2016/12/13.
 */
public class Test {
    public static void main(String[] args) {
        TestProxy testProxy = new TestProxy();

        Speak speakProxy = (Speak) testProxy.bind(new SpeakImpl());

        speakProxy.speakChinese();
        speakProxy.speakEnglish();
        speakProxy.speakJapanese();
    }
}
