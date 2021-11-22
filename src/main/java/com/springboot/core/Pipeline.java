package com.springboot.core;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

@Component
public class Pipeline {

	private static Properties properties;
	private static String propertiesName = "tokenize,ssplit,pos,lemma,ner,parse,dcoref,sentiment"; // "tokenize, ssplit, pos,
																							// lemma, ner,
	// parse, sentiment, regexner";
	private static StanfordCoreNLP stanfordCoreNLP;

	private Pipeline() {

	}

	static {
		properties = new Properties();
		properties.setProperty("annotators", propertiesName);
		properties.setProperty("tokenize.language", "es");
		properties.setProperty("tokenize.whitespace", "true");

//		properties.setProperty("ssplit.boundaryTokenRegex", "\\.|[!?]+");

		// disable fine grained ner
//		 properties.setProperty("ner.applyFineGrained", "false");

		// customize fine grained ner
		// properties.setProperty("ner.fine.regexner.mapping", "example.rules");
		// properties.setProperty("ner.fine.regexner.ignorecase", "true");

		// add additional rules, customize TokensRegexNER annotator
		// properties.setProperty("ner.additional.regexner.mapping", "example.rules");
		// properties.setProperty("ner.additional.regexner.ignorecase", "true");

		// add 2 additional rules files ; set the first one to be case-insensitive
		// properties.setProperty("ner.additional.regexner.mapping",
		// "ignorecase=true,example_one.rules;example_two.rules");

		// set document date to be a specific date (other options are explained in the
		// document date section)
		// properties.setProperty("ner.docdate.useFixedDate", "2019-01-01");

		// only run rules based NER
//		 properties.setProperty("ner.rulesOnly", "true");

		// only run statistical NER
//		 properties.setProperty("ner.statisticalOnly", "true");
	}

	@Bean(name = "stanfordCoreNLP")
	public static StanfordCoreNLP getPipeline() {

		if (stanfordCoreNLP == null) {
			stanfordCoreNLP = new StanfordCoreNLP(properties);
		}

		return stanfordCoreNLP;
	}
}
