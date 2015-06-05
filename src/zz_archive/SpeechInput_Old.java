package zz_archive;

import java.io.IOException;

import com.in.SentenceStack;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

public class SpeechInput_Old implements Runnable{
	
	private static boolean running = true,
			listening = false;
	
	private static final String ACOUSTIC_MODEL =
	        "resource:/edu/cmu/sphinx/models/en-us/en-us";
	private static final String DICTIONARY_PATH =
	        "resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict";
	private static final String LANGUAGE_MODEL =
		        "./import/en-us.lm.dmp";
	
	private static Configuration configuration;
	    
	private static LiveSpeechRecognizer live;
	private static SentenceStack sentence;
	    
    public SpeechInput_Old(SentenceStack s) throws IOException{
    	
        try{
        	configuration = new Configuration();
            configuration.setAcousticModelPath(ACOUSTIC_MODEL);
            configuration.setDictionaryPath(DICTIONARY_PATH);
            configuration.setLanguageModelPath(LANGUAGE_MODEL);
         //   configuration.setUseGrammar(false);
        	live = new LiveSpeechRecognizer(configuration);
            sentence = s;
            Listen(true);
        }
        catch(Exception e){

        }
        
    }
    
    /**
     * Starts/stops JARVIS from listening to input speech.
     * @param x - true starts listening, false stops.
     * @throws IOException 
     */
    public void Listen(boolean x) throws IOException{
    	if(listening == x){
    		return;
    	}
    	if(x){
    		// clear mic cache when starting recognition
	    		live.startRecognition(true);
	    		listening = true;
	    	}
	    	else{
	    		live.stopRecognition();
	    		listening = false;
	    	}
	    }
	    
	@Override
	public void run() {
		String utterance;
		try {
			while (running) {
				while (listening){
					utterance = live.getResult().getHypothesis();
					sentence.addSentence(utterance);
					Thread.sleep(50);
				}
				Thread.sleep(400);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
