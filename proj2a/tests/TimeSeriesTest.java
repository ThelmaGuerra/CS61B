import ngrams.TimeSeries;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.*;

import static com.google.common.truth.Truth.assertThat;

/** Unit Tests for the TimeSeries class.
 *  @author Josh Hug
 */
public class TimeSeriesTest {
    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1994, 200.0);
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);


        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1995, 500.0);
        dogPopulation.put(1994, 400.0);


        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0,
        //           1992: 100
        //           1994: 600
        //           1995: 500

        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1991, 1992, 1994, 1995));

        assertThat(totalPopulation.years()).isEqualTo(expectedYears);

        List<Double> expectedTotal = new ArrayList<>
                (Arrays.asList(0.0, 100.0, 600.0, 500.0));

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertThat(totalPopulation.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }
    }

    @Test
    public void TestYearsAndData() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 300.0);
        catPopulation.put(1994, 200.0);

        List<Integer> dummyYears = new ArrayList<>();
        dummyYears.add(1991);
        dummyYears.add(1992);
        dummyYears.add(1994);

        List<Double> dummyData = new ArrayList<>();
        dummyData.add(0.0);
        dummyData.add(300.0);
        dummyData.add(200.0);

        /**     Testing TimeSeries.years()
         */

        assertThat(catPopulation.years()).isEqualTo(dummyYears);

        /**     Testing TimeSeries.data()
         */

        assertThat(catPopulation.data()).isEqualTo(dummyData);

        /**     Testing Constructor that creates a sliced copy of different Time Series,
         *      using the keys (years).
         */

        TimeSeries fullNewTS = new TimeSeries(catPopulation, 1991, 1994);

        assertThat(fullNewTS.data()).isEqualTo(catPopulation.data());
        assertThat(fullNewTS.years()).isEqualTo(catPopulation.years());


        TimeSeries slicedNewTS = new TimeSeries(catPopulation, 1992, 1994);

        List<Integer> slicedDummyYears = new ArrayList<>();
        slicedDummyYears.add(1992);
        slicedDummyYears.add(1994);

        List<Double> slicedDummyData = new ArrayList<>();
        slicedDummyData.add(300.0);
        slicedDummyData.add(200.0);

        assertThat(slicedNewTS.data()).isEqualTo(slicedDummyData);
        assertThat(slicedNewTS.years()).isEqualTo(slicedDummyYears);
    }

    @Test
    public void TestPlus() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 300.0);
        catPopulation.put(1994, 200.0);


        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1995, 500.0);
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1991, 30.0);

        TimeSeries plusSeries = catPopulation.plus(dogPopulation);
        System.out.println(plusSeries);


        List<Integer> slicedDummyYears = new ArrayList<>();
        slicedDummyYears.add(1991);
        slicedDummyYears.add(1992);
        slicedDummyYears.add(1994);
        slicedDummyYears.add(1995);

        List<Double> slicedDummyData = new ArrayList<>();
        slicedDummyData.add(30.0);
        slicedDummyData.add(300.0);
        slicedDummyData.add(600.0);
        slicedDummyData.add(500.0);

        assertThat(plusSeries.data()).isEqualTo(slicedDummyData);
        assertThat(plusSeries.years()).isEqualTo(slicedDummyYears);
    }

    @Test
    public void TestDividedBy() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 20.0);
        catPopulation.put(1992, 300.0);
        catPopulation.put(1994, 200.0);


        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1995, 2.0);
        dogPopulation.put(1994, 2.0);
        dogPopulation.put(1991, 2.0);
        dogPopulation.put(1992, 2.0);

        TimeSeries result = catPopulation.dividedBy(dogPopulation);
        System.out.println(result);

        List<Integer> slicedDummyYears = new ArrayList<>();
        slicedDummyYears.add(1991);
        slicedDummyYears.add(1992);
        slicedDummyYears.add(1994);


        List<Double> slicedDummyData = new ArrayList<>();
        slicedDummyData.add(10.0);
        slicedDummyData.add(150.0);
        slicedDummyData.add(100.0);


        assertThat(result.data()).isEqualTo(slicedDummyData);
        assertThat(result.years()).isEqualTo(slicedDummyYears);
    }
} 