package com.springboot.controller;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.json.simple.JSONObject;
import com.springboot.core.Pipeline;
import com.springboot.model.Type;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

@RestController
public class NERController {

	@Autowired
	private StanfordCoreNLP stanfordCoreNLP;

	@GetMapping("/welcome")
	public JSONObject welcome() {
		String text = "The President does not support him";
		JSONObject json = new JSONObject();

		json.put("pos", POSTagger(text));
		json.put("sentiment", SentimentAnalysis(text));
		json.put("lemma", LemmaAnalysis(text));
		json.put("ner", NERAnalysis(text));

		return json;
	}

	@PostMapping
	@RequestMapping(value = "/ner")
	public Set<String> NamedEntityRecogniser(@RequestBody String input, @RequestParam final Type type) {

		CoreDocument coreDocument = new CoreDocument(input);
		stanfordCoreNLP.annotate(coreDocument);
		List<CoreLabel> coreLabels = coreDocument.tokens();

		HashSet<String> listingHashSet = new HashSet<>(collectionList(coreLabels, type));
		return listingHashSet;
//		return new HashSet<>(collectionList(coreLabels, type));
	}

	private List<String> collectionList(List<CoreLabel> coreLabels, final Type type) {
		return coreLabels.stream()
				.filter(coreLabelValue -> type.getName()
						.equalsIgnoreCase(coreLabelValue.get(CoreAnnotations.NamedEntityTagAnnotation.class)))
				.map(obj -> obj.originalText()).collect(Collectors.toList());

	}

	// 1 Part of Speech Function
	public JSONObject POSTagger(final String text) {
		StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();

		CoreDocument coreDocument = new CoreDocument(text);
		stanfordCoreNLP.annotate(coreDocument);

//		List<Object> posList = new ArrayList<Object>();
		JSONObject json = new JSONObject();
		for (CoreLabel coreLabel : coreDocument.tokens()) {
			String pos = coreLabel.get(CoreAnnotations.PartOfSpeechAnnotation.class);
//			System.out.println(coreLabel.originalText() + " = " + pos);
//			posList.add(coreLabel);
			json.put(coreLabel.originalText(), pos);
		}

		return json;
//		return posList;
	}

	// 2 Sentiment Analysis
	public JSONObject SentimentAnalysis(final String text) {
		StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();

		CoreDocument coreDocument = new CoreDocument(text);
		stanfordCoreNLP.annotate(coreDocument);

		JSONObject json = new JSONObject();
		for (CoreSentence sentence : coreDocument.sentences()) {
			String sentiment = sentence.sentiment();
			json.put(sentence, sentiment);
		}

		return json;
	}

	// 3 Lemma Analysis
	public JSONObject LemmaAnalysis(final String text) {
		StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();

		CoreDocument coreDocument = new CoreDocument(text);
		stanfordCoreNLP.annotate(coreDocument);

		JSONObject json = new JSONObject();
		for (CoreLabel coreLabel : coreDocument.tokens()) {
			String lemma = coreLabel.lemma();
			json.put(coreLabel.originalText(), lemma);
		}

		return json;
	}

	// 4 NER Analysis
	public JSONObject NERAnalysis(final String text) {
		StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();

		CoreDocument coreDocument = new CoreDocument(text);
		stanfordCoreNLP.annotate(coreDocument);

		JSONObject json = new JSONObject();
		for (CoreLabel coreLabel : coreDocument.tokens()) {
			String nerString = coreLabel.ner();
			json.put(coreLabel.originalText(), nerString);
		}

		return json;
	}

}
