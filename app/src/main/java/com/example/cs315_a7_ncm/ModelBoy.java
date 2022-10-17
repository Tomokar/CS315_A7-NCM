package com.example.cs315_a7_ncm;

import com.google.gson.annotations.SerializedName;

public class ModelBoy
{
    @SerializedName("word")
    String wordsWord;
    @SerializedName("type")
    String wordsType;
    @SerializedName("definition")
    String wordsDefinition;
    @SerializedName("etymology")
    String wordsEtymology;

    public ModelBoy(String word, String type, String definition/*, String etymology*/)
    {
        wordsWord = word;
        wordsType = type;
        wordsDefinition = definition;
//        wordsEtymology = etymology;
    }

    public String getWordsWord() { return wordsWord; }
    public String getWordsType() { return wordsType; }
    public String getWordsDefinition() { return wordsDefinition; }
//    public String getWordsEtymology() { return wordsEtymology; }

    public void setWordsWord(final String wordsWord) { this.wordsWord = wordsWord; }
    public void setWordsType(final String wordsType) { this.wordsType = wordsType; }
    public void setWordsDefinition(final String wordsDefinition) { this.wordsDefinition = wordsDefinition; }
//    public void setWordsEtymology(final String wordsEtymology) { this.wordsEtymology = wordsEtymology; }
}
