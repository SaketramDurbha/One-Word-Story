package me.onewordstory.entites;

public class Word extends Component {
	public int word_id;
	public String word;
	public int sentence_id;
	
	public int getWord_id() {
		return word_id;
	}
	public void setWord_id(int word_id) {
		this.word_id = word_id;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getSentence_id() {
		return sentence_id;
	}
	public void setSentence_id(int sentence_id) {
		this.sentence_id = sentence_id;
	}
}
