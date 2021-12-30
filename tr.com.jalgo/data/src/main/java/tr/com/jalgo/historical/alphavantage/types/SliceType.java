package tr.com.jalgo.historical.alphavantage.types;

import lombok.Getter;

/*https://www.alphavantage.co/documentation/
 * Two years of minute-level intraday data contains over 2 million data points, 
 * which can take up to Gigabytes of memory. To ensure optimal API response speed, 
 * the trailing 2 years of intraday data is evenly divided into 24 "slices"
 * Each slice is a 30-day window, with year1month1 being the most recent and year2month12 being the farthest from today.
 * By default, slice=year1month1.
 * */
public enum SliceType {
	//@formatter:off
		YEAR_1_MONTH_1("year1month1"),
		YEAR_1_MONTH_2("year1month2"),
		YEAR_1_MONTH_3("year1month3"),
		YEAR_1_MONTH_4("year1month4"),
		YEAR_1_MONTH_5("year1month5"),
		YEAR_1_MONTH_6("year1month6"),
		YEAR_1_MONTH_7("year1month7"),
		YEAR_1_MONTH_8("year1month8"),
		YEAR_1_MONTH_9("year1month9"),
		YEAR_1_MONTH_10("year1month10"),
		YEAR_1_MONTH_11("year1month11"),
		YEAR_1_MONTH_12("year1month12"),
		YEAR_2_MONTH_1("year2month1"),
		YEAR_2_MONTH_2("year2month2"),
		YEAR_2_MONTH_3("year2month3"),
		YEAR_2_MONTH_4("year2month4"),
		YEAR_2_MONTH_5("year2month5"),
		YEAR_2_MONTH_6("year2month6"),
		YEAR_2_MONTH_7("year2month7"),
		YEAR_2_MONTH_8("year2month8"),
		YEAR_2_MONTH_9("year2month9"),
		YEAR_2_MONTH_10("year2month10"),
		YEAR_2_MONTH_11("year2month11"),
		YEAR_2_MONTH_12("year2month12");
	//@formatter:on	
	@Getter
	private String value;

	SliceType(String value) {
		this.value = value;
	}
}
