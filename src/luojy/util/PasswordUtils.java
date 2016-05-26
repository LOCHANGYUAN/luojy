package luojy.util;

import java.util.ArrayList;
import java.util.List;

import edu.vt.middleware.password.AlphabeticalSequenceRule;
import edu.vt.middleware.password.CharacterCharacteristicsRule;
import edu.vt.middleware.password.DigitCharacterRule;
import edu.vt.middleware.password.LengthRule;
import edu.vt.middleware.password.LowercaseCharacterRule;
import edu.vt.middleware.password.NonAlphanumericCharacterRule;
import edu.vt.middleware.password.NumericalSequenceRule;
import edu.vt.middleware.password.Password;
import edu.vt.middleware.password.PasswordData;
import edu.vt.middleware.password.PasswordValidator;
import edu.vt.middleware.password.QwertySequenceRule;
import edu.vt.middleware.password.RepeatCharacterRegexRule;
import edu.vt.middleware.password.Rule;
import edu.vt.middleware.password.RuleResult;
import edu.vt.middleware.password.UppercaseCharacterRule;
import edu.vt.middleware.password.WhitespaceRule;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.apache.log4j.Logger;

public class PasswordUtils {
	private static Logger logger = Logger.getLogger(PasswordUtils.class);

	private static List<Rule> RULE_LIST = new ArrayList<Rule>();
	// 設定rule清單
	static {
		// password must be between 8 and 16 chars long
		RULE_LIST.add(new LengthRule(8, 16));
		// don't allow whitespace
		RULE_LIST.add(new WhitespaceRule());

		// don't allow alphabetical sequences
		RULE_LIST.add(new AlphabeticalSequenceRule());
		// don't allow numerical sequences of length 3
		RULE_LIST.add(new NumericalSequenceRule(3, true));
		// don't allow qwerty sequences
		RULE_LIST.add(new QwertySequenceRule());
		// don't allow 4 repeat characters
		RULE_LIST.add(new RepeatCharacterRegexRule(4));

		// control allowed characters
		CharacterCharacteristicsRule charRule = new CharacterCharacteristicsRule();
		RULE_LIST.add(charRule);
		// require at least 1 digit in passwords
		charRule.getRules().add(new DigitCharacterRule(1));

		// require at least 1 non-alphanumeric char
		charRule.getRules().add(new NonAlphanumericCharacterRule(1));

		// require at least 1 upper case char
		charRule.getRules().add(new UppercaseCharacterRule(1));

		// require at least 1 lower case char
		charRule.getRules().add(new LowercaseCharacterRule(1));

		// require at least 3 of the previous rules be met
		charRule.setNumberOfCharacteristics(3);
	}

	public static void main(String[] args) {
		logger.info("start");
		PasswordUtils.checkPasswordByAllRule("Mroctober");
		logger.info("end");
	}

	/**
	 * @param rule
	 * @return
	 */
	public static List<Rule> getRuleList(Rule rule) {
		List<Rule> ruleList = new ArrayList<Rule>();
		ruleList.add(rule);
		return ruleList;
	}

	/**
	 * @param passwordStr
	 * @param RULE_LIST
	 * @return
	 */
	public static List<String> checkPasswordByAllRule(String passwordStr) {
		return checkPasswordByRule(passwordStr, RULE_LIST);
	}

	/**
	 * @param passwordStr
	 * @param ruleList
	 * @return
	 */
	public static List<String> checkPasswordByRule(String passwordStr,List<Rule> ruleList) {
		List<String> resultList = new ArrayList<String>();
		for (Rule rule : ruleList) {
			resultList.addAll(checkPasswordByRule(passwordStr, rule));
		}
		logger.info(resultList);
		return resultList;
	}

	/**
	 * @param passwordStr
	 * @return
	 */
	public static List<String> checkPasswordByRule(String passwordStr, Rule rule) {
		List<Rule> ruleList = getRuleList(rule);
		PasswordValidator validator = new PasswordValidator(ruleList);
		PasswordData passwordData = new PasswordData(new Password(passwordStr));

		RuleResult result = validator.validate(passwordData);
		if(!result.isValid()){
			logger.info(passwordStr + ":" + validator.getMessages(result));
		}
		
		return validator.getMessages(result);
	}

}
