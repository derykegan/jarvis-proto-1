package com.in;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

public class SpeechInput implements Runnable{
	
	SentenceStack sent;

	/**
	 * Create new speech recognition module.
	 * @param s - The sentence stack to pass decoded sentences to.
	 */
	public SpeechInput(SentenceStack s){
		sent = s;
	}

	@Override
	public void run() {
		try{
			Configuration configuration = new Configuration();
			configuration.setAcousticModelPath("./import/cmusphinx-en-us-5.2");
			configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
			configuration.setLanguageModelPath("./import/en-us.lm.dmp");
			LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
			// Start recognition process pruning previously cached data.
			recognizer.startRecognition(true);
			while(true){
			SpeechResult result = recognizer.getResult();
			// Pause recognition process. It can be resumed then with startRecognition(false).
			//recognizer.stopRecognition();
			sent.addSentence(result.getHypothesis());
			}
		}
		catch(Exception e){

		}
	}

}
