// Data class containing password pieces used for building passwords
package com.example.passwordgame.resources;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Arrays;
import java.util.List;

public class PasswordPieces {
    private final List<String> nouns = Arrays.asList(
        "air", "amount", "baby", "bag", "basketball", "bear", "bee", "birth", "book", "brother",
        "calculator", "celery", "circle", "circle", "collar", "color", "competition", "copper",
        "cows", "crow", "day", "dinner", "dog", "dolls", "door", "dust", "eyes", "flag", "floor",
        "floor", "fog", "fruit", "ghost", "girl", "governor", "grain", "grass", "grip", "growth",
        "hammer", "hammer", "holiday", "horses", "humor", "icicle", "island", "lake", "lamp",
        "leg", "limit", "linen", "loaf", "lunch", "mask", "match", "name", "nest", "notebook",
        "orange", "pancake", "planes", "plate", "pleasure", "plough", "pocket", "quiet", "rabbit",
        "respect", "robin", "rod", "rose", "school", "scissors", "self", "school", "shelf",
        "ship", "sister", "skate", "soup", "spider", "square", "statement", "steel", "stew",
        "stone", "ticket", "toe", "toe", "tongue", "tooth", "toy", "voyage", "waves", "weather",
        "weight", "weight", "wind", "wool", "wool", "writer", "yard", "year", "zipper", "zoo"
    );
    private final List<String> pronouns = Arrays.asList(
        "i", "me", "mine", "my", "myself", "you", "yours", "he", "him", "his", "she", "her",
        "hers", "it", "we", "they", "us", "them", "who"
    );
    private final List<String> adjectives = Arrays.asList(
        "abrupt", "adorable", "alcoholic", "angry", "bouncy", "brave", "chivalrous", "courageous",
        "cruel", "deep", "defective", "draconian", "easy", "economic", "electric", "empty",
        "envious", "flat", "flippant", "frightening", "futuristic", "fuzzy", "gaping", "gigantic",
        "halting", "hellish", "high", "hospitable", "hot", "hulking", "hysterical", "interesting",
        "lacking", "malicious", "meaty", "merciful", "mysterious", "neat", "nippy", "nosy",
        "odd", "odd", "organic", "outrageous", "outstanding", "periodic", "polite", "productive",
        "rapid", "romantic", "selfish", "serious", "skinny", "snobbish", "sparkling", "spiky",
        "spiritual", "spotted", "strange", "striped", "tacit", "tangy", "tearful", "terrific",
        "therapeutic", "threatening", "unequal", "uptight", "used", "various", "victorious",
        "vigorous", "vulgar", "weak", "wicked", "willing"
    );
    private final List<String> verbs = Arrays.asList(
        "admit", "agree", "allow", "analyze", "am", "beg", "belong", "bump", "bury", "buzz",
        "care", "carry", "cheer", "clean", "coach", "compete", "copy", "crack", "crash",
        "dance", "delight", "describe", "destroy", "discover", "dislike", "doubt", "drip",
        "empty", "extend", "fasten", "fill", "flash", "frighten", "gather", "handle", "hate",
        "heat", "hook", "hug", "ignore", "increase", "instruct", "interfere", "interrupt",
        "invent", "irritate", "is", "join", "license", "like", "love", "mend", "mix", "murder",
        "nod", "obtain", "offend", "open", "pack", "peck", "peel", "permit", "play", "poke",
        "practice", "recognise", "reign", "repair", "reply", "report", "ruin", "rule", "scorch",
        "seal", "sign", "smash", "smash", "sound", "spot", "squash", "stay", "steal", "stitch",
        "suffer", "suggest", "switch", "tap", "test", "tickle", "tour", "try", "welcome",
        "whistle", "work", "zip"
    );
    private final List<String> prepositions = Arrays.asList(
        "about", "above", "across", "against", "against", "along", "among", "around", "as", "at",
        "before", "behind", "below", "beneath", "beside", "besides", "between", "by", "during",
        "for", "from", "in", "inside", "into", "near", "of", "on", "onto", "outside", "over",
        "past", "per", "plus", "than", "the", "towards", "underneath", "until", "up", "upon",
        "with", "within", "without"
    );
    private final List<String> symbols = Arrays.asList(
       "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "_", "+", "=", "/", "<", ">",
        "?", "~", ".", ",", ":", ";", "[", "]", "{", "}"
    );
    private final List<String> numbers = Arrays.asList(
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"
    );

    public PasswordPieces() {}

    // Returns ObservableLists<String> for use in ComboBoxes
    public ObservableList<String> getNounObservableList() {
        return FXCollections.observableList(nouns);
    }
    public ObservableList<String> getPronounObservableList() {
        return FXCollections.observableList(pronouns);
    }
    public ObservableList<String> getAdjectiveObservableList() {
        return FXCollections.observableList(adjectives);
    }
    public ObservableList<String> getVerbObservableList() {
        return FXCollections.observableList(verbs);
    }
    public ObservableList<String> getPrepositionObservableList() {
        return FXCollections.observableList(prepositions);
    }
    public ObservableList<String> getSymbolObservableList() {
        return FXCollections.observableList(symbols);
    }
    public ObservableList<String> getNumberObservableList() {
        return FXCollections.observableList(numbers);
    }
    public int nounSize() {
        return nouns.size();
    }
    public int pronounSize() {
        return pronouns.size();
    }
    public int adjectiveSize() {
        return adjectives.size();
    }
    public int verbSize() {
        return verbs.size();
    }
    public int prepositionSize() {
        return prepositions.size();
    }
    public int symbolSize() {
        return symbols.size();
    }
    public int numberSize() {
        return numbers.size();
    }
    public String nounAt(int i) {
        return nouns.get(i);
    }
    public String pronounAt(int i) {
        return pronouns.get(i);
    }
    public String adjectiveAt(int i) {
        return adjectives.get(i);
    }
    public String verbAt(int i) {
        return verbs.get(i);
    }
    public String prepositionAt(int i) {
        return prepositions.get(i);
    }
    public String symbolAt(int i) {
        return symbols.get(i);
    }
    public String numberAt(int i) {
        return numbers.get(i);
    }
}

