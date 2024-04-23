package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.*;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        // TODO: Fill in this constructor.
        NavigableMap<Integer, Double> ts_submap = ts.subMap(startYear, true, endYear, true);
        Set<Map.Entry<Integer,Double>> ts_SetOfMapEntries = ts_submap.entrySet();

        for(Map.Entry<Integer,Double> mapEntry : ts_SetOfMapEntries) {
            this.put(mapEntry.getKey(), mapEntry.getValue());
        }
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        // TODO: Fill in this method.
        Set<Integer> years_set = this.keySet();
        return new ArrayList<>(years_set);
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        // TODO: Fill in this method.
        Collection<Double> collection_of_values = this.values();

        return new ArrayList<>(collection_of_values);
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        // TODO: Fill in this method.

        List<Integer> my_years = this.years();
        List<Integer> ts_years = ts.years();

        Set<Integer> set_of_years = new HashSet<>();

        set_of_years.addAll(my_years);
        set_of_years.addAll(ts_years);
        System.out.println(set_of_years);

        TimeSeries result = new TimeSeries();

        if(set_of_years.isEmpty()) {
            return result;
        }

        for(Integer year : set_of_years) {
            result.put(year, Optional.ofNullable(this.get(year)).orElse(0.0) + Optional.ofNullable(ts.get(year)).orElse(0.0));
        }

        return result;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        // TODO: Fill in this method.

        List<Integer> my_years = this.years();

        TimeSeries result = new TimeSeries();

        for(int year : my_years) {
            try {
                result.put(year, this.get(year) / ts.get(year));
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }


        return result;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
